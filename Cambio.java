import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Cambio {
    private static Scanner in = new Scanner(System.in);     // declaring it here makes it available to ALL methods
    public static void main(String[] args) {
        
        int[] player1 = new int[4];
        int[] player2 = new int[4];

        ArrayList<Integer> deck = createDeck();

        dealToPlayer(deck, player1);
        dealToPlayer(deck, player2);

        System.out.print("Player 1: ");
        printArray(player1);
        System.out.print("Player 2: ");
        printArray(player2);

        boolean playing = true;
        while(playing) {
            // Playern 1 turn:
            playerTurn("Player One", player1, deck);

            // Player 2 turn:
            playerTurn("Matt", player2, deck);
        }
    }

    public static void playerTurn(String playerName, int[] playerHand, ArrayList<Integer> deck) {
        clearScreen();
        System.out.println(playerName + "'s turn: ");
        printArray(playerHand);    // DEBUG
        int card = deck.remove(deck.size() - 1);
        System.out.println("You drew a: " + card);
        System.out.println("Which card do you want to swap?");
        int swapPosition = Integer.parseInt(in.nextLine()); // read in a String, convert to int

        int originalCard = playerHand[swapPosition];
        System.out.println("You are swapping out a " + originalCard);
        playerHand[swapPosition] = card;

        printArray(playerHand);    // DEBUG
        System.out.println("Press ENTER to continue");
        in.nextLine();
    }

    public static ArrayList<Integer> createDeck() {
        ArrayList deck = new ArrayList<Integer>();
        for (int i = 1; i <= 4; i++) { // for each suit
            for (int j = 1; j <= 13; j++) {
                deck.add(j);    // add card number
            }
        }

        Collections.shuffle(deck);
        
        return deck;
    }

    public static void dealToPlayer(ArrayList<Integer> availableCards, int[] playerHand) {
        for (int i = 0; i < playerHand.length; i++) {
            playerHand[i] = availableCards.remove(availableCards.size() - 1);
        }
    }

    public static void clearScreen() {
        System.out.println("\033[H\033[2J");
    }

    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public static <T> void prt(T stuff) {
        System.out.print(stuff);
    }

    public static <T> void prn(T stuff) {
        System.out.println(stuff);
    }
}
