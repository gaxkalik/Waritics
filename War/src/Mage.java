public class Mage extends Character
{
    private Character target;

    public Mage(int x, int y, Character target)
    {
        super("Mage", x, y, 30, 30, 100, 1, loadTexture("/mage.png"));
        this.target = target;
    }

    public void update()
    {
        if (target != null && target.isAlive())
        {
            if (x < target.x) x += speed;
            if (x > target.x) x -= speed;
            if (y < target.y) y += speed;
            if (y > target.y) y -= speed;
        }
    }

    public void attack(Character target)
    {
        target.takeDamage(25);
    }
}
