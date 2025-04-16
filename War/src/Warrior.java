public class Warrior extends Character
{
    public Warrior(int x, int y)
    {
        super("Warrior", x, y, 40, 40, 150, 4, loadTexture("/warrior.png"));
    }

    public void update() {}

    public void attack(Character target)
    {
        target.takeDamage(20);
    }
}
