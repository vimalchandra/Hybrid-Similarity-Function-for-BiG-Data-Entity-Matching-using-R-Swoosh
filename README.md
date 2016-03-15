# Hybrid-Similarity-Function-for-Big-Data-Entity-Matching-using-R-Swoosh
Consolidated Code

CS298 Master's project Instruction on how to run (OS required Windows 8.1 or 10):

1. Download  and install Eclipse IDE for Java from the link "http://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/mars/2/eclipse-jee-mars-2-win32-x86_64.zip".
2. Clone this project in desktop and import the maven project into Eclipse IDE.
3. Open 'Pom.xml' in the project and add the folowing dependency to the dependencies section to install Spark for development
   <dependency>
     <groupId>com.sparkjava</groupId>
     <artifactId>spark-core</artifactId>
     <version>2.3</version>
   </dependency>
4. Add "java-string-similarity-0.13.jar" to the external jars section of the project from the link "https://github.com/tdebatty/java-string-similarity/releases".
5. Download the "input.txt" file from the git hub project section of "Hybrid Similarity Function for Big Data Entity Matching using R-Swoosh" and place it on desktop.
6. Give path of "input.txt" file on the line "83" of code in the "Main.java" file. Add the path here in this manner "String testFile = "C:/Users/SAIRAM/Desktop/input.txt";".
7. Specify the output file path on the line "134" of code in the "Main.java" file. Add the path here in this manner "File outputFile = new File("C:\\Users\\SAIRAM\\Desktop\\clusters.txt");".
8. Save Main.java file and build the project.
9. Click on "Run" and observe that output file is generated in the specified path.
