/* ShortestPaths.java
   CSC 226 - Fall 2019
   
   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
   java ShortestPaths
   
   To conveniently test the algorithm with a large input, create a text file
   containing one or more test graphs (in the format described below) and run
   the program with
   java ShortestPaths file.txt
   where file.txt is replaced by the name of the text file.
   
   The input consists of a series of graphs in the following format:
   
   <number of vertices>
   <adjacency matrix row 1>
   ...
   <adjacency matrix row n>
   
   Entry A[i][j] of the adjacency matrix gives the weight of the edge from 
   vertex i to vertex j (if A[i][j] is 0, then the edge does not exist).
   Note that since the graph is undirected, it is assumed that A[i][j]
   is always equal to A[j][i].
   
   An input file can contain an unlimited number of graphs; each will be processed separately.
   
   NOTE: For the purpose of marking, we consider the runtime (time complexity)
         of your implementation to be based only on the work done starting from
	 the ShortestPaths() method. That is, do not not be concerned with the fact that
	 the current main method reads in a file that encodes graphs via an
	 adjacency matrix (which takes time O(n^2) for a graph of n vertices).
   
   
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Iterator;


//Do not change the name of the ShortestPaths class
public class ShortestPaths{

    //TODO: Your code here
    public static int n; // number of vertices
	public static int[] distance; //array contain the all the distance for each vertices from source.
	public static String[] path; //array contain the shortest path for all the vertices
	
    /* ShortestPaths(adj) 
       Given an adjacency list for an undirected, weighted graph, calculates and stores the
       shortest paths to all the vertices from the source vertex.
       
       The number of vertices is adj.length
       For vertex i:
         adj[i].length is the number of edges
         adj[i][j] is an int[2] that stores the j'th edge for vertex i, where:
           the edge has endpoints i and adj[i][j][0]
           the edge weight is adj[i][j][1] and assumed to be a positive integer
       
       All weights will be positive.
    */
    static void ShortestPaths(int[][][] adj, int source){
	n = adj.length;

	//TODO: Your code here
		//initial the distance and path array.
		distance = new int[n];
		path = new String[n];
		IndexMinPQ<Integer> pq = new IndexMinPQ<Integer>(n); //PriorityQueue processing all the vertex. 
		boolean visited[] = new boolean[n]; //boolean type to check if the vertices has being visited or not.
	
		for(int i = 0; i < n; i++){
			path[i] = "" + source; //initial all paths from the source. 
			distance[i] = -1; // initial the distance to -1 for all vertices.
		}
		distance[source] = 0; //the distance for the source is to itself is 0;
		
		/*using for loop, loop though all the edge that source connected.
		And load the distance to from the source to it's neighbours.
		*/
		//initial the pq with the source and the neighbour of the source.
		visited[source] = true;
		for(int i = 0; i < adj[source].length; i++){
			int neighbour = adj[source][i][0];
			pq.insert(neighbour, adj[source][i][1]);
			distance[neighbour] = adj[source][i][1]; //add all the neighbours distance to specific index.
			path[neighbour] += " --> " + neighbour; //add the path from source to it's neighbours.
		}
		//loop for go though entire vertices and find all the shortest path for each vertex.
		while(!pq.isEmpty()){
			int s = pq.minIndex(); //visited next smallest vertex.
			visited[s] = true;
			distance[s] = pq.minKey(); //sign the shortest path to the vertex s.
			pq.delMin();
			//loop for the vertex neighbours.
			for(int j = 0; j < adj[s].length; j++){
				int sub_neighbour = adj[s][j][0]; //index of the smallest vertice's neighbours.
				//skip the vertex that already visited.
				if(visited[sub_neighbour]){
					continue;
				}
				//check if there is a shorter path for the vertex that also connected with previous vertex.
				int tot = distance[s]+adj[s][j][1];
				//if contains in pq, and updates the shorter distance to the pq.
				//add sub_neighbour to the pq if not contains in pq. 
				if(pq.contains(sub_neighbour) && pq.keyOf(sub_neighbour) > tot){
					path[sub_neighbour] = path[s] + " --> " + sub_neighbour;
					pq.changeKey(sub_neighbour, tot);//updates the distance for the path
				}else if(!pq.contains(sub_neighbour)){
					pq.insert(sub_neighbour, tot);
					path[sub_neighbour] = path[s] + " --> " + sub_neighbour;
				}
			}
		}
    }
    
    static void PrintPaths(int source){
	//TODO: Your code here
	
	//using for loop to outprint the each shortest path to each vertex from source.
	//and outprint the total distance of each path at the end. 
		for(int i = 0; i < n; i++){
				System.out.println("The path from " + source + " to " + i + " is: " + path[i] + " and the total distance is : " + distance[i]);
		}
    }
    
    
    /* main()
       Contains code to test the ShortestPaths function. You may modify the
       testing code if needed, but nothing in this function will be considered
       during marking, and the testing process used for marking will not
       execute any of the code below.
    */
    public static void main(String[] args) throws FileNotFoundException{
	Scanner s;
	if (args.length > 0){
	    //If a file argument was provided on the command line, read from the file
	    try{
		s = new Scanner(new File(args[0]));
	    } catch(java.io.FileNotFoundException e){
		System.out.printf("Unable to open %s\n",args[0]);
		return;
	    }
	    System.out.printf("Reading input values from %s.\n",args[0]);
	}
	else{
	    //Otherwise, read from standard input
	    s = new Scanner(System.in);
	    System.out.printf("Reading input values from stdin.\n");
	}
	
	int graphNum = 0;
	double totalTimeSeconds = 0;
	
	//Read graphs until EOF is encountered (or an error occurs)
	while(true){
	    graphNum++;
	    if(graphNum != 1 && !s.hasNextInt())
		break;
	    System.out.printf("Reading graph %d\n",graphNum);
	    int n = s.nextInt();
	    int[][][] adj = new int[n][][];
	    
	    int valuesRead = 0;
	    for (int i = 0; i < n && s.hasNextInt(); i++){
		LinkedList<int[]> edgeList = new LinkedList<int[]>(); 
		for (int j = 0; j < n && s.hasNextInt(); j++){
		    int weight = s.nextInt();
		    if(weight > 0) {
			edgeList.add(new int[]{j, weight});
		    }
		    valuesRead++;
		}
		adj[i] = new int[edgeList.size()][2];
		Iterator it = edgeList.iterator();
		for(int k = 0; k < edgeList.size(); k++) {
		    adj[i][k] = (int[]) it.next();
		}
	    }
	    if (valuesRead < n * n){
		System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
		break;
	    }
	    /*
	    // output the adjacency list representation of the graph
	    for(int i = 0; i < n; i++) {
	    	System.out.print(i + ": ");
	    	for(int j = 0; j < adj[i].length; j++) {
	    	    System.out.print("(" + adj[i][j][0] + ", " + adj[i][j][1] + ") ");
	    	}
	    	System.out.print("\n");
	    }
	    */
	    long startTime = System.currentTimeMillis();
	    
	    ShortestPaths(adj, 0);
	    PrintPaths(0);
	    long endTime = System.currentTimeMillis();
	    totalTimeSeconds += (endTime-startTime)/1000.0;
	    
	    //System.out.printf("Graph %d: Minimum weight of a 0-1 path is %d\n",graphNum,totalWeight);
	}
	graphNum--;
	System.out.printf("Processed %d graph%s.\nAverage Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>0)?totalTimeSeconds/graphNum:0);
    }
}
