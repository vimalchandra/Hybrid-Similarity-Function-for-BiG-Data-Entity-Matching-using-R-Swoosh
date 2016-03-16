package myapp;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.PairFunction;
import info.debatty.java.stringsimilarity.JaroWinkler;
import info.debatty.java.stringsimilarity.Levenshtein;
import info.debatty.java.stringsimilarity.LongestCommonSubsequence;
import scala.Tuple2;

public class Main {
	
	
	 public static String stringDistance(Iterable<String> list,int clusterCount){
			
			
			Levenshtein l = new Levenshtein();
			JaroWinkler jw = new JaroWinkler();
			LongestCommonSubsequence lc = new LongestCommonSubsequence();

			/*****************************************************************************************************
			 * Implementation of Hybrid Similarity Function with R-Swoosh
			 *****************************************************************************************************/

			Map<Integer,List<String>> clusters = new HashMap<Integer,List<String>>();
			int index = 0;
			
			for(String x : list){
				if(index == 0){
					List<String> arrayList = new ArrayList<String>();
					arrayList.add(x);
					clusters.put(index,arrayList);
					index++;
				}
				else{
					
					for(int indexer = 0; indexer < clusters.size(); indexer++){
						List<String> arrayList = new ArrayList<String>();
						arrayList = clusters.get(indexer);
						try{
							if((((l.distance(x, arrayList.get(0)))+(lc.distance(x, arrayList.get(0)))+(jw.distance(x, arrayList.get(0))-1))/3)<0.3){
								arrayList.add(x);
								clusters.remove(indexer);
								clusters.put(indexer,arrayList);
								break;
							}
						}catch(NullPointerException e){
							e.printStackTrace();
						}
						
					}

				}
			}
			
			StringBuilder sbr = new StringBuilder();
			for(List<String> x : clusters.values()){
				System.out.println("Cluster: " + String.valueOf(clusterCount) +
						"\t Entities clustered: " + x);
				sbr.append("Cluster: " + String.valueOf(clusterCount) +
						"\t Entities clustered: " + x + "\n");
			}
			return sbr.toString();
		    }
		
		    public static void main(String[] args) throws IOException{
			
			
			String testFile = args[0];
			JavaSparkContext sc = new JavaSparkContext("local", "SimpleAPP");
			
			JavaRDD<String> logData = sc.textFile(testFile).cache();
			
			/*****************************************************************************************************
			 *  Pre-Processing Step 
			 *****************************************************************************************************/

			JavaRDD<String> processData = logData.flatMap(new FlatMapFunction<String, String>() {
				public Iterable<String> call(String s){
					return Arrays.asList(s.replace(")(","#").split("#"));
				}
			});
			
			JavaRDD<String> cleanData = processData.flatMap(new FlatMapFunction<String, String>() {
				public Iterable<String> call(String s){
					return Arrays.asList(s.replace("(", "").replace(")", ""));
				}
			});

			/*****************************************************************************************************
			 *  Map Step 
			 *****************************************************************************************************/

			JavaPairRDD<Double, String> groupData = cleanData.mapToPair(new PairFunction<String, Double, String>() {
				public Tuple2<Double, String> call(String s) {
				int count = 0;
				Double index = 0.0;
				StringBuilder sb = new StringBuilder();
				String[] items = s.split(",");
				for(String x : items){
					if(count == 0){
						count++;
						index = Double.parseDouble(x);
					}
					else{
						sb.append(x);
						sb.append(' ');
					}
				}
				        return new Tuple2<Double, String>(index, sb.toString());
				      }
				});

			/*****************************************************************************************************
			 * Step to group similar entities together and writing output to a file
			 *****************************************************************************************************/
				
				JavaPairRDD<Double,Iterable<String>> groupsOfData = groupData.groupByKey();
				
				File outputFile = new File(args[1]);
				FileWriter fw = new FileWriter(outputFile);
				
				int clusterCount = 0;
				for(Tuple2<Double, Iterable<String>> x : groupsOfData.collect()){
					fw.write(Main.stringDistance(x._2,clusterCount));
					clusterCount++;
				}
				fw.close();
				sc.close();
				}
		}