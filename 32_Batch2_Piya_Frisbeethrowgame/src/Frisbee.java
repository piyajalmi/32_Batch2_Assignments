//AUTHOR: PIYA JALMI
//ROLL NO: 32
// TITLE:FRISBEE THROW GAME
//START DATE:21 SEPETEMBER 2024
//MODIFIED DATE:22 SEPTEMBER 2024
//DESCRIPTION: THIS IS AN OUTDOOR GAME IN WHICH THE FRISBEE IS THROWN BY SPECIFYING THE SPEED AND ANGLE OF THROW, AND THE PERSON OPPOSITE TO IT WILL EITHER CATCH OR MISS THE FRISBEE.
import java.util.Random;


public class Frisbee {
    private final double GRAVITY = 9.8;
    private int catcherPosition;  // Catcher's position in meters

    public Frisbee() {
        // Randomly place the catcher between 30 and 60 meters
        Random random = new Random();
        this.catcherPosition = 30 + random.nextInt(31); // Random number between 30 and 60
    }

    // Calculate the distance the frisbee will travel in meters
    public double calculateDistance(int angle, int speed) {
        double radians = Math.toRadians(angle);
        double distance = (speed * speed * Math.sin(2 * radians)) / GRAVITY;
        return distance;
    }

    // Check if the frisbee is caught based on real-world distance
    public boolean isCaught(double distance) {
        return Math.abs(distance - catcherPosition) <= 5;  // Within 5 meters of the catcher
    }

    // Check if the frisbee is caught based on pixel collision (X and Y axis check)
    public boolean isCaughtByPixels(int frisbeeX, int frisbeeY, int catcherX, int catcherY) {
        // Check if the frisbee is within the catcher's horizontal range and near the body
        boolean inXRange = Math.abs(frisbeeX - catcherX) <= 30;  // Tolerance for horizontal distance
        boolean inYRange = Math.abs(frisbeeY - (catcherY - 25)) <= 30;  // Tolerance for vertical position (body height)

        return inXRange && inYRange;
    }

    // Get the catcher's position in meters
    public int getCatcherPosition() {
        return catcherPosition;
    }
}
