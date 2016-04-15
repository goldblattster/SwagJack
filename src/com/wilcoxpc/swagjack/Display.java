package com.wilcoxpc.swagjack;

import java.util.ArrayList;
import java.util.List;

/**
 * Class with utility methods for displaying the status of the game.
 */
public class Display {
    /**
     * Convert list of integers to String array
     *
     * @param integers List of integers to convert
     * @return String array from the list of integers
     */
    private static String[] convertIntegers(List<Integer> integers) {
        String[] ret = new String[integers.size()]; // array to return
        for (int i = 0; i < ret.length; i++)
        {
            // iterate over integer list, convert to string, and add to array
            ret[i] = integers.get(i).toString();
        }
        return ret;
    }

    /**
     * Display the current status of the game
     *
     * @param computerHand The computer's current hand
     * @param playerHand The player's current hand
     */
    public static void displayGame(Hand computerHand, Hand playerHand) {
        // get possible sums of players hand for display
        ArrayList<Integer> playerPossibleSums = Hand.getPossibleSums(playerHand);

        // flush out the screen
        System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        // iterate over dealer's cards and print only the first one
        System.out.println("Dealer's Cards\n");
        for (Card card: computerHand.getCards()) {
            if (card == computerHand.getCards().get(0)) {
                System.out.println("    " + card.toString());
                continue;
            }

            System.out.println("    XX");
        }

        // iterate over player's cards and print all of them. Also, print values of the player's hand
        System.out.println("\nYour Cards\n");
        System.out.print("Value(s): ");
        System.out.println(String.join(", ", convertIntegers(playerPossibleSums)));
        for (Card card: playerHand.getCards()) {
            System.out.println("    " + card.toString());
        }

        System.out.println();
    }

}
