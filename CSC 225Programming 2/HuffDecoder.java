/* HuffDecoder.java

   Starter code for compressed file decoder. You do not have to use this
   program as a starting point if you don't want to, but your implementation
   must have the same command line interface. Do not modify the HuffFileReader
   or HuffFileWriter classes (provided separately).
   
   B. Bird - 03/19/2019
   (Wenbo, Wu/V00928620/July 3 2019)
*/

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;


public class HuffDecoder {


    private HuffFileReader inputReader;
    private BufferedOutputStream outputFile;

    /* Basic constructor to open input and output files. */
    public HuffDecoder(String inputFilename, String outputFilename) throws FileNotFoundException {
        inputReader = new HuffFileReader(inputFilename);
        outputFile = new BufferedOutputStream(new FileOutputStream(outputFilename));
    }


    public void decode() throws IOException {

        /* This is where actual decoding should happen. */

        /* The outputFile.write() method can be used to write individual bytes to the output file.*/
        HashMap<int[], Byte> bysymbol = new HashMap<>();
        HashMap<String, Byte> bysymbolStr = new HashMap<>();
        StringBuilder code = new StringBuilder();
		//using for loop to loop through the symbol table
        for (HuffFileSymbol sym = inputReader.readSymbol(); sym != null; sym = inputReader.readSymbol()) {
			//contain the symbol in to StringBuilder
            for (int symbolBit : sym.symbolBits) {
                code.append(symbolBit);
            }
			//cover the symbol to string and put to HashMap
            bysymbolStr.put(code.toString(), sym.symbol[0]);
            code.delete(0, code.length());//delete the string
        }

        HashMap<Byte, int[]> readStreamBit = new HashMap<>();
		//using while loop to find the specific string from the symbol table
		//if in the table ouput the string
        while (true) {

            if (bysymbolStr.containsKey(code.toString())) {
                outputFile.write(bysymbolStr.get(code.toString()));
                code.delete(0, code.length());
            } else {
                int b = inputReader.readStreamBit();
                if (b == -1)
                    break;
                code.append(b);
            }


        }

        inputReader.close();
        outputFile.close();


    }


    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Usage: java HuffDecoder <input file> <output file>");
            return;
        }
        String inputFilename = args[0];
        String outputFilename = args[1];

        try {
            HuffDecoder decoder = new HuffDecoder(inputFilename, outputFilename);
            decoder.decode();
        } catch (FileNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
