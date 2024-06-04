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
 * Signed, Tony Marsalla
 * Author: Tony Marsalla
 * Penn email: <marsalla@seas.upenn.edu>
 * Date: 2024-02-12
 */


import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Analyzer {

    /*
     * Implement this method in Part 1
     */
	
    public static List<Sentence> readFile(String filename) {
    //trying this a different way
    	  List<Sentence> sentences = new ArrayList<>();
          if (filename == null) {
              return sentences;
          }
           //Creating the file object, reading the file, then buffering the data
          try (BufferedReader brush = new BufferedReader(new FileReader(filename))) {
              String line;
              //as we read through the file, we want to iterate through each line
              //!= null is simply saying that as long as content exist and isn't empty, enter the for loop
              while ((line = brush.readLine()) != null) {
                //parsing the new format
                  Sentence sentence = parseSentence(line);
                  if (sentence != null) {
                      sentences.add(sentence);
                  }
              }
          } catch (IOException e) {
              e.printStackTrace();
          } 
          return sentences;
      }

      public static Sentence parseSentence(String line) {
        if (line == null || line.isBlank()) {
        return null;
    }
    String[] parts = line.trim().split(" ", 2);
    if (parts.length != 2) {
        return null;
    }
    try {
        int score = Integer.valueOf(parts[0]);
        if (score < -2 || score > 2) {
            //throw new IllegalArgumentException("Invalid score: " + score + " not in range [-2, 2]");
            return null;
        }
        String text = parts[1];
        return new Sentence(score, text);
    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
        e.printStackTrace();
        return null;
    }
}
  

    /*
     * Implement this method in Part 2
     */
     public static List<String> allOccurrences(List<Sentence> sentences) {
    	   
    //here I initate allwords to be of the List class so that I return a list. 
   	 List<String> allWords = new ArrayList<>();
    	//if the input is null, it must return an empty list
        if (sentences == null) {
             return allWords; 
         }
        //preserving the order of the words as there iterated
         for (Sentence sentence : sentences) {
            //ignoring both null sentence objects and invalid words
             if (sentence != null && sentence.getText() != null) {
                //splitting the sentence into an array of characters based on whitespace
                 String[] words = sentence.getText().split("\\s+"); 
                 for (String word : words) {
                    //using a regex technique that will take however the word appears and make it lowercase 
                     String cleanedWord = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
                     if (!cleanedWord.isEmpty()) 
                         //adding the words to allwords
                         allWords.add(cleanedWord);
                     }
                 }
             }
         //it must return a non-null list
         return allWords;
     }

    /*
     * Implement this method in Part 3
     */
    public static Set<String> uniqueWords(List<Sentence> sentences) {
    	 //the output must be a set so therefore, uniqueWords must use the Set interface
         Set<String> uniqueWords = new HashSet<>();
    	   //if the input is null, it must return an empty set
            if (sentences == null) {
    	        return uniqueWords; 
    	    }
             //preserving the order of the words as there iterated
    	    for (Sentence sentence : sentences) {
                //ignoring both null sentence objects and invalid words
    	        if (sentence != null && sentence.getText() != null) {
                //splitting the sentence into an array of characters based on whitespace
    	            String[] words = sentence.getText().split("\\s+"); 
    	            for (String word : words) {
    	            //using a regex technique that will take however the word appears and make it lowercase 
                        String clearWord = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
    	                if (!clearWord.isEmpty()) {
                            //adding the word to uniqueWords
    	                    uniqueWords.add(clearWord); 
    	                }
    	            }
    	        }
    	    }
            //it must return a non-null list
    	    return uniqueWords;
    	}
    

    /*
     * Implement this method in Part 4
     */
      public static Map<String, ObservationTally> wordTallies(List<Sentence> sentences) {
    	 
    	if (sentences == null) {
            return new HashMap<>(); 
        }
        //selecting a class that implements map and instatiating a reference variable
        Map<String, ObservationTally> wordMap = new HashMap<>();
       //preserving the order of the words as there iterated
        for (Sentence sentence : sentences) {
        //ignoring both null sentence objects and invalid words
            if (sentence != null && sentence.getText() != null) {
        //splitting the sentence into an array of characters based on whitespace
                String[] words = sentence.getText().split("\\s+"); 
                for (String word : words) {
                    //making each word lowercase
                    word = word.toLowerCase(); 
                    //as long as the iteration iterates something that isn't null, start at the first character
                    //starting at the first character because this is for tallying
                    if (!word.isEmpty() && Character.isLetter(word.charAt(0))) {
                        wordMap.putIfAbsent(word, new ObservationTally()); 
                        wordMap.get(word).increaseTotal(sentence.getScore()); 
                    }
                }
            }
        }
        return wordMap;
    }


    /*
     * Implement this method in Part 5
     */
    public static Map<String, Double> calculateScores(Map<String, ObservationTally> tallies) {
    	
        Map<String, Double> wordScores = new HashMap<>();
        //adding this
        //if tallies are null, return empty list
        if (tallies == null) {
            return wordScores; 
        }
        //iterate entry through the entrytalliesset
        for (Map.Entry<String, ObservationTally> entry : tallies.entrySet()) {
           //instantiating the key 
            String word = entry.getKey();
            //instantiating the value
            ObservationTally tally = entry.getValue();
            //calculating the score
            double score = tally.calculateScore();
            wordScores.put(word, score);
        }
        //return the wordscore
        return wordScores;
    }

    /*
     * Implement this method in Part 6
     */
     public static double calculateSentenceScore(Map<String, Double> wordScores, String text) {
   	 if (text == null || text.isEmpty() || wordScores == null || wordScores.isEmpty()) {
   	        return 0.0;
   	    }
   	    String[] words = text.split("\\s+");
   	    double totalScore = 0.0;
   	    //initiating a word counter
        int wordCount = 0;
   	    for (String word : words) {
   	    //using a regex technique that will take however the word appears and make it lowercase 
            String clearWord = word.replaceAll("[^a-zA-Z09]", "").toLowerCase();
   	     //ignoring both null sentence objects and invalid words
            if (!clearWord.isEmpty() && wordScores.containsKey(clearWord)) {
                   totalScore += wordScores.get(clearWord);
                  //word counter must still be implemented even with totalscore increasing
                   wordCount++;
            } else {
                    wordCount++;
               }
     }
   	    if (wordCount == 0) {
   	        return 0.0;
   	    }
   	    return totalScore/wordCount;
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
        //for testing

/*    	  Scanner scanner = new Scanner(System.in);
          String filename = "reviews.txt"; // Default file name
          
          System.out.println("Would you like to specify the input file? (yes/no)");
          String response = scanner.nextLine().toLowerCase();
          if (response.equals("yes")) {
              System.out.println("Please enter the name of the input file:");
              filename = scanner.nextLine();
          }*/
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
