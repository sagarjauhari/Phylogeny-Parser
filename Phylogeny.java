/**
 * Phylogeny in the Phylogenetic Tree Algorithm
 * William Blair wdblair@bu.edu
 * CS431 Homework 5
 */
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Iterator;

public class Phylogeny 
{
	/**
	 * Phylogeny's Name (Case i)
	 */
	private String name;
	/**
	 * Traits in the Phylogeny
	 */
    private String[] traits;
    /**
     * Species in this Phylogeny
     */
    private Species[] species; 
    /**
     * The Binary Matrix, consisting of columns of traits
     */
	private TraitColumn[] binary_matrix;
	/**
	 * Indicates if the Phylogeny is perfect
	 */
	private Boolean perfect;
	/**
	 * The Tree representation of the Phylogey
	 */
	private PhylogeneticTree tree;
	
	public Phylogeny(String species_traits)
	{
		build(species_traits);
	}
	
	/**
	 * Stores:
	 * All Species and their Traits
	 * A Distinct List of Traits
	 */
	private void build(String species_traits)
	{
		//Temporary Storage for Traits and Species
		HashSet<String> traits = new HashSet<String>(200);
		LinkedList<Species> species = new LinkedList<Species>();
		
		String[] lines = species_traits.split("\n");
        for(int i=0; i < lines.length; i++) 
        	{
        		Species s = new Species();
		        String[] parts = lines[i].split(",");
		        //First string is species.
		        s.setName(parts[0]);
		        //Add all the Species' Traits
		        for(int j = 1; j < parts.length; j++)
		            {
		                if(!traits.contains(parts[j]))
		                    {
		                        traits.add(parts[j]);
		                    }  
		                s.addTrait(parts[j]);
		            }
		        species.add(s);
        	}
        //Populate our Phylogeny's Species Array
        ListIterator<Species> s_iterator = species.listIterator();
        this.species = new Species[species.size()];
        int i = 0;
        while(s_iterator.hasNext())
        	{	
        		
	        	this.species[i] = s_iterator.next();
	            i++;
	        }
        //Populate our Phylogeny's Traits Array
        Iterator<String> t_iterator = traits.iterator();
        this.traits = new String[traits.size()];
        i = 0;
        while(t_iterator.hasNext())
        	{
        		this.traits[i] = t_iterator.next();
        		i++;
        	}
    }
	
	/**
	 * Builds a Sorted Binary Matrix from the Phylogeny's Species and Trait data
	 */
	public void buildBinaryMatrix()
	{
		binary_matrix = new TraitColumn[traits.length];
		for(int i = 0; i < traits.length; i++ )
			{
				Boolean[] tempArray = new Boolean[species.length];
				for(int j = 0; j < species.length; j++)
					{
						tempArray[j] = species[j].hasTrait(traits[i]);
					}
				binary_matrix[i] = new TraitColumn(traits[i], tempArray);
			}
		Arrays.sort(binary_matrix, new CompareTraits());
		//Set if the Phylogeny is Perfect
		checkPerfect();
	}
	
	/**
	 * Builds the paths of traits from the Phylogeny's Binary Matrix
	 */
	public void buildPaths()
	{
		for(TraitColumn trait : binary_matrix) {
			for(int i= 0; i < trait.getSpecies().length; i++ ) {
				if(trait.getSpecies()[i]) {
					species[i].addToPath(trait.getTrait());
				}
			}
		}
		//Symbolize the end of the path
		for(Species singleSpecies : species) {
			singleSpecies.addToPath("%");
		}
	}
	
	/**
	 * Construct the Tree Data Structure
	 */
	public void buildTree()
	{
		tree = new PhylogeneticTree(null);
		//Add each Species to the Pathh
		for(Species singleSpecies : species) {
			tree.addToTree(singleSpecies.getPath(), singleSpecies.getName());
		}
	}
	
	/**
	 * Removes all the % signs
	 */
	public void pruneTree()
	{
		inOrderPrune(tree);
	}
	
	/**
	 * Saves the Tree to a DOT file
	 */
	public void saveTree()
	{
		FileWriter outFile = null;
		try {
			outFile = new FileWriter(name+".dot");
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrintWriter out = new PrintWriter(outFile);	
		//Start our DOT file
		out.println("digraph "+name+" {");
		out.println("size=\"8,11\";");
		out.println("node [shape=point];");
		out.println("edge [arrowhead=none];");
		inOrderSave(tree, out,1);
		out.println("}");
		out.close();
	}
	/**
	 * An Inorder Traversal of the Tree, assigning an ID to each Node so that a file can be generated.
	 */
	private int inOrderSave(PhylogeneticTree t, PrintWriter out, int index)
	{
		if(t.getLeftTree() != null){
			index = inOrderSave(t.getLeftTree(), out,index);
		}
		if(t.getId() == 0)
		{
			t.setId(index);
			index++;
			if(t.getLabel() != null){
				out.println(t.getId()+"[shape=ellipse,label="+t.getLabel()+"];");
			}
			if(t.getLeftTree() != null) {
				String label = (t.getLeftEdgeLabel() != "") ? "[label="+t.getLeftEdgeLabel()+"]": "";
				out.println(t.getId() +"->"+ t.getLeftTree().getId()+label+";");
			}
		}
		
		if(t.getRightTree() != null){
			index = inOrderSave(t.getRightTree(),out,index);
		}
		
		if(t.getRightTree()!= null) {
			String label = (t.getRightEdgeLabel() != "") ? "[shape=ellipse,label="+t.getRightEdgeLabel()+"]" : "";
			out.println(t.getId()+"->"+t.getRightTree().getId()+label+";");
		}
		
		return index;
	}
	
	/**
	 * An Inorder Traversal of the Tree, removing any unnecessary edges or nodes.
	 */
	private void inOrderPrune(PhylogeneticTree t)
	{
		if (t.getLeftTree()  != null){
			inOrderPrune(t.getLeftTree());
		}
		
		if(t.getLeftEdgeLabel() == "%") {
			
			if(t.getCountChildren() == 2) {
				t.setLeftEdgeLabel("");
			} 
			else {
				t.setLabel(t.getLeftTree().getLabel());
				t.removeLeftTree();
			}
		}
		else if(t.getRightEdgeLabel() == "%") {
			if(t.getCountChildren() == 2) {
				t.setRightEdgeLabel("");
			} 
			else {
				t.setLabel(t.getRightTree().getLabel());
				t.removeRightTree();
			}
		}
		if (t.getRightTree() != null) {
			inOrderPrune(t.getRightTree());
		}
	}
	
	/*
	 * Check if each pair of traits are compatible with one another.
	 * Uses the compatibility statement in Theorem 2.6 in Carolina Uhler's paper, Finding a Perfect Phylogeny
	 */
	private void checkPerfect() {
		for(int i = 0; i < binary_matrix.length; i++) {
			for(int j = i+1; j < binary_matrix.length; j++) {
				if(!binary_matrix[i].isPerfectWith(binary_matrix[j])) {
					perfect = false;
					return;
				}
			}
		}
		perfect = true;
	}
	
	public Boolean isPerfect()
	{
		return perfect;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
}
