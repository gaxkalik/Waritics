import java.util.concurrent.TimeUnit;

public class GeneralScwarts extends Character
{
    private Character target;

    public GeneralScwarts(int x, int y, Character target)
    {
        super("General Schwartz", x, y, 100, 100, 100, 1, 2,
                0, loadTexture("/BOSS1.png"));
        this.target = target;

    }

    public void move()
    {


        attack(target);

       /* if (target != null && target.isAlive())
        {
            if (x < target.x) x += speed;
            if (x > target.x) x -= speed;
            if (y < target.y) y += speed;
            if (y > target.y) y -= speed;
        }*/
    }
}
