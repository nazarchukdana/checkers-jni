import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckersMenu {
    private JFrame frame;

    public CheckersMenu() {
        frame = new JFrame("Checkers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        //
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding between components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.getContentPane().setBackground(Color.BLACK);
        //
        JLabel titleLabel = new JLabel("Checkers", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setForeground(Color.ORANGE);
        frame.add(titleLabel, gbc);
        gbc.gridy = 1;
        //
        JButton playButton = new JButton("PLAY");
        playButton.setFont(new Font("Arial", Font.BOLD, 20));
        playButton.setBackground(Color.BLACK);
        playButton.setForeground(Color.ORANGE);
        playButton.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
        playButton.setPreferredSize(new Dimension(100, 50));
        playButton.setFocusPainted(false);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openGame();
            }
        });
        frame.add(playButton, gbc);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private void openGame() {
        frame.dispose();
        new Game();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CheckersMenu::new);
    }
}

