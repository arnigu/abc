package abc.weaving.aspectinfo;

import java.util.*;
import polyglot.util.Position;
import soot.*;
import abc.weaving.matching.*;
import abc.weaving.residues.*;

/** Handler for <code>if</code> condition pointcut. */
public class If extends Pointcut {
    private List/*<Var>*/ vars;
    private MethodSig impl;

    int jp,jpsp,ejp;

    public If(List vars, MethodSig impl, int jp, int jpsp, int ejp, Position pos) {
	super(pos);
	this.vars = vars;
	this.impl = impl;
	
	this.jp = jp;
	this.jpsp = jpsp;
	this.ejp = ejp;
    }

    public boolean hasJoinPoint() {
	return jp != -1;
    }

    public boolean hasJoinPointStaticPart() {
	return jpsp != -1;
    }

    public boolean hasEnclosingJoinPoint() {
	return ejp != -1;
    }

    public int joinPointPos() {
	return jp;
    }

    public int joinPointStaticPartPos() {
	return jpsp;
    }

    public int enclosingJoinPointPos() {
	return ejp;
    }


    /** Get the pointcut variables that should be given as arguments to
     *  the method implementing the <code>if</code> condition.
     *  @return a list of {@link abc.weaving.aspectinfo.Var} objects.
     */
    public List getVars() {
	return vars;
    }

    /** Get the signature of the method implementing
     *  the <code>if</code> condition.
     */
    public MethodSig getImpl() {
	return impl;
    }

    public String toString() {
	return "if(...)";
    }

    public Residue matchesAt(WeavingEnv we,SootClass cls,SootMethod method,ShadowMatch sm) {
	Residue ret=AlwaysMatch.v;

	List/*<WeavingVar>*/ args=new LinkedList();
	Iterator it=vars.iterator();
	int i=0;
	while(it.hasNext()) {
	    WeavingVar wvar;
	    Var var=(Var) it.next();

	    if(i==joinPointStaticPartPos()) {
		wvar=new LocalVar(RefType.v("org.aspectj.lang.JoinPoint$StaticPart"),
				 "thisJoinPointStaticPart");
		ret=AndResidue.construct
		    (ret,new Load(new StaticJoinPointInfo(sm.getSJPInfo()),wvar));
	    } else if(i==enclosingJoinPointPos()) {
		wvar=new LocalVar(RefType.v("org.aspectj.lang.JoinPoint$StaticPart"),
				 "thisEnclosingJoinPointStaticPart");
		ret=AndResidue.construct
		    (ret,new Load(new StaticJoinPointInfo(sm.getEnclosing().getSJPInfo()),wvar));
	    } else if(i==joinPointPos()) {
		wvar=new LocalVar(RefType.v("org.aspectj.lang.JoinPoint"),
				 "thisJoinPoint");
		ret=AndResidue.construct
		    (ret,new Load(new JoinPointInfo(sm),wvar));

		// make sure the SJP info will be around later for 
		// the JoinPointInfo residue
		sm.recordSJPInfo(); 
	    } else wvar=we.getWeavingVar(var);

	    args.add(wvar);
	    i++;
	}
	ret=AndResidue.construct(ret,IfResidue.construct(impl.getSootMethod(),args));
	return ret;
    }

    protected Pointcut inline(Hashtable renameEnv,
			      Hashtable typeEnv,
			      Aspect context) {
	Iterator it=vars.iterator();
	List newvars=new LinkedList();
	while(it.hasNext())
	    newvars.add(((Var) it.next()).rename(renameEnv));
	return new If(newvars,impl,jp,jpsp,ejp,getPosition());
    }

    public void registerSetupAdvice(Aspect context,Hashtable typeMap) {}
    public void getFreeVars(Set/*<String>*/ result) {
	// just want binding occurrences, so do nothing
    }

}
