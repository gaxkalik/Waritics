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
    private ArrayList<Character> entities;
    private Character player;
    private String statusMessage = "";
    private boolean up, down, left, right;
    private int level = 1;
    private Image background;
    private ArrayList<JButton> buttons;
    private int playerCount;

    private JButton move;


    public GamePanel()
    {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setLayout(null);

        //background = new ImageIcon(getClass().getResource("./BG1.jpeg")).getImage();

    /*    if(level == 1)
        {
            background = new ImageIcon(getClass().getResource("./BG1.png")).getImage();
        }
        else if(level == 2)
        {
            background = new ImageIcon(getClass().getResource("./BG2.jpeg")).getImage();
        }
        else
        {
            background = new ImageIcon(getClass().getResource("./BG3.jpeg")).getImage();
        }

     */


        setFocusable(true);
        addKeyListener(this);


        entities = new ArrayList<>();
        buttons = new ArrayList<>();

        loadLevel(level);

        timer = new Timer(20, this);
        timer.start();
    }

    private void loadLevel(int level) {
        entities.clear();

        if(level == 1)
        {
           background = new ImageIcon(getClass().getResource("./BG1.png")).getImage();
           player = new Doc(100,100);
           Character boss = new GeneralScwarts(400, 400, player);
           entities.add(boss);
           entities.add(player);
        }
        else if(level == 2)
        {
           background = new ImageIcon(getClass().getResource("./BG2.jpeg")).getImage();
           player = new Police(100,100);
           Character boss = new Archer(400, 400, player);
           entities.add(boss);
           entities.add(player);
        }
        else
        {
            background = new ImageIcon(getClass().getResource("./BG3.jpeg")).getImage();
            player = new Police(100,100);
            Character boss = new Archer(400, 400, player);
            entities.add(boss);
            entities.add(player);
        }




    /*    player = new Doc(100, 100);
        entities.add(player);
        player.setEquipment(new Equipment(0, 200, Equipment.EquipmentType.ARMOR));
        player.setEquipment(new Equipment(200, 0, Equipment.EquipmentType.WEAPON));

        Character boss = new GeneralScwarts(400, 400, player);
        Character boss2 = new Archer(250, 400, player);
        entities.add(boss);
        entities.add(boss2);

        int enemyCount = 2 + level;
        for (int i = 0; i < enemyCount; i++) {
            Character enemy = (i % 2 == 0) ? new GeneralScwarts(200 + i * 60, 100 + i * 40, player)
                                           : new Archer(200 + i * 60, 100 + i * 40, player);
            entities.add(enemy);
        }*/

        playerCount=10;
        for (int i = 0; i < playerCount; i++) {
            JButton attackButton = new JButton("<html>Attack<br>" + player.name + "</html>");

            attackButton.setFont(new Font("Arial", Font.PLAIN, 8));
            attackButton.setFocusable(false);

            attackButton.setBounds(70*i,550,70,50);
            add(attackButton);
            buttons.add(attackButton);

        }




        move = new JButton("Attack");



        move.setFocusable(false);
        move.setBounds(500,300,100,50);
        move.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (Character character : entities) {
                    if (character != player && character.isAlive()) {
                        character.move();
                        player.attack(character);
                        statusMessage = player.name + " attacked " + character.name + "!";
                    }
                }
            }
        });

        add(move);

    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(background, 0, 0,800 ,600,null);
        for (Character e : entities) {
            if (e.isAlive()) e.draw(g);
        }

        g.setColor(Color.WHITE);
        g.drawString("Level: " + level, 700, 20);
        g.drawString(statusMessage, 10, 580);
    }

    public void setPlayerCount(int playerCount)
    {
        this.playerCount = playerCount;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (up && player.y >= 0)
            player.y -= player.speed;
        if (down && player.y <= 600 - 40)
            player.y += player.speed;
        if (left && player.x >= 0)
            player.x -= player.speed;
        if (right && player.x <= 800 - 40)
            player.x += player.speed;



        boolean enemiesAlive = false;

        for (Character character : entities) {
            if (character != player && character.isAlive()) {
                character.move();

                if (player.getBounds().intersects(character.getBounds())) {
                    character.attack(player);
                    statusMessage = character.name + " attacked you!";
                }

                if (character.getBounds().intersects(player.getBounds())) {
                    player.attack(character);
                    statusMessage = player.name + " attacked " + character.name + "!";
                }

                enemiesAlive = true;
            }
        }

        if (!enemiesAlive) {
            level++;
            loadLevel(level);
            statusMessage = "Level Up! Welcome to Level " + level;
        }


        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
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
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
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
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        return;
    }
}