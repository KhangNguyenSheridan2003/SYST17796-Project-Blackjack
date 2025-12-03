/**
 * SYST 17796 Project - Blackjack
 * Main class - program entry point.
 * 
 * @author Solo Creator Team
 * @date November 2025
 */
package ca.sheridancollege.project;

/**
 * Main class to start the Blackjack game.
 */
public class Main {
    
    /**
     * Program entry point.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Create and start the game
        BlackjackGame game = new BlackjackGame();
        game.play();
    }
}
