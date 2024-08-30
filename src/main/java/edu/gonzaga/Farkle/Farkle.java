package edu.gonzaga.Farkle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// import com.github.dnsev.identicon.Identicon;

// Java Swing based Farkle frontend
// Add/edit/change as you see fit to get it to play Yahtzee!
class Farkle {
    // If you have your HW3 code, you could include it like this, as needed
    Player player = new Player();
    Timer timer;
    // Meld meld;
    // Hand hand;
    int temp = 0;
    TurnHand hand = new TurnHand(6);
    FarkleScoreChecker scoreChecker = new FarkleScoreChecker();
    // Main game GUI window and two main panels (left & right)
    JFrame mainWindowFrame;
    JPanel controlPanel;
    JPanel scorecardPanel;

    // Dice view, user input, reroll status, and reroll button
    JTextField diceValuesTextField;
    JTextField diceKeepStringTextField;
    JButton diceRerollBtn;
    JTextField rerollsLeftTextField;

    // Player name - set it to your choice
    JTextField playerNameTextField = new JTextField();

    // Buttons for showing dice and checkboxes for meld include/exclude
    ArrayList<JButton> diceButtons = new ArrayList<>();
    ArrayList<JCheckBox> meldCheckboxes = new ArrayList<>();

    JButton rerollButton = new JButton("Reroll");
    JButton bankMeldButton = new JButton("Bank Meld");
    JTextField diceDebugLabel = new JTextField();
    JLabel meldScoreTextLabel = new JLabel();
    JLabel bankScoreTextLabel = new JLabel();
    JButton rollButton = new JButton();

    JPanel playerInfoPanel = new JPanel();
    JPanel diceControlPanel = new JPanel();
    JPanel meldControlPanel = new JPanel();

    DiceImages diceImages = new DiceImages("media/");


    public static void main(String [] args) {
        Farkle app = new Farkle();    // Create, then run GUI
        app.runGUI();
    }

    // Constructor for the actual Farkle object
    public Farkle() {
        // player = new Player();
        // Create any object you'll need for storing the game:
        // Player, Scorecard, Hand/Dice
    }

    // Sets up the full Swing GUI, but does not do any callback code
    void setupGUI() {
        // Make and configure the window itself
        this.mainWindowFrame = new JFrame("James' GUI Farkle");
        this.mainWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainWindowFrame.setLocation(100,100);
        
        // Player info and roll button panel
        this.playerInfoPanel = genPlayerInfoPanel();

        // Dice status and checkboxes to show the hand and which to include in the meld
        this.diceControlPanel = genDiceControlPanel();

        // The bottom Meld control panel
        this.meldControlPanel = genMeldControlPanel();

        mainWindowFrame.getContentPane().add(BorderLayout.NORTH, this.playerInfoPanel);
        mainWindowFrame.getContentPane().add(BorderLayout.CENTER, this.diceControlPanel);
        mainWindowFrame.getContentPane().add(BorderLayout.SOUTH, this.meldControlPanel);
        mainWindowFrame.pack();
    }

    /**
     * Generates and returns a JPanel containing components for meld control.
     *
     * This method creates a JPanel with a flow layout. It includes components such as a label
     * for meld score, a button to calculate meld, and a label to display the meld score. 
     *
     * @return A JPanel containing components for meld control.
     */
    private JPanel genMeldControlPanel() {
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new FlowLayout());
        newPanel.setBackground(new java.awt.Color(204, 166, 166));

        JLabel meldScoreLabel = new JLabel("Meld Score:");
        JLabel bankScoreLabel = new JLabel("Bank Score:");
        this.meldScoreTextLabel.setText("0");
        this.bankScoreTextLabel.setText(String.valueOf(player.getScore()));

        newPanel.add(rerollButton);
        newPanel.add(bankMeldButton);
        newPanel.add(meldScoreLabel);
        newPanel.add(this.meldScoreTextLabel);
        newPanel.add(bankScoreLabel);
        newPanel.add(this.bankScoreTextLabel);

        return newPanel;
    }


    /**
     * Generates and returns a JPanel containing components for dice control.
     *
     * This method creates a JPanel with a black border and a grid layout (3 rows, 7 columns).
     * It includes components such as labels for dice values and meld options, buttons for each
     * dice, and checkboxes for melding. The dice buttons and meld checkboxes are added to
     * corresponding lists for further manipulation.
     *
     * @return A JPanel containing components for dice control.
     */
    private JPanel genDiceControlPanel() {
        JPanel newPanel = new JPanel();
        newPanel.setBackground(new java.awt.Color(102, 178, 255));
        newPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        newPanel.setLayout(new GridLayout(3, 7, 1, 1));
        JLabel diceLabel = new JLabel("Dice Vals:");
        JLabel meldBoxesLabel = new JLabel("Meld 'em:");

        newPanel.add(new Panel());  // Upper left corner is blank
        for(Integer index = 0; index < 6; index++) {
            //JLabel colLabel = new JLabel(index.toString(), SwingConstants.CENTER);
            JLabel colLabel = new JLabel(Character.toString('A' + index), SwingConstants.CENTER);
            newPanel.add(colLabel);
        }
        newPanel.add(diceLabel);

        for(Integer index = 0; index < 6; index++) {
            JButton diceStatusButton = new JButton(diceImages.getDieImage(index + 1));
            this.diceButtons.add(diceStatusButton);
            newPanel.add(diceStatusButton);
        }

        newPanel.add(meldBoxesLabel);
        for(Integer index = 0; index < 6; index++) {
            JCheckBox meldCheckbox = new JCheckBox();
            meldCheckbox.setHorizontalAlignment(SwingConstants.CENTER);
            this.meldCheckboxes.add(meldCheckbox);
            newPanel.add(meldCheckbox);
        }

        return newPanel;
    }

    /**
     * Generates and returns a JPanel containing player information components.
     *
     * This method creates a JPanel with a black border and a horizontal flow layout.
     * It includes components such as a JLabel for player name, a JTextField for entering
     * the player name, a JButton for rolling dice, and a debug label for dice information.
     * The player name text field, dice debug label, and roll button are added to the panel
     * with appropriate configurations.
     *
     * @return A JPanel containing components for player information.
     */
    private JPanel genPlayerInfoPanel() {
        JPanel newPanel = new JPanel();
        newPanel.setBackground(new java.awt.Color(204, 166, 166));
        newPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        newPanel.setLayout(new FlowLayout());    // Left to right

        JLabel playerNameLabel = new JLabel("Player name:");
        playerNameTextField.setColumns(20);
        diceDebugLabel.setColumns(6);
        diceDebugLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the text
        rollButton.setText("Roll Dice");


        newPanel.add(playerNameLabel);   // Add our player label
        newPanel.add(playerNameTextField); // Add our player text field
        newPanel.add(rollButton);        // Put the roll button on there
        newPanel.add(this.diceDebugLabel);

        return newPanel;
    }


    /*
     *  This is a method to show you how you can set/read the visible values
     *   in the various text widgets.
     */
    private void putDemoDefaultValuesInGUI() {
        // Example setting of player name
        this.playerNameTextField.setText("Unknown Player");

        // Example Dice debug output
        this.diceDebugLabel.setText("124566");
    }

    /*
     * This is a demo of how to add callbacks to the buttons
     *  These callbacks can access the class member variables this way
     */
    private void addDemoButtonCallbackHandlers() {
        // Example of a button callback - just prints when clicked
        this.rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timer == null || !timer.isRunning()) {
                    timer = new Timer(100, new ActionListener() {
                        int rollCount = 0;
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (rollCount < 6) {
                                hand.rollDice(6);
                                for (int i = 0; i < 6; i++) {
                                    int sideUp = hand.getDice().get(i).getSideUp();
                                    diceButtons.get(i).setIcon(diceImages.getDieImage(sideUp));
                                }
                                rollCount++;
                            } else {
                                timer.stop();
                                diceDebugLabel.setText(hand.printDice(6));
                                if (!scoreChecker.checkFarkle(hand.getDice(), 6)) {
                                    diceDebugLabel.setText("Farkle!");
                                    meldScoreTextLabel.setText("0");
                                    temp = 0;
                                }
                            }
                        }
                    });
                    timer.start();
                }
            }
        });
        bankMeldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.setScore(player.getScore() + Integer.parseInt(meldScoreTextLabel.getText()));
                for(JCheckBox checkBox : meldCheckboxes) {
                    checkBox.setSelected(false);
                }
                temp = 0;
                meldScoreTextLabel.setText("0");
                rollButton.doClick();
                bankScoreTextLabel.setText(String.valueOf(player.getScore()));
            }
        });
        rerollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timer == null || !timer.isRunning()) {
                    timer = new Timer(100, new ActionListener() {
                        int rollCount = 0;
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            temp = Integer.parseInt(meldScoreTextLabel.getText());
                            int numDice = 6;
                            int notNull = 0;
                            for(JCheckBox checkBox : meldCheckboxes) {
                                if(checkBox.isSelected()) {
                                    numDice--;
                                }
                                checkBox.setSelected(false);
                            }
                            for (int i = 0; i < 6; i++) {
                                if (diceButtons.get(i).getIcon() == null) {
                                    notNull++;
                                }
                            }
                            if (rollCount < 6) {
                                hand.rollDice(numDice - notNull);
                                for (int i = 0; i < 6; i++) {
                                    if (i < numDice - notNull) {
                                        int sideUp = hand.getDice().get(i).getSideUp();
                                        diceButtons.get(i).setIcon(diceImages.getDieImage(sideUp));
                                    }
                                    else
                                        diceButtons.get(i).setIcon(null);
                                }
                                rollCount++;
                            } else {
                                timer.stop();
                                diceDebugLabel.setText(hand.printDice(numDice - notNull));
                                if (!scoreChecker.checkFarkle(hand.getDice(), numDice - notNull)) {
                                    diceDebugLabel.setText("Farkle!");
                                    meldScoreTextLabel.setText("0");
                                    temp = 0;
                                }
                            }
                        }
                    });
                    timer.start();
                }
            }
        });
        // Example of a checkbox handling events when checked/unchecked
        meldCheckboxes.get(0).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char i = 65;
                hand.reset(6);
                for(JCheckBox checkBox : meldCheckboxes) {
                    if(checkBox.isSelected()) {
                        hand.bankDice(String.valueOf(i));
                    }
                    i++;
                }
                meldScoreTextLabel.setText(String.valueOf(scoreChecker.scoreCalculator(hand.getDice(),hand.getIsInMeld(),6) + temp));
            }
        });
        meldCheckboxes.get(1).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char i = 65;
                hand.reset(6);
                for(JCheckBox checkBox : meldCheckboxes) {
                    if(checkBox.isSelected()) {
                        hand.bankDice(String.valueOf(i));
                    }
                    i++;
                }
                meldScoreTextLabel.setText(String.valueOf(scoreChecker.scoreCalculator(hand.getDice(),hand.getIsInMeld(),6) + temp));
            }
        });
        meldCheckboxes.get(2).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char i = 65;
                hand.reset(6);
                for(JCheckBox checkBox : meldCheckboxes) {
                    if(checkBox.isSelected()) {
                        hand.bankDice(String.valueOf(i));
                    }
                    i++;
                }
                meldScoreTextLabel.setText(String.valueOf(scoreChecker.scoreCalculator(hand.getDice(),hand.getIsInMeld(),6) + temp));
            }
        });
        meldCheckboxes.get(3).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char i = 65;
                hand.reset(6);
                for(JCheckBox checkBox : meldCheckboxes) {
                    if(checkBox.isSelected()) {
                        hand.bankDice(String.valueOf(i));
                    }
                    i++;
                }
                meldScoreTextLabel.setText(String.valueOf(scoreChecker.scoreCalculator(hand.getDice(),hand.getIsInMeld(),6) + temp));
            }
        });
        meldCheckboxes.get(4).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char i = 65;
                hand.reset(6);
                for(JCheckBox checkBox : meldCheckboxes) {
                    if(checkBox.isSelected()) {
                        hand.bankDice(String.valueOf(i));
                    }
                    i++;
                }
                meldScoreTextLabel.setText(String.valueOf(scoreChecker.scoreCalculator(hand.getDice(),hand.getIsInMeld(),6) + temp));
            }
        });
        meldCheckboxes.get(5).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char i = 65;
                hand.reset(6);
                for(JCheckBox checkBox : meldCheckboxes) {
                    if(checkBox.isSelected()) {
                        hand.bankDice(String.valueOf(i));
                    }
                    i++;
                }
                meldScoreTextLabel.setText(String.valueOf(scoreChecker.scoreCalculator(hand.getDice(),hand.getIsInMeld(),6) + temp));
            }
        });
    }

    /*
     *  Builds the GUI frontend and allows you to hook up the callbacks/data for game
     */
    void runGUI() {
        System.out.println("Starting GUI app");
        setupGUI();

        // These methods are to show you how it works
        // Once you get started working, you can comment them out so they don't
        //  mess up your own code.
        putDemoDefaultValuesInGUI();
        addDemoButtonCallbackHandlers();

        // Right here is where you could methods to add your own callbacks
        // so that you can make the game actually work.

        // Run the main window - begins GUI activity
        mainWindowFrame.setVisible(true);
        System.out.println("Done in GUI app");
    }
    public void sleep(int i) throws InterruptedException {
            Thread.sleep(i);
    }
}