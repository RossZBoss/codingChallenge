
Prerequisites
- hsqldb install and on your classpath, see: http://hsqldb.org/
- update the datasource in database configuration file to include your username/password and database name,
    the file is found here: src/main/java/com/test/codingChallenge/configuration/DatabaseConfiguration.class

How To Run Application
- use the gradle wrapper to build the jar, open terminal and navigate to root of project directory then run command:
    "gradlew bootJar" this will generate a jar in codingChallenge/build/libs/codingChallenge-0.0.1-SNAPSHOT.jar
- run jar by navigating to  codingChallenge/build/libs/ then writing the following command in console:
    "java -jar codingChallenge-0.0.1-SNAPSHOT.jar"
- when shell has started, type help and a list of options will be displayed.
