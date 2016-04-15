package com.wilcoxpc.swagjack;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main class of the program, which runs the game.
 */
public class Main {
    public static void main(String[] args) {
        // the player starts with $1000
        BigDecimal bank = new BigDecimal(1000.0);

        // while true, play the game over and over
        while (true) {
            Scanner scanner = new Scanner(System.in);

            // collect bets
            System.out.println("You have " + NumberFormat.getCurrencyInstance().format(bank) + " in the bank.");

            // BigDecimal is a reliable way to keep track of currency
            BigDecimal bet = new BigDecimal(-1.0);
            // validate the supplied value for being above $0 and under what is in the bank
            while (bet.doubleValue() < 0 || bet.doubleValue() > bank.doubleValue()) {
                System.out.println("You must answer with a positive amount equal or less to the money in the bank.");
                System.out.println("How many dollars would you like to bet? ");

                try {
                    bet = new BigDecimal(scanner.nextDouble());
                } catch (Exception ex) {
                    System.out.println("Please enter a valid dollar amount. \"54.28\", for example.");
                    scanner.next();
                }

            }

            // play the game and get the status back
            switch (play()) {
                case PLAYER_WIN:
                    System.out.println("You won!");

                    // add the player's bet to his balance
                    bank = bank.add(bet);

                    System.out.println("You gained " + NumberFormat.getCurrencyInstance().format(bet) + ".");
                    System.out.println("You have " + NumberFormat.getCurrencyInstance().format(bank) + " in the bank.");
                    break;
                case PLAYER_LOSS:
                    System.out.println("You lost!");

                    // subtract the player's bet from his balance
                    bank = bank.subtract(bet);

                    System.out.println("You lost " + NumberFormat.getCurrencyInstance().format(bet) + ".");
                    System.out.println("You have " + NumberFormat.getCurrencyInstance().format(bank) + " in the bank.");
                    break;
                case TIE:
                    System.out.println("You tied with the computer!");
                    System.out.println("You didn't lose or gain any money.");
                    System.out.println("You have " + NumberFormat.getCurrencyInstance().format(bank) + " in the bank.");
                    break;
                default:
                    System.out.println("This should never happen.");
                    break;
            }

            System.out.println("Would you like to play again? ");
            String response = scanner.next();

            // validate user response: it only replays if he says yes or y
            if (!(response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("y"))) {
                break;
            }
        }


    }

    /**
     * Initiate a new game of BlackJack
     *
     * @return The outcome of the game
     */
    private static GameResult play() {
        GameResult validation;

        // create new deck and shuffle it
        Deck deck = new Deck();
        deck.shuffle();

        // set to true when either player is standing
        boolean computerStanding = false;
        boolean playerStanding = false;

        Hand computerHand = new Hand(new Card[]{deck.draw(), deck.draw()}); // give computer and player two cards
        Hand playerHand = new Hand(new Card[]{deck.draw(), deck.draw()});   // each

        Scanner scanner = new Scanner(System.in);

        while (true) {
            // print the game to the screen
            Display.displayGame(computerHand, playerHand);

            // update status of the game - this tests if anyone got 21 at the beginning of the game
            validation = Hand.validate(computerHand, playerHand);
            if (validation != GameResult.CONTINUE) {
                return validation;
            }

            // end the game if the computer and player are both standing
            if (computerStanding && playerStanding) {
                System.out.println("Both the computer and the player are standing.");

                // get the hand values of both players
                ArrayList<Integer> playerPossibleSums = Hand.getPossibleSums(playerHand);
                ArrayList<Integer> computerPossibleSums = Hand.getPossibleSums(computerHand);

                int playerMax = Collections.max(playerPossibleSums);
                int computerMax = Collections.max(computerPossibleSums);

                System.out.println("Computer value: " + computerMax);
                System.out.println("Player value: " + playerMax);
                System.out.println();

                // decide who has a bigger value and end the game accordingly
                if (playerMax > computerMax) {
                    return GameResult.PLAYER_WIN;
                } else if (playerMax == computerMax) {
                    return GameResult.TIE;
                } else {
                    return GameResult.PLAYER_LOSS;
                }
            }

            // the player's turn starts if he is not standing
            if (!playerStanding) {
                boolean actionTaken = false;

                // keep prompting the player until he gives a valid response
                while (!actionTaken) {
                    System.out.print("Do you want to hit or stand? ");
                    String response = scanner.next();

                    if (response.equalsIgnoreCase("hit")) {
                        // if hit, give the player a new card
                        playerHand.addCard(deck.draw());
                        actionTaken = true;
                    } else if (response.equalsIgnoreCase("stand")) {
                        // if stand, mark the player as standing
                        playerStanding = true;
                        actionTaken = true;
                    } else {
                        System.out.println("Please respond with \"hit\" or \"stand\".");
                    }
                }
            }

            // update status of the game since the player has just made his turn
            validation = Hand.validate(computerHand, playerHand);
            if (validation != GameResult.CONTINUE) {
                return validation;
            }

            // the computer needs to get his possible sums to decide whether or not to hit or stand
            ArrayList<Integer> computerPossibleSums = Hand.getPossibleSums(computerHand);

            if (!computerStanding) {
                int maxPossibleSum = Collections.max(computerPossibleSums);

                // if the computer's sum is over 17, then he will stand
                if (maxPossibleSum < 17) {
                    System.out.println("The computer elected to hit.");
                    computerHand.addCard(deck.draw()); // give the computer a new card
                } else {
                    System.out.println("The computer elected to stand.");
                    computerStanding = true; // mark the computer as standing
                }
            }

            // update status of the game since the computer has just made his turn
            validation = Hand.validate(computerHand, playerHand);
            if (validation != GameResult.CONTINUE) {
                return validation;
            }

        }


    }
}
