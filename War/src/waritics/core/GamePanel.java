package waritics.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * The {@code GamePanel} class represents the main panel where the game is rendered and logic is executed.
 * It manages game entities, levels, user interface elements, and the game loop.
 * This panel extends {@code JPanel} and implements {@code ActionListener} to handle timer events.
 */
public class GamePanel extends JPanel implements ActionListener
{
    /**The timer that triggers game updates and repaints at a fixed interval.*/
    private final Timer timer;
    /**A list to store all {@code Character} entities that are currently displayed on the screen.*/
    ArrayList<Character> entities;
    /**The main boss character in the current game level.*/
    Character boss;
    /**A message to display game status or information to the player.*/
    private String statusMessage = "";
    /**The current level of the game.*/
    int level = 0;
    /**The background image for the current game level or screen.*/
    private Image background;
    /**A list to store the player-controlled {@code Character} entities.*/
    ArrayList<Players> players;
    /** An instance of the {@code Config} class to handle game configuration loading and saving.*/
    private Config config;
    /**A map to store the grid coordinates for the attack buttons. The key is an index, and the value is an array containing the x and y coordinates.*/
    HashMap<Integer, Integer[]> attackButtonGrid;
    private int damage = 0, defense = 0;

    /**
     * Constructs a new {@code GamePanel} for a specific game level.
     * Initializes the panel, sets up basic configurations, loads the level, and starts the game timer.
     *
     * @param level The initial game level to load.
     */
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

        timer = new Timer(50, this);            //generates an event for game loop
        timer.start();
    }

    /**
     * Loads the game elements and UI components for a given level.
     * This method clears existing entities and players, sets the background,
     * creates characters based on the level, and generates attack buttons and the main menu button.
     * It also handles loading and saving game configuration.
     *
     * @param level The level to load. Different integer values correspond to different game states:
     *  0: Main Menu
     *  1: Game Story
     *  2: Level 1
     *  3: Level 2
     * -1: Game Over Screen
     *  Other positive integers: Future levels
     */
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
            boss = new ColonelAckermann(600, 400, players);

            Players p1 = new Doc(100, 400);
            Players p2 = new Police(220, 400, boss);

            entities.add(boss);
            players.add(p1);
            entities.add(p1);

            players.add(p2);
            entities.add(p2);

        }
        else if (level == 3)       //loads second level
        {
            background = new ImageIcon(getClass().getResource("../textures/BG2.jpeg")).getImage();

            Players p1 = new Police(130, 440, defense, damage, boss);
            boss = new GeneralSchwartz(600, 400, players);
            entities.add(boss);
            entities.add(p1);
            players.add(p1);
        }
        else if (level == -1)       //the game over screen
        {
            loadGameOverScreen();
        }
        else if (level == 1)        //the story of the game
        {
            loadMainStory();
        }
        else if (level == 0)        //main menu
        {
            loadMainMenu();
        }
        else if (level == -2)
        {
            loadEquipmentMenu();
        }
        else            //more levels to come
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

        for (Players p : players)
            p.addAttackButon(this);

        //generateAttackButtons();
        generateMainMenuButton();

    }


    /**
     * Loads and displays the main menu screen with options to start a new game, continue, or exit.
     * Sets the background and adds buttons to the panel.
     */
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

    private void loadGameOverScreen()
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
    }

/*
    /**
     * Generates attack buttons for each player in the game.
     * Each button is associated with a specific player and triggers their attack action on the boss.
     * The buttons are initially placed at the bottom of the screen and then move to random grid locations when being clicked,
     * becoming available again after a cooldown based on the player's attack speed.
     *
    private void generateAttackButtons()
    {
        for (int i = 0; i < players.size(); i++)
        {

            String name = players.get(i).name;
            JButton attackButton = new JButton("<html>Attack<br>" + name + "</html>");

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
                    int gridIndex = (int) (Math.random() * 40);
                    attackButton.setBounds(attackButtonGrid.get(gridIndex)[0], attackButtonGrid.get(gridIndex)[1], 75, 50);
                    attackTimer.start();

                    if (entities != null && players != null)
                    {
                        for (int i = 0; i < players.size(); i++)
                        {
                            if (players.get(i).isAlive() && boss.isAlive())
                            {
                                if (players.get(i).name.equals(name))
                                {
                                    players.get(i).attack(boss);
                                    statusMessage = players.get(i).name + " attacked " + boss.name + "!";
                                }
                            }
                        }
                    }
                }
            });

            add(attackButton);


        }
    }

*/
    /**
     * Generates a button that allows the player to return to the main menu from gameplay levels (level >= 2).
     * This button is positioned at the top-left corner of the screen.
     */
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


    /**
     * Loads and displays the initial story of the game.
     * Presents a story text and a button to proceed to the first gameplay level.
     */
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

    /**
     * Checks if there is at least one alive player in the {@code players} list.
     * It also removes any players who are no longer alive from the list.
     *
     * @return {@code true} if there is at least one alive player; {@code false} otherwise.
     */
    private boolean alivePlayers()
    {

        if(players == null || players.size()==0)
            return true;
        for (int i = 0; i < players.size(); i++)
        {
            if(!players.get(i).isAlive())
                players.remove(i);
        }
        for (int i = 0; i < players.size(); i++)
             if(players.get(i).isAlive())
                return true;
        return false;
    }

    public Character getBoss()
    {
        return boss;
    }


    /**
     * Overrides the {@code paintComponent} method to draw the game elements on the panel.
     * This includes drawing the background image and all alive entities.
     * It also displays the current level and any status messages during gameplay.
     *
     * @param g The {@code Graphics} object used for drawing.
     */
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


    /**
     * Invoked by the game timer at regular intervals.
     * This method updates the game state, checks for game over or level completion conditions,
     * triggers boss attacks, and requests a repaint of the panel.
     *
     * @param e The {@code ActionEvent} generated by the timer.
     */

    int i=1;

    @Override
    public void actionPerformed(ActionEvent e)      //timer activates this every 20ms
    {

        if(!alivePlayers())
        {
            loadLevel(-1);
        }
        else if (boss.isAlive())
        {
            for (Players p : players)
            {
                if(p.attaksDone == 3)
                {
                    p.attaksDone = 0;
                    add(p.addSuperAttackButon(this));
                }
            }
            boss.attack();
        }
        else
        {
            loadLevel(-2);
            statusMessage = "Level Up! Welcome to Level " + level;
        }

        repaint();                      //draws the game by calling paint component
    }
}
