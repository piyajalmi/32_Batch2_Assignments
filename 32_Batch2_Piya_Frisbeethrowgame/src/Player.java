//AUTHOR: PIYA JALMI
//ROLL NO: 32
// TITLE:FRISBEE THROW GAME
//START DATE:21 SEPETEMBER 2024
//MODIFIED DATE:22 SEPTEMBER 2024
//DESCRIPTION: THIS IS AN OUTDOOR GAME IN WHICH THE FRISBEE IS THROWN BY SPECIFYING THE SPEED AND ANGLE OF THROW, AND THE PERSON OPPOSITE TO IT WILL EITHER CATCH OR MISS THE FRISBEE.
import java.util.Scanner;

public class Player {
    private String name;
    private int angle;  // Angle of throw
    private int speed;  // Speed of throw

    public Player(String name) {
        this.name = name;
    }

    public void makeThrow() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter throw angle (0-90): ");
        angle = scanner.nextInt();

        System.out.print("Enter throw speed (1-10): ");
        speed = scanner.nextInt();
    }

    public int getAngle() {
        return angle;
    }

    public int getSpeed() {
        return speed;
    }
}
