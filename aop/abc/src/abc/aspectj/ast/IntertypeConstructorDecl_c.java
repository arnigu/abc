package abc.aspectj.ast;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import polyglot.ast.Node;
import polyglot.ast.Block;
import polyglot.ast.TypeNode;
import polyglot.ast.Formal;
import polyglot.ast.Node;
import polyglot.util.CodeWriter;
import polyglot.util.UniqueID;
import polyglot.util.Position;

import polyglot.visit.*;
import polyglot.types.*;

import polyglot.ext.jl.ast.ConstructorDecl_c;

import abc.aspectj.visit.*;

public class IntertypeConstructorDecl_c extends ConstructorDecl_c
    implements IntertypeConstructorDecl, ContainsAspectInfo
{
    protected TypeNode host;

    public IntertypeConstructorDecl_c(Position pos,
                                 Flags flags,
                                 TypeNode host,
				 String name,
                                 List formals,
                                 List throwTypes,
	  	                 Block body) {
	super(pos,flags,name,formals,throwTypes,body);
	this.host = host;
    }

    protected IntertypeConstructorDecl_c reconstruct(List formals, 
						     List throwTypes, 
						     Block body,
						     TypeNode host) {
	if(host != this.host) {
	    IntertypeConstructorDecl_c n 
		= (IntertypeConstructorDecl_c) copy();
	    n.host = host;
	    return (IntertypeConstructorDecl_c) 
		n.reconstruct(formals,throwTypes,body);
	}
	return (IntertypeConstructorDecl_c) 
	    super.reconstruct(formals,throwTypes,body);
    }

    public Node visitChildren(NodeVisitor v) {
        List formals = visitList(this.formals, v);
        List throwTypes = visitList(this.throwTypes, v);
        Block body = (Block) visitChild(this.body, v);
	TypeNode host=(TypeNode) visitChild(this.host,v);
	return reconstruct(formals,throwTypes,body,host);
    }

    public NodeVisitor addMembersEnter(AddMemberVisitor am) {
	Type ht = host.type();
	if (ht instanceof ParsedClassType) {
	    ((ParsedClassType)ht).addConstructor(constructorInstance());
	}
        return am.bypassChildren(this);
    }

    /** Duplicate most of the things for ConstructorDecl here to avoid comparing
     *  the name against the contaning class.
     */
    public Node typeCheck(TypeChecker tc) throws SemanticException {
        Context c = tc.context();
        TypeSystem ts = tc.typeSystem();

        ClassType ct = c.currentClass();

	if (ct.flags().isInterface()) {
	    throw new SemanticException(
		"Cannot declare an intertype constructor inside an interface.",
		position());
	}

        if (ct.isAnonymous()) {
	    throw new SemanticException(
		"Cannot declare an intertype constructor inside an anonymous class.",
		position());
        }

	/*
        String ctName = ct.name();

        if (! ctName.equals(name)) {
	    throw new SemanticException("Constructor name \"" + name +
                "\" does not match name of containing class \"" +
                ctName + "\".", position());
        }
	*/

	try {
	    ts.checkConstructorFlags(flags());
	}
	catch (SemanticException e) {
	    throw new SemanticException(e.getMessage(), position());
	}

	if (body == null && ! flags().isNative()) {
	    throw new SemanticException("Missing constructor body.",
		position());
	}

	if (body != null && flags().isNative()) {
	    throw new SemanticException(
		"A native constructor cannot have a body.", position());
	}

        for (Iterator i = throwTypes().iterator(); i.hasNext(); ) {
            TypeNode tn = (TypeNode) i.next();
            Type t = tn.type();
            if (! t.isThrowable()) {
                throw new SemanticException("Type \"" + t +
                    "\" is not a subclass of \"" + ts.Throwable() + "\".",
                    tn.position());
            }
        }

        return this;
    }

    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
	w.begin(0);
	w.write(flags.translate());
        print(host,w,tr);
        w.write(".new("); 

        w.begin(0);

	for (Iterator i = formals.iterator(); i.hasNext(); ) {
	    Formal f = (Formal) i.next();
	    print(f, w, tr);

	    if (i.hasNext()) {
		w.write(",");
		w.allowBreak(0, " ");
	    }
	}

	w.end();

	w.write(")");

	w.begin(0);

        if (! throwTypes().isEmpty()) {
	    w.allowBreak(6);
	    w.write("throws ");

	    for (Iterator i = throwTypes().iterator(); i.hasNext(); ) {
	        TypeNode tn = (TypeNode) i.next();
		print(tn, w, tr);

		if (i.hasNext()) {
		    w.write(",");
		    w.allowBreak(4, " ");
		}
	    }
	}

	w.end();

	if (body != null) {
	    printSubStmt(body, w, tr);
	}
	else {
	    w.write(";");
	}

	w.end();

    }

    public void update(abc.weaving.aspectinfo.GlobalAspectInfo gai, abc.weaving.aspectinfo.Aspect current_aspect) {
	System.out.println("ICD host: "+host.toString());
	List params = new ArrayList();
	Iterator fi = formals().iterator();
	while (fi.hasNext()) {
	    Formal f = (Formal)fi.next();
	    params.add(AspectInfoHarvester.toAbcType(f.type().type()));
	}
	List exc = new ArrayList();
	Iterator ti = throwTypes().iterator();
	while (ti.hasNext()) {
	    TypeNode t = (TypeNode)ti.next();
	    exc.add(t.type().toString());
	}
	abc.weaving.aspectinfo.MethodSig impl = new abc.weaving.aspectinfo.MethodSig
	    (AspectInfoHarvester.convertModifiers(flags()),
	     current_aspect.getInstanceClass(),
	     new abc.weaving.aspectinfo.AbcType(soot.VoidType.v()),
	     name(),
	     params,
	     exc,
	     position());
	abc.weaving.aspectinfo.MethodSig target = new abc.weaving.aspectinfo.MethodSig
	    (AspectInfoHarvester.convertModifiers(flags()),
	     gai.getClass(host.toString()),
	     new abc.weaving.aspectinfo.AbcType(soot.VoidType.v()),
	     name(),
	     params,
	     exc,
	     null);
	abc.weaving.aspectinfo.IntertypeConstructorDecl icd = new abc.weaving.aspectinfo.IntertypeConstructorDecl
	    (target, impl, current_aspect, position());
	gai.addIntertypeConstructorDecl(icd);
    }
}
	

	

     


