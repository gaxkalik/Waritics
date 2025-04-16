import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;

public abstract class Character {
    protected JLabel label;
    protected int x, y;
    protected int width, height;
    protected int health;
    protected int maxHealth;
    protected int speed;
    protected int damage;
    protected double attackSpeed;
    protected Image texture;
    protected String name;

    public Character(String name, int x, int y, int width, int height, int health, int speed, int damage, double attackSpeed, Image texture)
    {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.health = health;
        this.maxHealth = health;
        this.speed = speed;
        this.texture = texture;
        this.damage = damage;
        this.attackSpeed = attackSpeed;

        label = new JLabel(new ImageIcon(texture));
        label.setBounds(x, y, width, height);
    }

    public abstract void update();
    public abstract void attack(Character target);

    public void draw(JPanel panel) {
        label.setLocation(x, y);
        panel.add(label);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }
}
