package WordSearchGame;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import javax.swing.*;


public class WordSearchSolver implements ActionListener{
	
	static JFrame frame= new JFrame("WordSearchSolver");
	static JPanel panel = new JPanel();
	static JPanel panel1 = new JPanel();
	static JButton solveButton= new JButton("Solve");
	static JTextField[][] jtexts= new JTextField[4][4]; 
	static WordSearchSolver world = new WordSearchSolver();
	static GNode[][] gnodes= new GNode[4][4];
	static TST dictionary;
	static String solveString;
	static boolean solve_with_gui=true;
	static HashSet<String> table;
	
	
	public static void main (String args[]) throws IOException{
		dictionary=new Dictionary("C:/Users/hp/Documents/dictionary.txt").getDictionary();
		table=new HashSet<String>();
		if(solve_with_gui){
			guiSolve();
		}
		else{
			scannerSolve();
			initializeBorad();
			solvePuzzle();
		}
	}

	//private static sort(HashSet table){
		
	//}
	private static void scannerSolve() {
		System.out.println("insert 16 letters:");
		Scanner sc= new Scanner(System.in);
		solveString= sc.nextLine();
		sc.close();
	}

	private static void guiSolve() {
		panel.setLayout(new GridLayout(4,4,0,0));
		panel1.setLayout(new FlowLayout());
		frame.setSize(600, 600);
		for(int i=0; i<4;i++){
			for(int j=0; j<4; j++){
				jtexts[i][j]=new JTextField();
				panel.add(jtexts[i][j]);
			}
		}
		panel1.add(solveButton);
		solveButton.addActionListener(world);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.add(panel1, BorderLayout.NORTH);
		frame.add(panel,BorderLayout.CENTER);
	}

	private static void initializeBorad() {
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				if(solve_with_gui){
					gnodes[i][j]=new GNode(jtexts[i][j].getText().charAt(0));
				}
				else{
					char ch= solveString.charAt((i*4)+j);
					gnodes[i][j]=new GNode(ch);
				}
			}
		}
		for(int i=0;i<gnodes.length;i++){
			for(int j=0;j<gnodes.length;j++){
				if(i!=0 && j!=0){
					gnodes[i][j].adjacentNodes.add(gnodes[i-1][j-1]);
				}
				if(i!=0){
					gnodes[i][j].adjacentNodes.add(gnodes[i-1][j]);
				}
				if(i!=0 && j!=gnodes.length-1){
					gnodes[i][j].adjacentNodes.add(gnodes[i-1][j+1]);
				}
				if(j!=0){
					gnodes[i][j].adjacentNodes.add(gnodes[i][j-1]);
				}
				if(j!= gnodes.length-1){
					gnodes[i][j].adjacentNodes.add(gnodes[i][j+1]);
				}
				if(i!=gnodes.length-1 && j!=0){
					gnodes[i][j].adjacentNodes.add(gnodes[i+1][j-1]);
				}
				if(i!=gnodes.length-1){
					gnodes[i][j].adjacentNodes.add(gnodes[i+1][j]);
				}
				if(i!=gnodes.length-1 && j!=gnodes.length-1){
					gnodes[i][j].adjacentNodes.add(gnodes[i+1][j+1]);
				}
			}
		}
	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource().getClass()==solveButton.getClass()){
			initializeBorad();
			solvePuzzle();
			solveButton.removeActionListener(world);
		}		
	}
	public static void solvePuzzle(){
		for(int i=0;i<gnodes.length;i++){
			for(int j=0;j<gnodes.length;j++){
				//System.out.println("checking letter: "+gnodes[i][j].letter);
				findWords(gnodes[i][j],dictionary.root,"");
			}
		}
	}
	public static void findWords(GNode gNode, Node node, String word){
		if(node==null){
			return;
		}
		Node retNode=dictionary.findNode(node, gNode.letter);
		if(retNode==null){
			return;
		}
		gNode.visited=true;
		word=word+retNode.letter;
		if(retNode.isWord){
			if(!table.contains(word)){
				System.out.println(word);
				table.add(word);
			}
		}
		for(GNode gn: gNode.adjacentNodes){
			if(!gn.visited){
				findWords(gn,retNode.center,word);
			}
		}
		gNode.visited=false;
	}
}
class GNode{
	boolean visited;
	ArrayList<GNode> adjacentNodes;
	char letter;
	GNode(char letter){
		this.letter=letter;
		adjacentNodes=new ArrayList<GNode>();
		visited=false;
	}
}

