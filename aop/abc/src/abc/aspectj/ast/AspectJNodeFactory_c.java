package abc.aspectj.ast;

import polyglot.ast.*;
import polyglot.ext.jl.ast.*;
import polyglot.types.Flags;
import polyglot.types.Package;
import polyglot.types.Type;
import polyglot.types.Qualifier;
import polyglot.types.SemanticException;
import polyglot.util.*;
import java.util.*;
import abc.aspectj.ast.*;
import polyglot.ext.jl.parse.Name;

/**
 * NodeFactory for aspectj extension.
 */
public class AspectJNodeFactory_c 
       extends soot.javaToJimple.jj.ast.JjNodeFactory_c 
       implements AspectJNodeFactory {

    public ClassnamePatternExpr constructClassnamePatternFromTypePattern(TypePatternExpr tpe) throws SemanticException {
	return tpe.transformToClassnamePattern(this);
    }


    public  AspectDecl AspectDecl(Position pos,
                                  boolean privileged,
                                  Flags flags,
                                  String name,
                                  TypeNode superClass,
                                  List interfaces,
                                  PerClause per,
                                  AspectBody body) {
	AspectDecl n = new AspectDecl_c(pos,privileged, flags, name, 
                                        superClass, interfaces, per, body);
        return n;
    }

    public AspectBody AspectBody(Position pos, List members) {
        AspectBody n = new AspectBody_c(pos,members);
        return n;
    }

    public PerTarget PerTarget(Position pos, Pointcut pc) {
        PerTarget n = new PerTarget_c(pos,pc);
        return n;
    }

    public PerThis PerThis(Position pos, Pointcut pc) {
	PerThis n = new PerThis_c(pos,pc);
	return n;
    }
 
    public PerCflow PerCflow(Position pos, Pointcut pc) {
	PerCflow n = new PerCflow_c(pos,pc);
	return n;
    }

    public PerCflowBelow PerCflowBelow(Position pos, Pointcut pc) {
	PerCflowBelow n = new PerCflowBelow_c(pos,pc);
	return n;
    }

    public IsSingleton IsSingleton(Position pos) {
        IsSingleton n = new IsSingleton_c(pos);
        return n;
    }

    public DeclareParentsExt DeclareParentsExt(Position pos,
                                               ClassnamePatternExpr pat,
                                               TypeNode type) {
        DeclareParentsExt n = new DeclareParentsExt_c(pos,pat,type);
        return n;
    }

    public DeclareParentsImpl DeclareParentsImpl(Position pos,
                                                 ClassnamePatternExpr pat,
                                                 List interfaces) {
        DeclareParentsImpl n = new DeclareParentsImpl_c(pos,pat,interfaces);
        return n;
    }

    public DeclareWarning DeclareWarning(Position pos,
                                         Pointcut pc,
                                         String text) {
        DeclareWarning n = new DeclareWarning_c(pos,pc,text);
        return n;
    }

    public DeclareError DeclareError(Position pos,
                                     Pointcut pc,
                                     String text) {
        DeclareError n = new DeclareError_c(pos,pc,text);
        return n;
    }

    public DeclareSoft DeclareSoft(Position pos,
                                   TypeNode type,
                                   Pointcut pc) {
	DeclareSoft n = new DeclareSoft_c(pos,type,pc);
        return n;
    }

    public DeclarePrecedence DeclarePrecedence(Position pos,
                                               List pats) {
	DeclarePrecedence n = new DeclarePrecedence_c(pos,pats);
        return n;
    }

    public PointcutDecl PointcutDecl(Position pos,
                                     Flags flags,
                                     String name,
                                     List formals,
                                     Pointcut pc,
                                     TypeNode voidn ) {
        PointcutDecl n = new PointcutDecl_c(pos,flags,name,formals,pc,voidn);
        return n;
    }

    public AdviceDecl AdviceDecl(Position pos,
				 Flags flags,
				 AdviceSpec spec,
				 List throwTypes,
				 Pointcut pc,
				 Block body) {
	AdviceDecl n = new AdviceDecl_c(pos,flags,spec,throwTypes,pc,body);
	return n;
    }

    public Before Before(Position pos,
			 List formals,
			 TypeNode voidn) {
	Before n = new Before_c(pos,formals,voidn);
	return n;
    }

    public After After(Position pos,
		       List formals,
		       TypeNode voidn) {
	After n = new After_c(pos,formals,voidn);
	return n;
    }

    public AfterReturning AfterReturning(Position pos,
					 List formals,
					 Formal returnResult,
					 TypeNode voidn) {
	AfterReturning n = new AfterReturning_c(pos,formals,returnResult,voidn);
	return n;
    }

     public AfterThrowing AfterThrowing(Position pos,
					List formals,
					Formal exc,
					TypeNode voidn) {
		AfterThrowing n = new AfterThrowing_c(pos,formals,exc,voidn);
		return n;
     }

    public Around Around(Position pos,
			 TypeNode returnType,
			 List formals) {
	Around n = new Around_c(pos,returnType,formals);
	return n;
    }

    public IntertypeMethodDecl
           IntertypeMethodDecl(Position pos,
                               Flags flags,
                               TypeNode returnType,
                               TypeNode host,
                               String name,
                               List formals,
                               List throwTypes,
	  	               Block body) {
	IntertypeMethodDecl n = new IntertypeMethodDecl_c(pos,flags,returnType,
							  host,name,formals,
							  throwTypes,body);
	return n;
    }

    public IntertypeConstructorDecl
           IntertypeConstructorDecl(Position pos,
                               Flags flags,
                               TypeNode host,
                               String name,
                               List formals,
                               List throwTypes,
	  	               Block body) {
	IntertypeConstructorDecl n 
                         = new IntertypeConstructorDecl_c(pos,flags,
							  host,name,formals,
							  throwTypes,body);
	return n;
    }

    public IntertypeFieldDecl
	   IntertypeFieldDecl(Position pos,
		   	      Flags flags,
			      TypeNode type,
			      TypeNode host,
			      String name,
			      Expr init) {
	IntertypeFieldDecl n
	    = new IntertypeFieldDecl_c(pos,flags,type,host,name,init);
	return n;
    }

    public PCBinary PCBinary(Position pos,
			     Pointcut left,
			     PCBinary.Operator op,
			     Pointcut right) {
	PCBinary n = new PCBinary_c(pos,left,op,right);
	return n;
    }

    public PCNot PCNot(Position pos, Pointcut pc) {
	PCNot n = new PCNot_c(pos,pc);
        return n;
    }


    public PCCall PCCall(Position pos, MethodConstructorPattern pat) {
	return new PCCall_c(pos,pat);
    }

    public PCExecution PCExecution(Position pos, MethodConstructorPattern pat) {
	return new PCExecution_c(pos,pat);
    }


    public PCWithinCode PCWithinCode(Position pos,
				     MethodConstructorPattern pat) {
	return new PCWithinCode_c(pos,pat);
    }


    public PCInitialization PCInitialization(Position pos,
					     ConstructorPattern pat) {
	return new PCInitialization_c(pos,pat);
    }

    
    public PCPreinitialization PCPreinitialization(Position pos,
					     ConstructorPattern pat) {
	return new PCPreinitialization_c(pos,pat);
    }

    public PCGet PCGet(Position pos, FieldPattern pat) {
	return new PCGet_c(pos,pat);
    }

    public PCSet PCSet(Position pos, FieldPattern pat) {
	return new PCSet_c(pos,pat);
    }

    public PCHandler PCHandler(Position pos, ClassnamePatternExpr pat) {
	return new PCHandler_c(pos,pat);
    }

    public PCStaticInitialization 
	PCStaticInitialization(Position pos,
			       ClassnamePatternExpr pat) {
	return new PCStaticInitialization_c(pos,pat);
    }

    public PCWithin PCWithin(Position pos, ClassnamePatternExpr pat) {
	return new PCWithin_c(pos,pat);
    }

    public PCThis PCThis(Position pos, ArgPattern pat) {
	return new PCThis_c(pos,pat);
    }

    public PCTarget PCTarget(Position pos, ArgPattern pat) {
	return new PCTarget_c(pos,pat);
    }

    public PCArgs PCArgs(Position pos, List pats) {
	return new PCArgs_c(pos,pats);
    }

    public PCAdviceExecution PCAdviceExecution(Position pos) {
	return new PCAdviceExecution_c(pos);
    }

    public PCCflow PCCflow(Position pos, Pointcut pc) {
	return new PCCflow_c(pos,pc);
    }

    public PCCflowBelow PCCflowBelow(Position pos, Pointcut pc) {
	return new PCCflowBelow_c(pos,pc);
    }

    public PCIf PCIf(Position pos, Expr expr) {
	return new PCIf_c(pos,expr);
    }

    public PCName PCName(Position pos, Receiver target, String name, List args) {
	return new PCName_c(pos,target,name,args);
    }

    public SimpleNamePattern SimpleNamePattern(Position pos, String pat) {
	return new SimpleNamePattern_c(pos,pat);
    }

    public DotNamePattern DotNamePattern(Position pos, NamePattern init, SimpleNamePattern last) {
	return new DotNamePattern_c(pos,init,last);
    }

    public DotDotNamePattern DotDotNamePattern(Position pos,
					       NamePattern init) {
	return new DotDotNamePattern_c(pos,init);
    }

    public CPEUniversal CPEUniversal(Position pos) {
	return new CPEUniversal_c(pos);
    }

    public CPEBinary CPEBinary(Position pos,
			       ClassnamePatternExpr left,
			       CPEBinary.Operator op,
			       ClassnamePatternExpr right) {
	return new CPEBinary_c(pos,left,op,right);
    }

    public CPENot CPENot(Position pos,
			 ClassnamePatternExpr cpe) {
	return new CPENot_c(pos,cpe);
    }

    public CPEName CPEName(Position pos, NamePattern pat) {
	return new CPEName_c(pos,pat);
    }

    public CPESubName CPESubName(Position pos, NamePattern pat) {
	return new CPESubName_c(pos,pat);
    }

    public TPEUniversal TPEUniversal(Position pos) {
	return new TPEUniversal_c(pos);
    }

    public TPEBinary TPEBinary(Position pos,
			       TypePatternExpr left,
			       TPEBinary.Operator op,
			       TypePatternExpr right) {
	return new TPEBinary_c(pos,left,op,right);
    }

    public TPENot TPENot(Position pos,
			 TypePatternExpr expr) {
	return new TPENot_c(pos,expr);
    }

    public TPEType TPEType(Position pos, TypeNode type) {
	return new TPEType_c(pos,type);
    }

    public TPEArray TPEArray(Position pos, TypePatternExpr base, int dims) {
	return new TPEArray_c(pos,base,dims);
    }

    public TPERefTypePat TPERefTypePat(Position pos, RefTypePattern pat) {
	return new TPERefTypePat_c(pos,pat);
    }

    public RTPName RTPName(Position pos, NamePattern pat) {
	return new RTPName_c(pos,pat);
    }

    public RTPSubName RTPSubName(Position pos, NamePattern pat){
	return new RTPSubName_c(pos,pat);
    }

    public MethodPattern MethodPattern(Position pos,
				       List modifiers,
				       TypePatternExpr type,
				       ClassTypeDotId name,
				       List formals,
				       List throwspats) {
	return new MethodPattern_c(pos,modifiers,type,name,formals,throwspats);
    }

    public ConstructorPattern ConstructorPattern(Position pos,
						 List modifiers,
						 ClassTypeDotNew name,
						 List formals,
						 List throwspats) {
	return new ConstructorPattern_c(pos,
					modifiers,
					name,
					formals,
					throwspats);
    }

    public FieldPattern FieldPattern(Position pos,
				     List modifiers,
				     TypePatternExpr type,
				     ClassTypeDotId name) {
	return new FieldPattern_c(pos, modifiers, type, name);
    }

    public ModifierPattern ModifierPattern(Position pos,
		  		           Flags modifier,
				           boolean positive) {
	return new ModifierPattern_c(pos,modifier,positive);
    }

    public ClassTypeDotId ClassTypeDotId(Position pos, 
					 ClassnamePatternExpr base,
					 SimpleNamePattern name) {
	return new ClassTypeDotId_c(pos,base,name);
    }

    public ClassTypeDotNew ClassTypeDotNew(Position pos,
					   ClassnamePatternExpr base) {
	return new ClassTypeDotNew_c(pos,base);
    }

    public DotDotFormalPattern DotDotFormalPattern(Position pos) {
	return new DotDotFormalPattern_c(pos);
    }

    public TypeFormalPattern TypeFormalPattern(Position pos,
					       TypePatternExpr expr) {
	return new TypeFormalPattern_c(pos,expr);
    }

    public ThrowsPattern ThrowsPattern(Position pos,
				       ClassnamePatternExpr type,
				       boolean positive) {
	return new ThrowsPattern_c(pos, type, positive);
    }

    public AmbExpr AmbExpr(Position pos, String name) {
	return new PPAmbExpr_c(pos,name);
    }

    public Field Field(Position pos, Receiver target, String name) {
	return new PPField_c(pos,target,name);
    }

    public CharLit CharLit(Position pos, char value) {
        return new FixCharLit_c(pos,value);
    }

    public ProceedCall ProceedCall(Position pos, Receiver recv, List arguments) {
	return new ProceedCall_c(pos,recv,arguments);
    }
	
    public AmbTypeOrLocal AmbTypeOrLocal(Position pos,TypeNode type) {
	return new AmbTypeOrLocal_c(pos,type);
    }

    public ArgStar ArgStar(Position pos) {
	return new ArgStar_c(pos);
    }

    public ArgDotDot ArgDotDot(Position pos) {
	return new ArgDotDot_c(pos);
    }
    
    public Special Special(Position pos, Special.Kind kind, TypeNode outer) {
		Special n = new AJSpecial_c(pos, kind, outer);
		return n;
	}
	     
	public Special hostSpecial(Position pos, Special.Kind kind, TypeNode outer) {
		Special n = new HostSpecial_c(pos, kind, outer);
		return n;
	}
	
	public ConstructorCall ConstructorCall(Position pos, ConstructorCall.Kind kind, Expr qualifier, List arguments) {
		ConstructorCall cc = new AJConstructorCall_c(pos,kind,qualifier,arguments);
		return cc;
	}
	
	public ConstructorCall hostConstructorCall(Position pos, ConstructorCall.Kind kind, Expr qualifier, List arguments) {
		ConstructorCall cc = new HostConstructorCall_c(pos,kind,qualifier,arguments);
		return cc;
	}
	
	public Call Call(Position pos, Receiver target, String name,List arguments) {
		Call c = new AJCall_c(pos,target,name,arguments);
		return c;
	}
	
	public Disamb disamb() {
		   return new AJDisamb_c();
	}
	
	public New New(Position pos,Expr qualifier, TypeNode tn, List arguments, ClassBody body) {
		return new AJNew_c(pos,qualifier,tn,arguments,body);
	}
	
	public ClassBody ClassBody(Position pos, List members) {
		return new AJClassBody_c(pos, members);
    }
    
    /*
	public ConstructorDecl ConstructorDecl(Position pos, Flags flags, String name, 
											List formals, List throwTypes, Block body) {
		ConstructorDecl cd = new AJConstructorDecl_c(pos,flags,name,formals,throwTypes,body);
		return cd;
	} */
	
	
}









