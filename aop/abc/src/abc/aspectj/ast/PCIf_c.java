package abc.aspectj.ast;

import polyglot.ast.*;

import polyglot.types.*;
import polyglot.util.*;
import polyglot.visit.*;
import java.util.*;

import abc.aspectj.types.AspectJTypeSystem;
import abc.aspectj.visit.AspectInfoHarvester;

import abc.weaving.aspectinfo.MethodCategory;

public class PCIf_c extends Pointcut_c implements PCIf
{
    protected Expr expr;
    private String methodName;
    private MethodDecl methodDecl;

    public PCIf_c(Position pos, Expr expr)  {
	super(pos);
        this.expr = expr;
    }

    public Precedence precedence() {
		return Precedence.LITERAL;
    }

    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
		w.write("if(");
        print(expr, w, tr);
        w.write(")");
    }
    
	/** Reconstruct the pointcut. */
	protected PCIf_c reconstruct(Expr expr) {
	   if (expr != this.expr) {
		   PCIf_c n = (PCIf_c) copy();
		   n.expr = expr;
		   return n;
	   }

	   return this;
	}

	/** Visit the children of the pointcut. */
	public Node visitChildren(NodeVisitor v) {
	   Expr expr = (Expr) visitChild(this.expr, v);
	   return reconstruct(expr);
	}

    
	/** Type check the pointcut. */
	public Node typeCheck(TypeChecker tc) throws SemanticException {
		TypeSystem ts = tc.typeSystem();

		if (! ts.equals(expr.type(), ts.Boolean())) {
			throw new SemanticException(
			"Condition of if pointcut must have boolean type.",
			expr.position());
		}
		
		return this;
	}

	public Type childExpectedType(Expr child, AscriptionVisitor av) {
		TypeSystem ts = av.typeSystem();

		if (child == expr) {
			return ts.Boolean();
		}

		return child.type();
	}

	public MethodDecl exprMethod(AspectJNodeFactory nf, AspectJTypeSystem ts, List formals, ParsedClassType container){
		Return ret = nf.Return(position(),expr);
		Block bl = nf.Block(position()).append(ret);
		TypeNode retType = nf.CanonicalTypeNode(position(),ts.Boolean());
		List args = new LinkedList(formals);
		List throwTypes = new LinkedList();
		for (Iterator i = expr.throwTypes(ts).iterator(); i.hasNext(); ) {
			Type t = (Type) i.next();
			TypeNode tn = nf.CanonicalTypeNode(position(),t);
			throwTypes.add(tn);
		}
		List formaltypes = new ArrayList();
		Iterator fi = formals.iterator();
		while (fi.hasNext()) {
		    Formal f = (Formal)fi.next();
		    formaltypes.add(f.type().type());
		}
		methodName = UniqueID.newID("if");
		MethodDecl md = nf.MethodDecl(position(),Flags.STATIC.Private(),retType,methodName,args,throwTypes,bl);
		MethodInstance mi = ts.methodInstance(position, container,
						      Flags.STATIC.Private(), retType.type(), methodName,
						      new ArrayList(formaltypes),
						      new ArrayList(expr.throwTypes(ts)));
		container.addMethod(mi);
		md = md.methodInstance(mi);
		methodDecl = md;
		return md;
	}
	
	public PCIf liftMethod(AspectJNodeFactory nf){
		Expr exp = nf.Call(position(),methodName);
		return reconstruct(exp);
	}

    public abc.weaving.aspectinfo.Pointcut makeAIPointcut() {
	MethodCategory.register(methodDecl, MethodCategory.IF_EXPR);

	List vars = new ArrayList();
	Iterator fi = methodDecl.formals().iterator();
	while (fi.hasNext()) {
	    Formal f = (Formal)fi.next();
	    vars.add(new abc.weaving.aspectinfo.Var(f.name(), f.position()));
	}
	return new abc.weaving.aspectinfo.If
	    (vars, AspectInfoHarvester.makeMethodSig(methodDecl),position);
    }

}
