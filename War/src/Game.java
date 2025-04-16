import javax.swing.*;

class Game
{
    public static void main(String[] args)
    {
        // ImageIcon icon = new ImageIcon("test.jpg");
        JFrame frame = new JFrame("RPG Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        //   frame.setIconImage(icon.getImage());
        frame.add(new GamePanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
