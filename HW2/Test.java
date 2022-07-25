import java.io.*;

public class Test {
	
	public static void read_file(File file_name, Graph graph_name) throws NumberFormatException, IOException {
		
		FileReader fileReader = new FileReader(file_name);
		String line;
		BufferedReader br = new BufferedReader(fileReader);

		while ((line = br.readLine()) != null) {
			String[] elements=line.split(" ");
			graph_name.addEdge(Integer.parseInt(elements[0]), Integer.parseInt( elements[1]));
		}
		br.close();
	}
	
	public static void main(String[] args) throws IOException {
		
		
		Graph karate_club_graph = new Graph(34);
		Graph facebook_social_graph = new Graph(1518);
		
		File karate_club = new File("karate_club_network.txt");
		File facebook_social = new File("facebook_social_network.txt");
		
		read_file(karate_club,karate_club_graph);
		read_file(facebook_social,facebook_social_graph);
		
        for(int i=1;i<=karate_club_graph.size()-1;i++) {
        	for(int j=i+1;j<=karate_club_graph.size();j++) {
        		karate_club_graph.BFS(i,j);
            }
        }
        
        System.out.println("2019510069 Muzaffer Sevili\n");
        System.out.print("Zachary Karate Club Network - The The Highest Node for Betweennes: ");
        karate_club_graph.betweenness();
        
        System.out.print("Zachary Karate Club Network - The The Highest Node for Closeness: ");
        karate_club_graph.closeness();
        System.out.println();
        
        //Slow Code
        /*
        for(int i=1;i<=facebook_social_graph.size()-1;i++) {
        	for(int j=i+1;j<=facebook_social_graph.size();j++) {
        		facebook_social_graph.BFS(i,j);
            }
        }
        System.out.print("Facebook Social Network - The The Highest Node for Betweennes: ");
        facebook_social_graph.betweenness();
        
        System.out.print("Facebook Social Network - The The Highest Node for Closeness: ");
        facebook_social_graph.closeness();
        */
}
}