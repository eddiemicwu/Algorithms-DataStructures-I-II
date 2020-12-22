/* HuffEncoder.java

   Starter code for compressed file encoder. You do not have to use this
   program as a starting point if you don't want to, but your implementation
   must have the same command line interface. Do not modify the HuffFileReader
   or HuffFileWriter classes (provided separately).
   
   B. Bird - 03/19/2019
   (Wenbo, Wu/V00928620/July 3 2019)
*/

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class HuffEncoder {

    private BufferedInputStream inputFile;
    private HuffFileWriter outputWriter;
	//A List for contains HuffmanTreeElement
    private LinkedList<HuffTreeNode> huffTreeNodes = new LinkedList<>();
	//HashMap for HuffManTree
    private HashMap<Byte, String> byteStringHashMap = new HashMap<>();

    public HuffEncoder(String inputFilename, String outputFilename) throws FileNotFoundException {
        inputFile = new BufferedInputStream(new FileInputStream(inputFilename));
        outputWriter = new HuffFileWriter(outputFilename);
    }


    public void encode() throws IOException {

        //You may want to start by reading the entire file into a list to make it easier
        //to navigate.
        LinkedList<Byte> input_bytes = new LinkedList<Byte>();
        for (int nextByte = inputFile.read(); nextByte != -1; nextByte = inputFile.read()) {
            input_bytes.add((byte) nextByte);
        }
        //Suggested algorithm:

        //Compute the frequency of each input symbol. Since symbols are one character long,
        //you can simply iterate through input_bytes to see each symbol.

        //Build a prefix code for the encoding scheme (if using Huffman Coding, build a
        //Huffman tree).

        //Write the symbol table to the output file

        //Call outputWriter.finalizeSymbols() to end the symbol table

        //Iterate through each input byte and determine its encode bitstring representation,
        //then write that to the output file with outputWriter.writeStreamBit()

        //Call outputWriter.close() to end the output file
		
		//create a HashMap for contains the element in list
        HashMap<Byte, Integer> countMap = new HashMap<>();
        inputFile.close();//close the file that encode from
		//using forEach method to input the element in to the HashMap
		//if the element already exists in the HashMap increment it
        input_bytes.forEach(perByte -> {
            if (!countMap.containsKey(perByte)) {
                countMap.put(perByte, 0);
            }
            countMap.put(perByte, countMap.get(perByte) + 1);
        });
		//using forEach method to loop through the HashMap build above
		//make it to a Huffman tree node and put it in to the list first
        countMap.forEach((k, v) -> {
            HuffTreeNode huffTreeNode = new HuffTreeNode(v, k);
            huffTreeNodes.add(storeLocationOfHuffNode(huffTreeNode), huffTreeNode);
        });
		//use while loop to covert all the element in list to a huffman tree
        while (huffTreeNodes.size() > 1) {
            HuffTreeNode firstNode = huffTreeNodes.removeFirst();//covert the first two
            HuffTreeNode secondNode = huffTreeNodes.removeFirst();//element in to a tree
            HuffTreeNode fatherNode =
                    new HuffTreeNode(firstNode.getCountValue() + secondNode.getCountValue(), (byte) -1);
            fatherNode.setLeftNode(firstNode);
            fatherNode.setRightNode(secondNode);
            huffTreeNodes.add(storeLocationOfHuffNode(fatherNode), fatherNode);
        }

        HuffTreeNode firstHuff = huffTreeNodes.getFirst();
        getHuffmCode(firstHuff, "");//sign huffman code to all the element in tree
        HashMap<Byte, int[]> bysymbol = new HashMap<>();
		//using forEach method to get all the huffman code that being signed to the string
		//store them in to hashMap
        byteStringHashMap.forEach((k, v) -> {
            int[] ints = generateStringToInts(v);
            bysymbol.put(k, ints);
            HuffFileSymbol huffFileSymbol = new HuffFileSymbol(k, ints);
            outputWriter.writeSymbol(huffFileSymbol);
        });
        outputWriter.finalizeSymbols();//finish symbol table
		//write the symbol in to output file
        input_bytes.forEach(perByte -> {
            for (int i : bysymbol.get(perByte)) {
                outputWriter.writeStreamBit(i);
            }

        });
        outputWriter.close();//end
        

    }
	//get the huffman code number for all the string that being sigened huffmancode
    private int[] generateStringToInts(String code) {
        int[] ints = new int[code.length()];
        for (int i = 0; i < code.length(); i++) {
            ints[i] = Integer.parseInt(String.valueOf(code.charAt(i)));
        }
        return ints;
    }
	//recusion to getting huffman code(symbolBits) 
    private void getHuffmCode(HuffTreeNode beginNode, String symbolBits) {
        if (beginNode.getLeftNode() != null) {
            getHuffmCode(beginNode.getLeftNode(), symbolBits + "0");
        }
        if (beginNode.getRightNode() != null) {
            getHuffmCode(beginNode.getRightNode(), symbolBits + "1");
        }
        if (beginNode.getLeftNode() == null && beginNode.getRightNode() == null) {
            if (!byteStringHashMap.containsKey(beginNode.getSymbol())) {
                byteStringHashMap.put(beginNode.getSymbol(), "");
            }
            byteStringHashMap.put(beginNode.getSymbol(), symbolBits);
        }
    }
	//
    private int storeLocationOfHuffNode(HuffTreeNode node) {
        for (int i = 0; i < huffTreeNodes.size(); i++) {
            if (node.getCountValue() <= huffTreeNodes.get(i).getCountValue()) {
                return i;
            }
        }
        return huffTreeNodes.size();
    }


    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Usage: java HuffEncoder <input file> <output file>");
            return;
        }
        String inputFilename = args[0];
        String outputFilename = args[1];

        try {
            HuffEncoder encoder = new HuffEncoder(inputFilename, outputFilename);
            encoder.encode();
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }

    }
}
//Huffman Tree
class HuffTreeNode {
    private int countValue;
    private Byte symbol;
    private HuffTreeNode leftNode;
    private HuffTreeNode rightNode;

    public HuffTreeNode() {
    }

    public HuffTreeNode(int countValue, Byte symbol, HuffTreeNode leftNode, HuffTreeNode rightNode) {
        this.countValue = countValue;
        this.symbol = symbol;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    public HuffTreeNode(int countValue, Byte symbol) {
        this.countValue = countValue;
        this.symbol = symbol;
    }

    public int getCountValue() {
        return countValue;
    }

    public void setCountValue(int countValue) {
        this.countValue = countValue;
    }

    public Byte getSymbol() {
        return symbol;
    }

    public void setSymbol(Byte symbol) {
        this.symbol = symbol;
    }

    public HuffTreeNode getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(HuffTreeNode leftNode) {
        this.leftNode = leftNode;
    }

    public HuffTreeNode getRightNode() {
        return rightNode;
    }

    public void setRightNode(HuffTreeNode rightNode) {
        this.rightNode = rightNode;
    }
}

