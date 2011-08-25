/**
 * Compares two columns of the binary matrix
 * William Blair wdblair@bu.edu
 * CS431 Homework 5
 *
 */
import java.util.Comparator;

public class CompareTraits implements Comparator<TraitColumn> {
	/**
	 * Put the greater column at the beginning of the binary matrix
	 */
	public int compare(TraitColumn t1, TraitColumn t2) {
		int val1 = t1.getWeightedValue();
		int val2 = t2.getWeightedValue();
		return (val1 > val2 ) ? -1 : (val1 < val2 ) ? 1 :  0;
	}
}
