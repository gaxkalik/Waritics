import java.awt.image.BufferedImage;

public class Doctor extends Player
{
    public Doctor(int x, int y, int width, int height, int health, int speed, int attack, int defence, BufferedImage texture)
    {
        super("Doctor", x, y , width, height, health, speed, attack, defence, texture);
    }

    public void move()
    {
    }

    public void heal()
    {
        health = maxHealth;
    }


}
