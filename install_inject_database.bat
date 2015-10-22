TITLE install_inject_database
mysql -uroot -pbadminto < tables.sql
ECHO Database installed
pause
cd C:\Users\jokin\Desktop\Batch_MofscriptAdaptation\ScheMoL.Blog
%ANT_HOME%\bin\ant -buildfile build_wiki.xml
