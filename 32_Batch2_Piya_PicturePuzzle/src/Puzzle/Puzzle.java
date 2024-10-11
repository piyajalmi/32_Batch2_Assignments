import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List; // Explicit import
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.*;

class MyButton extends JButton {
    private static final long serialVersionUID = 1L;
    boolean isLastButton;

    public MyButton(boolean isLastButton) {
        this.isLastButton = isLastButton;
        setBorder(null);
        init();
    }

    public MyButton(Image iconImage, boolean isLastButton) {
        setIcon(new ImageIcon(iconImage));
        this.isLastButton = isLastButton;
        setBorder(null);
        init();
    }

    private void init() {
        if (!isLastButton) {
            setBorder(BorderFactory.createLineBorder(Color.GRAY));
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    setBorder(BorderFactory.createLineBorder(Color.YELLOW));
                }

                public void mouseExited(MouseEvent e) {
                    setBorder(BorderFactory.createLineBorder(Color.GRAY));
                }
            });
        } else {
            setBorder(BorderFactory.createLineBorder(Color.CYAN));
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    setBorder(BorderFactory.createLineBorder(Color.GREEN));
                }

                public void mouseExited(MouseEvent e) {
                    setBorder(BorderFactory.createLineBorder(Color.CYAN));
                }
            });
        }
    }
}

public class Puzzle extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    JPanel northWrapper, centerWrapper, southWrapper;
    JLabel topLabel, starImageLabel;
    JButton mainMenu, solutionImage, timeButton, clickButton;
    BufferedImage sourceImage, resizedImage;
    Image createdImage;
    int width, height;
    long beforeTime;
    String timeTaken;
    boolean timeFlag;
    int clicks;

    Scanner sc = new Scanner(System.in);
    Image iconImage = Toolkit.getDefaultToolkit().getImage("src\\resources\\puzzle-icon.png").getScaledInstance(80, 80, Image.SCALE_SMOOTH);
    int pictureFlag;

    List<MyButton> buttons; 
    List<Point> solution; 

    final int NUMBER_OF_BUTTONS = 12;
    final int DESIRED_WIDTH = 300;

    public Puzzle() {
        System.out.println("Which picture do you want to play? \n" + 
        "1. Sergio Ramos, Champions League, 2014-18\n" +
        "2. Luka Modric, Ballon D'or, 2018 \n" +
        "3. Cristiano Ronaldo, Euro, 2016");
        int numberChosen = sc.nextInt();

        switch (numberChosen) {
            case 1:
                pictureFlag = 1;
                break;
            case 2:
                pictureFlag = 2;
                break;
            case 3:
                pictureFlag = 3;
                break;
            default:
                System.out.println("Wrong input: Default Picture 1 chosen.");
                pictureFlag = 1;
        }

        solution = new ArrayList<>();
        solution.add(new Point(0, 0));
        solution.add(new Point(0, 1));
        solution.add(new Point(0, 2));
        solution.add(new Point(1, 0));
        solution.add(new Point(1, 1));
        solution.add(new Point(1, 2));
        solution.add(new Point(2, 0));
        solution.add(new Point(2, 1));
        solution.add(new Point(2, 2));
        solution.add(new Point(3, 0));
        solution.add(new Point(3, 1));
        solution.add(new Point(3, 2));

        buttons = new ArrayList<>();

        if (centerWrapper != null) {
            centerWrapper.removeAll();
        }

        centerWrapper = new JPanel();
        centerWrapper.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        centerWrapper.setLayout(new GridLayout(4, 3, 0, 0));

        try {
            sourceImage = loadImage();
            int h = getNewHeight(sourceImage.getWidth(), sourceImage.getHeight());
            resizedImage = resizeImage(sourceImage, DESIRED_WIDTH, h, BufferedImage.TYPE_INT_ARGB);
        } catch (IOException e) {
            e.printStackTrace();
        }

        width = resizedImage.getWidth(null);
        height = resizedImage.getHeight(null);
        MyButton lastButton = null;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                createdImage = createImage(new FilteredImageSource(resizedImage.getSource(),
                        new CropImageFilter(j * width / 3, i * height / 4, width / 3, height / 4)));

                if (i == 3 && j == 2) {
                    lastButton = new MyButton(createdImage, true);
                    lastButton.putClientProperty("position", new Point(i, j));
                } else {
                    MyButton button = new MyButton(createdImage, false);
                    button.putClientProperty("position", new Point(i, j));
                    buttons.add(button);
                }
            }
        }

        Collections.shuffle(buttons);
        buttons.add(lastButton);

        for (int i = 0; i < NUMBER_OF_BUTTONS; i++) {
            JButton button = buttons.get(i);
            centerWrapper.add(button);
            button.addActionListener(new ClickAction());
        }

        add(centerWrapper, BorderLayout.CENTER);

        Image starImage = Toolkit.getDefaultToolkit().getImage("src\\resources\\star-icon.png").getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        topLabel = new JLabel("Star icon swaps with its neighboring icon");
        starImageLabel = new JLabel(new ImageIcon(starImage));

        northWrapper = new JPanel();
        northWrapper.add(topLabel);
        northWrapper.add(starImageLabel);

        mainMenu = new JButton("Menu");
        mainMenu.addActionListener(this);
        solutionImage = new JButton("Solution");
        solutionImage.addActionListener(this);
        timeButton = new JButton("00 : 00");
        timeButton.setToolTipText("Time Taken");

        clickButton = new JButton("0");
        clickButton.setToolTipText("Total Clicks");

        southWrapper = new JPanel();
        southWrapper.add(mainMenu);
        southWrapper.add(solutionImage);
        southWrapper.add(timeButton);
        southWrapper.add(clickButton);

        add(northWrapper, BorderLayout.NORTH);
        add(centerWrapper, BorderLayout.CENTER);
        add(southWrapper, BorderLayout.SOUTH);

        pack();
        setTitle("Picture Puzzle");
        setLayout(new BorderLayout());
        setIconImage(iconImage);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Make sure the frame requests focus
        setFocusable(true);
        requestFocusInWindow();

        // Add Key Listener for Arrow Key Movements
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int emptyTileIndex = findEmptyTileIndex();
                System.out.println("Key pressed: " + KeyEvent.getKeyText(e.getKeyCode())); // Debugging output

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        moveTile(emptyTileIndex, emptyTileIndex + 3); // Move tile down
                        break;
                    case KeyEvent.VK_DOWN:
                        moveTile(emptyTileIndex, emptyTileIndex - 3); // Move tile up
                        break;
                    case KeyEvent.VK_LEFT:
                        moveTile(emptyTileIndex, emptyTileIndex + 1); // Move tile right
                        break;
                    case KeyEvent.VK_RIGHT:
                        moveTile(emptyTileIndex, emptyTileIndex - 1); // Move tile left
                        break;
                }
            }
        });
    }

    // Method to find the empty tile
    private int findEmptyTileIndex() {
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).isLastButton) {
                return i;
            }
        }
        return -1;
    }

    // Move tile logic
    private void moveTile(int emptyTileIndex, int newTileIndex) {
        if (newTileIndex >= 0 && newTileIndex < buttons.size() && isAdjacent(newTileIndex, emptyTileIndex)) {
            Collections.swap(buttons, emptyTileIndex, newTileIndex);
            updateButtons();
            clicks++;
            clickButton.setText("" + clicks);
            checkSolution();
        }
    }

    private BufferedImage loadImage() throws IOException {
        BufferedImage sourceImage = null;
        String basePath = "C:\\Users\\piyaj\\Downloads\\";

        if (pictureFlag == 1)
            sourceImage = ImageIO.read(new File(basePath + "sergio_ramos_champions_league_2018.jpg"));
        else if (pictureFlag == 2)
            sourceImage = ImageIO.read(new File(basePath + "luka_modric_ballon_dor_2018.jpg"));
        else if (pictureFlag == 3)
            sourceImage = ImageIO.read(new File(basePath + "cristiano_ronaldo_euro_2016.jpg"));

        return sourceImage;
    }

    private boolean isAdjacent(int index1, int index2) {
        Point p1 = (Point) buttons.get(index1).getClientProperty("position");
        Point p2 = (Point) buttons.get(index2).getClientProperty("position");

        return (Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y)) == 1;
    }

    private void updateButtons() {
        centerWrapper.removeAll();
        for (JButton btn : buttons) {
            centerWrapper.add(btn);
        }
        centerWrapper.validate();
    }

    private int getNewHeight(int w, int h) {
        double ratio = DESIRED_WIDTH / (double) w;
        return (int) (h * ratio);
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height, int type) {
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    private void checkSolution() {
        List<Point> current = new ArrayList<>(); 
        for (JButton btn : buttons) {
            current.add((Point) btn.getClientProperty("position"));
        }

        if (compareList(solution, current)) {
            long afterTime = System.currentTimeMillis();
            Duration duration = Duration.ofMillis(afterTime - beforeTime);
            timeTaken = new DecimalFormat("###,###").format(duration.toSeconds());
            timeButton.setText(timeTaken + " s");
            JOptionPane.showMessageDialog(centerWrapper, "Congratulations! You've completed the puzzle.");
        }
    }

    private boolean compareList(List<Point> solution, List<Point> current) {
        return solution.toString().contentEquals(current.toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Actions for buttons if necessary
    }

    class ClickAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            int emptyTileIndex = findEmptyTileIndex();
            int clickedButtonIndex = buttons.indexOf(button);

            if (isAdjacent(clickedButtonIndex, emptyTileIndex)) {
                Collections.swap(buttons, clickedButtonIndex, emptyTileIndex);
                updateButtons();
                clicks++;
                clickButton.setText("" + clicks);
                checkSolution();
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Puzzle puzzle = new Puzzle();
        });
    }
}
