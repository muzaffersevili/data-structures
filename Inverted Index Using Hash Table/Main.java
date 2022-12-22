import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {

		//long startTime_Indexing = System.currentTimeMillis();
		
		HashTable<String, ArrayList<Node<String, Integer>>> table = new HashTable<String, ArrayList<Node<String, Integer>>>(1024);
		String DELIMITERS = "[-+=" + " " + // space
				"\r\n " + // carriage return line fit
				"1234567890" + // numbers
				"â€™'\"" + // apostrophe
				"(){}<>\\[\\]" + // brackets
				":" + // colon
				"," + // comma
				"â€’â€“â€”â€•" + // dashes
				"â€¦" + // ellipsis
				"!" + // exclamation mark
				"." + // full stop/period
				"Â«Â»" + // guillemets
				"-â€�" + // hyphen
				"?" + // question mark
				"â€˜â€™â€œâ€�" + // quotation marks
				";" + // semicolon
				"/" + // slash/stroke
				"â�„" + // solidus
				"â� " + // space?
				"Â·" + // interpunct
				"&" + // ampersand
				"@" + // at sign
				"*" + // asterisk
				"\\" + // backslash
				"â€¢" + // bullet
				"^" + // caret
				"Â¤Â¢$â‚¬Â£Â¥â‚©â‚ª" + // currency
				"â€ â€¡" + // dagger
				"Â°" + // degree
				"Â¡" + // inverted exclamation point
				"Â¿" + // inverted question mark
				"Â¬" + // negation
				"#" + // number sign (hashtag)
				"â„–" + // numero sign ()
				"%â€°â€±" + // percent and related signs
				"Â¶" + // pilcrow
				"â€²" + // prime
				"Â§" + // section sign
				"~" + // tilde/swung dash
				"Â¨" + // umlaut/diaeresis
				"_" + // underscore/understrike
				"|Â¦" + // vertical/pipe/broken bar
				"â�‚" + // asterism
				"â˜�" + // index/fist
				"âˆ´" + // therefore sign
				"â€½" + // interrobang
				"â€»" + // reference mark
				"]";

		ArrayList<String> stop_words = FolderOperations.Read_lines(System.getProperty("user.dir") + "\\stop_words_en.txt");

		File directory = new File(System.getProperty("user.dir") + "\\bbc");

		for (int i = 0; i < directory.list().length; i++) {

			String subfolder = directory.list()[i];
			File subdirectory = new File(System.getProperty("user.dir") + "\\bbc\\" + subfolder);

			for (int j = 0; j < subdirectory.list().length; j++) {

				String file = subdirectory.list()[j];
				String content = FolderOperations.Read_file(System.getProperty("user.dir") + "\\bbc\\" + subfolder + "\\" + file);

				String[] words = content.split(DELIMITERS);

				String filename=subfolder+" "+file;

				for (int k = 0; k < words.length; k++) {
					String word = words[k].toLowerCase();

					if (stop_words.indexOf(word) == -1 && word.length() > 0) {

						if (table.get(word) == null) {
							ArrayList<Node<String, Integer>> value = new ArrayList<Node<String, Integer>>();
							Node<String, Integer> node = new Node<String, Integer>(filename, 1);
							value.add(node);
							table.put(word, value);
						} 

						else {
							ArrayList<Node<String, Integer>> list = table.get(word);
							boolean found = false;

							for (int l = 0; l < list.size(); l++) {

								Node<String, Integer> node = list.get(l);
								if (node.getKey().equals(filename)) {
									node.setValue(node.getValue() + 1);
									found = true;
									break;
								}
							}

							if (found == false) {
								Node<String, Integer> node = new Node<String, Integer>(filename, 1);
								table.get(word).add(node);
							}
						}
					}
				}
			}
		}
		
		/*
		long endTime_Indexing = System.currentTimeMillis();
		long estimatedTime_Indexing = endTime_Indexing - startTime_Indexing;
		System.out.println("Estimated Time: "+estimatedTime_Indexing);
		*/
		
		//Collusion Number
		//table.collision();
		
		long startTime_Search = System.currentTimeMillis();
		
		// Search
		ArrayList<String> search = FolderOperations.Read_lines(System.getProperty("user.dir") + "\\search.txt");

		for(int i=0; i<search.size(); i++) {

			String word=search.get(i);
			ArrayList<Node<String, Integer>> a = table.get(word);

			if(a!=null) {
				int docNum = a.size();
				System.out.println();
				System.out.println(word+" found in "+docNum + " documents!");

				for (int v = 0; v < a.size(); v++) {
					Node<String, Integer> node = a.get(v);
					System.out.println(node.getKey() + ":" + node.getValue());
				}
			}
			else 
			{
				System.out.println();
				System.out.println(word+" not found");
			}
		}
		
		/*
		long endTime_Search = System.currentTimeMillis();
		long estimatedTime_Search = endTime_Search - startTime_Search;
		System.out.println("Estimated Time: "+estimatedTime_Search);
		*/
		
		
		//Extra: Searching a word
		boolean flag=true;
		Scanner scn = new Scanner(System.in);
		while (flag) {
			System.out.println("\nIf you want to exit, please enter 'deü'");
			System.out.print("Please enter a word to search: ");
			String input = scn.next();

			if(input.equals("deü")) {
				System.out.println("\nHave a Good Day!");
				flag=false;break;
			}

			else {
				ArrayList<Node<String, Integer>> a = table.get(input);
				if(a!=null) {
					int docNum = a.size();
					System.out.println(docNum + " " + "documents found!");

					for (int i = 0; i < a.size(); i++) {
						Node<String, Integer> node = a.get(i);
						System.out.println(node.getKey() + ":" + node.getValue());
					}
				}
				else {
					System.out.println();
					System.out.println(input+" not found!");
				}
			}
		}
		scn.close();
	}
}
