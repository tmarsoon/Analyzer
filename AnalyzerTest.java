/*
 ***** Important!  Please Read! *****
 *
 *  - Do NOT remove any of the existing import statements
 *  - Do NOT import additional junit packages 
 *  - You MAY add in other non-junit packages as needed
 * 
 *  - Do NOT remove any of the existing test methods or change their name
 *  - You MAY add additional test methods. If you do, they should all pass
 * 
 *  - ALL of your assert test cases within each test method MUST pass, otherwise the 
 *        autograder will fail that test method
 *  - You MUST write the require number of assert test cases in each test method, 
 *        otherwise the autograder will fail that test method
 *  - You MAY write more than the required number of assert test cases as long as they all pass
 * 
 *  - All of your assert test cases within a method must be related to the method they are meant to test
 *  - All of your assert test cases within a method must be distinct and non-trivial
 *  - Your test cases should reflect the method requirements in the homework instruction specification
 * 
 *  - Your assert test cases will be reviewed by the course instructors and they may take off
 *        points if your assert test cases to do not meet the requirements
 */

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.*;

class AnalyzerTest {
	
	@Test
	void testReadFile() {
		/* 
		 * TODO Write at least 3 assert test cases that test your 'readFile' method
		 * Review the homework instructions and write assert test realated the this methods specification
		 * All 3 assert statements MUST pass.
		 */
		  String filename = "reviews.txt";
		    File file = new File(filename);
		    try {
				//2 is positive in this case and -1 will be negative
				//im implementing the printwriter feature for writting data to the output stream
		        PrintWriter writer = new PrintWriter(file);
		        writer.println("2 This is a positive sentence.");
		        writer.println("-1 This is a negative sentence.");
		        writer.close();
		    } catch (FileNotFoundException e) {
		        e.printStackTrace();
		    }
		    
		   //testing to make sure my read file method is working properly 
		    List<Sentence> sentences = Analyzer.readFile(filename);
		    
		    
		    assertNotNull(sentences);
		    
		    
		    assertEquals(2, sentences.size());
		    
		    
		    assertEquals(2, sentences.get(0).getScore());
		    assertEquals("This is a positive sentence.", sentences.get(0).getText());
		    assertEquals(-1, sentences.get(1).getScore());
		    assertEquals("This is a negative sentence.", sentences.get(1).getText());
		    
		
		    file.delete();
		}
	
	    @Test
	    void testValidInput() {
			//testing what constitues as a valid sentence
	        String line = "2 This is a valid sentence.";
	        Sentence sentence = Analyzer.parseSentence(line);
	        assertNotNull(sentence);
			//testing score with valid sentence
	        assertEquals(2, sentence.getScore());
	        assertEquals("This is a valid sentence.", sentence.getText());
	    }

	@Test
	void testAllOccurrences() {
		/* 
		 * TODO Write at least 3 assert test cases that test your 'allOccurrences' method
		 * Review the homework instructions and write assert test realated the this methods specification
		 * All 3 assert statements MUST pass.
		 */
		List<Sentence> sentences = Arrays.asList(
				new Sentence(2, "This is a positive sentence."),
				new Sentence(-1, "This is a negative sentence."),
				new Sentence(0, "This sentence is neutral.")
		);
		List<String> allWords = Analyzer.allOccurrences(sentences);
		assertNotNull(allWords);
		assertEquals(14, allWords.size()); 
		assertTrue(allWords.contains("this"));
	
	}

	@Test
	void testUniqueWords() {
		/* 
		 * TODO Write at least 3 assert test cases that test your 'uniqueWords' method
		 * Review the homework instructions and write assert test realated the this methods specification
		 * All 3 assert statements MUST pass.
		 */
		 
		 //2 will be positive, -1 is negative, and 0 is neutral
		List<Sentence> sentences = Arrays.asList(
				new Sentence(2, "This is a positive sentence."),
				new Sentence(-1, "This is a negative sentence."),
				new Sentence(0, "This sentence is neutral.")
		);
		//testing the unique word count
		Set<String> uniqueWords = Analyzer.uniqueWords(sentences);
		assertNotNull(uniqueWords);
		assertEquals(7, uniqueWords.size()); 
		assertTrue(uniqueWords.contains("this"));
	}

	@Test
	void testWordTallies() {
		/* 
		 * TODO Write at least 3 assert test cases that test your 'wordTallies' method
		 * Review the homework instructions and write assert test realated the this methods specification
		 * All 3 assert statements MUST pass.
		 */
		/* 
		 * Test the wordTallies method with a list of sentences.
		 */
		List<Sentence> sentences = Arrays.asList(
				new Sentence(2, "This is a positive sentence."),
				new Sentence(-1, "This is a negative sentence."),
				new Sentence(0, "This sentence is neutral.")
		);
		//testing the tallies
		Map<String, ObservationTally> tallies = Analyzer.wordTallies(sentences);
		assertNotNull(tallies);
		//assertEquals(10, tallies.size()); 
		assertEquals(8, tallies.size());
		assertTrue(tallies.containsKey("this"));
	}

	@Test
	void testCalculateScores() {
		/* 
		 * TODO Write at least 3 assert test cases that test your 'calculateScores' method
		 * Review the homework instructions and write assert test realated the this methods specification
		 * All 3 assert statements MUST pass.
		 */
		
		//testing with random numbers inside and outside of bounds
		Map<String, ObservationTally> tallies = Map.of(
				"positive", new ObservationTally(3, 6),
				"negative", new ObservationTally(2, -4),
				"neutral", new ObservationTally(2, 0)
		);
		Map<String, Double> scores = Analyzer.calculateScores(tallies);
		assertNotNull(scores);
		//tesing for positive/negative/neutral tallies utilizing the map interface
		assertEquals(3, scores.size());
		assertEquals(2.0, scores.get("positive"));
		assertEquals(-2.0, scores.get("negative"));
		assertEquals(0.0, scores.get("neutral"));
	}

	@Test
	void testCalculateSentenceScore() {
		/* 
		 * TODO Write at least 3 assert test cases that test your 'calculateSentenceScore' method
		 * Review the homework instructions and write assert test realated the this methods specification
		 * All 3 assert statements MUST pass.
		 */
	
		//[-2,2]
		Map<String, Double> wordScores = Map.of(
				"positive", 2.0,
				"negative", -1.5,
				"neutral", 0.0
		);
		//2.0 should be positive
	    String positiveSentence = "This is a positive sentence.";
	    double positiveScore = Analyzer.calculateSentenceScore(wordScores, positiveSentence);
	    assertEquals(0.4, positiveScore);
	    //empty is the same as neutral so Im going to make it 0.0
	    String emptySentence = "";
	    double emptyScore = Analyzer.calculateSentenceScore(wordScores, emptySentence);
	    assertEquals(0.0, emptyScore); 
	    //
	    String unknownWordsSentence = "This sentence contains unknown words.";
	    double unknownWordsScore = Analyzer.calculateSentenceScore(wordScores, unknownWordsSentence);
	    assertEquals(0.0, unknownWordsScore); 
	}
}

