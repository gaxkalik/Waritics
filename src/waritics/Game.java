package waritics;

import waritics.core.*;
import javax.swing.*;
import java.io.*;

class Game
{
    public static void main(String[] args)
    {


        JFrame frame = new JFrame("RPG Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        //frame.add(new ImageIcon("test.jpg"));

        int level = 0;

        frame.add(new GamePanel(level));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
