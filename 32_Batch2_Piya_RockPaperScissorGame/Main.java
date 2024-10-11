//AUTHOR: PIYA JALMI
//ROLL NO: 32
// TITLE:ROCK, PAPER, SCISSORS GAME
//START DATE:05 AUGUST 2024
//MODIFIED DATE:05 AUGUST 2024
//DESCRIPTION: THIS IS A COMMAND LINE INTERFACE GAME CALLED ROCK, PAPER, SCISSORS.
public class Main {
    public static void main(String[] args) {
        // Create a player
        Player player = new Player("John");

        // Create the game
        Game game = new Game(player);

        // Play a round
        game.playRound();
    }
}
