/**
 * Parser for the Phylogenetic Tree Algorithm
 * William Blair wdblair@bu.edu
 * CS431 Homework 5
 */
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Parser {
	private static Scanner scan;
	
	public Parser(String file)  {
		
		try {
		scan = new Scanner(new File(file)).useDelimiter("EOP\n");
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public Phylogeny nextPhylogeny() {
		return  new Phylogeny(scan.next());
	}
	
	public Boolean hasPhylogeny() {
		return scan.hasNext();
	}
}
