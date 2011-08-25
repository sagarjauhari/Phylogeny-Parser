/**
 * The Completed Phylogenetic Tree in the Algorithm
 * William Blair wdblair@bu.edu
 * CS431 Homework 5
 */
import java.util.LinkedList;
import java.util.ListIterator;

public class PhylogeneticTree {
	private int id;
	private String label;
	private String leftEdgeLabel;
	private String rightEdgeLabel;
	private PhylogeneticTree left;
	private PhylogeneticTree right;
	private int children;
	
	public PhylogeneticTree(String label) {
		this.label = label;
		id = 0;
		children = 0;
	}
	/**
	 * Adds a species path of traits to the Tree
	 * 
	 */
	public void addToTree(LinkedList<String> path, String speciesName)
	{
		ListIterator<String> iterator = path.listIterator();
		PhylogeneticTree tmp = this;
		
		while(iterator.hasNext())
		{
			String nextEdgeLabel = iterator.next();
			//First check if this trait already exists
			if(tmp.getLeftEdgeLabel() == nextEdgeLabel)
			{
				tmp = tmp.getLeftTree();
				continue;
			}
			else if(tmp.getRightEdgeLabel() == nextEdgeLabel)
			{
				tmp = tmp.getRightTree();
				continue;
			}
			//If we get this far, it means we need to add the label, check if we can add on either side
			if(tmp.getLeftTree() == null) {
				String nodeLabel = (nextEdgeLabel == "%") ? speciesName : null;
				tmp.setLeftTree(new PhylogeneticTree(nodeLabel), nextEdgeLabel);
				tmp = tmp.getLeftTree();
			} 
			else if(tmp.getRightTree() == null) {
				String nodeLabel = (nextEdgeLabel == "%") ? speciesName : null;
				tmp.setRightTree(new PhylogeneticTree(nodeLabel), nextEdgeLabel);
				tmp = tmp.getRightTree();
			}
		}
	}
	
	/**
	 * 
	 * Below are all the standard accessor and setter methods
	 */
	
	
	public void setLeftTree(PhylogeneticTree l, String edgeLabel)
	{
		if(left == null) {
			children++;
		}
		left = l;
		leftEdgeLabel = edgeLabel;
	}
	
	public void setRightTree(PhylogeneticTree r, String edgeLabel) 
	{
		if(right == null) {
			children++;
		}
		right = r;
		rightEdgeLabel = edgeLabel;
	}
	
	public int getCountChildren()
	{
		return children;
	}
	
	public PhylogeneticTree getLeftTree()
	{
		return left;
	}
	
	public PhylogeneticTree getRightTree()
	{
		return right;
	}
	
	public void setLeftEdgeLabel(String label)
	{
		leftEdgeLabel = label;
	}
	
	public void setRightEdgeLabel(String label)
	{
		rightEdgeLabel = label;
	}
	
	public String getLeftEdgeLabel()
	{
		return leftEdgeLabel;
	}
	
	public String getRightEdgeLabel()
	{
		return rightEdgeLabel;
	}
	
	public String getLabel()
	{
		return label;
	}
	
	public void setLabel(String label)
	{
		this.label = label;
	}
	
	public void removeLeftTree()
	{
		left = null;
	}
	
	public void removeRightTree()
	{
		right = null;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getId()
	{
		return id;
	}
}