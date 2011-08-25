/**
 * Main File for Phylogenetic Tree Algorithm
 * William Blair wdblair@bu.edu
 * CS431 Homework 5
 */

public class Algorithm
{
	private static void buildPhylogeneticTree(Phylogeny p, int index)
    {
		p.setName("case_"+index);
		p.buildBinaryMatrix();
		if(!p.isPerfect())
		{
			System.out.println("Not a perfect phylogeny.");
			return;
		}
		p.buildPaths();
		p.buildTree();
		p.pruneTree();
		p.saveTree();
    }
	
    public static void main(String[] args)
    {
		//Get the file
    	Parser p = new Parser("species.input");
    	//Perform the algorithm on each phylogeny
    	int i = 0;
    	while(p.hasPhylogeny())
			{
    			System.out.println("Case "+i+":");
				buildPhylogeneticTree(p.nextPhylogeny(), i);
				i++;
    		}
    }
}