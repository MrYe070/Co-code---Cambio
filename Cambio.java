import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Cambio {
    private static Scanner in = new Scanner(System.in);     // declaring it here makes it available to ALL methods
    
    public static void main(String[] args) {
        
        // Players hands.
        int[] player1 = new int[4];
        int[] player2 = new int[4];

        // Deck and discard pile
        ArrayList<Integer> deck = createDeck();
        ArrayList<Integer> discardPile = new ArrayList<Integer>();

        // Draw top card from deck, put into discard pile.
        discardPile.add(deck.get(deck.size() - 1));

        dealToPlayer(deck, player1);
        dealToPlayer(deck, player2);

        // DEBUG
        // System.out.print("Player 1: ");
        // printArray(player1);
        // System.out.print("Player 2: ");
        // printArray(player2);

        

        // Main game loop
        boolean playing = true;
        int cambioPlayer = 0;
        while(playing) {
            boolean playerAnnouncedCambio;
            // Playern 1 turn:
            playerAnnouncedCambio = playerTurn("Player One", player1, deck, discardPile);

            if (playerAnnouncedCambio) {
                cambioPlayer = 0;
                break;
            }

            // Player 2 turn:
            playerAnnouncedCambio = playerTurn("Matt", player2, deck, discardPile);
            
            if (playerAnnouncedCambio) {
                cambioPlayer = 1;
                break;
            }
        }

        if (cambioPlayer == 0) {
            playerTurn("Matt", player2, deck, discardPile);
        }
        else {
            playerTurn("Player One", player1, deck, discardPile);
        }

        // Determine winner.
        int player1Points = calculatePoints(player1);
        int player2Points = calculatePoints(player2);


        prt("Player 1: " + player1Points + ", with hand: ");
        printArray(player1);
        prt("Player 2: " + player2Points + ", with hand: ");
        printArray(player2);
        if (player1Points < player2Points) {
            prn("Player 1 wins");
        }
        else if (player2Points < player1Points) {
            prn("Player 2 wins.");
        }
        else {
            prt("Tie.");
        }
    }

    public static boolean playerTurn(String playerName, int[] playerHand,
            ArrayList<Integer> deck, ArrayList<Integer> discard) {
        clearScreen();

        System.out.println(playerName + "'s turn: ");
        // printArray(playerHand);    // DEBUG
        
        System.out.println("Discard pile: " + discard.get(discard.size() - 1));

        System.out.println("(1) Draw from deck.");
        System.out.println("(2) Draw top discarded card.");
        System.out.println("(3) Announce Cambio.");

        int option = Integer.parseInt(in.nextLine());

        int card;
        if (option == 1) {
            card = deck.remove(deck.size() - 1);
            System.out.println("You drew a: " + card);
        }
        else if (option == 2) {
            card = discard.remove(discard.size() - 1);
            System.out.println("You picked up the " + card + " from the discard pile.");
        }
        else {  // Announcing cambio.
            System.out.println("You announced Cambio, your opponents get one more turn.");
            System.out.println("Press ENTER to continue");
            in.nextLine();
            return true;
        }


        System.out.println("Which card do you want to swap?");
        int swapPosition = Integer.parseInt(in.nextLine()); // read in a String, convert to int

        // Check whether valid index
        if (0 <= swapPosition && swapPosition < playerHand.length) {
            int originalCard = playerHand[swapPosition];
            displayHand(playerHand, swapPosition);
            System.out.println("You are swapping out a " + originalCard);
            playerHand[swapPosition] = card;
            discard.add(originalCard);
        }
        else {  // Otherwise, player just want to discard card.
            System.out.println("You discarded it.");
            discard.add(card);
        }

        System.out.println("Discard pile: " + discard.get(discard.size() - 1));
        displayHand(playerHand, swapPosition);
        // printArray(playerHand);    // DEBUG
        System.out.println("Press ENTER to continue");
        in.nextLine();

        return false;
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

    public static int calculatePoints(int[] playerHand) {
        int points = 0;
        for (int i = 0; i < playerHand.length; i++) {
            int card = playerHand[i];
            if (1 <= card && card <= 10) {
                points += card;
            } else if (11 <= card && card <= 12) {
                points += 10;
            } /*else if (card == 13) {
                // King, 0 points.
            }*/
        }

        return points;
    }

    public static void displayHand(int[] playerHand, int faceUpIndex) {
        String facedownCard = "?";
        for (int i = 0; i < playerHand.length; i++) {
            if (i == faceUpIndex)
                prt(playerHand[i]);
            else
                prt(facedownCard);
        }
        prn();
    }

    public static <T> void prt(T stuff) {
        System.out.print(stuff);
    }

    public static <T> void prn(T stuff) {
        System.out.println(stuff);
    }

    public static void prn() {
        System.out.println();
    }
}
