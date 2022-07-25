import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FolderOperations {

	public static ArrayList<String> Read_lines(String f)  {
		try {
		BufferedReader reader = new BufferedReader(new FileReader(f));

		ArrayList<String> lines = new ArrayList<String>();
		String line;

		while ((line = reader.readLine()) != null) {
			lines.add(line);
		}
		reader.close();

		return lines;
		}
		catch (NumberFormatException numformat) {
			System.out.println("Document is not found!");
			return new ArrayList<String>();
		}
		catch (IOException io) {
			System.out.println("Document is not found!");
			return new ArrayList<String>();
		}
	}

	public static String Read_file(String f) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			  StringBuilder content = new StringBuilder();
			    String line;
			     
			    while ((line = reader.readLine()) != null) {
			        content.append(line);
			        content.append(System.lineSeparator());
			    }
			    reader.close();
			 
			    return content.toString();
		}
		catch (NumberFormatException numformat) {
			System.out.println("Document is not found!");
			return "";
		}
		catch (IOException io) {
			System.out.println("Document is not found!");
			return "";
		}
	}
}
