// Name: Prahalathan Sundaramoorthy
// USC NetID: prahalat
// CS 455 PA4
// Fall 2020

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
   A Rack of Scrabble tiles
   Representation Invariant : a rack of words is always represented as a string
 */

public class Rack {
   private String rackWord;

   /**
    * Creates a rack with the given word
    * @param word
    */
   public Rack(String word){
      rackWord = word;
   }

   /**
    * A public interface to get all the subsets of the rack word. This method first creates a unique word (with no duplicates)
    * and a corresponding multiplicity array to be passed as arguments to the recursive function allSubsets.
    * @return ArrayList of strings that are subsets of the rack word.
    */
   public ArrayList<String> getSubsets(){
      Map<Character, Integer> charCounter = new HashMap<>(); // Hashmap to store character and its counts
      for (int i = 0; i < rackWord.length(); i++){
         if (!charCounter.containsKey(rackWord.charAt(i))){
            charCounter.put(rackWord.charAt(i), 1);
         }
         else {
            charCounter.put(rackWord.charAt(i), charCounter.get(rackWord.charAt(i)) + 1);
         }
      }
      int[] mul = new int[charCounter.size()];
      String unique = "";
      int i = 0;
      for (char ch : charCounter.keySet()) {
         unique = unique + ch;
         mul[i] = charCounter.get(ch);
         i += 1;
      }
      ArrayList<String> subsets = allSubsets(unique, mul, 0);

      return subsets;

   }

   /**
      Finds all subsets of the multiset starting at position k in unique and mult.
      unique and mult describe a multiset such that mult[i] is the multiplicity of the char
           unique.charAt(i).
      PRE: mult.length must be at least as big as unique.length()
           0 <= k <= unique.length()
      @param unique a string of unique letters
      @param mult the multiplicity of each letter from unique.  
      @param k the smallest index of unique and mult to consider.
      @return all subsets of the indicated multiset.  Unlike the multiset in the parameters,
      each subset is represented as a String that can have repeated characters in it.
      @author Claire Bono
    */
   private static ArrayList<String> allSubsets(String unique, int[] mult, int k) {
      ArrayList<String> allCombos = new ArrayList<>();
      
      if (k == unique.length()) {  // multiset is empty
         allCombos.add("");
         return allCombos;
      }
      
      // get all subsets of the multiset without the first unique char
      ArrayList<String> restCombos = allSubsets(unique, mult, k+1);
      
      // prepend all possible numbers of the first char (i.e., the one at position k) 
      // to the front of each string in restCombos.  Suppose that char is 'a'...
      
      String firstPart = "";          // in outer loop firstPart takes on the values: "", "a", "aa", ...
      for (int n = 0; n <= mult[k]; n++) {   
         for (int i = 0; i < restCombos.size(); i++) {  // for each of the subsets 
                                                        // we found in the recursive call
            // create and add a new string with n 'a's in front of that subset
            allCombos.add(firstPart + restCombos.get(i));  
         }
         firstPart += unique.charAt(k);  // append another instance of 'a' to the first part
      }
      
      return allCombos;
   }

   
}
