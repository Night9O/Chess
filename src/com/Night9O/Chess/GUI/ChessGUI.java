package com.Night9O.Chess.GUI;

import com.Night9O.Chess.MainHub.ChessGameHub;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChessGUI extends JPanel implements ActionListener
{
    private Timer timer;
    private ChessGameHub chessGameHub;
    private Dimension dimension;

    public ChessGUI(Dimension dimension)
    {
        this.dimension = dimension;

        chessGameHub = new ChessGameHub(dimension);
        addMouseListener(chessGameHub.getPlayer().getPlayerMouseListener());

        timer = new Timer(60, this);
        timer.start();
    }

    @Override
    public void paint(Graphics g)
    {
        //Clear BackGround
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, dimension.width, dimension.height);

        chessGameHub.paint(g);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        repaint();
        chessGameHub.update();
    }
}
