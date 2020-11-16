// Name: Prahalathan Sundaramoorthy
// USC NetID: prahalat
// CS 455 PA4
// Fall 2020


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Calculates scores for strings. Scores are generated according to the following (ignores cases):
 * (1 point)-A, E, I, O, U, L, N, S, T, R
 * (2 points)-D, G
 * (3 points)-B, C, M, P
 * (4 points)-F, H, V, W, Y
 * (5 points)-K
 * (8 points)- J, X
 *(10 points)-Q, Z
 *
 * Representation Invariant : the score table array is always of size 26 and contains non-negative values.
 */
public class ScoreTable {
    private final static int ALPHABET_COUNT = 26;
    private int[] scores = new int[ALPHABET_COUNT];

    public ScoreTable() {
    char[] alphabets = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    for (char ch : alphabets) {
        if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u' || ch == 'l' || ch == 'n' || ch == 's' || ch == 't' || ch == 'r') {
            scores[ch - 'a'] = 1;
        }
        if (ch == 'd' || ch == 'g') {
            scores[ch - 'a'] = 2;
        }
        if (ch == 'b' || ch == 'c' || ch == 'm' || ch == 'p') {
            scores[ch - 'a'] = 3;
        }
        if (ch == 'f' || ch == 'h' || ch == 'v' || ch == 'w' || ch == 'y') {
            scores[ch - 'a'] = 4;
        }
        if (ch == 'k') {
            scores['k' - 'a'] = 5;
        }
        if (ch == 'j' || ch == 'x') {
            scores[ch - 'a'] = 8;
        }
        if (ch == 'q' || ch == 'z') {
            scores[ch - 'a'] = 10;
        }
    }
    }

    /**
     * Calculates scores for the words present in an arrayList of strings.
     * @param allAnagrams An arrayList of strings for which scores are to be calculated.
     * @return a HashMap with words as keys and corresponding scores as values.
     * PRE: each string in allAnagrams are valid strings (only contains letters).
     */
    public Map<String, Integer> getScores(ArrayList<String> allAnagrams){
        Map<String, Integer> scoresMap = new HashMap<>();
        int sum;
        for (String word : allAnagrams){
            sum = 0;
            for (char ch : word.toCharArray()){
                sum = sum + scores[Character.toLowerCase(ch) - 'a'];
            }
            scoresMap.put(word, sum);
        }
        return scoresMap;
    }
}
