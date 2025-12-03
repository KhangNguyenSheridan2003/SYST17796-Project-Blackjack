/**
 * SYST 17796 Project - Blackjack
 * A player class for Blackjack game.
 * 
 * @author Solo Creator Team
 * @date November 2025
 */
package ca.sheridancollege.project;

/**
 * Represents a human player in Blackjack.
 * Extends the abstract Player class with Blackjack-specific functionality.
 */
public class BlackjackPlayer extends Player {
    
    private Hand hand;          // The player's current hand
    private int points;         // The player's point total (betting currency)
    private int currentBet;     // The current bet amount
    private boolean standing;   // Whether the player has chosen to stand
    
    // Constants
    public static final int STARTING_POINTS = 100;
    
    /**
     * Constructor to create a player with starting points.
     * 
     * @param name the player's name
     */
    public BlackjackPlayer(String name) {
        super(name);
        this.hand = new Hand();
        this.points = STARTING_POINTS;
        this.currentBet = 0;
        this.standing = false;
    }
    
    /**
     * Constructor to create a player with specified starting points.
     * 
     * @param name the player's name
     * @param startingPoints the initial point total
     */
    public BlackjackPlayer(String name, int startingPoints) {
        super(name);
        this.hand = new Hand();
        this.points = startingPoints;
        this.currentBet = 0;
        this.standing = false;
    }
    
    /**
     * Gets the player's hand.
     * 
     * @return the hand
     */
    public Hand getHand() {
        return hand;
    }
    
    /**
     * Gets the player's current points.
     * 
     * @return the points
     */
    public int getPoints() {
        return points;
    }
    
    /**
     * Adds points to the player's total.
     * 
     * @param amount the amount to add
     */
    public void addPoints(int amount) {
        if (amount > 0) {
            this.points += amount;
        }
    }
    
    /**
     * Removes points from the player's total.
     * 
     * @param amount the amount to remove
     * @return true if successful, false if insufficient points
     */
    public boolean removePoints(int amount) {
        if (amount > 0 && this.points >= amount) {
            this.points -= amount;
            return true;
        }
        return false;
    }
    
    /**
     * Gets the current bet amount.
     * 
     * @return the current bet
     */
    public int getCurrentBet() {
        return currentBet;
    }
    
    /**
     * Places a bet for the current round.
     * 
     * @param amount the amount to bet
     * @return true if bet was placed successfully
     */
    public boolean placeBet(int amount) {
        if (amount > 0 && amount <= points) {
            this.currentBet = amount;
            return true;
        }
        return false;
    }
    
    /**
     * Clears the current bet (called after round ends).
     */
    public void clearBet() {
        this.currentBet = 0;
    }
    
    /**
     * Player takes a hit - draws a card from the deck.
     * 
     * @param deck the deck to draw from
     * @return the card that was drawn, or null if deck is empty
     */
    public Card hit(Deck deck) {
        if (deck == null || deck.isEmpty()) {
            return null;
        }
        Card card = deck.draw();
        hand.addCard(card);
        return card;
    }
    
    /**
     * Player chooses to stand - no more cards will be drawn.
     */
    public void stand() {
        this.standing = true;
    }
    
    /**
     * Checks if player is standing.
     * 
     * @return true if player has chosen to stand
     */
    public boolean isStanding() {
        return standing;
    }
    
    /**
     * Gets the value of the player's hand.
     * 
     * @return the hand value
     */
    public int getHandValue() {
        return hand.getValue();
    }
    
    /**
     * Checks if the player has busted.
     * 
     * @return true if hand value exceeds 21
     */
    public boolean isBusted() {
        return hand.isBusted();
    }
    
    /**
     * Checks if player has a Blackjack.
     * 
     * @return true if player has exactly 21 with 2 cards
     */
    public boolean hasBlackjack() {
        return hand.isBlackjack();
    }
    
    /**
     * Resets the player for a new round.
     */
    public void resetForNewRound() {
        hand.clear();
        currentBet = 0;
        standing = false;
    }
    
    /**
     * Checks if the player has any points left.
     * 
     * @return true if player has points > 0
     */
    public boolean hasPoints() {
        return points > 0;
    }
    
    /**
     * Implementation of abstract play() method.
     * In Blackjack, actual play is handled by BlackjackGame.
     */
    @Override
    public void play() {
        // Play logic is handled by BlackjackGame
        // This method exists to satisfy the abstract class requirement
    }
    
    /**
     * Returns a string representation of the player.
     * 
     * @return player info string
     */
    @Override
    public String toString() {
        return getName() + " - Points: " + points + ", Hand: " + hand.toString();
    }
}
