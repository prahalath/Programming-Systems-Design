// Name: Prahalathan Sundaramoorthy
// USC NetID: prahalat
// CS 455 PA4
// Fall 2020

import java.util.Comparator;
import java.util.Map;

/**
 * A class that implements comparator interface of type Map entries to be used by Collections.sort() method
 * to sort an ArrayList of map entries.
 * Returns value greater than 0 if entry 2's value is greater than entry 1. If both values are same, words are compared
 * in alphabetical order.
 */
public class ScoreMapComparator implements Comparator<Map.Entry<String, Integer>> {
    public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
        if (e2.getValue() - e1.getValue() != 0){
            return e2.getValue() - e1.getValue();
        }
        return e1.getKey().compareTo(e2.getKey());
    }
}
