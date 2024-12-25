import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGuessingGame {
    
    private JFrame frame;
    private JTextField guessField;
    private JLabel feedbackLabel;
    private JButton guessButton;
    private JButton playAgainButton;
    private int targetNumber;
    private int attempts;
    private int maxAttempts = 10;
    private int score;
    private int roundsPlayed;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                NumberGuessingGame window = new NumberGuessingGame();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public NumberGuessingGame() {
        initialize();
    }

    private void initialize() {
        // Initialize frame
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        // Initialize the components
        JLabel titleLabel = new JLabel("Number Guessing Game");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        titleLabel.setBounds(120, 10, 200, 30);
        frame.getContentPane().add(titleLabel);
        
        JLabel instructionLabel = new JLabel("Enter a number between 1 and 100:");
        instructionLabel.setBounds(120, 60, 200, 20);
        frame.getContentPane().add(instructionLabel);
        
        guessField = new JTextField();
        guessField.setBounds(150, 90, 100, 25);
        frame.getContentPane().add(guessField);
        guessField.setColumns(10);
        
        guessButton = new JButton("Guess");
        guessButton.setBounds(170, 130, 90, 30);
        frame.getContentPane().add(guessButton);
        
        feedbackLabel = new JLabel("");
        feedbackLabel.setBounds(150, 170, 250, 25);
        frame.getContentPane().add(feedbackLabel);
        
        playAgainButton = new JButton("Play Again");
        playAgainButton.setBounds(150, 210, 120, 30);
        playAgainButton.setEnabled(false);
        frame.getContentPane().add(playAgainButton);
        
        // Start the game
        startNewRound();

        // Add action listener to the guess button
        guessButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int guess = Integer.parseInt(guessField.getText());
                    if (guess < 1 || guess > 100) {
                        feedbackLabel.setText("Guess must be between 1 and 100.");
                    } else {
                        attempts++;
                        if (guess < targetNumber) {
                            feedbackLabel.setText("Too low! Try again.");
                        } else if (guess > targetNumber) {
                            feedbackLabel.setText("Too high! Try again.");
                        } else {
                            feedbackLabel.setText("Correct! You guessed it in " + attempts + " attempts.");
                            score += Math.max(0, 10 - attempts);
                            JOptionPane.showMessageDialog(frame, "Round over! Your score for this round: " + Math.max(0, 10 - attempts));
                            playAgainButton.setEnabled(true);
                            guessButton.setEnabled(false);
                        }
                    }
                } catch (NumberFormatException ex) {
                    feedbackLabel.setText("Please enter a valid number.");
                }
            }
        });

        // Action listener for play again button
        playAgainButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                roundsPlayed++;
                startNewRound();
            }
        });
    }

    private void startNewRound() {
        targetNumber = new Random().nextInt(100) + 1; // Random number between 1 and 100
        attempts = 0;
        guessField.setText("");
        feedbackLabel.setText("");
        guessButton.setEnabled(true);
        playAgainButton.setEnabled(false);
    }

    private void gameOver() {
        JOptionPane.showMessageDialog(frame, "Game over! You played " + roundsPlayed + " rounds. Total score: " + score);
        int playAgain = JOptionPane.showConfirmDialog(frame, "Do you want to play again?", "Play Again", JOptionPane.YES_NO_OPTION);
        if (playAgain == JOptionPane.YES_OPTION) {
            score = 0;
            roundsPlayed = 0;
            startNewRound();
        } else {
            System.exit(0);
        }
    }
}
