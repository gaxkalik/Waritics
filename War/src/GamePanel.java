import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener, KeyListener
{
    private Timer timer;
    private ArrayList<Character> entities;      //list to store all entities that are shown on the screen
    private Character player;
    private Character boss;
    private String statusMessage = "";
    //private boolean up, down, left, right;
    private int level = 1;
    private Image background;
    private ArrayList<JButton> buttons;         //buttons for attacking enemies
    private ArrayList<Character> players;       //list to store players




    public GamePanel()
    {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setLayout(null);

        setFocusable(true);
        addKeyListener(this);

        entities = new ArrayList<>();
        players = new ArrayList<>();
        buttons = new ArrayList<>();

        loadLevel(level);

        timer = new Timer(20, this);
        timer.start();
    }

    private void loadLevel(int level)
    {
        removeAll();
        entities.clear();
        players.clear();
        buttons.clear();

        if (level == 1)
        {
            background = new ImageIcon(getClass().getResource("./BG1.png")).getImage();
            Character p1 = new Doc(100, 100);
            //Character p2 = new Police(200, 100);

            boss = new GeneralSchwartz(400, 400, players);
            entities.add(boss);
            //entities.add(player);
            //players.add(player);
            players.add(p1);
            entities.add(p1);
        }
        else if (level == 2)
        {
            background = new ImageIcon(getClass().getResource("./BG2.jpeg")).getImage();

            Character p1 = new Police(100, 100);
            Character p2 = new Doc(200, 100);
            boss = new ColonelAckermann(400, 400, players);
            entities.add(boss);
            entities.add(p1);
            entities.add(p2);
            players.add(p1);
            players.add(p2);
        }
        else
        {
            background = new ImageIcon(getClass().getResource("./BG3.jpeg")).getImage();
            player = new Police(100, 100);
            boss = new ColonelAckermann(400, 400, players);
            entities.add(boss);
            entities.add(player);
        }




        for (int i = 0; i < players.size(); i++)
        {
            JButton attackButton = new JButton("<html>Attack<br>" + players.get(i).name + "</html>");

            //remove(attackButton);

            attackButton.setFont(new Font("Arial", Font.PLAIN, 8));
            attackButton.setFocusable(false);
            attackButton.setBounds(70 * i, 550, 70, 50);


            Timer attackTimer = new Timer(players.get(i).attackSpeed, new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    attackButton.setEnabled(true);

                }
            });
            attackTimer.setRepeats(false);

            attackButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    attackButton.setEnabled(false);
                    attackTimer.start();
                    if (entities != null && players != null)
                    {
                        for (int i = 0; i < players.size(); i++)
                        {

                            if (players.get(i).isAlive() && boss.isAlive())
                            {
                                players.get(i).update();
                                players.get(i).attack(boss);
                                statusMessage = player.name + " attacked " + players.get(i).name + "!";
                            }
                        }
                    }
                }
            });

            add(attackButton);
            //buttons.add(attackButton);


        }

    }


    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, 800, 600, null);
        for (Character e : entities)
        {
            if (e.isAlive()) e.draw(g);

        }

        g.setColor(Color.WHITE);
        g.drawString("Level: " + level, 700, 20);

        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString(statusMessage, 150, 25);
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        /*
        if (up && player.y >= 0)
            player.y -= player.speed;
        if (down && player.y <= 600 - 40)
            player.y += player.speed;
        if (left && player.x >= 0)
            player.x -= player.speed;
        if (right && player.x <= 800 - 40)
            player.x += player.speed;

         */




    /*
        for (Character character : entities)
        {
            if (character != player && character.isAlive())
            {
                character.update();

                if (player.getBounds().intersects(character.getBounds()))
                {
                    character.attack(player);
                    statusMessage = character.name + " attacked you!";
                }

                if (character.getBounds().intersects(player.getBounds()))
                {
                    player.attack(character);
                    statusMessage = player.name + " attacked " + character.name + "!";
                }

            }
        }
        */
        if(boss.isAlive())
            boss.update();
        else
        {
            loadLevel(++level);
            statusMessage = "Level Up! Welcome to Level " + level;
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        /*
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
            up = true;
        else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
            down = true;
        else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
            left = true;
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
            right = true;
        else if (e.getKeyCode() == KeyEvent.VK_SHIFT)
            player.speed = 7;
        else if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            for (Character character : entities)
            {
                if (character != player && character.isAlive() && player.getBounds().intersects(character.getBounds()))
                {
                    character.takeDamage(40);
                    statusMessage = player.name + " used special attack on " + character.name + "!";
                }
            }
        }

         */
    }



    @Override
    public void keyReleased(KeyEvent e)
    {
        /*
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
            up = false;
        else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
            down = false;
        else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
            left = false;
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
            right = false;
        else if (e.getKeyCode() == KeyEvent.VK_SHIFT)
            player.speed = 4;

         */
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        return;
    }
}