package abc.weaving.aspectinfo;

import java.util.*;

import polyglot.util.Position;
import abc.weaving.matching.WeavingEnv;
import abc.weaving.residues.*;

/** Handler for <code>this</code> condition pointcut with a variable argument. */
public class ThisVar extends ThisAny {
    private Var var;

    public ThisVar(Var var,Position pos) {
	super(pos);
	this.var = var;
    }

    /** Get the pointcut variable that is bound by this
     *  <code>this</code> pointcut.
     */
    public Var getVar() {
	return var;
    }

    public String toString() {
	return "this("+var+")";
    }

    protected Residue matchesAt(WeavingEnv we,ContextValue cv) {
	return Bind.construct
	    (cv,we.getAbcType(var).getSootType(),we.getWeavingVar(var));
    }

    protected Pointcut inline(Hashtable renameEnv,
			      Hashtable typeEnv,
			      Aspect context) {
	Var var=this.var.rename(renameEnv);

	if(var==this.var) return this;
	else return new ThisVar(var,getPosition());
    }
    public void getFreeVars(Set/*<String>*/ result) {
	result.add(var.getName());
    }

    public boolean equivalent(Pointcut otherpc) {
	if (otherpc instanceof ThisVar) {
	    Var othervar = ((ThisVar)otherpc).getVar();
	    return (othervar.equals(var));
	} else return false;
    }
	/* (non-Javadoc)
	 * @see abc.weaving.aspectinfo.Pointcut#equivalent(abc.weaving.aspectinfo.Pointcut, java.util.Hashtable)
	 */
	public boolean equivalent(Pointcut otherpc, Hashtable renaming) {
		if (otherpc instanceof ThisVar) {
			Var othervar = ((ThisVar)otherpc).getVar();
			return (var.canRenameTo(othervar, renaming));
		} else return false;
	}

}
