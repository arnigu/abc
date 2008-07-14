package jastadd;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import AST.BytecodeParser;
import AST.CompilationUnit;
import AST.JavaParser;
import AST.ModuleCompilationUnit;
import AST.Options;

//primitive extension archi, change to something polyglot-like if needed
public class JastAddModules extends JastAdd  {
	
	public static final String INSTANCE_MODULES_OPTION = "-instance-modules";
	public static final String DEBUG_OPTION = "-debug";
	
	public static void main(String args[]) {
		if (!new JastAddModules().compile(args)) {
			System.exit(1);
		}
	}
	
	public boolean compile(String[] args) {
		JastAdd jastAdd = this;
		boolean result = jastAdd.process(args, new BytecodeParser(),
				new JavaParser() {
					public CompilationUnit parse(java.io.InputStream is,
							String fileName) throws java.io.IOException,
							beaver.Parser.Exception {
						return new parser.JavaParser().parse(is, fileName);
					}
				});
		if (!result)
			return false;
		
		
		Collection errors = new LinkedList();
		Collection warnings = new LinkedList();
		program.initErrHandling(errors, warnings);
		try {
			//check if moduleDecls on non-ModuleCompilationUnit CUs point to a valid module
			program.checkModuleDecls();
			
			if (program.options().hasOption(DEBUG_OPTION)) {
				System.out.println("----------Module contents----------");
				program.printJAModules();
				System.out.println("----------CU AST before insert----------");
				program.printJAModuleCUAST(0);
			}
			
			//insert the ModuleCompilationUnits above the CUs that are a member
			result = program.insertModuleCUs();
			if (!result) {
				return false;
			}

			if (program.options().hasOption(DEBUG_OPTION)) {
				System.out.println("----------CU AST after insert----------");
				program.printJAModuleCUAST(0);
				System.out.println("----------Module CU imports before import own----------");
				System.out.println(program.toStringJAModuleCUImports());
			}
				
			//generate the ModuleCompilationUnits created by import own/merges
			result = program.generateImportOwn();
			if (!result) {
				return false;
			}
			
			if (program.options().hasOption(DEBUG_OPTION)) {
				System.out.println("-------------Instance ModuleCompilationUnit------------");
				System.out.println(program.getInstanceModuleCU());
				System.out.println("-----------End Instance ModuleCompilationUnit----------");
				System.out.println("----------CU AST after generateImportOwn----------");
				program.printJAModuleCUAST(0);
				System.out.println("----------Module CU imports after import own----------");
				System.out.println(program.toStringJAModuleCUImports());
			}

			//check if there are any duplicate module names (should never happen, mainly for debug)
			program.checkDuplicateModuleName();
			
		} catch (UnrecoverableSemanticError e) {
			System.out.println("Unrecoverable semantic error(s) found.");
		}
		
		program.collectModuleErrors(errors, warnings);
		for (Iterator i = errors.iterator(); i.hasNext(); ) {
			System.out.println(i.next());
		}
		for (Iterator i = warnings.iterator(); i.hasNext(); ) {
			System.out.println(i.next());
		}
		if (errors.size() > 0) {
			return false;
		}
		
		
		jastAdd.generate();
		return true;
	}

	public void generate() {
		
		program.generateIntertypeDecls();
		program.transformation();
		for (Iterator iter = program.compilationUnitIterator(); iter.hasNext();) {
			CompilationUnit cu = (CompilationUnit) iter.next();
			//System.out.println("-----outer loop cu: " + cu.relativeName());
			//if (cu.fromSource()) {System.out.println(cu);}
			//System.out.println("------------------------------");
			if (cu.fromSource()) {
				for (int i = 0; i < cu.getNumTypeDecl(); i++) {
					cu.getTypeDecl(i).generateClassfile();
				}
			}
			//TODO: bad hack, fix by refining compilationUnitIterator() in ClassPath.jrag
			if (cu instanceof ModuleCompilationUnit) {
				for (CompilationUnit childCU : ((ModuleCompilationUnit)cu).getCompilationUnitList()) {
					//System.out.println("-----inner loop cu: " + childCU.relativeName());
					//if (childCU.fromSource()) {System.out.println(childCU);}
					//System.out.println("------------------------------");
					if (childCU.fromSource()) {
						for (int i = 0; i < childCU.getNumTypeDecl(); i++) {
							childCU.getTypeDecl(i).generateClassfile();
						}
					}
				}
			}
		}
		// program.generateClassfile();
	}

	protected void initOptions() {
		super.initOptions();
		Options options = program.options();
		options.addKeyValueOption(INSTANCE_MODULES_OPTION); //specifies the module that is going to be used to instantiate the generated package names
		options.addKeyOption(DEBUG_OPTION);
	}
}
