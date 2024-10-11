//AUTHOR: PIYA JALMI
//ROLL NO: 32
// TITLE:FRISBEE THROW GAME
//START DATE:21 SEPETEMBER 2024
//MODIFIED DATE:22 SEPTEMBER 2024
//DESCRIPTION: THIS IS AN OUTDOOR GAME IN WHICH THE FRISBEE IS THROWN BY SPECIFYING THE SPEED AND ANGLE OF THROW, AND THE PERSON OPPOSITE TO IT WILL EITHER CATCH OR MISS THE FRISBEE.
public class Game {
    private Player player;
    private Frisbee frisbee;

    public Game(String playerName) {
        player = new Player(playerName);
        frisbee = new Frisbee();
    }

    public void start() {
        System.out.println("Welcome to the Virtual Frisbee Game!");

        // Player makes the throw
        player.makeThrow();

        // Calculate the distance the frisbee travels
        double distance = frisbee.calculateDistance(player.getAngle(), player.getSpeed());
        System.out.printf("The frisbee traveled %.2f meters!%n", distance);

        // Check if the frisbee was caught
        if (frisbee.isCaught()) {
            System.out.println("The frisbee was caught!");
        } else {
            System.out.println("The frisbee was missed.");
        }
    }

    public static void main(String[] args) {
        Game game = new Game("John");  // You can ask the player for their name if desired
        game.start();
    }
}
