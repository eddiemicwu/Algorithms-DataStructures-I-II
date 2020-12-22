/* A3Algorithms.java
   CSC 225 - Summer 2019


   B. Bird - 04/28/2019
   (Add your name/studentID/date here)
*/ 

import java.awt.Color;
import java.util.LinkedList;
import java.util.Queue;

public class A3Algorithms{

	/* FloodFillDFS(v, writer, fillColour)
	   Traverse the component the vertex v using DFS and set the colour 
	   of the pixels corresponding to all vertices encountered during the 
	   traversal to fillColour.
	   
	   To change the colour of a pixel at position (x,y) in the image to a 
	   colour c, use
			writer.setPixel(x,y,c);
	*/
	public static void FloodFillDFS(PixelVertex v, PixelWriter writer, Color fillColour) {
		if (v != null) {
            v.setVisited(true);
            writer.setPixel(v.getX(), v.getY(), fillColour);
			PixelVertex[] vertices = v.getNeighbours();
			for (PixelVertex vertex : vertices) {
				if (!vertex.isVisited()) {
					FloodFillDFS(vertex, writer, fillColour);
				}
			}
		}
	}
	
	/* FloodFillBFS(v, writer, fillColour)
	   Traverse the component the vertex v using BFS and set the colour 
	   of the pixels corresponding to all vertices encountered during the 
	   traversal to fillColour.
	   
	   To change the colour of a pixel at position (x,y) in the image to a 
	   colour c, use
			writer.setPixel(x,y,c);
	*/
	public static void FloodFillBFS(PixelVertex v, PixelWriter writer, Color fillColour){
		Queue<PixelVertex> queue = new LinkedList<>();
		queue.add(v);
		v.setVisited(true);
		while(!queue.isEmpty()) {
			PixelVertex vertex = queue.remove();
			writer.setPixel(vertex.getX(), vertex.getY(), fillColour);
			PixelVertex[] vertices = vertex.getNeighbours();
			for (PixelVertex pv : vertices) {
				if (!pv.isVisited()) {
					pv.setVisited(true);
					queue.add(pv);
				}
			}
		}
	}
	
	/* OutlineRegionDFS(v, writer, outlineColour)
	   Traverse the component the vertex v using DFS and set the colour 
	   of the pixels corresponding to all vertices with degree less than 4
	   encountered during the traversal to outlineColour.
	   
	   To change the colour of a pixel at position (x,y) in the image to a 
	   colour c, use
			writer.setPixel(x,y,c);
	*/
	public static void OutlineRegionDFS(PixelVertex v, PixelWriter writer, Color outlineColour) {
		if (v != null) {
			v.setVisited(true);
			if (v.getDegree() < 4) {
				writer.setPixel(v.getX(), v.getY(), outlineColour);
			}
			PixelVertex[] vertices = v.getNeighbours();
			for (PixelVertex vertex : vertices) {
				if (!vertex.isVisited()) {
					OutlineRegionDFS(vertex, writer, outlineColour);
				}
			}
		}
	}
	
	/* OutlineRegionBFS(v, writer, outlineColour)
	   Traverse the component the vertex v using BFS and set the colour 
	   of the pixels corresponding to all vertices with degree less than 4
	   encountered during the traversal to outlineColour.
	   
	   To change the colour of a pixel at position (x,y) in the image to a 
	   colour c, use
			writer.setPixel(x,y,c);
	*/
	public static void OutlineRegionBFS(PixelVertex v, PixelWriter writer, Color outlineColour){
		Queue<PixelVertex> queue = new LinkedList<>();
		queue.add(v);
		v.setVisited(true);
		while(!queue.isEmpty()) {
			PixelVertex vertex = queue.remove();
			if (vertex.getDegree() < 4) {
				writer.setPixel(vertex.getX(), vertex.getY(), outlineColour);
			}
			PixelVertex[] vertices = vertex.getNeighbours();
			for (PixelVertex pv : vertices) {
				if (!pv.isVisited()) {
					pv.setVisited(true);
					queue.add(pv);
				}
			}
		}
	}

	/* CountComponents(G)
	   Count the number of connected components in the provided PixelGraph 
	   object.
	*/
	public static int CountComponents(PixelGraph G){
		int count = 0;
		PixelVertex pixelVertex = checkUnvisited(G);
		while (pixelVertex != null) {
			count++;
			dfs(pixelVertex);
			pixelVertex = checkUnvisited(G);
		}
		return count;
	}

	private static void dfs(PixelVertex v) {
		if (v != null) {
			v.setVisited(true);
			PixelVertex[] vertices = v.getNeighbours();
			for (PixelVertex vertex : vertices) {
				if (!vertex.isVisited()) {
					dfs(vertex);
				}
			}
		}
	}

	private static PixelVertex checkUnvisited(PixelGraph G) {
		int width = G.getWidth();
		int height = G.getHeight();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (!G.getPixelVertex(x, y).isVisited()) {
					return G.getPixelVertex(x, y);
				}
			}
		}
		return null;
	}
}