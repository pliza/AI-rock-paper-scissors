package main.ui;

import javax.swing.*;
import java.awt.*;

import main.model.Move;

public class MainUI {

    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private User user;
    private Move prevMove = null;
    private int roundNumber = 0;
    private static final int MAX_ROUNDS = 10;

    public MainUI() {
        frame = new JFrame("AI Rock Paper Scissors");
        frame.setSize(2000, 1200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel startScreen = createStartScreen();
        JPanel selectionScreen = createSelectionScreen();
        user = new User("Player");

        mainPanel.add(startScreen, "start");
        mainPanel.add(selectionScreen, "select");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JPanel createStartScreen() {
        ImageIcon originalBg = new ImageIcon("src/assets/startup.png");

        JPanel panel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(originalBg.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        ImageIcon playIcon = new ImageIcon("src/assets/play.png");
        Image scaledImage = playIcon.getImage().getScaledInstance(500, 300, Image.SCALE_SMOOTH);
        JButton playButton = new JButton(new ImageIcon(scaledImage));

        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.setFocusPainted(false);
        playButton.setOpaque(false);

        playButton.addActionListener(e -> cardLayout.show(mainPanel, "select"));

        panel.add(playButton);

        panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int w = frame.getWidth();
                int h = frame.getHeight();
                int btnW = 200;
                int btnH = 80;
                int x = (w - btnW) / 2;
                int y = h - 250;
                playButton.setBounds(x, y, btnW, btnH);
            }
        });

        return panel;
    }

    private JPanel createSelectionScreen() {
        ImageIcon bgIcon = new ImageIcon("src/assets/selection_bg.png");

        JPanel panel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        int handWidth = 800;
        int handHeight = 450;
        int baseX = 850;
        int baseY = 0;
        int spacingY = 290;

        JButton rockButton = createImageButton("src/assets/rock.png", handWidth, handHeight);
        JButton paperButton = createImageButton("src/assets/paper.png", handWidth, handHeight);
        JButton scissorsButton = createImageButton("src/assets/scissors.png", handWidth, handHeight);

        rockButton.setBounds(baseX, baseY, handWidth, handHeight);
        paperButton.setBounds(baseX, baseY + spacingY, handWidth, handHeight);
        scissorsButton.setBounds(baseX, baseY + spacingY * 2, handWidth, handHeight);

        rockButton.addActionListener(e -> handlePlayerMove("rock"));
        paperButton.addActionListener(e -> handlePlayerMove("paper"));
        scissorsButton.addActionListener(e -> handlePlayerMove("scissors"));


        panel.add(rockButton);
        panel.add(paperButton);
        panel.add(scissorsButton);

        return panel;
    }

    private void handlePlayerMove(String moveName) {
        Move currMove = new Move(moveName);
        Move aiMove;

        if (roundNumber == 0 || prevMove == null) {
            aiMove = Main.generateRandomMove();
        } else {
            aiMove = user.getAIMove(prevMove);
            user.getTracker().addMove(prevMove, currMove);
        }

        user.AIScore += user.getScore(currMove, aiMove);
        user.playerScore += user.getScore(aiMove, currMove);

        prevMove = currMove;
        roundNumber++;

        JPanel resultScreen = createResultScreen(currMove, aiMove);
        mainPanel.add(resultScreen, "result");
        cardLayout.show(mainPanel, "result");
    }

    private JPanel createResultScreen(Move playerMove, Move aiMove) {
        ImageIcon bgIcon = new ImageIcon("src/assets/result_bg.png");

        JPanel panel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        // AI move image
        String aiHandPath = "src/assets/ai_" + aiMove.getName().toLowerCase() + ".png";
        ImageIcon aiHandIcon = new ImageIcon(aiHandPath);
        Image aiScaled = aiHandIcon.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH);
        JLabel aiHandLabel = new JLabel(new ImageIcon(aiScaled));
        aiHandLabel.setBounds(15, 400, 700, 500);
        panel.add(aiHandLabel);

        // Player move image
        String playerHandPath = "src/assets/" + playerMove.getName().toLowerCase() + ".png";
        ImageIcon playerHandIcon = new ImageIcon(playerHandPath);
        Image playerScaled = playerHandIcon.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH);
        JLabel playerHandLabel = new JLabel(new ImageIcon(playerScaled));
        playerHandLabel.setBounds(750, 400, 700, 500);
        panel.add(playerHandLabel);

        // Score label
        JLabel scoreLabel = new JLabel("Your Score: " + user.playerScore + " | AI Score: " + user.AIScore);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 32));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(800, 30, 600, 50);
        panel.add(scoreLabel);

        // Next Round button
        JButton nextButton = new JButton(roundNumber >= MAX_ROUNDS ? "View Results" : "Next Round");
        nextButton.setBounds(550, 700, 300, 50);
        nextButton.setFont(new Font("Arial", Font.BOLD, 22));
        nextButton.setFocusPainted(false);
        panel.add(nextButton);

        nextButton.addActionListener(e -> {
            if (roundNumber >= MAX_ROUNDS) {
                JPanel finalScreen = createGameOverScreen();
                mainPanel.add(finalScreen, "final");
                cardLayout.show(mainPanel, "final");
            } else {
                cardLayout.show(mainPanel, "select");
            }
        });

        return panel;
    }

    private JPanel createGameOverScreen() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(30, 30, 30));

        JLabel gameOver = new JLabel("Game Over!");
        gameOver.setFont(new Font("Arial", Font.BOLD, 72));
        gameOver.setForeground(Color.WHITE);
        gameOver.setBounds(700, 200, 600, 100);
        panel.add(gameOver);

        JLabel scoreLabel = new JLabel("Final Score — You: " + user.playerScore + " | AI: " + user.AIScore);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 36));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(620, 350, 800, 100);
        panel.add(scoreLabel);

        JButton quitButton = new JButton("Menu");
        quitButton.setBounds(850, 500, 200, 60);
        quitButton.setFont(new Font("Arial", Font.BOLD, 22));
        panel.add(quitButton);

        quitButton.addActionListener(e -> {
        cardLayout.show(mainPanel, "start");
        roundNumber = 0;
        user = new User("Player");
        prevMove = null; });

        return panel;
    }

    private JButton createImageButton(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(scaled));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        return button;
    }

    public static void main(String[] args) {
        new MainUI();
    }
}
