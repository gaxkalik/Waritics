import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

class GamePanel extends JPanel implements ActionListener, KeyListener
{
    private Timer timer;
    private ArrayList<Entity> entities;
    private Entity player;
    private Random random;
    private String statusMessage = "";
    private boolean up, down, left, right;
    private int level = 1;

    public GamePanel() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        random = new Random();
        entities = new ArrayList<>();

        loadLevel(level);

        timer = new Timer(20, this);
        timer.start();
    }

    private void loadLevel(int level) {
        entities.clear();

        player = new Warrior(100, 100);
        entities.add(player);

        int enemyCount = 2 + level;
        for (int i = 0; i < enemyCount; i++) {
            Entity enemy = (i % 2 == 0) ? new Mage(200 + i * 60, 100 + i * 40, player)
                    : new Archer(200 + i * 60, 100 + i * 40, player);
            entities.add(enemy);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Entity e : entities) {
            if (e.isAlive()) e.draw(g);
        }
        g.setColor(Color.WHITE);
        g.drawString("Level: " + level, 700, 20);
        g.drawString(statusMessage, 10, 580);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (up) player.y -= player.speed;
        if (down) player.y += player.speed;
        if (left) player.x -= player.speed;
        if (right) player.x += player.speed;

        boolean enemiesAlive = false;

        for (Entity entity : entities) {
            if (entity != player && entity.isAlive()) {
                entity.update();

                if (player.getBounds().intersects(entity.getBounds())) {
                    entity.attack(player);
                    statusMessage = entity.name + " attacked you!";
                }

                if (entity.getBounds().intersects(player.getBounds())) {
                    player.attack(entity);
                    statusMessage = player.name + " attacked " + entity.name + "!";
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
            for (Entity entity : entities)
            {
                if (entity != player && entity.isAlive() && player.getBounds().intersects(entity.getBounds()))
                {
                    entity.takeDamage(40);
                    statusMessage = player.name + " used special attack on " + entity.name + "!";
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
    public void keyTyped(KeyEvent e) {}
}