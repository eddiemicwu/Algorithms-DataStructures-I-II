/* PixelGraph.java
   CSC 225 - Summer 2019

   B. Bird - 04/28/2019
   (Add your name/studentID/date here)
*/ 

import java.awt.Color;

public class PixelGraph{
	private Color[][] imagePixels;
	private PixelVertex[][] pixelVertices;
	private int width;
	private int height;

	/* PixelGraph constructor
	   Given a 2d array of colour values (where element [x][y] is the colour 
	   of the pixel at position (x,y) in the image), initialize the data
	   structure to contain the pixel graph of the image. 
	*/
	public PixelGraph(Color[][] imagePixels){
		this.imagePixels = imagePixels;
		this.width = imagePixels.length;
		this.height = imagePixels[0].length;
		pixelVertices = new PixelVertex[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				pixelVertices[x][y] = new PixelVertex(x, y);
			}
		}
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				PixelVertex current = pixelVertices[x][y];
				if (validCoordinate(x - 1, y, width, height)) {
					if (imagePixels[x][y].equals(imagePixels[x - 1][y])) {
						current.addNeighbour(pixelVertices[x - 1][y]);
					}
				}
				if (validCoordinate(x + 1, y, width, height)) {
					if (imagePixels[x][y].equals(imagePixels[x + 1][y])) {
						current.addNeighbour(pixelVertices[x + 1][y]);
					}
				}
				if (validCoordinate(x, y - 1, width, height)) {
					if (imagePixels[x][y].equals(imagePixels[x][y - 1])) {
						current.addNeighbour(pixelVertices[x][y - 1]);
					}
				}
				if (validCoordinate(x, y + 1, width, height)) {
					if (imagePixels[x][y].equals(imagePixels[x][y + 1])) {
						current.addNeighbour(pixelVertices[x][y + 1]);
					}
				}
			}
		}
	}

	private boolean validCoordinate(int x, int y, int width, int height) {
		if (x >= 0 && x < width && y >= 0 && y < height) {
			return true;
		} else {
			return false;
		}
	}
	
	/* getPixelVertex(x,y)
	   Given an (x,y) coordinate pair, return the PixelVertex object 
	   corresponding to the pixel at the provided coordinates.
	   This method is not required to perform any error checking (and you may
	   assume that the provided (x,y) pair is always a valid point in the 
	   image).
	*/
	public PixelVertex getPixelVertex(int x, int y){
		return pixelVertices[x][y];
	}
	
	/* getWidth()
	   Return the width of the image corresponding to this PixelGraph 
	   object.
	*/
	public int getWidth(){
		return imagePixels.length;
	}
	
	/* getHeight()
	   Return the height of the image corresponding to this PixelGraph 
	   object.
	*/
	public int getHeight(){
		return imagePixels[0].length;
	}
	
}