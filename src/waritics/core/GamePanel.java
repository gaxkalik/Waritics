package waritics.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The {@code GamePanel} class represents the main panel where the game is rendered and logic is executed.
 * It manages game entities, levels, user interface elements, and the game loop.
 * This panel extends {@code JPanel} and implements {@code ActionListener} to handle timer events.
 */
public class GamePanel extends JPanel implements ActionListener
{
    public static final int NUMBER_OF_BACKGROUNDS = 5;
    public static final int WIDTH_OF_WINDOW = 800;
    public static final int HEIGHT_OF_WINDOW = 600;

    private String playerName;

    private int currentBG;
    /**The timer that triggers game updates and repaints at a fixed interval.*/
    private final Timer timer;
    /**A list to store all {@code Character} entities that are currently displayed on the screen.*/
    private ArrayList<Character> entities;
    /**The main boss character in the current game level.*/
    private Character boss;
    /**A message to display game status or information to the player.*/
    private String statusMessage = "";
    /**The current level of the game.*/
    private int level = 0;
    /**The background image for the current game level or screen.*/
    private Image background;
    private Image [] backgroundS;
    /**A list to store the player-controlled {@code Character} entities.*/
    private ArrayList<Players> players;
    /** An instance of the {@code Config} class to handle game configuration loading and saving.*/
    private final Config config;
    /**A map to store the grid coordinates for the attack buttons. The key is an index, and the value is an array containing the x and y coordinates.*/
    private HashMap<Integer, Integer[]> attackButtonGrid;
    private int damage = 0, defense = 0;

    /**
     * Constructs a new {@code GamePanel} for a specific game level.
     * Initializes the panel, sets up basic configurations, loads the level, and starts the game timer.
     *
     * @param level The initial game level to load.
     */
    public GamePanel(int level)
    {
        setPreferredSize(new Dimension(WIDTH_OF_WINDOW, HEIGHT_OF_WINDOW));
        setBackground(Color.BLACK);
        setLayout(null);

        setFocusable(true);

        config = new Config(this);
        entities = new ArrayList<>();
        players = new ArrayList<>();
        loadBackgroundsImages();

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
        boss = null;
        statusMessage = "";

        if (level == 2)         //loads first level
        {
            currentBG=0;
            background = backgroundS[currentBG];
            boss = new ColonelAckermann(550, 350, players);

            Players p1 = new Doc(50, 350, defense, damage);
            //Players p2 = new Police(220, 400, boss);

            entities.add(boss);
            players.add(p1);
            entities.add(p1);

            //players.add(p2);
            //entities.add(p2);

        }
        else if (level == 3)       //loads second level
        {
            currentBG=1;
            background = backgroundS[currentBG];
            boss = new GeneralSchwartz(550, 350, players);
            Players p1 = new Police(70, 390, defense, damage, boss);

            entities.add(boss);
            entities.add(p1);
            players.add(p1);
        }
        else if (level == 4)
        {
            currentBG=2;
            background = backgroundS[currentBG];
            boss = new GeneralSchwartz(450, 370, players);
            Players p1 = new Police(130, 400, defense, damage, boss);
            p1.setId(0);
            Players p2 = new Doc(250, 350, defense,damage);
            p2.setId(1);
            entities.add(boss);
            entities.add(p1);
            entities.add(p2);
            players.add(p1);
            players.add(p2);

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
        else if (level == -3)
        {
            loadStatMenu();
        }
        else            //more levels to come
        {
            currentBG=4;
            background = backgroundS[currentBG++];
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


    public void loadBackgroundsImages()
    {
        backgroundS = new Image[NUMBER_OF_BACKGROUNDS];
        System.out.println(getClass().getResource("../textures/BG"+ (5-1) +".png"));
        try
        {
            for (int i = 0; i < NUMBER_OF_BACKGROUNDS; i++)
            {
                backgroundS[i] = new ImageIcon(getClass().getResource("../textures/BG"+ i +".png")).getImage();
            }
        }
        catch (Exception E)
        {
            statusMessage = "Error loading background!";
        }
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
        label.setFont(PixelFont.pixelFont50);//new Font("Arial", Font.BOLD, 50));
        label.setForeground(Color.LIGHT_GRAY);
        label.setBounds((WIDTH_OF_WINDOW-200)/2, 100, 400, 100);

        JButton startButton = new JButton("NEW GAME");
        startButton.setBackground(Color.RED);
        startButton.setFocusable(false);
        startButton.setFont(PixelFont.pixelFont20);//new Font("Arial", Font.PLAIN, 19));
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
        exitButton.setFont(PixelFont.pixelFont20);//new Font("Arial", Font.PLAIN, 19));
        exitButton.setBounds(295, 475, 200, 75);
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
        loadButton.setFont(PixelFont.pixelFont20);//new Font("Arial", Font.PLAIN, 19));
        loadButton.setBounds(295, 325, 200, 75);
        loadButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //System.out.println("hehehehehe");
                try
                {
                    config.loadFromDisc();
                    loadLevel(config.level);
                    statusMessage = "Successfully loaded last game";
                }
                catch (Exception E)
                {
                    loadLevel(2);
                    statusMessage = "Error loading game!";
                }

            }
        });

        JButton statisticsButton = new JButton("STATISTICS");
        statisticsButton.setFocusable(false);
        statisticsButton.setFont(PixelFont.pixelFont20);//new Font("Arial", Font.PLAIN, 19));
        statisticsButton.setBounds(295, 400, 200, 75);
        statisticsButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                loadLevel(-3);
            }
        });

        
        add(loadButton);
        add(startButton);
        add(label);
        add(exitButton);
        add(statisticsButton);

    }

    private void loadStatMenu()
    {
        ArrayList<String> stats = null;

        statusMessage = "";
        background = new ImageIcon(getClass().getResource("../textures/BG_MAIN.jpeg")).getImage();
        JLabel label = new JLabel("STATISTICS");
        label.setFont(PixelFont.pixelFont50);//new Font("Arial", Font.BOLD, 50));
        label.setForeground(Color.LIGHT_GRAY);
        label.setBounds((WIDTH_OF_WINDOW-200)/2, 100, 400, 100);

        JTextArea textArea = new JTextArea();
        //textArea.setLayout(new ScrollPaneLayout());
        textArea.setEditable(false);
        textArea.setFont(PixelFont.pixelFont20);//new Font("Arial", Font.PLAIN, 15));
        textArea.setForeground(Color.BLACK);
        textArea.setBounds(200, 200, 400, 300);


        try
        {
            stats = config.loadStatistics();
            for (int i = 0; i< stats.size(); i++)
            {
                textArea.append(stats.get(i)+"\n");
            }
        }
        catch (Exception e)
        {
            statusMessage = "Error loading statistics!";
            System.out.println("Error loading statistics!");
        }
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(200, 200, 400, 300);

        add(label);
        add(scrollPane);

    }

    private void loadGameOverScreen()
    {
        background = new ImageIcon(getClass().getResource("../textures/BG_MAIN.jpeg")).getImage();

        JLabel label = new JLabel("GAME OVER");
        label.setFont(PixelFont.pixelFont50);//new Font("Arial", Font.PLAIN, 50));
        label.setForeground(new Color(247, 68, 2));

        label.setBounds(270, 100, 400, 100);

        JButton startButton = new JButton("MAIN MENU");
        startButton.setFocusable(false);
        startButton.setFont(PixelFont.pixelFont20);//new Font("Arial", Font.PLAIN, 19));
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
        exitButton.setFont(PixelFont.pixelFont20);//new Font("Arial", Font.PLAIN, 19));
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

    /**
     * Generates a button that allows the player to return to the main menu from gameplay levels (level >= 2).
     * This button is positioned at the top-left corner of the screen.
     */
    private void generateMainMenuButton()
    {
        if(level >= 2 || level == -3)
        {
            JButton mainMenuButton = new JButton("MAIN MENU");
            mainMenuButton.setFocusable(false);
            mainMenuButton.setFont(PixelFont.pixelFont10);//new Font("Arial", Font.PLAIN, 8));
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
        JTextField nameInput = new JTextField("Enter your name here");
        nameInput.setFont(PixelFont.pixelFont15);//new Font("Arial", Font.PLAIN, 15));
        nameInput.setForeground(Color.GRAY);
        nameInput.setBackground(Color.LIGHT_GRAY.brighter());
        nameInput.setToolTipText("Enter your name here");
        nameInput.setBounds(270, 350, 250, 40);
        nameInput.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                playerName = nameInput.getText();
                System.out.println(playerName);
                loadLevel(2);
            }
        });
        nameInput.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
                nameInput.setForeground(Color.BLACK);
                nameInput.setText("");
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                if (nameInput.getText().equals(""))
                {
                    nameInput.setForeground(Color.GRAY);
                    nameInput.setText("Enter your name here");
                }
            }
        });




        JLabel label = new JLabel("<html>On one sunny day, from nowhere cataclysm occurred and portals were opened all over the world. From portals evil conquerors throughout the history were summoned. They conquered the world and injected the fear into all of the people who were still alive. Only a few of them, ordinary people like doctors & policemen were willing to fight. They should defeat all of the evil leaders to return the world to its peaceful times once again!!</html>");
        label.setFont(PixelFont.pixelFont15);//new Font("Arial", Font.BOLD, 15));
        label.setForeground(Color.YELLOW.brighter().brighter());
        label.setBounds(200, 50, 400, 400);

        JButton button = new JButton("Continue");
        button.setFocusable(false);
        button.setFont(PixelFont.pixelFont20);//new Font("Arial", Font.PLAIN, 19));
        button.setBounds(295, 400, 200, 75);
        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                playerName = nameInput.getText();
                System.out.println(playerName);
                loadLevel(2);
            }
        });

        add(nameInput);

        add(button);
        add(label);
    }

    public Character getBoss()
    {
        return boss;
    }

    public int getLevel()
    {
        return level;
    }

    public String getPlayerName()
    {
        return playerName;
    }

    private void loadEquipmentMenu()
    {
        JButton armorButton = new JButton("Armor: +3");
        armorButton.setFocusable(false);
        armorButton.setFont(PixelFont.pixelFont10);//new Font("Arial", Font.PLAIN, 8));
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
        weaponButton.setFont(PixelFont.pixelFont10);//new Font("Arial", Font.PLAIN, 8));
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

    public HashMap<Integer, Integer[]> getAttackButtonGrid()
    {
        return attackButtonGrid;
    }



    /**
     * Overrides the {@code paintComponent} method to draw the game elements on the panel.
     * This includes drawing the background image and all alive entities.
     * It also displays the current level and any status messages during gameplay.
     *
     * @param g The {@code Graphics} object used for drawing.
     */
    @Override
    public void paintComponent(Graphics g)
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
            g.setFont(PixelFont.pixelFont15);
            g.drawString("Level: " + (level - 1), 700, 20);

            g.setColor(Color.RED);
            g.setFont(PixelFont.pixelFont15);//new Font("Arial", Font.BOLD, 18));
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


    @Override
    public void actionPerformed(ActionEvent e)      //timer activates this every 20ms
    {

        if(!alivePlayers())
        {
            loadLevel(-1);
        }
        else if (boss == null)
        {
           repaint();
           return;
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
