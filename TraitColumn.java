/**
 * Represents a Trait Column in the Binary Matrix for the Phylogenetic Tree
 * William Blair wdblair@bu.edu
 * CS431 Homework 5
 */
public class TraitColumn {
	
	/**
	 * The name of the trait
	 */
	private String trait;
	
	/**
	 * An array of Booleans indicating if this trait is found in each species
	 */
	private Boolean[] species;
	
	/**
	 * The integer representing this column of the binary matrix
	 * 
	 */
	private int weightedValue;
	
	public TraitColumn(String name, Boolean[] species) {
		this.trait = name;
		this.species = species;
		setWeightedValue();
	}
	
	/**
	 * Use bitwise operators to build the integer representation of the column
	 */
	private void setWeightedValue() {
		weightedValue = 0;
		int max_shift= species.length - 1; 
		for(int i = 0; i < species.length; i++) {
			int bit = (species[i]) ? 1 : 0;
			weightedValue |= bit << (max_shift - i);
		}
	}
	
	/**
	 * Checks if this column is perfect with respect to another column ahead of it using bitwise operations.
	 * 1.Use & to see if the other trait depends on this trait. If not, return true
	 * 2.If it is dependent, make sure it does not occur without this trait occurring first.
	 */
	public Boolean isPerfectWith(TraitColumn other_trait) {
		
		if((weightedValue & other_trait.getWeightedValue()) != 0)
		{
			int xor = weightedValue ^ other_trait.getWeightedValue();
			int masked = xor & (~weightedValue);
			return (masked == 0) ? true : false;
		}
		return true;
	}
	
	public int getWeightedValue() {
		return weightedValue;
	}
	
	public String getTrait() {
		return trait;
	}
	
	public Boolean[] getSpecies() {
		return species;
	}
}
