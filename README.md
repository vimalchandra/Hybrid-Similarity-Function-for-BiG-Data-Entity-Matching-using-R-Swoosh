# Hybrid-Similarity-Function-for-Big-Data-Entity-Matching-using-R-Swoosh
Consolidated Code

CS298 Master's project Instruction on how to run (OS required Windows 8.1 or 10):

1. Download and install Eclipse IDE for Java from the link "http://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/mars/2/eclipse-jee-mars-2-win32-x86_64.zip".
2. Clone this project in desktop and import the maven project into Eclipse IDE
3. Open 'Pom.xml' in the project and add the folowing dependency to the dependencies section to install Spark for development and build the project
   <dependency>
     <groupId>com.sparkjava</groupId>
     <artifactId>spark-core</artifactId>
     <version>2.3</version>
   </dependency>
4. Downlaod and add "java-string-similarity-0.13.jar" to the external jars section of the project from the link "https://github.com/tdebatty/java-string-similarity/releases".
5. Download the "input.txt" file from the git hub project section of "Hybrid Similarity Function for Big Data Entity Matching using R-Swoosh" and place it on desktop.
6. Click on Run --> Edit Configuration. Add input argument as file location for input file "input.txt".               Example:"C:/Users/Vimal/Desktop/input.txt".
7. Click on Run --> Edit Configuration. Add input argument as file location for output file "clusters.txt”.
Example: "C:\\Users\\vimal\\Desktop\\clusters.txt”. Give at least a single space between the arguments.

8.Click on "Apply" and click on "Run".Observe that output file is generated in the specified path.
