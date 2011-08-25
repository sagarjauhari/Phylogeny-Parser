/**
 * Species in the Phylogenetic Tree Algorithm
 * William Blair wdblair@bu.edu
 * CS431 Homework 5
 */

import java.util.HashSet;
import java.util.LinkedList;

public class Species 
{
    private String name;
    /**
     * A Set of this Species' traits, used for quickly determining what traits this Species has.
     */
    private HashSet<String> traits;
    /**
     * The path of Traits that lead to this Species in the Phylogeny
     */
    private LinkedList<String> path;
    
    public Species(){
    	traits = new HashSet<String>();
    	path = new LinkedList<String>();
    }
    
    public String getName() 
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void addTrait(String c)
    {
        traits.add(c);
    }
    
    public Boolean hasTrait(String t)
    {
    	return traits.contains(t);
    }
    
    public HashSet<String> getTraits(){
    	return traits;
    }
    
    public void setPath(LinkedList<String> path)
    {
    	this.path = path;
    }
    
    public LinkedList<String> getPath()
    {
    	return path;
    }
    
    public void addToPath(String trait)
    {
    	path.add(trait);
    }
}