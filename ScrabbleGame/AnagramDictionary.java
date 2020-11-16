// Name: Prahalathan Sundaramoorthy
// USC NetID: prahalat
// CS 455 PA4
// Fall 2020

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


/**
   A dictionary of all anagram sets. 
   Note: the processing is case-sensitive; so if the dictionary has all lower
   case words, you will likely want any string you test to have all lower case
   letters too, and likewise if the dictionary words are all upper case.
 */
public class AnagramDictionary {

   private Map<String, ArrayList<String>> anagramDictionary;
   private Set<String> dictionary;

   /**
      Create an anagram dictionary from the list of words given in the file
      indicated by fileName.  
      @param fileName  the name of the file to read from
      @throws FileNotFoundException  if the file is not found
      @throws IllegalDictionaryException  if the dictionary has any duplicate words
    */
   public AnagramDictionary(String fileName) throws FileNotFoundException,
                                                    IllegalDictionaryException {
      File file = new File(fileName);
      Scanner in = new Scanner(file);
      anagramDictionary = new HashMap<>();
      dictionary = new HashSet<>();
      String word, sortedWord;
      while (in.hasNextLine()){
         word = in.nextLine();
         if (dictionary.contains(word)){
            throw new IllegalDictionaryException("ERROR: Illegal dictionary: dictionary file has a duplicate word: " + word);
         }
         dictionary.add(word);
         sortedWord = sortWord(word);
         if (!anagramDictionary.containsKey(sortedWord)) {
            anagramDictionary.put(sortedWord, new ArrayList<>());
         }
         anagramDictionary.get(sortedWord).add(word);

      }

   }

   /**
    * Sorts the given word. The Arrays.sort() method is used to sort an array of characters.
    * @param word
    * @return sorted word
    */
   private String sortWord(String word) {
      char[] wordArray = word.toCharArray();
      Arrays.sort(wordArray);
      return new String(wordArray);

   }


   /**
      Get all anagrams of the given string. This method is case-sensitive.
      E.g. "CARE" and "race" would not be recognized as anagrams.
      @param s string to process
      @return a list of the anagrams of s
    */
   public ArrayList<String> getAnagramsOf(String s) {
      return anagramDictionary.get(sortWord(s));
   }

}
