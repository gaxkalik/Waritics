package waritics.core;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GamePanel extends JPanel implements ActionListener
{
    private final Timer timer;
    private ArrayList<Character> entities;      //list to store all entities that are shown on the screen
    private Character boss;
    private String statusMessage = "";
    int level = 0;
    private Image background;
    private ArrayList<Character> players;       //list to store players
    private Config config;
    private HashMap<Integer, Integer[]> attackButtonGrid;
    private int damage = 0, defense = 0;

    public GamePanel(int level)
    {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setLayout(null);

        setFocusable(true);

        config = new Config(this);
        entities = new ArrayList<>();
        players = new ArrayList<>();
        attackButtonGrid = new HashMap<Integer, Integer[]>(42);
        int x = 50, y = 25;
        for (int i = 0; i < 42; i++) {
            if (i % 7 == 0) {
                x = 50;
                y += 75;
            }
            attackButtonGrid.put(i, new Integer[]{x, y});
            x += 100;
        }
        loadLevel(level);

        timer = new Timer(30, this);            //generates an event for game loop
        timer.start();
    }

    private void loadLevel(int level)
    {
        if (level != -2)
            this.level = level;
        removeAll();
        entities.clear();
        players.clear();
        statusMessage = "";

        if (level == 2)         //loads first level
        {
            background = new ImageIcon(getClass().getResource("../textures/BG1.png")).getImage();
            Character p1 = new Doc(100, 400);

            boss = new ColonelAckermann(600, 400, players);
            entities.add(boss);
            players.add(p1);
            entities.add(p1);

        } else if (level == 3)       //loads second level
        {
            background = new ImageIcon(getClass().getResource("../textures/BG2.jpeg")).getImage();

            Character p1 = new Police(130, 440, defense, damage);
            boss = new GeneralSchwartz(600, 400, players);
            entities.add(boss);
            entities.add(p1);
            players.add(p1);
        } else if (level == -1)       //the game over screen
        {
            boss = new Placeholder();
            entities.add(boss);

            background = new ImageIcon(getClass().getResource("../textures/BG_MAIN.jpeg")).getImage();

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

            add(label);
            add(startButton);
            add(exitButton);

        } else if (level == 1)        //the story of the game
        {
            loadMainStory();
        } else if (level == 0)        //main menu
        {
            loadMainMenu();
        } else if (level == -2) {
            loadEquipmentMenu();
        } else            //more levels to come
        {
            background = new ImageIcon(getClass().getResource("../textures/BG3.jpeg")).getImage();
            //player = new Police(100, 100);
            boss = new ColonelAckermann(400, 400, players);
            entities.add(boss);
            //entities.add(player);
        }

        if (level >= 2)
        {
            try
            {
                config.saveToDisc();
            } catch (Exception E)
            {
                statusMessage = "Error saving game!";
            }
        }

        generateAttackButtons();
        generateMainMenuButton();

    }

    private void loadMainMenu()
    {
        statusMessage = "";
        background = new ImageIcon(getClass().getResource("../textures/BG_MAIN.jpeg")).getImage();

        JLabel label = new JLabel("WARITICS");
        label.setFont(new Font("Arial", Font.BOLD, 50));
        label.setForeground(Color.LIGHT_GRAY);
        label.setBounds(270, 100, 400, 100);

        boss = new Placeholder();
        entities.add(boss);

        JButton startButton = new JButton("NEW GAME");
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
        exitButton.setBounds(295, 400, 200, 75);
        exitButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });

        JButton loadButton = new JButton("CONTINUE");
        loadButton.setFocusable(false);
        loadButton.setFont(new Font("Arial", Font.PLAIN, 19));
        loadButton.setBounds(295, 325, 200, 75);
        loadButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //System.out.println("sdfghj");
                try
                {
                    config.loadFromDisc();
                    loadLevel(config.level);
                } catch (Exception E)
                {
                    loadLevel(0);
                    statusMessage = "Error loading game!";
                }

            }
        });

        add(loadButton);
        add(startButton);
        add(label);
        add(exitButton);

    }

    private void generateAttackButtons()
    {
        for (int i = 0; i < players.size(); i++)
        {
            if(!(players.get(i) instanceof Placeholder))
            {
                JButton attackButton = new JButton("<html>Attack<br>" + players.get(i).name + "</html>");

                attackButton.setFont(new Font("Arial", Font.PLAIN, 8));
                attackButton.setFocusable(false);
                attackButton.setBounds(70 * i, 550, 70, 50);


                Timer attackTimer = new Timer(players.get(i).attackSpeed, new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        attackButton.setEnabled(true);
                        attackButton.setVisible(true);
                    }
                });
                attackTimer.setRepeats(false);

                attackButton.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        attackButton.setEnabled(false);
                        attackButton.setVisible(false);
                        int gridIndex = (int)(Math.random() * 40);
                        attackButton.setBounds(attackButtonGrid.get(gridIndex)[0], attackButtonGrid.get(gridIndex)[1], 75, 50);
                        attackTimer.start();

                        if (entities != null && players != null)
                        {
                            for (int i = 0; i < players.size(); i++)
                            {
                                if (players.get(i).isAlive() && boss.isAlive())
                                {
                                    players.get(i).attack(boss);
                                    statusMessage = players.get(i).name + " attacked " + boss.name + "!";
                                }
                            }
                        }
                    }
                });

                add(attackButton);
            }

        }
    }

    private void generateMainMenuButton()
    {
        if(level >= 2)
        {
            JButton mainMenuButton = new JButton("MAIN MENU");
            mainMenuButton.setFocusable(false);
            mainMenuButton.setFont(new Font("Arial", Font.PLAIN, 8));
            mainMenuButton.setBounds(0, 0, 75, 40);
            mainMenuButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    loadLevel(0);
                }
            }
            );
            add(mainMenuButton);
        }
    }

    private void loadMainStory()
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

    private void loadEquipmentMenu()
    {
        boss = new Placeholder();
        entities.add(boss);

        JButton armorButton = new JButton("Armor: +3");
        armorButton.setFocusable(false);
        armorButton.setFont(new Font("Arial", Font.PLAIN, 8));
        armorButton.setBounds(200, 250, 100, 50);
        armorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                defense += 3;
                System.out.println(level);
                loadLevel(++level);
            }
        });
        JButton weaponButton = new JButton("Damage: +3");
        weaponButton.setFocusable(false);
        weaponButton.setFont(new Font("Arial", Font.PLAIN, 8));
        weaponButton.setBounds(400, 250, 100, 50);
        weaponButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                damage += 3;
                loadLevel(++level);
            }
        });
        add(armorButton);
        add(weaponButton);
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
    public void actionPerformed(ActionEvent e)      //timer activates this every 20ms
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

            loadLevel(-2);
            statusMessage = "Level Up! Welcome to Level " + level;
        }

        repaint();                      //draws the game by calling paint component
    }
}
