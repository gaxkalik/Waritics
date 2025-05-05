import java.util.ArrayList;

public class ColonelAckermann extends Character
{
    private ArrayList<Character> targets;
    private int target;

    public ColonelAckermann(int x, int y, ArrayList<Character> targets)
    {
        super("Colonel Ackermann", x, y, 100, 100, 100, 1000 ,
                20, 10, loadTexture("/BOSS2.png"));
        this.targets = targets;
        good=false;
    }

    public void update()
    {
        try
        {
            attack(targets.get(target%2));
            target++;
        }
        catch (Exception e)
        {
            attack(targets.getFirst());
        }


    /*
      if (target != null && target.isAlive())
        {
            if (x < target.x) x += speed;
            if (x > target.x) x -= speed;
            if (y < target.y) y += speed;
            if (y > target.y) y -= speed;
        }*/
    }
}
