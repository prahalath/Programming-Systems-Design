// Name: Prahalathan Sundaramoorthy
// USC NetID: prahalat
// CS 455 PA4
// Fall 2020

import java.io.FileNotFoundException;
import java.util.*;

/**
 * Main program that prompts for rack word and dictionary from user. If no dictionary file is mentioned "sowpods.txt"
 * is used as the dictionary. Handles exception and calls corresponding functions to process the rack word.
 *
 */
public class WordFinder {
    public static void main(String[] args){
        String fileName;
        if (args.length == 0){
            fileName = "sowpods.txt";
        }
        else {
            fileName = args[0];
        }
        try {
            AnagramDictionary dictionary = new AnagramDictionary(fileName);
            Scanner in = new Scanner(System.in);
            String word;
            System.out.println("Type . to quit.");
            while (true){
                System.out.print("Rack? ");
                word = in.next();
                if (word.equals(".")){
                    return;
                }
                processRack(word, dictionary);
            }
        }
        catch (FileNotFoundException e){
            System.out.println("ERROR: Dictionary file \"" + fileName+ "\" does not exist.");
            System.out.println("Exiting program.");
        }
        catch (IllegalDictionaryException e){
            System.out.println(e.getMessage());
            System.out.println("Exiting program.");
        }
    }

    /**
     * Creates a rack with the input string. Calls corresponding methods to find all anagrams from the dictionary
     * for each subset of the word; also calls methods to sorts the anagrams by scores and print them.
     * @param word the string entered by the user
     * @param dictionary dictionary created from dictionary file
     */
    private static void processRack(String word, AnagramDictionary dictionary){
        Rack rack = new Rack(word);
        ArrayList<String> allAnagrams = new ArrayList<>();
        ArrayList<String> anagrams = null;
        for (String subsetWord : rack.getSubsets()){
            if (!subsetWord.equals("")) {
                anagrams = dictionary.getAnagramsOf(subsetWord);
                if (anagrams != null){
                    allAnagrams.addAll(anagrams);
                }
            }
        }
        ArrayList<Map.Entry<String,Integer>> scoresArray = getAndSortScores(allAnagrams);
        printScores(scoresArray, word);
    }

    /**
     * Gets scores for each anagram and sorts them. Words with high scores are placed first, while words with same scores are
     * sorted by alphabetical order. Sorting is done using a class that implements comparator interface.
     * @param allAnagrams an ArrayList of anagrams generated from the letters of the rack
     * @return a sorted ArrayList of map entries whose keys are anagrams and values are scores
     */
    private static ArrayList<Map.Entry<String,Integer>> getAndSortScores(ArrayList<String> allAnagrams){
        ScoreTable scoreGenerator = new ScoreTable();
        Map<String, Integer> scoresMap = scoreGenerator.getScores(allAnagrams);
        ArrayList<Map.Entry<String,Integer>> scoresArray = new ArrayList<>();

        for(Map.Entry<String,Integer> entry: scoresMap.entrySet())
            scoresArray.add(entry);

        Collections.sort(scoresArray, new ScoreMapComparator());
        return scoresArray;
    }

    /**
     * Prints the sorted anagrams along with their scores to system out.
     * @param scoresArray  ArrayList of map entries whose keys are anagrams and values are scores
     * @param word input rack letters
     */
    private static void printScores(ArrayList<Map.Entry<String,Integer>> scoresArray, String word){
        System.out.println("We can make " + scoresArray.size() + " words from \""+ word +"\"");
        if (scoresArray.size() > 0){
            System.out.println("All of the words with their scores (sorted by score):");
            for(Map.Entry<String, Integer> entry: scoresArray){
                System.out.println(entry.getValue() + ": " + entry.getKey());
            }
        }
    }
    }
