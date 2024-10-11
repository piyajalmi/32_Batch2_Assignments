//AUTHOR: PIYA JALMI
//ROLL NO: 32
// TITLE:FRISBEE THROW GAME
//START DATE:21 SEPETEMBER 2024
//MODIFIED DATE:22 SEPTEMBER 2024
//DESCRIPTION: THIS IS AN OUTDOOR GAME IN WHICH THE FRISBEE IS THROWN BY SPECIFYING THE SPEED AND ANGLE OF THROW, AND THE PERSON OPPOSITE TO IT WILL EITHER CATCH OR MISS THE FRISBEE.
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameUI extends JFrame {
    private Player player;
    private Frisbee frisbee;

    // Swing components
    private JTextField angleField, speedField;
    private JLabel resultLabel;
    private FrisbeePanel frisbeePanel;
    private Timer timer;

    // Frisbee position and physics parameters
    private int frisbeeX = 50;  // Starting X position of the frisbee
    private int frisbeeY = 350; // Starting Y position of the frisbee
    private double velocityX, velocityY;
    private double time = 0;  // Time counter for the animation
    private final double GRAVITY = 9.8;  // Simulated gravity constant

    public GameUI(String playerName) {
        // Initialize player and frisbee objects
        player = new Player(playerName);
        frisbee = new Frisbee();

        // Set up the JFrame (game window)
        setTitle("Virtual Frisbee Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Angle input field
        JLabel angleLabel = new JLabel("Enter throw angle (0-90):");
        angleLabel.setBounds(30, 30, 200, 25);
        add(angleLabel);

        angleField = new JTextField();
        angleField.setBounds(220, 30, 100, 25);
        add(angleField);

        // Speed input field
        JLabel speedLabel = new JLabel("Enter throw speed (1-10):");
        speedLabel.setBounds(30, 70, 200, 25);
        add(speedLabel);

        speedField = new JTextField();
        speedField.setBounds(220, 70, 100, 25);
        add(speedField);

        // Throw button
        JButton throwButton = new JButton("Throw Frisbee");
        throwButton.setBounds(130, 110, 150, 30);
        add(throwButton);

        // Result label to display the outcome
        resultLabel = new JLabel("");
        resultLabel.setBounds(30, 150, 700, 50);
        add(resultLabel);

        // Frisbee Panel for drawing the frisbee and catcher
        frisbeePanel = new FrisbeePanel();
        frisbeePanel.setBounds(0, 200, 800, 400);
        add(frisbeePanel);

        // Action listener for the throw button
        throwButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleThrow();
            }
        });
    }

    // Handle the throw action
    // Handle the throw action
// Handle the throw action
// Handle the throw action
private void handleThrow() {
    try {
        int angle = Integer.parseInt(angleField.getText());
        int speed = Integer.parseInt(speedField.getText());

        if (angle < 0 || angle > 90 || speed < 1 || speed > 10) {
            resultLabel.setText("Invalid input. Please enter valid angle and speed.");
            return;
        }

        // Calculate the distance the frisbee travels in meters
        double distance = frisbee.calculateDistance(angle, speed);
        String resultText = String.format("The frisbee traveled %.2f meters! ", distance);

        // Pixel position of the catcher on the panel
        int catcherX = frisbee.getCatcherPosition() * 10 + 50;
        int catcherY = 350;  // Catcher’s Y position is fixed

        // Check if the frisbee was caught based on distance (for realism)
        if (frisbee.isCaught(distance)) {
            resultText += "The frisbee was caught!";
        } else {
            resultText += "The frisbee was missed.";
        }

        resultLabel.setText(resultText + " (Catcher position: " + frisbee.getCatcherPosition() + " meters)");

        // Calculate the initial velocity components for animation
        double radians = Math.toRadians(angle);
        velocityX = speed * Math.cos(radians) * 10;  // Adjusting speed scaling for better visuals
        velocityY = speed * Math.sin(radians) * 10;

        // Reset frisbee position and time
        frisbeeX = 50;
        frisbeeY = 350;  // Start from bottom of the panel
        time = 0;

        // Start the animation
        startFrisbeeAnimation();
    } catch (NumberFormatException ex) {
        resultLabel.setText("Please enter valid numbers for angle and speed.");
    }
}

// Method to check if the frisbee has been caught based on pixel position
private boolean checkIfCaughtByPosition() {
    int catcherX = frisbee.getCatcherPosition() * 10 + 50;
    int catcherY = 350;  // Fixed catcher Y position

    // Check if frisbee is near the catcher (allowing a slight tolerance)
    if (Math.abs(frisbeeX - catcherX) <= 30 && frisbeeY >= catcherY - 30) {
        return true;  // Frisbee is caught
    }

    return false;  // Frisbee is not caught
}

// Start the frisbee animation
private void startFrisbeeAnimation() {
    if (timer != null && timer.isRunning()) {
        timer.stop();
    }

    timer = new Timer(30, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            time += 0.1;  // Increment time for the projectile calculation

            // Calculate new frisbee position using projectile motion equations
            frisbeeX = (int) (50 + velocityX * time);
            frisbeeY = (int) (350 - (velocityY * time - 0.5 * GRAVITY * time * time));

            // Check if the frisbee is caught based on pixel positions
            if (checkIfCaughtByPosition()) {
                resultLabel.setText("The frisbee was caught!");
                timer.stop();  // Stop the animation if caught
                return;
            }

            // Stop the animation when the frisbee reaches the ground or end of panel
            if (frisbeeY >= 350 || frisbeeX >= 750) {
                if (!checkIfCaughtByPosition()) {
                    resultLabel.setText("The frisbee was missed.");
                }
                timer.stop();
            }

            frisbeePanel.repaint();  // Repaint the panel to show movement
        }
    });

    timer.start();
}


    // Panel to draw the frisbee and the catcher
    private class FrisbeePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            // Draw the frisbee
            g.setColor(Color.BLUE);
            g.fillOval(frisbeeX, frisbeeY, 30, 30);  // Draw the frisbee as a blue circle
            
            // Draw the catcher (simple stick figure)
            g.setColor(Color.RED);
            int catcherX = frisbee.getCatcherPosition() * 10 + 50;  // Scale and position of the catcher
            int catcherY = 350;  // Fixed Y position for the catcher

            g.fillRect(catcherX, catcherY - 50, 10, 50);  // Body
            g.fillRect(catcherX - 5, catcherY - 60, 20, 10);  // Head
            g.drawLine(catcherX, catcherY, catcherX - 10, catcherY + 20);  // Left leg
            g.drawLine(catcherX, catcherY, catcherX + 10, catcherY + 20);  // Right leg
            g.drawLine(catcherX, catcherY - 50, catcherX - 15, catcherY - 40);  // Left arm
            g.drawLine(catcherX, catcherY - 50, catcherX + 15, catcherY - 40);  // Right arm
        }
    }

    public static void main(String[] args) {
        // Create the game UI
        GameUI gameUI = new GameUI("John");
        gameUI.setVisible(true);
    }
}
