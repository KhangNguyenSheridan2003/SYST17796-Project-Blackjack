/**
 * SYST 17796 Project - Blackjack
 * A dealer class representing the CPU-controlled dealer.
 * 
 * @author Solo Creator Team
 * @date November 2025
 */
package ca.sheridancollege.project;

/**
 * Represents the dealer in Blackjack.
 * Extends BlackjackPlayer with automatic play behavior.
 * Dealer must hit on 16 or less and stand on 17 or more.
 */
public class Dealer extends BlackjackPlayer {
    
    // Dealer must stand on this value or higher
    public static final int DEALER_STAND_VALUE = 17;
    
    /**
     * Constructor to create a dealer.
     * Dealer doesn't need points as they don't bet.
     */
    public Dealer() {
        super("Dealer", 0); // Dealer doesn't use points
    }
    
    /**
     * Dealer plays automatically according to Blackjack rules.
     * Dealer must hit until hand value is 17 or higher.
     * 
     * @param deck the deck to draw from
     */
    public void autoPlay(Deck deck) {
        // Dealer hits until reaching 17 or higher
        while (getHandValue() < DEALER_STAND_VALUE && !isBusted()) {
            hit(deck);
        }
        stand();
    }
    
    /**
     * Checks if dealer should hit based on current hand.
     * 
     * @return true if dealer should hit (hand value < 17)
     */
    public boolean shouldHit() {
        return getHandValue() < DEALER_STAND_VALUE;
    }
    
    /**
     * Returns the dealer's visible card (first card) value.
     * Used when dealer's second card is hidden.
     * 
     * @return value of the first card, or 0 if no cards
     */
    public int getVisibleCardValue() {
        if (getHand().getCards().isEmpty()) {
            return 0;
        }
        BlackjackCard firstCard = (BlackjackCard) getHand().getCards().get(0);
        return firstCard.getValue();
    }
    
    /**
     * Returns the dealer's visible card.
     * 
     * @return the first card, or null if no cards
     */
    public Card getVisibleCard() {
        if (getHand().getCards().isEmpty()) {
            return null;
        }
        return getHand().getCards().get(0);
    }
    
    /**
     * Returns a string representation of dealer's hand with one card hidden.
     * 
     * @return string showing visible card and hidden card
     */
    public String getHiddenHandString() {
        return getHand().toStringHidden();
    }
    
    /**
     * Returns the full hand string (all cards visible).
     * 
     * @return string showing all cards
     */
    public String getFullHandString() {
        return getHand().toString();
    }
    
    /**
     * Override toString for dealer-specific display.
     * 
     * @return dealer info string
     */
    @Override
    public String toString() {
        return "Dealer - Hand: " + getHand().toString();
    }
}
