package abc.weaving.residues;

import soot.SootMethod;
import soot.util.Chain;
import soot.jimple.Stmt;
import abc.soot.util.LocalGeneratorEx;
import abc.weaving.weaver.WeavingContext;

/** Conjunction of two residues
 *  @author Ganesh Sittampalam
 *  @date 28-Apr-04
 */ 
public class AndResidue extends AbstractResidue {
    private Residue left;
    private Residue right;

    /** Get the left operand */
    public Residue getLeftOp() {
	return left;
    }

    /** Get the right operand */
    public Residue getRightOp() {
	return right;
    }

    public String toString() {
	return "("+left+") && ("+right+")";
    }

    public Stmt codeGen(SootMethod method,LocalGeneratorEx localgen,
			Chain units,Stmt begin,Stmt fail,WeavingContext wc) {

	Stmt middle=left.codeGen(method,localgen,units,begin,fail,wc);
	return right.codeGen(method,localgen,units,middle,fail,wc);
    }

    /** Private constructor to force use of smart constructor */
    private AndResidue(Residue left,Residue right) {
	this.left=left;
	this.right=right;
    }

    /** Smart constructor; some short-circuiting may need to be removed
     *  to mimic ajc behaviour
     */
    public static Residue construct(Residue left,Residue right) {
	if(NeverMatch.neverMatches(left) || right instanceof AlwaysMatch) 
	    return left;
	if(left instanceof AlwaysMatch || NeverMatch.neverMatches(right))
	    return right;
	return new AndResidue(left,right);
    }

}
