/* PixelVertex.java
   CSC 225 - Summer 2019


   B. Bird - 04/28/2019
   (Add your name/studentID/date here)
*/


import java.util.ArrayList;
import java.util.List;

public class PixelVertex{
	private List<PixelVertex> neighbours = new ArrayList<>();
	private int x;
	private int y;
	private boolean visited = false;

	/* Add a constructor here (if necessary) */
	public PixelVertex(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/* getX()
	   Return the x-coordinate of the pixel corresponding to this vertex.
	*/
	public int getX(){
		return x;
	}
	
	/* getY()
	   Return the y-coordinate of the pixel corresponding to this vertex.
	*/
	public int getY(){
		return y;
	}
	
	/* getNeighbours()
	   Return an array containing references to all neighbours of this vertex.
	   The size of the array must be equal to the degree of this vertex (and
	   the array may therefore contain no duplicates).
	*/
	public PixelVertex[] getNeighbours(){
		return neighbours.toArray(new PixelVertex[0]);
	}
	
	/* addNeighbour(newNeighbour)
	   Add the provided vertex as a neighbour of this vertex.
	*/
	public void addNeighbour(PixelVertex newNeighbour){
		neighbours.add(newNeighbour);
	}
	
	/* removeNeighbour(neighbour)
	   If the provided vertex object is a neighbour of this vertex,
	   remove it from the list of neighbours.
	*/
	public void removeNeighbour(PixelVertex neighbour){
		neighbours.remove(neighbour);
	}
	
	/* getDegree()
	   Return the degree of this vertex. Since the graph is simple, 
	   the degree is equal to the number of distinct neighbours of this vertex.
	*/
	public int getDegree(){
		return neighbours.size();
	}
	
	/* isNeighbour(otherVertex)
	   Return true if the provided PixelVertex object is a neighbour of this
	   vertex and false otherwise.
	*/
	public boolean isNeighbour(PixelVertex otherVertex){
		return neighbours.contains(otherVertex);
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public boolean isVisited() {
		return visited;
	}
}