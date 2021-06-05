import com.Night9O.Chess.GUI.ChessGUI;

import javax.swing.*;
import java.awt.*;

public class Main
{
    public static void main(String[] args)
    {
        Dimension dimension = new Dimension(700, 700);
        JFrame jFrame = new JFrame();
        ChessGUI chessGUI = new ChessGUI(dimension);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(dimension);
        jFrame.setTitle("Chess By Night9O");
        jFrame.setResizable(false);
        jFrame.add(chessGUI);
        jFrame.setVisible(true);
    }
}
