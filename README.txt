To execute double-click on "initialize.bat". 

Requirements: 
- ant
- mysql
- script to install the database

Files needed to be configured:
- Rename script to install the database to tables.sql
- initialize.bat: change paths
- /ScheMoL.Blog/build_wiki.xml: change database configuration
- transformation/FreeMind_MediaWiki.m2t: change path in file("filename.sql") to establish the output 
- compare_execute.bat: change arguments of call to CompareModels. Names of the two models to be compared are needed

This batch executes the following four steps:
1- Installs a database executing the tables.sql
2- Injects a database schema to model
3- Compares two models
4- Executes the mofscript transformation

