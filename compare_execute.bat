TITLE compare_execute
cd C:\Users\jokin\Desktop\Batch_MofscriptAdaptation
java -classpath .;org.eclipse.emf.ecore_2.5.0.v200906151043.jar;org.eclipse.emf.common_2.5.0.v200906151043.jar;org.eclipse.emf.ecore.xmi_2.5.0.v200906151043.jar;org.eclipse.emf.compare_0.8.1.v200902100450.jar;org.eclipse.emf.compare_3.0.0.201212180932.jar;guava-10.0.1.jar;org.eclipse.emf.compare.match_0.8.1.v200902100450.jar;org.eclipse.emf.compare.diff_0.8.1.v200902100450.jar onekin.compare.CompareModels wikidb116.ecore wikidb119.ecore
ECHO schema models compared
pause
java -classpath .;org.eclipse.emf.ecore_2.5.0.v200906151043.jar;org.eclipse.emf.common_2.5.0.v200906151043.jar;org.eclipse.emf.ecore.xmi_2.5.0.v200906151043.jar;org.eclipse.emf.compare_0.8.1.v200902100450.jar;org.eclipse.emf.compare_3.0.0.201212180932.jar;guava-10.0.1.jar;org.eclipse.emf.compare.match_0.8.1.v200902100450.jar;org.eclipse.emf.compare.diff_0.8.1.v200902100450.jar;org.eclipse.mofscript.runtime.jar;org.eclipse.mofscript.parser.jar;antlr-runtime-3.1.3.jar;org.eclipse.mofscript.model.jar;org.eclipse.mofscript.fileresourcemodel.jar;ant.jar onekin.runmofscript.RunMofScript
ECHO transformation executed
pause