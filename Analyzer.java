/*
 * I attest that the code in this file is entirely my own except for the starter
 * code provided with the assignment and the following exceptions:
 * <Enter all external resources and collaborations here.>
 *
 * Note external code may reduce your score but appropriate citation is required
 * to avoid academic integrity violations. Please see the Course Syllabus as
 * well as the university code of academic integrity:
 *    https://catalog.upenn.edu/pennbook/code-of-academic-integrity/
 *
 * Signed,Tony Marsalla
 * Author: Tony Marsalla
 * Penn email: <marsalla@seas.upenn.edu>
 * Date: 2024-02-10
 */

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Analyzer {

    /*
     * Implement this method in Part 1
     */
    public static List<Sentence> readFile(String filename) {
    	 List<Sentence> sentences = new ArrayList<>();

         try {
             File file = new File(filename);
             Scanner scanner = new Scanner(file);

             while (scanner.hasNextLine()) {
                 String line = scanner.nextLine();
                 Sentence sentence = parseSentence(line);
                 if (sentence != null) {
                     sentences.add(sentence);
                 }
             }

             scanner.close();
         } catch (FileNotFoundException e) {
             System.err.println("File not found: " + filename);
         }

         return sentences
    }

    /*
     * Implement this method in Part 2
     * 
     */
    public static List<String> allOccurrences(List<Sentence> sentences) {
        return null;
    }

    /*
     * Implement this method in Part 3
     */
    public static Set<String> uniqueWords(List<Sentence> sentences) {
        return null;
    }

    /*
     * Implement this method in Part 4
     */
    public static Map<String, ObservationTally> wordTallies(List<Sentence> sentences) {
        return null;
    }


    /*
     * Implement this method in Part 5
     */
    public static Map<String, Double> calculateScores(Map<String, ObservationTally> tallies) {
        return null;
    }

    /*
     * Implement this method in Part 6
     */
    public static double calculateSentenceScore(Map<String, Double> wordScores, String text) {
        return 0;
    }

    /*
     * You do not need to modify this code but can use it for testing your program!
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please specify the name of the input file");
            return;
        }
        String filename = args[0];

        System.out.printf("Processing input from \"%s\".\n", filename);
        List<Sentence> sentences = Analyzer.readFile(filename);
        System.out.printf("%5d sentences read.\n", sentences.size());

        var aWords = allOccurrences(sentences);
        System.out.printf("%8d total words found.\n", aWords.size());

        Set<String> uWords = Analyzer.uniqueWords(sentences);
        System.out.printf("%8d unique words found.\n", uWords.size());

        Map<String, ObservationTally> tallies = wordTallies(sentences);
        System.out.printf("This should be the same number as the unique word count: %8d.\n", tallies.size());

        Map<String, Double> wordScores = Analyzer.calculateScores(tallies);

        String scoreAnother = "yes";
        Scanner in = new Scanner(System.in);
        while (scoreAnother.equals("yes")) {
            System.out.print("Please enter a sentence: ");
            System.out.flush();
            String sentence = in.nextLine();
            double score = Analyzer.calculateSentenceScore(wordScores, sentence);
            System.out.println("The sentiment score is " + score);

            boolean gotValidResponse = false;
            while (!gotValidResponse) {
                System.out.print("\nWould you like to score another sentence [yes/no]: ");
                System.out.flush();

                scoreAnother = in.nextLine().toLowerCase();
                switch (scoreAnother) {
                    case "yes":
                    case "no":
                        gotValidResponse = true;
                        break;
                    default:
                        System.out.println("Invalid response: " + scoreAnother);
                        gotValidResponse = false;
                }
            }
        }
        in.close();
    }
}
