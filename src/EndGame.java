import javax.swing.*;
import java.awt.*;

public class EndGame {
    private JFrame frame;
    public EndGame(Game game){
        frame = new JFrame("Checkers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        //
        frame.setLayout(new BorderLayout());
        Color winnerColor = game.getWinner() == game.getWHITE_CHECKER() ? Color.WHITE : Color.BLACK;
        System.out.println(game.getWinner());
        frame.getContentPane().setBackground(winnerColor);
        //
        JLabel titleLabel = new JLabel("YOU'RE A WINNER!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setForeground(winnerColor == Color.WHITE ? Color.BLACK : Color.WHITE);
        frame.add(titleLabel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
