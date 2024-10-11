//AUTHOR: PIYA JALMI
//ROLL NO: 32
// TITLE:ROCK, PAPER, SCISSORS GAME
//START DATE:05 AUGUST 2024
//MODIFIED DATE:05 AUGUST 2024
//DESCRIPTION: THIS IS A COMMAND LINE INTERFACE GAME CALLED ROCK, PAPER, SCISSORS.
import java.util.Random;
import java.util.Scanner;

public class Game {
    private Player player;
    private String[] moves = {"Rock", "Paper", "Scissors"};

    public Game(Player player) {
        this.player = player;
    }

    public void playRound() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose your move: Rock, Paper, or Scissors?");
        String playerMove = scanner.nextLine().trim();
        player.setMove(playerMove);

        String computerMove = getComputerMove();
        System.out.println("Computer chose: " + computerMove);

        determineWinner(playerMove, computerMove);
    }

    private String getComputerMove() {
        Random random = new Random();
        return moves[random.nextInt(moves.length)];
    }

    private void determineWinner(String playerMove, String computerMove) {
        if (playerMove.equalsIgnoreCase(computerMove)) {
            System.out.println("It's a tie!");
        } else if (
            (playerMove.equalsIgnoreCase("Rock") && computerMove.equalsIgnoreCase("Scissors")) ||
            (playerMove.equalsIgnoreCase("Paper") && computerMove.equalsIgnoreCase("Rock")) ||
            (playerMove.equalsIgnoreCase("Scissors") && computerMove.equalsIgnoreCase("Paper"))
        ) {
            System.out.println("You win!");
        } else {
            System.out.println("You lose!");
        }
    }
}
