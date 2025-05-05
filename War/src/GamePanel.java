import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener
{
    private Timer timer;
    private ArrayList<Character> entities;      //list to store all entities that are shown on the screen
    private Character boss;
    private String statusMessage = "";
    private int level = 0;
    private Image background;
    private ArrayList<JButton> buttons;         //buttons for attacking enemies
    private ArrayList<Character> players;       //list to store players


    public GamePanel()
    {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setLayout(null);

        setFocusable(true);


        entities = new ArrayList<>();
        players = new ArrayList<>();
        buttons = new ArrayList<>();

        loadLevel(level);

        timer = new Timer(20, this);
        timer.start();
    }

    private void loadLevel(int level)
    {
        this.level = level;
        removeAll();
        entities.clear();
        players.clear();
        buttons.clear();

        if (level == 2)         //loads first level
        {
            background = new ImageIcon(getClass().getResource("./BG1.png")).getImage();
            Character p1 = new Doc(100, 400);

            boss = new ColonelAckermann(600, 400, players);
            entities.add(boss);
            players.add(p1);
            entities.add(p1);
        }
        else if (level == 3)       //loads second level
        {
            background = new ImageIcon(getClass().getResource("./BG2.jpeg")).getImage();

            Character p1 = new Police(130, 440);
            boss = new GeneralSchwartz(600, 400, players);
            entities.add(boss);
            entities.add(p1);
            players.add(p1);
        }
        else if (level == -1)       //the game over screen
        {
            boss = new Placeholder();
            entities.add(boss);

            background = new ImageIcon(getClass().getResource("./BG_MAIN.jpeg")).getImage();

            JLabel label = new JLabel("GAME OVER");
            label.setFont(new Font("Arial", Font.PLAIN, 50));
            label.setForeground(new Color(247, 68, 2));

            label.setBounds(270, 100, 400, 100);

            JButton startButton = new JButton("MAIN MENU");
            startButton.setFocusable(false);
            startButton.setFont(new Font("Arial", Font.PLAIN, 19));
            startButton.setBounds(295, 250, 200, 75);
            startButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                   loadLevel(0);
                }
            });

            JButton exitButton= new JButton("EXIT GAME");
            exitButton.setFocusable(false);
            exitButton.setFont(new Font("Arial", Font.PLAIN, 19));
            exitButton.setBounds(295, 325, 200, 75);
            exitButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    System.exit(0);
                }
            });

            add(label);
            add(startButton);
            add(exitButton);

        }
        else if (level == 1)        //the story of the game
        {
            boss = new Placeholder();
            entities.add(boss);

           JLabel label = new JLabel("<html>On one sunny day, from nowhere cataclysm occurred and portals were opened all over the world. From portals evil conquerors throughout the history were summoned. They conquered the world and injected the fear into all of the people who were still alive. Only a few of them, ordinary people like doctors & policemen were willing to fight. They should defeat all of the evil leaders to return the world to its peaceful times once again!!</html>");
            label.setFont(new Font("Arial", Font.BOLD, 15));
            label.setForeground(Color.WHITE);
            label.setBounds(200, 100, 400, 400);

            JButton button = new JButton("Continue");
            button.setFocusable(false);
            button.setFont(new Font("Arial", Font.PLAIN, 19));
            button.setBounds(295, 400, 200, 75);
            button.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    loadLevel(2);
                }
            });

            add(button);
            add(label);
        }
        else if (level == 0)        //main menu
        {
            statusMessage="";
            background = new ImageIcon(getClass().getResource("./BG_MAIN.jpeg")).getImage();

            JLabel label = new JLabel("WARITICS");
            label.setFont(new Font("Arial", Font.BOLD, 50));
            label.setForeground(Color.LIGHT_GRAY);
            label.setBounds(270, 100, 400, 100);

            boss = new Placeholder();
            entities.add(boss);

            JButton startButton = new JButton("START THE GAME");
            startButton.setFocusable(false);
            startButton.setFont(new Font("Arial", Font.PLAIN, 19));
            startButton.setBounds(295, 250, 200, 75);
            startButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    loadLevel(1);
                }
            });

            JButton exitButton = new JButton("EXIT GAME");
            exitButton.setFocusable(false);
            exitButton.setFont(new Font("Arial", Font.PLAIN, 19));
            exitButton.setBounds(295, 325, 200, 75);
            exitButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    System.exit(0);
                }
            });

            add(startButton);
            add(label);
            add(exitButton);

        }
        else            //more levels to come
        {
            background = new ImageIcon(getClass().getResource("./BG3.jpeg")).getImage();
            //player = new Police(100, 100);
            boss = new ColonelAckermann(400, 400, players);
            entities.add(boss);
            //entities.add(player);
        }


        for (int i = 0; i < players.size(); i++)
        {
            if(!(players.get(i) instanceof Placeholder))
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
                                    //players.get(i).update();
                                    players.get(i).attack(boss);
                                    statusMessage = players.get(i).name + " attacked " + boss.name + "!";
                                }
                            }
                        }
                    }
                });

                add(attackButton);
                //buttons.add(attackButton);
            }

        }

    }

    private void increaseLevel()
    {
        loadLevel(++level);
    }

    private boolean alivePlayers()
    {
        for (int i = 0; i < players.size(); i++)
            if(!players.get(i).isAlive())
                return false;
        return true;
    }


    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, 800, 600, null);
        for (Character e : entities)
        {
            if (e.isAlive())
                e.draw(g);
        }
        if (level >= 2)
        {
            g.setColor(Color.WHITE);
            g.drawString("Level: " + (level - 1), 700, 20);

            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.drawString(statusMessage, 150, 25);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(!alivePlayers())
        {
            loadLevel(-1);
        }
        else if (boss.isAlive())
        {
            boss.update();
        }
        else
        {
            loadLevel(++level);
            statusMessage = "Level Up! Welcome to Level " + level;
        }

        repaint();
    }
}