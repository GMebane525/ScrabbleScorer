import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

/**
        * Scrabble Scorer app scores valid words based on scrabble dictionary
        * @version January 26, 2023
        * @author Gil Mebane
        */
public class ScrabbleScorer {

    private String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private int[] values = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
    private ArrayList<ArrayList<String>> dictionary;

    /**
     * simple constructor; initializes arrays
     */
    public ScrabbleScorer(){
        dictionary = new ArrayList<>();
        for(int i = 0; i<26; i++)
            dictionary.add(new ArrayList<String>());
        buildDictionary();
    }

    /**
     * parses through the scrabble words text file, creates buckets, and sorts the buckets alphabetically
     */
    public void buildDictionary(){
        try{
                Scanner in = new Scanner(new File("SCRABBLE_WORDS (1).txt"));
                while(in.hasNext()){
                    String word = in.nextLine();
                    dictionary.get(alpha.indexOf(word.substring(0, 1))).add(word);
                }
                in.close();
                // now I need to sort all the buckets
                for(int i = 0; i < dictionary.size(); i++)
                    Collections.sort(dictionary.get(i));
        }

        catch(Exception e){
            System.out.println("Error here: " + e);
        }
    }

    /**
     * determines the validity of user input based on whether the word can be found in dictionary and eliminates number inputs
     * @param word input for a possible scrabble word
     * @return true if valid word, false otherwise
     */
    public boolean isValidWord(String word){

        return (alpha.indexOf(word.substring(0, 1)) > 0) && (Collections.binarySearch(dictionary.get(alpha.indexOf(word.substring(0, 1))), word) >= 0);
    }

    /**
     * Iterates through the user input and assigns values to each letter based on array list values using string alpha. It also sums up these points to return a total
     * @param word input for a scrabble word
     * @return score of the scrabble word
     */
    public int getWordScore(String word){
        int score = 0;
        for(int i = 0; i < word.length(); i++){
            int index = alpha.indexOf(word.substring(i, i+1));
            score += values[index];
        }
        return score;
    }

    /**
     * Main method for class ScrabbleScorer
     *
     * @param args Command-line arguments, if needed.
     */
    public static void main(String[] args){
        ScrabbleScorer app = new ScrabbleScorer();
        System.out.println("* Welcome to the Scrabble Word Scorer app *");
        Scanner in = new Scanner(System.in);
        while(true){
            System.out.print("Enter a word to score or 0 to quit: ");
            String word = in.nextLine().toUpperCase();
            if(word.equals("0")){
                break;
            }
            if(word.length() != 0 && app.isValidWord(word)) {
                System.out.println(word + " = " + app.getWordScore(word) + " points");
            }
            else{
                System.out.println(word + " is not a valid word in the dictionary");
            }
        }
        System.out.println("Exiting the program thanks for playing");
    }

}
