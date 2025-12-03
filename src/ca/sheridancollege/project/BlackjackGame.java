/**
 * SYST 17796 Project - Blackjack
 * The main game class that controls Blackjack gameplay.
 * 
 * @author Solo Creator Team
 * @date November 2025
 */
package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Controls the Blackjack game flow.
 * Extends the abstract Game class with Blackjack-specific rules.
 */
public class BlackjackGame extends Game {
    
    private Deck deck;
    private Dealer dealer;
    private ArrayList<BlackjackPlayer> blackjackPlayers;
    private int roundNumber;
    private Scanner scanner;
    
    // Game settings
    public static final int WINNING_POINTS = 200;   // Win condition
    public static final double BLACKJACK_PAYOUT = 1.5; // 3:2 payout for blackjack
    
    /**
     * Constructor to create a new Blackjack game.
     */
    public BlackjackGame() {
        super("Blackjack");
        this.deck = new Deck();
        this.dealer = new Dealer();
        this.blackjackPlayers = new ArrayList<>();
        this.roundNumber = 0;
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Starts the game by getting player information.
     */
    public void start() {
        printWelcome();
        setupPlayers();
    }
    
    /**
     * Prints the welcome message and game rules.
     */
    private void printWelcome() {
        System.out.println("+----------------------------------------------------------+");
        System.out.println("|              WELCOME TO BLACKJACK!                       |");
        System.out.println("+----------------------------------------------------------+");
        System.out.println("|  Goal: Get as close to 21 as possible without busting    |");
        System.out.println("|                                                          |");
        System.out.println("|  Card Values:                                            |");
        System.out.println("|    * Number cards (2-10): Face value                     |");
        System.out.println("|    * Face cards (J, Q, K): 10 points                     |");
        System.out.println("|    * Ace: 1 or 11 points (automatic)                     |");
        System.out.println("|                                                          |");
        System.out.println("|  Actions:                                                |");
        System.out.println("|    * Hit: Draw another card                              |");
        System.out.println("|    * Stand: Keep your current hand                       |");
        System.out.println("|                                                          |");
        System.out.println("|  Win Condition: Reach " + WINNING_POINTS + " points to win!                |");
        System.out.println("+----------------------------------------------------------+");
        System.out.println();
    }
    
    /**
     * Sets up players for the game.
     */
    private void setupPlayers() {
        System.out.print("Enter number of players (1-2): ");
        int numPlayers = getValidInput(1, 2);
        
        for (int i = 1; i <= numPlayers; i++) {
            System.out.print("Enter name for Player " + i + ": ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                name = "Player " + i;
            }
            BlackjackPlayer player = new BlackjackPlayer(name);
            blackjackPlayers.add(player);
            getPlayers().add(player);
        }
        
        System.out.println("\nPlayers ready! Each player starts with " + 
                           BlackjackPlayer.STARTING_POINTS + " points.");
        System.out.println();
    }
    
    /**
     * Main game loop - implements the abstract play() method.
     */
    @Override
    public void play() {
        start();
        
        boolean gameOver = false;
        
        while (!gameOver) {
            roundNumber++;
            System.out.println("\n============================================================");
            System.out.println("                    ROUND " + roundNumber);
            System.out.println("============================================================");
            
            // Reset for new round
            resetRound();
            
            // Check if deck needs reshuffling
            if (deck.cardsRemaining() < 15) {
                System.out.println("Reshuffling deck...\n");
                deck.reset();
            }
            
            // Betting phase
            if (!bettingPhase()) {
                gameOver = true;
                continue;
            }
            
            // Deal initial cards
            dealInitialCards();
            
            // Check for dealer Blackjack
            if (dealer.hasBlackjack()) {
                System.out.println("\n*** Dealer has BLACKJACK! ***");
                showAllHands();
                resolveRound();
            } else {
                // Player turns
                for (BlackjackPlayer player : blackjackPlayers) {
                    if (player.hasPoints()) {
                        playerTurn(player);
                    }
                }
                
                // Dealer turn
                dealerTurn();
                
                // Resolve round
                resolveRound();
            }
            
            // Show points summary
            showPointsSummary();
            
            // Check win/lose conditions
            gameOver = checkGameOver();
            
            if (!gameOver) {
                System.out.print("\nPress Enter to continue to next round...");
                scanner.nextLine();
            }
        }
        
        declareWinner();
    }
    
    /**
     * Resets all hands for a new round.
     */
    private void resetRound() {
        dealer.resetForNewRound();
        for (BlackjackPlayer player : blackjackPlayers) {
            player.resetForNewRound();
        }
    }
    
    /**
     * Handles the betting phase for all players.
     * 
     * @return true if at least one player placed a bet
     */
    private boolean bettingPhase() {
        boolean anyBets = false;
        
        for (BlackjackPlayer player : blackjackPlayers) {
            if (player.getPoints() <= 0) {
                System.out.println(player.getName() + " has no points left and cannot bet.");
                continue;
            }
            
            System.out.println("\n" + player.getName() + "'s turn to bet.");
            System.out.println("Current points: " + player.getPoints());
            System.out.print("Enter bet amount (1-" + player.getPoints() + ", or 0 to quit): ");
            
            int bet = getValidInput(0, player.getPoints());
            
            if (bet == 0) {
                System.out.println(player.getName() + " has chosen to quit.");
                player.removePoints(player.getPoints()); // Set to 0
            } else {
                player.placeBet(bet);
                System.out.println(player.getName() + " bets " + bet + " points.");
                anyBets = true;
            }
        }
        
        return anyBets;
    }
    
    /**
     * Deals initial two cards to all players and dealer.
     */
    private void dealInitialCards() {
        System.out.println("\n--- Dealing cards ---");
        
        // Deal 2 cards to each player and dealer (alternating)
        for (int i = 0; i < 2; i++) {
            for (BlackjackPlayer player : blackjackPlayers) {
                if (player.getCurrentBet() > 0) {
                    player.hit(deck);
                }
            }
            dealer.hit(deck);
        }
        
        // Show hands
        for (BlackjackPlayer player : blackjackPlayers) {
            if (player.getCurrentBet() > 0) {
                System.out.println(player.getName() + ": " + player.getHand());
                if (player.hasBlackjack()) {
                    System.out.println("  ** BLACKJACK! **");
                }
            }
        }
        System.out.println("Dealer: " + dealer.getHiddenHandString());
    }
    
    /**
     * Handles a player's turn.
     * 
     * @param player the player whose turn it is
     */
    private void playerTurn(BlackjackPlayer player) {
        if (player.getCurrentBet() <= 0) {
            return;
        }
        
        System.out.println("\n--- " + player.getName() + "'s Turn ---");
        
        // Skip if player has Blackjack
        if (player.hasBlackjack()) {
            System.out.println("You have Blackjack! Waiting for dealer...");
            player.stand();
            return;
        }
        
        while (!player.isStanding() && !player.isBusted()) {
            System.out.println("\nYour hand: " + player.getHand());
            System.out.println("Dealer shows: " + dealer.getVisibleCard());
            System.out.println("\nChoose action:");
            System.out.println("  1. Hit (draw a card)");
            System.out.println("  2. Stand (keep current hand)");
            System.out.print("Enter choice (1-2): ");
            
            int choice = getValidInput(1, 2);
            
            if (choice == 1) {
                Card drawnCard = player.hit(deck);
                System.out.println("You drew: " + drawnCard);
                System.out.println("Hand value: " + player.getHandValue());
                
                if (player.isBusted()) {
                    System.out.println("BUST! Your hand exceeds 21.");
                }
            } else {
                player.stand();
                System.out.println(player.getName() + " stands with " + player.getHandValue());
            }
        }
    }
    
    /**
     * Handles the dealer's turn.
     */
    private void dealerTurn() {
        System.out.println("\n--- Dealer's Turn ---");
        System.out.println("Dealer reveals: " + dealer.getFullHandString());
        
        // Check if all players busted
        boolean allBusted = true;
        for (BlackjackPlayer player : blackjackPlayers) {
            if (player.getCurrentBet() > 0 && !player.isBusted()) {
                allBusted = false;
                break;
            }
        }
        
        if (allBusted) {
            System.out.println("All players busted. Dealer wins automatically.");
            return;
        }
        
        // Dealer plays
        while (dealer.shouldHit()) {
            Card drawnCard = dealer.hit(deck);
            System.out.println("Dealer draws: " + drawnCard);
            System.out.println("Dealer's hand: " + dealer.getFullHandString());
            
            try {
                Thread.sleep(1000); // Pause for dramatic effect
            } catch (InterruptedException e) {
                // Ignore
            }
        }
        
        if (dealer.isBusted()) {
            System.out.println("Dealer BUSTS with " + dealer.getHandValue() + "!");
        } else {
            System.out.println("Dealer stands with " + dealer.getHandValue());
        }
    }
    
    /**
     * Resolves the round and updates points.
     */
    private void resolveRound() {
        System.out.println("\n--- Round Results ---");
        
        int dealerValue = dealer.getHandValue();
        boolean dealerBusted = dealer.isBusted();
        boolean dealerBlackjack = dealer.hasBlackjack();
        
        for (BlackjackPlayer player : blackjackPlayers) {
            if (player.getCurrentBet() <= 0) {
                continue;
            }
            
            int bet = player.getCurrentBet();
            int playerValue = player.getHandValue();
            boolean playerBusted = player.isBusted();
            boolean playerBlackjack = player.hasBlackjack();
            
            String result;
            int pointsChange;
            
            if (playerBusted) {
                // Player busted - loses bet
                result = "LOSE (Busted)";
                pointsChange = -bet;
            } else if (dealerBlackjack && !playerBlackjack) {
                // Dealer has blackjack, player doesn't
                result = "LOSE (Dealer Blackjack)";
                pointsChange = -bet;
            } else if (playerBlackjack && !dealerBlackjack) {
                // Player has blackjack, dealer doesn't - 3:2 payout
                result = "WIN (Blackjack!)";
                pointsChange = (int)(bet * BLACKJACK_PAYOUT);
            } else if (dealerBusted) {
                // Dealer busted - player wins
                result = "WIN (Dealer Busted)";
                pointsChange = bet;
            } else if (playerValue > dealerValue) {
                // Player has higher value
                result = "WIN";
                pointsChange = bet;
            } else if (playerValue < dealerValue) {
                // Dealer has higher value
                result = "LOSE";
                pointsChange = -bet;
            } else {
                // Push (tie)
                result = "PUSH (Tie)";
                pointsChange = 0;
            }
            
            // Apply points change
            if (pointsChange > 0) {
                player.addPoints(pointsChange);
            } else if (pointsChange < 0) {
                player.removePoints(-pointsChange);
            }
            
            // Display result
            String changeStr = pointsChange >= 0 ? "+" + pointsChange : String.valueOf(pointsChange);
            System.out.println(player.getName() + ": " + result + " (" + changeStr + " points)");
        }
    }
    
    /**
     * Shows the points summary for all players.
     */
    private void showPointsSummary() {
        System.out.println("\n--- Points Summary ---");
        for (BlackjackPlayer player : blackjackPlayers) {
            System.out.println(player.getName() + ": " + player.getPoints() + " points");
        }
    }
    
    /**
     * Shows all hands (used when dealer has Blackjack).
     */
    private void showAllHands() {
        System.out.println("\n--- All Hands ---");
        for (BlackjackPlayer player : blackjackPlayers) {
            if (player.getCurrentBet() > 0) {
                System.out.println(player.getName() + ": " + player.getHand());
            }
        }
        System.out.println("Dealer: " + dealer.getFullHandString());
    }
    
    /**
     * Checks if the game is over.
     * 
     * @return true if game should end
     */
    private boolean checkGameOver() {
        // Check if any player won
        for (BlackjackPlayer player : blackjackPlayers) {
            if (player.getPoints() >= WINNING_POINTS) {
                return true;
            }
        }
        
        // Check if all players are out
        boolean anyPlayersLeft = false;
        for (BlackjackPlayer player : blackjackPlayers) {
            if (player.getPoints() > 0) {
                anyPlayersLeft = true;
                break;
            }
        }
        
        return !anyPlayersLeft;
    }
    
    /**
     * Declares the winner - implements abstract method.
     */
    @Override
    public void declareWinner() {
        System.out.println("\n+----------------------------------------------------------+");
        System.out.println("|                   GAME OVER                              |");
        System.out.println("+----------------------------------------------------------+");
        
        // Find winner(s)
        BlackjackPlayer winner = null;
        int highestPoints = 0;
        
        for (BlackjackPlayer player : blackjackPlayers) {
            if (player.getPoints() > highestPoints) {
                highestPoints = player.getPoints();
                winner = player;
            }
        }
        
        System.out.println("|  Total Rounds Played: " + roundNumber);
        System.out.println("|");
        System.out.println("|  Final Standings:");
        for (BlackjackPlayer player : blackjackPlayers) {
            String status = player.getPoints() >= WINNING_POINTS ? " ** WINNER!" : 
                           player.getPoints() <= 0 ? " (Eliminated)" : "";
            System.out.println("|    " + player.getName() + ": " + player.getPoints() + " points" + status);
        }
        
        if (winner != null && winner.getPoints() >= WINNING_POINTS) {
            System.out.println("|");
            System.out.println("|  *** Congratulations " + winner.getName() + "! ***");
        } else if (winner != null) {
            System.out.println("|");
            System.out.println("|  " + winner.getName() + " wins by having the most points!");
        } else {
            System.out.println("|");
            System.out.println("|  No winner - all players eliminated.");
        }
        
        System.out.println("+----------------------------------------------------------+");
        System.out.println("\nThank you for playing!");
    }
    
    /**
     * Gets valid integer input from user.
     * 
     * @param min minimum valid value
     * @param max maximum valid value
     * @return valid integer in range
     */
    private int getValidInput(int min, int max) {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.print("Please enter a number between " + min + " and " + max + ": ");
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
    
    /**
     * Gets the current round number.
     * 
     * @return the round number
     */
    public int getRoundNumber() {
        return roundNumber;
    }
    
    /**
     * Gets the dealer.
     * 
     * @return the dealer
     */
    public Dealer getDealer() {
        return dealer;
    }
    
    /**
     * Gets the deck.
     * 
     * @return the deck
     */
    public Deck getDeck() {
        return deck;
    }
}
