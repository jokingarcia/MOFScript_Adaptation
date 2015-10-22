package onekin.runmofscript;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.mofscript.MOFScriptModel.MOFScriptSpecification;
import org.eclipse.mofscript.parser.MofScriptParseError;
import org.eclipse.mofscript.parser.ParserUtil;
import org.eclipse.mofscript.runtime.ExecutionManager;
import org.eclipse.mofscript.runtime.ExecutionMessageListener;
import org.eclipse.mofscript.runtime.MofScriptExecutionException;

public class RunMofScript implements ExecutionMessageListener {

	private ParserUtil parserUtil;
	private ExecutionManager execMgr;
	private String inputModel;
	private String diffModel;
	private String schemaModel;
	private String transformation;
	private String curDir;

	/**
	 * Constructor
	 */
	public RunMofScript() {
		parserUtil = null;
		execMgr = null;
		inputModel = null;
		transformation = null;
		curDir = System.getProperty("user.dir");
		parserUtil = new ParserUtil();
		execMgr = ExecutionManager.getExecutionManager();
		diffModel = null;
		schemaModel = null;
	}

	public String getDiffModel() {
		return diffModel;
	}

	public void setDiffModel(String diffModel) {
		this.diffModel = diffModel;
	}

	public String getSchemaModel() {
		return schemaModel;
	}

	public void setSchemaModel(String schemaModel) {
		this.schemaModel = schemaModel;
	}

	/**
	 * Parses a transformation
	 * 
	 * @return numebr of parse errors
	 */
	protected int parse(String transformation) {
		File f = new File(transformation);
		
		MOFScriptSpecification spec = parserUtil.parse(f, true);
		int errorCount = ParserUtil.getModelChecker().getErrorCount();
		// check for errors:
		Iterator errorIt = ParserUtil.getModelChecker().getErrors(); // Iterator of MofScriptParseError objects
																		
		System.out.println("Parsing result: " + errorCount + " errors");
		if (errorCount > 0) {

			for (; errorIt.hasNext();) {
				MofScriptParseError parseError = (MofScriptParseError) errorIt
						.next();
				System.out.println("\t \t: Error: " + parseError.toString());
			}
		}
		return errorCount;
	}

	/**
	 * Executes the transformation
	 * 
	 * @param inputModel
	 *            - the name (path) of the inputmodel
	 */
	protected void execute(String inputModel, String pDiffModel, String pSchemaModel) {
		System.out.println(inputModel);
		System.out.println(diffModel);
		System.out.println(schemaModel);
		XMIResourceFactoryImpl _xmiFac = null;
	
		//inputModel
		_xmiFac = new XMIResourceFactoryImpl();
		EObject sourceModel = null;
		File sourceModelFile = new File(inputModel);
		ResourceSet rSet = new ResourceSetImpl();
		rSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
			.put("*", _xmiFac);
		URI uri = URI.createFileURI(sourceModelFile.getAbsolutePath());
		System.out.println("Source model path: "+sourceModelFile.getAbsolutePath());
		Resource resource = rSet.getResource(uri, true);
		if (resource != null) {
			if (resource.getContents().size() > 0) {
				sourceModel = (EObject) resource.getContents().get(0);
			}
		}
		//diffModel
		_xmiFac = new XMIResourceFactoryImpl();
		EObject diffModel = null;
		File diffModelFile = new File(pDiffModel);
		ResourceSet rSet2 = new ResourceSetImpl();
		rSet2.getResourceFactoryRegistry().getExtensionToFactoryMap()
			.put("*", _xmiFac);
		URI uri2 = URI.createFileURI(diffModelFile.getAbsolutePath());
		System.out.println("Diff model path: "+diffModelFile.getAbsolutePath());
		Resource resource2 = rSet2.getResource(uri2, true);
		if (resource2 != null) {
			if (resource2.getContents().size() > 0) {
				diffModel = (EObject) resource2.getContents().get(0);
			}
		}
		//schemaModel
		_xmiFac = new XMIResourceFactoryImpl();
		EObject schemaModel = null;
		File schemaModelFile = new File(pSchemaModel);
		ResourceSet rSet3 = new ResourceSetImpl();
		rSet3.getResourceFactoryRegistry().getExtensionToFactoryMap()
			.put("*", _xmiFac);
		URI uri3 = URI.createFileURI(schemaModelFile.getAbsolutePath());
		System.out.println("Schema model path: "+schemaModelFile.getAbsolutePath());
		Resource resource3 = rSet3.getResource(uri3, true);
		if (resource3 != null) {
			if (resource3.getContents().size() > 0) {
				schemaModel = (EObject) resource3.getContents().get(0);
			}
		}
		
		
		// set the source model for the execution manager
		execMgr.addSourceModel(sourceModel);
		execMgr.addSourceModel(diffModel);
		execMgr.addSourceModel(schemaModel);
		
		// sets the root output directory, if any is desired (e.g. "c:/temp")
		//execMgr.setRootDirectory("..");
		execMgr.setRootDirectory("D:\\Dropbox\\workspace_dropbox\\WikiWhirlEvolution\\models");
		//execMgr.setRootDirectory("C:\\Users\\Jokin\\Desktop\\Example\\models");
		// if true, files are not generated to the file system, but populated into a filemodel
		// which can be fetched afterwards. Value false will result in standard
		// file generation
		execMgr.setUseFileModel(false);
		// Turns on/off system logging
		execMgr.setUseLog(false);
		// Adds an output listener for the transformation execution.
		execMgr.getExecutionStack().addOutputMessageListener(this);
		
		try {
			execMgr.executeTransformation();
			// execMgr.getOutputModels();
		} catch (MofScriptExecutionException mex) {
			mex.printStackTrace();
		}

	}

	/**
	 * Parsing and executing
	 */
	public void parseAndExecute() {
		// register Form metamodel
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
				"ecore", new EcoreResourceFactoryImpl());

		// Read source metamodel
		ResourceSet rsSource = new ResourceSetImpl();
		ResourceSet rsSource2 = new ResourceSetImpl();
		//MODIFIED
		//String fileURI = curDir + "/plugins/WikiWhirl/WikiWhirl.MofScript/metamodels/WikiWhirl.ecore";
		String fileURI = curDir + "\\metamodels\\WikiWhirl.ecore";
		String fileURI2 = curDir + "\\metamodels\\diff.ecore";
		Resource resSource = rsSource.getResource(URI.createFileURI(fileURI), true);
		Resource resSource2 = rsSource.getResource(URI.createFileURI(fileURI2), true);

		LinkedList<EPackage> packages = new LinkedList<EPackage>();
		EList<EObject> objects = resSource.getContents();
		for (EObject object : objects) {
			if (object instanceof EPackage) {
				EPackage pkg = (EPackage) object;
				EPackage.Registry.INSTANCE.put(pkg.getNsURI(), pkg);
				System.out.println("Registered metamodel: " + pkg.getNsURI());
			}
		}
		
		LinkedList<EPackage> packages2 = new LinkedList<EPackage>();
		EList<EObject> objects2 = resSource2.getContents();
		for (EObject object2 : objects2) {
			if (object2 instanceof EPackage) {
				EPackage pkg2 = (EPackage) object2;
				EPackage.Registry.INSTANCE.put(pkg2.getNsURI(), pkg2);
				System.out.println("Registered metamodel: " + pkg2.getNsURI());
			}
		}
		
		int errs = parse(transformation);
		if (errs == 0) {
			execute(inputModel, diffModel, schemaModel);
		}
	}

	/**
	 * ExecutionMessageListener interface operations
	 */
	public void executionMessage(String type, String description) {
		//System.out.println(type + " - " + description);
	}

	/**
	 * Main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		RunMofScript runM = new RunMofScript();
		System.out.println("Current dir: "+runM.curDir);
		runM.setEcoreModel(runM.curDir + "\\models\\WikiWhirlModel.xmi");
		runM.setDiffModel(runM.curDir + "\\models\\diff_model.xmi");
		runM.setSchemaModel(runM.curDir + "\\metamodels\\wikidb119.ecore");
		//runM.setTransformation("transformations/FreeMind_MediaWiki.m2t");
		runM.setTransformation(runM.curDir + "\\transformations\\FreeMind_MediaWiki.m2t");

		runM.parseAndExecute();
	}
	
	public String getEcoreModel() {
		return inputModel;
	}

	public void setEcoreModel(String ecoreModel) {
		this.inputModel = ecoreModel;
	}

	public String getTransformation() {
		return transformation;
	}

	public void setTransformation(String transformation) {
		this.transformation = transformation;
	}
}