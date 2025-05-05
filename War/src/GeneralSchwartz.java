import java.util.ArrayList;

public class GeneralSchwartz extends Character
{
    private ArrayList<Character> targets;
    private int target;




    public GeneralSchwartz(int x, int y, ArrayList<Character> targets)
    {
        super("General Schwartz", x, y, 100, 100, 150, 1000, 10,
                0, loadTexture("/BOSS1.png"));
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
            //System.out.println("essss");
            attack(targets.get(0));
        }




       /* if (target != null && target.isAlive())
        {
            if (x < target.x) x += speed;
            if (x > target.x) x -= speed;
            if (y < target.y) y += speed;
            if (y > target.y) y -= speed;
        }*/
    }
}
