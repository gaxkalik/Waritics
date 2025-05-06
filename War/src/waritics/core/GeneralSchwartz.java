package waritics.core;

import java.util.ArrayList;

public class GeneralSchwartz extends Character
{
    private ArrayList<Character> targets;
    private int track = 0;




    public GeneralSchwartz(int x, int y, ArrayList<Character> targets)
    {
        super("General Schwartz", x, y, 100, 100, 150, 1000, 10,
                0, Character.loadTexture("BOSS1.png"));
        this.targets = targets;


        good=false;

    }

    public void update()
    {

        if (targets.size() == 1)
            attack(targets.get(0));
        else
        {
            track = 1 - track;
            attack(targets.get(track));
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
