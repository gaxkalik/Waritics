import java.awt.image.BufferedImage;

abstract class Player extends Character
{
    public Player(String name, int x, int y, int width, int height, int health, int speed, int attack, int defence, BufferedImage texture)
    {
        super(name, x, y, width, height, health, speed, attack, defence, texture);
    }
}