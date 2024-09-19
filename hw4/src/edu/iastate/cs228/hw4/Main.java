package edu.iastate.cs228.hw4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Jon Beltzhoover
 */
public class Main {

	/**
	 * Asks for a file to decode the secret message
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		System.out.println("Please enter filename to decode:");
		Scanner scnr = new Scanner(System.in);
		String filename = scnr.nextLine();
		scnr.close();
		
		String line = new String(Files.readAllBytes(Paths.get(filename))).trim();
		int position = line.lastIndexOf('\n');
		String binaryCode = line.substring(0, position);
		String encodedMsg = line.substring(position).trim();
		ArrayList<Character> characters = new ArrayList<Character>();
		for (char c : binaryCode.toCharArray()) {
			if (c != '^') {
				characters.add(c);
			}
		}
		
		String code = characters.toString();
		code = code.replace(",", "");
		code = code.replace(" ", "");
		code = code.replace("[", "");
		code = code.replace("]", "");
		
		MsgTree tree = new MsgTree(binaryCode);
		MsgTree.printCodes(tree, code);
		tree.decode(tree, encodedMsg);
	}

}
