import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class KMP{
    private String pattern;
	private int[][] arr;    
    
    public KMP(String pattern){  
		this.pattern = pattern;
		int M = pattern.length();
		int R = 256;
		arr = new int[R][M];
		arr[pattern.charAt(0)][0] = 1;
		for(int X = 0, j = 1; j < M; j++){
			for(int c = 0; c < R; c++){
				arr[c][j] = arr[c][X];
			}
			arr[pattern.charAt(j)][j] = j + 1;
			X = arr[pattern.charAt(j)][X];
		}
    }
    
    public int search(String txt){
		int i;//index of txt[]
		int j;//index of pat[]
		int N = txt.length();
		int M = pattern.length();
		for(i = 0, j = 0; i < N && j < M; i++){
			j = arr[txt.charAt(i)][j];
		}
		if(j == M){//found the pattern
			return i - M;
		}else{
			return N;
		}
    }
    
    public static void main(String[] args) throws FileNotFoundException{
	Scanner s;
	if (args.length > 0){
	    try{
		s = new Scanner(new File(args[0]));
	    }
	    catch(java.io.FileNotFoundException e){
		System.out.println("Unable to open "+args[0]+ ".");
		return;
	    }
	    System.out.println("Opened file "+args[0] + ".");
	    String text = "";
	    while(s.hasNext()){
		text += s.next();
	    }
	    
	    for(int i = 1; i < args.length; i++){
		KMP k = new KMP(args[i]);
		int index = k.search(text);
		if(index >= text.length()){
		    System.out.println("The pattern \"" + args[i] + "\" was not found.");
		}
		else System.out.println("The string \"" + args[i] + "\" was found at index " + index + ".");
	    }
	}
	else{
	    System.out.println("usage: java SubstringSearch <filename> <pattern_1> <pattern_2> ... <pattern_n>.");
	}
    }
}
