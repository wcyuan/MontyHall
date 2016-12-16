
/*****************************************************************************
 * Monty Hall
 * 
 * This class implements a version of the "Monty Hall" game
 * (https://en.wikipedia.org/wiki/Monty_Hall_problem). Monty Hall was the
 * original host of the game show "Let's Make a Deal". In one of the segments on
 * the show, a contestant had a choice of 3 doors. Behind one of the doors was a
 * prize, and behind the other doors there was nothing. The contestant chose the
 * door that she thought led to the prize. But before opening the door, Monty
 * Hall would open one of the other doors, one that did not have the prize. Then
 * the contestant could choose whether to open the door she originally chose, or
 * to switch to the other door. The question is: should you stick to your
 * original door or switch? Does it matter?
 * 
 * INSTRUCTIONS:
 * 
 * 1. Try playing the game a few times to make sure you understand how it works.
 * Do you think it is better to keep the same door or to switch?
 * 
 * 2. Play the game 10 times, and in each case record whether it would have been
 * better to keep your original choice or to switch.  Modify the main function
 * so it calls playManyGames instead of playOneGame, that way the program will
 * keep track for you of how many times you should have switched.  Does this
 * make you change your answer to question 1?  
 * 
 * 3. This program is able to simulate games without user input.  Change the
 * last argument of playManyGames from false to true.  Try simulating 100 games.
 * How many times was it better to switch?  Is this what you expected?  How
 * about if you simulate another 100 games, how much does the answer change?
 * 
 * 4. There is an argument that it doesn't matter if you switch doors, because
 * at the end, there are only two possibilities, so either one is equally likely
 * to have the prize.  Imagine a different version of the game where there are
 * 100 doors, and after you choose one door, Monty Hall will open 98 other
 * doors, which are all guaranteed not to have the prize.  You are again left
 * with only two doors.  In that case, do you think the prize is equally likely
 * to be behind either door?  This program can play the Monty Hall game with
 * any number of doors.  Try playing it with 100 doors.  Does that change
 * the number of times that you want to switch doors?
 * 
 * IMPLEMENTATION NOTES:
 * 
 * This program models the Monty Hall game with an integer array.  Each element
 * of the array corresponds to a door in the game.  The value at each element
 * is 1 if there is a prize behind that door, and 0 if there is no prize.
 * 
 * When we print messages to the User, we refer to the doors as door 1, door 2
 * and door 3.  That is, the first door is "door 1" -- the doors are "1-indexed"
 * However, we keep track of them in an array, and arrays in java are 
 * "0-indexed" -- the first slot in the array is index 0.  So that means that
 * the value for "door 1" is in array[0], not array[1], and the value for
 * "door 2" is in array[1], not array[2], etc.
 * 
 * By convention, when this program has variables that keep track of a door,
 * like prize_door which tells us which door the prize is behind, the value it
 * stores is in terms of the array index.  So the value will be 0, 1, or 2, not
 * 1, 2, or 3.  This is also true of the variable user_guess, which keeps track
 * of which door the user guessed.  
 */
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class MontyHall {

    /**
     * chooseRandomInt returns a random integer between 0 and n-1.
     * Each time it is called, it will return a different integer.
     * 
     * @param n: the upper bound of the range to choose from
     * @returns an integer between 0 and n-1, inclusive
     */
    public static int chooseRandomInt(int n) {
        Random r = new Random();
        return r.nextInt(n);
    }

    /**
     * initializeDoors returns a integer array which represents the doors
     * of the game.  There is one slot in the array for each door.  The value
     * of that slot will be 1 if there is a prize behind that door and 0
     * otherwise.
     * 
     * @param number_of_doors: the number of doors
     * @param prize_door: the door behind which to put the prize
     * @return an integer array representing the doors of the game
     */
    public static int[] initializeDoors(int number_of_doors, int prize_door) {
        int doors[] = new int[number_of_doors];
        for (int i = 0; i < number_of_doors; i++) {
            if (i == prize_door) {
                doors[i] = 1;
            } else {
                doors[i] = 0;
            }
        }
        return doors;
    }

    /**
     * getUserGuess prompts the user to enter a door number.  The doors are
     * numbered from 1 to the number of doors, so that's the number the user
     * entered.  Notice, though, that we want to return the door number as
     * an array index into the doors array, so we want to return a number from
     * 0 to number_of_doors - 1.  So when we return the number, we subtract 1.
     * 
     * If the user gives us invalid input, we print an appropriate error
     * message and ask them to try again.  The problem could be that the
     * number they entered was outside of the range 1 to number_of_doors, or
     * they might not have entered an integer.  We'll try up to 10 times, 
     * and if we still haven't gotten an acceptable answer, we'll System.exit
     * to quit the program.
     * 
     * @param number_of_doors: the number of doors in the game
     * @return the number the user chose, which is guaranteed to be between 0
     * and number_of_doors - 1
     */
    public static int getUserGuess(int number_of_doors) {
        // Typically we would want to close the scanner at the end of this
        // function, after we use it, but if we do that, it will also close
        // the System.in stream, and then we wouldn't be able to use it later
        // to get more input.
        Scanner scanner = new Scanner(System.in);
        for (int num_tries = 0; num_tries < 10; num_tries++) {
            System.out.println("Please pick a door from 1 to "
                + number_of_doors);
            try {
                int user_guess = scanner.nextInt();
                if (user_guess < 1) {
                    System.out.println("That guess (" + user_guess
                        + ") is too low!  Try again.");
                } else if (user_guess > number_of_doors) {
                    System.out.println("That guess (" + user_guess
                        + ") is too high!  Try again.");
                } else {
                    return user_guess - 1;
                }
            } catch (InputMismatchException e) {
                // This is the exception thrown by Scanner if the input was
                // not an integer.
                System.out.println(
                    "Sorry, that wasn't valid.  Please enter an integer.");
            }
        }
        System.out.println("Too many errors reading the user guess.  Exiting.");
        System.exit(1);
        return -1;
    }

    /**
     * After the user chooses a door, Monty Hall opens all the doors except
     * two.  Monty Hall definitely won't open the door that the user chose, and
     * he definitely won't open the door that has the prize.  
     * 
     * If the user did not choose the door with the prize, then it's easy to
     * figure out which doors to open -- open all the other doors.  But if the
     * user did choose the door with the prize, then Monty Hall has to randomly
     * choose one other door to leave shut.  That's what this function chooses.
     * 
     * Let's say there are 10 doors, and the user chose the door with array
     * index 2, which is the door with the prize.  Then the remaining doors are
     * 0, 1, 3, 4, 5, 6, 7, 8, and 9.  We have to choose one of these doors.
     * Since there are 9 possible doors to choose from, we should call
     * chooseRandomInt with argument 9 (which is number_of_doors - 1).  This
     * will give us a number from 0 to 8 (inclusive).  In order to get one of
     * the 9 door numbers, we have to add one as long as the random int is
     * greater than or equal to the user guess (which is also the prize door).
     * That will transform the range
     *   0, 1, 2, 3, 4, 5, 6, 7, 8
     * into the desired range
     *   0, 1, 3, 4, 5, 6, 7, 8, 9
     * 
     * @param number_of_doors: The number of doors in the game
     * @param prize_door: The door that the prize is behind,
     * from 0 to number_of_doors - 1
     * @param user_guess: The door that the user chose,
     * from 0 to number_of_doors - 1
     * @return
     */
    private static int getDoorToKeepShut(int number_of_doors, int prize_door,
                                         int user_guess) {
        int door_to_keep_shut;
        if (user_guess != prize_door) {
            door_to_keep_shut = prize_door;
        } else {
            door_to_keep_shut = chooseRandomInt(number_of_doors - 1);
            if (door_to_keep_shut >= prize_door) {
                door_to_keep_shut++;
            }
        }
        return door_to_keep_shut;
    }

    /**
     * This function prints out all of the doors that Monty Hall opens,
     * which are basically all the doors except the one the user chose
     * and the door which was selected to be kept shut.  Callers of this
     * function should first call getDoorToKeepShut and pass the return
     * value of that in as the parameter door_to_keep_shut.  
     * @param doors: the array of doors
     * @param user_guess: the door that the user chose
     * (from 0 to number_of_doors - 1)
     * @param door_to_keep_shut: the other door to keep shut
     * (from 0 to number_of_doors - 1, should be chosen by getDoorToKeepShut)
     */
    private static void openOtherDoors(int[] doors, int user_guess,
                                       int door_to_keep_shut) {
        for (int i = 0; i < doors.length; i++) {
            if (i == user_guess) {
                System.out.println("Monty Hall does not open door " + (i + 1)
                    + " since that's the door the user chose. ");
            } else if (i == door_to_keep_shut) {
                System.out.println("Monty Hall keeps door " + (i + 1)
                    + " closed.");
            } else {
                System.out.print("Monty Hall opens door " + (i + 1) + ". ");
                if (doors[i] == 0) {
                    System.out.println("There is nothing behind it.");
                } else {
                    // This should never happen, as long as
                    // door_to_keep_shut was chosen properly.
                    System.out.println("He accidentally reveated the prize!");
                }
            }
        }
    }

    /**
     * Ask the user if she wants to switch doors.  Keep prompting the user
     * until the user selects a valid door.
     * 
     * Similar to getUserGuess, we have to subtract 1 before returning the
     * door number, in order to translate from the range that the user uses
     * (from 1 to number_of_doors) to the range that the array uses
     * (from 0 to number_of_doors - 1).
     * 
     * @param user_guess: the door that the user selected the first time around
     * @param door_to_keep_shut: the other door that is still shut
     * @return: The user's new guess as an array index, from 0 to
     * number_of_doors - 1
     */
    private static int askToSwitch(int user_guess, int door_to_keep_shut) {
        Scanner scanner = new Scanner(System.in);
        for (int num_tries = 0; num_tries < 10; num_tries++) {
            System.out.println("Would you like to stick to door "
                + (user_guess + 1) + " or would you like to switch to door "
                + (door_to_keep_shut + 1) + "?");
            try {
                int new_guess = scanner.nextInt();
                if (new_guess != user_guess + 1
                    && new_guess != door_to_keep_shut + 1) {
                    System.out.println("Please pick either " + (user_guess + 1)
                        + " or " + (door_to_keep_shut + 1) + ".  Try again.");
                } else {
                    return new_guess - 1;
                }
            } catch (InputMismatchException e) {
                System.out.println(
                    "Sorry, that wasn't valid.  Please enter an integer.");
            }
        }
        System.out.println("Too many errors reading the user guess.  Exiting.");
        System.exit(1);
        return -1;
    }

    /**
     * This function helps finish a single Monty Hall game by revealing where
     * the prize is.
     * 
     * @param doors: the array of doors
     * @param new_guess: the final user guess
     * @param user_guess: the original user guess
     */
    private static void revealDoor(int[] doors, int new_guess, int user_guess) {
        System.out.println("Monty Hall opens door " + (new_guess + 1) + ".");
        if (doors[new_guess] == 1) {
            System.out.print("Congratulations, you won the prize! ");
            if (new_guess == user_guess) {
                System.out.println("Good thing you didn't switch doors!");
            } else {
                System.out.println("Good thing you switched doors!");
            }
        } else {
            System.out.print("Sorry, you didn't win the prize! ");
            if (new_guess == user_guess) {
                System.out.println("Guess you shoud have switched doors!");
            } else {
                System.out.println("Guess you shouldn't have switched doors!");
            }
        }
    }

    /**
     * playOneGame plays a single Monty Hall game.  
     * 
     * @param number_of_doors: the number of doors in this game
     * @param shouldSimulate: If true, we should run a simulation of the games
     * and shouldn't ask the user anything.  If false, we get the guesses
     * from the user.
     * @return true if it turned out to have been a good idea to switch,
     * false otherwise.
     */
    public static boolean playOneGame(int number_of_doors,
                                      boolean shouldSimulate) {
        // Create the set of doors
        int prize_door = chooseRandomInt(number_of_doors);
        int doors[] = initializeDoors(number_of_doors, prize_door);
        System.out.println("Welcome to the Monty Hall Game!");
        int user_guess;
        if (shouldSimulate) {
            user_guess = chooseRandomInt(number_of_doors);
            System.out.println("The simulation chooses door "
                + (user_guess + 1));
        } else {
            user_guess = getUserGuess(number_of_doors);
            if (user_guess < 0) {
                return false;
            }
        }

        int door_to_keep_shut = getDoorToKeepShut(number_of_doors, prize_door,
                                                  user_guess);
        openOtherDoors(doors, user_guess, door_to_keep_shut);
        int new_guess;
        if (shouldSimulate) {
            new_guess = user_guess;
        } else {
            new_guess = askToSwitch(user_guess, door_to_keep_shut);
        }
        revealDoor(doors, new_guess, user_guess);
        return doors[user_guess] != 1;
    }

    /**
     * Plays many Monty Hall games and keeps track of the number of games played
     * and in how many of those times it would have been beneficial to switch
     * doors.  We print these statistics to System.out as we go along.
     * 
     * @param number_of_doors: the number of doors in each game
     * @param number_of_games: the number of games to play
     * @param shouldSimulate: If true, we should run a simulation of the games
     * and shouldn't ask the user anything.  If false, we get the guesses
     * from the user.
     */
    public static void playManyGames(int number_of_doors, int number_of_games,
                                     boolean shouldSimulate) {
        int total_number_of_games = 0;
        int number_of_times_should_have_switched = 0;
        for (int i = 0; i < number_of_games; i++) {
            boolean should_have_switched = playOneGame(number_of_doors,
                                                       shouldSimulate);
            total_number_of_games++;
            if (should_have_switched) {
                number_of_times_should_have_switched++;
            }
            System.out.println("Played " + total_number_of_games + " games");
            System.out.println("Should have switched "
                + number_of_times_should_have_switched + " times");
        }
    }

    /**
     * The main function.  Change this to call whatever version of the Monty
     * Hall game you want to play (choose the number of doors, the number
     * of times to play, and whether to get input from the user or to only
     * simulate games).  
     * 
     * @param args: the arguments are ignored.
     */
    public static void main(String[] args) {
        MontyHall.playOneGame(3, false);
        // MontyHall.playManyGames(3, 100, true);
        // MontyHall.playManyGames(100, 100, true);
    }

}
