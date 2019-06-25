Welcome to Data Dictionary Generation (DD GEN) tool.

This tool is used to generate the Data Dictionary for given Oracle Database.
We can use generated data to compare Database schema and spell check schema.
Application: Data Dictionary Generator (DDGen)
USA Copyright Number/Date: TXu001755709 / 2011-05-17 (http://cocatalog.loc.gov)
Author: Bhavani P Polimetla
This software is released under GNU GPL V3 on Sep-04-2010
http://www.gnu.org/licenses/gpl-3.0.html

-o-

Download Source / Binaries from
http://sourceforge.net/projects/ddgen/files/DDGen%20Release%201.0.0/

-o-

Question: How to run DDGen.jar file?

Step 1:
First, download and extract ddgen_release1.0.0_bin.zip file to ddgen folder.

Step 2:
Download and copy the following files to ddgen folder
log4j-1.2.12.jar
ojdbc6-11.1.0.7.0.jar

http://mvnrepository.com/artifact/log4j/log4j/1.2.12

http://www.mvnsearch.org/maven2/com/oracle/ojdbc6/11.1.0.7.0/
https://maven.nuxeo.org/nexus/content/groups/public/com/oracle/ojdbc6/11.1.0.7.0/

If an exact version is not available, copy similar version files and modify run.bat file.

Step 3:
Modify java home and other versions in run.bat file.
The binaries are compiled using Java 1.5.

Step 4: Enter correct jdbc connection string
DDGen.properties

Step 5: In Windows
Run run.bat file from the command prompt to see the result files.

For Unix/Linux perform similar steps.

-o-

Question: Where and all I can use this tool?
1. Generate Data Dictionary for an existing database.
2. Compare QA/Prod environments.
3. Use full in migrating database from version x to version y.
4. During the database design, we need to make sure that there are no spelling mistakes in table names, column names, …etc. This tool is useful in giving all strings from the schema.

-o-

Question: Can we get a friendly UI?
1. I felt that it is straightforward to use through command prompt with compare to UI.
If you have time and interest, please take this code and build UI
Also, due to licensing issues with Oracle driver, I can’t bundle and build UI at this time.

2. To check the difference, we already have win merge tool (http://winmerge.org/).
In Unix/Linux we can use diff command.

-o-

Question: I see version issues while running the binaries.
To avoid version conflicts, download the source and build ddgen.jar file.
Follow the rest of the instructions in readme.txt file.
-o-

Please let me know your comments.
If you have any customization requests, please let me know.

-o-
----

Note: Many of these features were available with ER Win, which is a commercial version. As on Jun-26-2019, Still, we can see the value of it.

----

https://ddgen.wordpress.com/

----
