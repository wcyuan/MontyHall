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
 * 1. Try playing the game a few times. Try playing 10 times, and in each case
 * record whether it would have been better to keep your original choice or to
 * switch.
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
            }
            else {
                doors[i] = 0;
            }
        }
        return doors;
    }

    public static int getUserGuess(int number_of_doors) {
        Scanner scanner = new Scanner(System.in);
        for (int num_tries = 0; num_tries < 10; num_tries++) {
            System.out.println(
                "Please pick a door from 1 to " + number_of_doors);
            try {
                int user_guess = scanner.nextInt();
                if (user_guess < 1) {
                    System.out.println("That guess (" + user_guess
                            + ") is too low!  Try again.");
                }
                else if (user_guess > number_of_doors) {
                    System.out.println("That guess (" + user_guess
                            + ") is too high!  Try again.");
                }
                else {
                    return user_guess - 1;
                }
            }
            catch (InputMismatchException e) {
                System.out
                        .println("Sorry, that wasn't valid.  Please enter an integer.");
            }
        }
        System.out.println("Too many errors reading the user guess.  Exiting.");
        System.exit(1);
        return -1;
    }

    private static int getDoorToKeepShut(int number_of_doors, int prize_door,
            int user_guess) {
        int door_to_keep_shut;
        if (user_guess != prize_door) {
            door_to_keep_shut = prize_door;
        }
        else {
            door_to_keep_shut = chooseRandomInt(number_of_doors - 1);
            if (door_to_keep_shut >= prize_door) {
                door_to_keep_shut++;
            }
        }
        return door_to_keep_shut;
    }

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
                }
                else {
                    // This should never happen, as long as
                    // door_to_keep_shut was chosen properly.
                    System.out.println("He accidentally reveated the prize!");
                }
            }
        }
    }

    private static int askToSwitch(int user_guess, int door_to_keep_shut) {
        Scanner scanner = new Scanner(System.in);
        for (int num_tries = 0; num_tries < 10; num_tries++) {
            System.out.println("Would you like to stick to door "
                    + (user_guess + 1)
                    + " or would you like to switch to door "
                    + (door_to_keep_shut + 1) + "?");
            try {
                int new_guess = scanner.nextInt();
                if (new_guess != user_guess + 1 &&
                    new_guess != door_to_keep_shut + 1) {
                    System.out.println("Please pick either " + (user_guess + 1)
                        + " or " + (door_to_keep_shut + 1) + ".  Try again.");
                }
                else {
                    return new_guess - 1;
                }
            }
            catch (InputMismatchException e) {
                System.out.println(
                    "Sorry, that wasn't valid.  Please enter an integer.");
            }
        }
        System.out.println(
            "Too many errors reading the user guess.  Exiting.");
        System.exit(1);
        return -1;
    }

    private static void revealDoor(int[] doors, int new_guess, int user_guess) {
        System.out.println("Monty Hall opens door " + (new_guess + 1) + ".");
        if (doors[new_guess] == 1) {
            System.out.print("Congratulations, you won the prize! ");
            if (new_guess == user_guess) {
                System.out.println("Good thing you didn't switch doors!");
            }
            else {
                System.out.println("Good thing you switched doors!");
            }
        }
        else {
            System.out.print("Sorry, you didn't win the prize! ");
            if (new_guess == user_guess) {
                System.out.println("Guess you shoud have switched doors!");
            }
            else {
                System.out.println("Guess you shouldn't have switched doors!");
            }
        }
    }

    public static boolean playOneGame(int number_of_doors,
            boolean shouldSimulate) {
        // Create the set of doors
        int prize_door = chooseRandomInt(number_of_doors);
        int doors[] = initializeDoors(number_of_doors, prize_door);
        System.out.println("Welcome to the Monty Hall Game!");
        int user_guess;
        if (shouldSimulate) {
            user_guess = chooseRandomInt(number_of_doors);
            System.out.println("The simulation chooses door " +
                               (user_guess + 1));
        }
        else {
            user_guess = getUserGuess(number_of_doors);
            if (user_guess < 0) {
                return false;
            }
        }

        int door_to_keep_shut = getDoorToKeepShut(number_of_doors,
                                                  prize_door, user_guess);
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

    public static void main(String[] args) {
        MontyHall.playOneGame(3, false);
        //MontyHall.playManyGames(3, 100, true);
        //MontyHall.playManyGames(100, 100, true);
    }

}
