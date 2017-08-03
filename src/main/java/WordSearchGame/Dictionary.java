package WordSearchGame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Dictionary{
	TST dictionary;
	public Dictionary(String file) throws IOException{
		String word;
		boolean first=true;
		BufferedReader br = new BufferedReader(new FileReader(file));
		while ((word = br.readLine()) != null) {
			if(first){
				dictionary=new TST(word);
				first=false;
				continue;
			}
			dictionary.insert(word);
		}
		br.close();
	}
	public TST getDictionary(){
		return dictionary;
	}
	public static void main(String [] args) throws IOException{
		Dictionary dict=new Dictionary("C:/Users/hp/Documents/dictionary.txt");
		//dict.dictionary.printTrie();
		System.out.println(dict.dictionary.find("regardless"));
	}
}
class TST {
	Node root;
	public TST(String first_word){
		root= new Node(first_word.charAt(0));
		//System.out.println(root.letter);
		insert(first_word);
	}
	public Node findNode(Node node, char letter){
		while(true){
			if(node.letter>letter){
				if(node.left==null){
					return null;
				}
				node=node.left;
			}
			else if(node.letter<letter){
				if(node.right==null){
					return null;
				}
				node=node.right;
			}
			else if(node.letter==letter){
				return node;
			}
		}
	}
	public void insert(String word){
		Node node=root;
		for(int i=0;i<word.length();i++){
			char letter= word.charAt(i);
			while(true){
				if(node.letter>letter){
					if(node.left==null){
						node.left=new Node(word.charAt(i));
						node=node.left;
						i++;
						for(;i<word.length();i++){
							node.center=new Node(word.charAt(i));
							node=node.center;
						}
						i--;
						break;
					}
					node=node.left;
				}
				else if(node.letter<letter){
					if(node.right==null){
						node.right=new Node(word.charAt(i));
						node=node.right;
						i++;
						for(;i<word.length();i++){
							node.center=new Node(word.charAt(i));
							node=node.center;
						}
						i--;
						break;
					}
					node=node.right;
				}
				else if(node.letter==letter){
					if(node.center==null){
						i++;
						for(;i<word.length();i++){
							node.center=new Node(word.charAt(i));
							node=node.center;
						}
						i--;
						break;
					}
					if(i!=word.length()-1){
						node=node.center;
					}
					break;
				}
			}
			if(i==word.length()-1){
				node.isWord=true;
			}
		}
	}
	public boolean find(String word){
		Node node=root;
		for(int i=0;i<word.length();i++){
			char letter= word.charAt(i);
			//System.out.println("NEW LETTER: "+letter);
			while(true){
				if(node.letter>letter){
					//System.out.println(letter+ " is smaller than "+node.letter+" so checking left");
					if(node.left==null){
						return false;
					}
					node=node.left;
				}
				else if(node.letter<letter){
					//System.out.println(letter+ " is larger than "+node.letter+" so checking right");
					if(node.right==null){
						return false;
					}
					node=node.right;
				}
				else if(node.letter==letter){
					if(i!=word.length()-1){
						node=node.center;
					}
					break;
				}
			}
		}
		if(node.isWord){
			return true;
		}
		return false;
	}
	public void printTrie(){
		printTrie(root);
		
	}
	private void printTrie(Node node){
		if(node==null){
			return;
		}
		printTrie(node.left);
		printTrie(node.center);
		printTrie(node.right);
		System.out.println(node.letter);
	}
}

class Node{
	boolean isWord;
	char letter;
	Node left;
	Node right;
	Node center;
	Node(char letter){
		this.letter=letter;
		left=null;
		right=null;
		center=null;
		isWord=false;
	}
}
