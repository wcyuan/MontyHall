
/************************************************************************************
 * Monty Hall
 *  
 * This class implements a version of the "Monty Hall" game
 * (https://en.wikipedia.org/wiki/Monty_Hall_problem).  Monty Hall was the original 
 * host of the game show "Let's Make a Deal".  In one of the segments on the show, a 
 * contestant had a choice of 3 doors.  Behind one of the doors was a prize, and behind
 * the other doors there was nothing.  The contestant choose the door that she thought
 * led to the prize.  But before opening the door, Monty Hall would open one of the
 * other doors, one that did not have the prize.  Then the contestant could choose
 * whether to open the door she originally chose, or to switch to the other door.
 * The question is: should you stick to your door or switch?  Does it matter?
 * 
 * INSTRUCTIONS:
 * 
 * 1.  Try playing the game a few times.  Try playing 10 times, and in each case
 *     record whether it would have been better to keep your original choice or
 *     to switch.  
 * 
 * 
 */
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class MontyHall {

	public static int chooseRandomInt(int n) {
		Random r = new Random();
		return r.nextInt(n);
	}
	
	// Create an integer array representing the doors of the game.
	// There is one slot in the array for each
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

	public static int getUserGuess(int number_of_doors) {
		Scanner scanner = new Scanner(System.in);
		for (int num_tries = 0; num_tries < 10; num_tries++) {
			System.out.print("Please pick a door from 1 to " + number_of_doors);
			try {
				int user_guess = scanner.nextInt();
				if (user_guess < 1) {
					System.out.println("That guess (" + user_guess + ") is too low!  Try again.");
				} else if (user_guess > number_of_doors) {
					System.out.println("That guess (" + user_guess + ") is too high!  Try again.");
				} else {
					scanner.close();
					return user_guess - 1;
				}
			} catch (InputMismatchException e) {
				System.out.println("Sorry, that wasn't valid.  Please enter an integer.");
			}
		}
		System.out.println("Too many errors reading the user guess.  Exiting.");
		scanner.close();
		System.exit(1);
		return -1;
	}

	public static boolean playOneGame(int number_of_doors, boolean shouldSimulate) {
		// Create the set of doors
		int prize_door = chooseRandomInt(number_of_doors);
		int doors[] = initializeDoors(number_of_doors, prize_door);
		System.out.println("Welcome to the Monty Hall Game!");
		int user_guess;
		if (shouldSimulate) {
			user_guess = chooseRandomInt(number_of_doors);
		} else {
			user_guess = getUserGuess(number_of_doors);
			if (user_guess < 0) {
				return false;
			}
			//openOtherDoors(doors, prize_door, user_guess);
			//int new_guess = askToSwitch(doors);
			//revealDoor(doors, new_guess, user_guess);
		}
		return doors[user_guess] != 1;
	}

	public static void playManyGames(int number_of_doors, int number_of_games, boolean shouldSimulate) {
		int total_number_of_games = 0;
		int number_of_times_should_have_switched = 0;
		for (int i = 0; i < number_of_games; i++) {
			boolean should_have_switched = playOneGame(number_of_doors, shouldSimulate);
			total_number_of_games++;
			if (should_have_switched) {
				number_of_times_should_have_switched++;
			}
		}
		System.out.println("Played " + total_number_of_games + " games");
		System.out.println("Should have switched " + number_of_times_should_have_switched + " times");
	}

	public static void main(String[] args) {
		MontyHall.playOneGame(3, false);
	}

}
