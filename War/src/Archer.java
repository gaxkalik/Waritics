public class Archer extends Character
{
    private Character target;

    public Archer(int x, int y, Character target)
    {
        super("Archer", x, y, 35, 35, 120, 1, loadTexture("/archer.png"));
        this.target = target;
    }

    public void update() {
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
        target.takeDamage(15);
    }
}
//comment