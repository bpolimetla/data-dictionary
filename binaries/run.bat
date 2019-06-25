set JAVA_HOME=D:\software\jdk1.6.0_21
set path=%JAVA_HOME%\bin;%path%;
set JAVA_OPTS=-Xmx1024m -Xms512m

set CLASSPATH=.;D:/ddgen/ojdbc6-11.1.0.7.0.jar;
set CLASSPATH=%classpath%;D:/ddgen/log4j-1.2.12.jar;
set CLASSPATH=%classpath%;D:/ddgen/DDGen.jar;


java com.ddgen.datadictionary.DDGen all false