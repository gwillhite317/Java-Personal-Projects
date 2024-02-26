import java.util.Random;
import java.util.Scanner;

public class WordleFinal {
    public static void main(String[] Args) {
        rules(); //method to print rules
        String key = getKey(); //gets key from method getKey using a return statement, will be used as the answer which the player is attempting to guess
        boolean gameWon = false; //tracks if the Game has been beaten or not
        for (int i = 1; i <= 5; i++) {
            String userGuess = getGuess(i);
            String guess = userGuess.toUpperCase();
            gameWon = compareGuessAndKey(key, guess);
            if (gameWon) {
                break;
            }//end of if
        }//end of for

        //if statement will output a message letting the user know they lost if all guesses are used without a correct answer
        if (!gameWon) {
            System.out.println("Sorry, you lost. The correct word was: " + key);
        }
    }


    public static void rules() {
        System.out.println("The goal of this program is to simulate the game Wordle in the Terminal.");
        System.out.println("You can either type random to have the computer pick a word for you, or you can have a friend enter a 5 letter word for you to guess!");
        System.out.println("You will then have 5 guesses to try and figure out the secret word! After every guess the computer will return a 5 character pattern");
        System.out.println("This pattern will consist of, *G*'s to symbolize a letter that matches in both words, *Y*'s to symbolize a letter that matches in both words but is in a different position, and finally a *-* anywhere there was a letter that is not in the word you are attempting to guess.");
        System.out.println("GOOD LUCK!");
    }

    //method gets the answer which will be used in the game
    public static String getKey() {
        //scanner to receive the users input
        Scanner input = new Scanner(System.in);
        Random random = new Random();
        String[] wordsArray = {"APPLE", "BEACH", "CRANE", "DAISY", "ELOPE"};

        //string key identified as null so the process repeats until a valid Key is established
        String key = null;
        //loop asks user for a input of either a self written answer or a random one
        while (key == null) {
            try {
                System.out.println("Please enter a 5-letter word or type 'random':");
                //converts input to uppercase regardless of word
                String userInput = input.nextLine().toUpperCase();
                //if user input was the word "random" the computer will pick a random word from the array, otherwise it will use the users picked word as the answer/key
                if (userInput.equals("RANDOM")) {
                    int randomIndex = random.nextInt(wordsArray.length);
                    key = wordsArray[randomIndex];
                } else {
                    //throw statements corresponding to if the word is more than 5 letters or contains characters that are not letters
                    if (userInput.length() != 5) {
                        throw new IllegalArgumentException("The word must be exactly 5 letters long.");
                    }
                    if (userInput.matches(".*\\d.*")) {
                        throw new IllegalArgumentException("The word must not contain any numbers.");
                    }
                    key = userInput;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred. Please try again.");
            }
        }
        //returns key/answer to be used in all other functions
        return key;
    }

    //this method gets the users guesses
    public static String getGuess(int guessNumber) {
        Scanner scanner = new Scanner(System.in);
        String guess;
        boolean isValid = false;
        // isValid is false until a guess which fits all necessary parameters is answered! (follow our directions!!!!)
        while (!isValid) {
            System.out.print("Enter a 5-letter word without symbols or numbers for your guess " + guessNumber + ": ");
            guess = scanner.nextLine();
            isValid = isValidGuess(guess);

            if (!isValid) {
                System.out.println("Invalid input. Please try again.");
            } else {
                //returns the users guesses
                return guess;
            }
        }

        return ""; //this is never reached but helps avoid errors
    }

    //method makes sure the guess is five letters long, has no symbols/numbers etc
    public static boolean isValidGuess(String input) {
        if (input.length() != 5) {
            return false;
        }

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    //this method compares the Guess (converted to String G) and the key (converted to String K) and outputs G's Y's and -'s corresponding to if the letter is in both words at the same positions, in both words, or not in the word
    public static boolean compareGuessAndKey(String G, String K) {
        StringBuilder result = new StringBuilder("-----");
        int[] keyWordCount = new int[26];
        int[] guessWordCount = new int[26];

        // Check for correct positions and update the result string with a "G"
        for (int i = 0; i < K.length(); i++) {
            char keyChar = K.charAt(i);
            char guessChar = G.charAt(i);

            if (keyChar == guessChar) {
                result.setCharAt(i, 'G');
            }//end of if

            keyWordCount[keyChar - 'A']++;
            guessWordCount[guessChar - 'A']++;
        }//end of for

        // Check for correct letters in wrong positions and update the result string with a "Y"
        for (int i = 0; i < K.length(); i++) {
            char keyChar = K.charAt(i);
            char guessChar = G.charAt(i);

            if (keyChar != guessChar && keyWordCount[guessChar - 'A'] > 0 && guessWordCount[keyChar - 'A'] > 0) {
                result.setCharAt(i, 'Y');
                keyWordCount[guessChar - 'A']--;
                guessWordCount[keyChar - 'A']--;
            }//end of if
        }//end of for
        //checks if the answer was guessed and if it is prints a win message and returns true to stop the code!
        System.out.println("Result: " + result);
        if (result.toString().equals("GGGGG")) {
            System.out.println("Congratulations, You WIN!");
            return true;
        }//end of if
        //returns false if the answer isn't guessed making the game keep going
        return false;
    }//end of compare
}//end of WordleFinal


