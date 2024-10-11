//AUTHOR: PIYA JALMI
//ROLL NO: 32
// TITLE:ROCK, PAPER, SCISSORS GAME
//START DATE:05 AUGUST 2024
//MODIFIED DATE:05 AUGUST 2024
//DESCRIPTION: THIS IS A COMMAND LINE INTERFACE GAME CALLED ROCK, PAPER, SCISSORS.
public class Player {
    private String name;
    private String move;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }
}
