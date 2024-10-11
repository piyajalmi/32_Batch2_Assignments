//package Puzzle.Puzzle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.FilteredImageSource;

import java.util.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class MyButton extends JButton{
    private static final long serialVersionUID = 1L;
    
    boolean isLastButton;

    public MyButton(boolean isLastButton){
        this.isLastButton = isLastButton;
        setBorder(null);
        init();
    }

    public MyButton(Image iconImage, boolean isLastButton){
        setIcon(new ImageIcon(iconImage));
        this.isLastButton = isLastButton;
        setBorder(null);
        init();

    }

private void init(){
    if(!isLastButton){
        BorderFactory.createLineBorder(Color.GRAY);
        addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e){
                setBorder(BorderFactory.createLineBorder(Color.YELLOW));
            }

            public void mouseExited(MouseEvent e){
                setBorder(BorderFactory.createLineBorder(Color.GRAY));
            }
        });
    }
    else {

        BorderFactory.createLineBorder(Color.CYAN);
        addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e){
                setBorder(BorderFactory.createLineBorder(Color.GREEN));
            }

            public void mouseExited(MouseEvent e){
                setBorder(BorderFactory.createLineBorder(Color.CYAN));
            }
            
        });
    }
}
}


public class Puzzle extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1l;
    JPanel northWrapper, centerWrapper, southWrapper;
    JLabel topLabel;
    JLabel starImageLabel;
    JButton mainMenu, solutionImage, timeButton,clickButton;
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

    public Puzzle(){
        System.out.println("Which picture do you want to play? \n"+ 
        "1. Sergio Ramos, Champions League, 2014-18\n"
        + "2. Luka Modric, Ballon D'or, 2018 \n"
        + "3. Cristiano Ronaldo, Euro, 2016");
        int numberChosen = sc.nextInt();


        switch(numberChosen){
            case 1 : 
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

        solution.add(new Point(0,0));
        solution.add(new Point(0,1));
        solution.add(new Point(0,2));
        solution.add(new Point(1,0));
        solution.add(new Point(1,1));     
        solution.add(new Point(1,2));

        solution.add(new Point(2,0));
        solution.add(new Point(2,1));
        solution.add(new Point(2,2));
        solution.add(new Point(3,0));
        solution.add(new Point(3,1));
        solution.add(new Point(3,2));

        buttons =  new ArrayList<>();

        if(centerWrapper != null){
            centerWrapper.removeAll();
        }
        centerWrapper = new JPanel();
        centerWrapper.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        centerWrapper.setLayout(new GridLayout(4,3,0,0));

        try{
            sourceImage = loadImage();
            int h = getNewHeight(sourceImage.getWidth(), sourceImage.getHeight());
            resizedImage = resizeImage(sourceImage, DESIRED_WIDTH, h, BufferedImage.TYPE_INT_ARGB);

        }catch(IOException e){
            e.printStackTrace();
        }

        width = resizedImage.getWidth(null);
        height = resizedImage.getHeight(null);
        MyButton lastButton = null;

        for(int i=0; i<4;i++){
            for(int j =0; j<3; j++){

                createdImage = createImage(new FilteredImageSource(resizedImage.getSource(),
                new CropImageFilter(j*width/3, i*height/4, width/3,height/4)));

                if(i == 3 && j ==2){
                    lastButton = new MyButton(createdImage, true);
                    lastButton.putClientProperty("position", new Point(i,j));

                }
                else{
                    MyButton button = new MyButton(createdImage, false);

                    button.putClientProperty("position", new Point(i,j));
                    buttons.add(button);
                }
            }
        }

        Collections.shuffle(buttons);
        buttons.add(lastButton);

        for(int i=0; i< NUMBER_OF_BUTTONS; i++){
            JButton button = buttons.get(i);
			centerWrapper.add(button);
			button.addActionListener(new ClickAction());
        }
        add(centerWrapper, BorderLayout.CENTER);

        Image starImage = Toolkit.getDefaultToolkit().getImage("src\\resources\\star-icon.png").getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		topLabel = new JLabel("Star icon swaps with its neighouring icon");
		starImageLabel = new JLabel(new ImageIcon(starImage));
		
		northWrapper = new JPanel();
		northWrapper.add(topLabel);  northWrapper.add(starImageLabel);
		
		
		mainMenu = new JButton("Menu");
		mainMenu.addActionListener(this);
		solutionImage = new JButton("Solution");
		solutionImage.addActionListener(this);
		timeButton = new JButton("00 : 00");
		timeButton.setToolTipText("Time Taken");
		
		clickButton = new JButton("0");
		clickButton.setToolTipText("Total Clicks");
		
		southWrapper = new JPanel();
		southWrapper.add(mainMenu);  southWrapper.add(solutionImage);  southWrapper.add(timeButton);  southWrapper.add(clickButton);
		
		
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
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){

           @Override
			public void run() {
				new Puzzle();
			}

        });
    }
    private BufferedImage loadImage() throws IOException{
        BufferedImage sourceImage = null;

        String basePath = "C:\\Users\\piyaj\\Downloads\\";
        if(pictureFlag == 1)
            sourceImage = ImageIO.read(new File(basePath + "sergio_ramos_champions_league_2014-18.jpg"));
		else if(pictureFlag == 2)
			sourceImage = ImageIO.read(new File(basePath + "luka_modric_ballon_dor_2018.jpg"));
		else if(pictureFlag == 3)
			sourceImage = ImageIO.read(new File(basePath + "cristiano_ronaldo_euro_2016.jpg"));
		
		return sourceImage;
    }

    private int getNewHeight(int width, int height){
        double ratio = DESIRED_WIDTH / (double) width;

        int newHeight = (int) (height * ratio);
        return newHeight;
    }

    private BufferedImage resizeImage(BufferedImage sourceImage, int width, int height, int type){
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics g = resizedImage.createGraphics();
        g.drawImage(sourceImage, 0, 0, width, height, null);
        g.dispose();

        return resizedImage;

    }

    class ClickAction extends AbstractAction{
        private static final long serialVersionUID = 1L;

        @Override
		public void actionPerformed(ActionEvent e) {
			if(!timeFlag) {
				beforeTime = System.currentTimeMillis();
				timeFlag = true;
				countdownTime();
			}
			clicks++;
			clickButton.setText("" + clicks);
			checkButton(e);
			checkSolution();
    }
    private void checkButton(ActionEvent e){
        int lid = 0;

        for(MyButton button: buttons){
            if(button.isLastButton){
                lid = buttons.indexOf(button);
            }
        }

        JButton button = (JButton) e.getSource();
        int bid = buttons.indexOf(button);
        if((bid - 1 == lid) || (bid +1 ==lid) || (bid -3 == lid) || (bid + 3 == lid)){
            Collections.swap(buttons, bid, lid);
            updateButtons();
        }
    }
    private void updateButtons(){
        centerWrapper.removeAll();
        for(MyButton button:buttons){
            centerWrapper.add(button);
        }
        centerWrapper.validate();
    }

    private void checkSolution(){
        List<Point> current = new ArrayList<>();

        for(MyButton button: buttons){
            current.add((Point) button.getClientProperty("Position"));
        }
if(compareList(solution, current)){
    timeFlag = false;

    if(pictureFlag == 1){
        JOptionPane.showMessageDialog(centerWrapper, "Congratulations, You won!!\n", "You Won!! Time Taken:"+ timeTaken +"Clicks:"+ clicks, JOptionPane.INFORMATION_MESSAGE);
    }
    else if(pictureFlag == 2) {
        JOptionPane.showMessageDialog(centerWrapper, "Congratulations, You Won!\nJust like Cristiano Ronaldo won the Euro Tournament in 2016", 
                "You Won!!!   Time Taken: "+ timeTaken + "   Clicks: " + clicks, JOptionPane.INFORMATION_MESSAGE);
    }
    else if(pictureFlag == 3) {
        JOptionPane.showMessageDialog(centerWrapper, "Congratulations, You Won!\nJust like Luka Modric won his first Ballon D'or in 2018", 
                "You Won!!!   Time Taken: "+ timeTaken + "   Clicks: " + clicks, JOptionPane.INFORMATION_MESSAGE);
    }
    clicks = 0;
    timeTaken = null;

}

    }

    public boolean compareList(List<Point> list1, List<Point> list2){
        return list1.toString().contentEquals(list2.toString());
    }
    }

    public void countdownTime(){
        Thread t = new Thread(new Runnable(){


            @Override
			public void run() {
				while(timeFlag) {
					long currentTime = System.currentTimeMillis();
					long runninTime = currentTime - beforeTime;
					Duration duration = Duration.ofMillis(runninTime);
					
					long minutes = duration.toMinutes();
					duration = duration.minusMinutes(minutes);
					long seconds = (duration.toMillis())/1000; 
					
					DecimalFormat formatter = new DecimalFormat("00");
					timeTaken = formatter.format(minutes) + " : " + formatter.format(seconds);
					timeButton.setText(timeTaken);
                }
            }
        });
        t.start();
    }

    @Override
	public void actionPerformed(ActionEvent e) {
        if(e.getSource() == mainMenu){
            int reply = JOptionPane.showConfirmDialog(this, "All progress will be lost, are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);

            if(reply == JOptionPane.YES_OPTION){
                timeFlag = false;
                clicks = 0;
                dispose();
                new Puzzle();

            }
        }

        if(e.getSource() == solutionImage){
            JDialog dialog = new JDialog();
            dialog.setTitle("Solution");
            Image dialogIcon = Toolkit.getDefaultToolkit().getImage("src\\resources\\correct-mark-icon.png");
            dialog.setIconImage(dialogIcon);
            Image solutionImage = null;
            try{
                solutionImage = loadImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);

            }catch (IOException e1){
                e1.printStackTrace();
            }

            JLabel label = new JLabel(new ImageIcon(solutionImage));
            dialog.add(label);
            dialog.pack();
            dialog.setVisible(true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        }
}
}














    

