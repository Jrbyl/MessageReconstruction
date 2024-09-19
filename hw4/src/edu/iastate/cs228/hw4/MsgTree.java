package edu.iastate.cs228.hw4;

import java.util.Stack;

/**
 * @author Jon Beltzhoover
 * 
 * Contains the binary tree that holds the codes for each letter in the secret message.
 */
public class MsgTree {
	
	/**
	 * Holds the character at the current leaf node
	 */
	public char payloadChar;
	
	/**
	 * The left child of the current node
	 */
	public MsgTree left;
	
	/**
	 * The right child of the current node
	 */
	public MsgTree right;
	
	/**
	 * The character held at the current node
	 */
	public char data;
	
	/**
	 * Tracks the index location within the tree string
	 */
	private static int staticCharIdx = 0;
	
	/**
	 * Holds the code of a give letter/node
	 */
	private static String letterCode;
	
	/**
	 * Constructor building the tree from a string
	 * 
	 * @param encodingString - the encoded message
	 */
	public MsgTree(String encodingString) {
		if (encodingString.length() < 2 || encodingString == null) {
			return;
		}
		Stack<MsgTree> MsgStack = new Stack<MsgTree>();
		
		int index = 0;
		this.payloadChar = encodingString.charAt(index++);
		MsgTree currNode = this;
		MsgStack.push(currNode);
		boolean leftDirection = true;
		
		while (encodingString.length() > index) {
			MsgTree node = new MsgTree(encodingString.charAt(index++));
			if (leftDirection) {
				currNode.left = node;
				if (node.payloadChar == '^') {
					currNode = MsgStack.push(node);
					leftDirection = true;
				}
				else {
					if (!MsgStack.empty()) {
						currNode = MsgStack.pop();
					}
					leftDirection = false;
				}
			}
			else if (!leftDirection) {
				currNode.right = node;
				if (node.payloadChar == '^') {
					currNode = MsgStack.push(node);
					leftDirection = true;
				}
				else {
					if (!MsgStack.empty()) {
						currNode = MsgStack.pop();
					}
					leftDirection = false;
				}
			}
		}
	}
	
	/**
	 * Constructor for a single node with null children
	 * 
	 * @param payloadChar - a single character of the encoded string
	 */
	public MsgTree(char payloadChar) {
		this.payloadChar = payloadChar;
		this.data = payloadChar;
		this.left = null;
		this.right = null;
	}
	
	/**
	 * Method to print characters and their binary codes
	 * 
	 * @param root - the given node
	 * @param code - the string with the code for a given letter/node
	 */
	public static void printCodes(MsgTree root, String code) {
		System.out.println("character code\n----------------------");
		for (char ch : code.toCharArray()) {
			createCode(root, ch, letterCode = "");
			System.out.println("    " + (ch == '\n' ? "\\n" : ch + " ") + "    " + letterCode);
		}
	}
	
	/**
	 * Decodes the secret message
	 * 
	 * @param codes - the give node/letter
	 * @param msg - the binary message to be decoded
	 */
	public void decode(MsgTree codes, String msg) {
		System.out.println();
		System.out.println("MESSAGE:");
		MsgTree currNode = codes;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < msg.length(); i++) {
			char code = msg.charAt(i);
			currNode = (code == '0' ? currNode.left : currNode.right);
			if (currNode.payloadChar != '^') {
				createCode(codes, currNode.payloadChar, letterCode = "");
				sb.append(currNode.payloadChar);
				currNode = codes;
			}
		}
		System.out.println(sb.toString());
	}
	
	/**
	 * Recursively finds the code of a given node
	 * 
	 * @param root - the given node/letter
	 * @param rootChar - the payloadChar of the given node
	 * @param code - the binary code of the given node
	 * @return
	 */
	private static boolean createCode(MsgTree root, char rootChar, String code) {
		if (root != null) {
			if (root.payloadChar == rootChar) {
				letterCode = code;
				return true;
			}
			return createCode(root.left, rootChar, code + "0") || createCode(root.right, rootChar, code + "1");
		}
		return false;
	}
	
}
