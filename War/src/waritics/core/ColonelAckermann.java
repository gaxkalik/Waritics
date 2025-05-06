package waritics.core;

import java.util.ArrayList;

public class ColonelAckermann extends Character
{
    private ArrayList<Character> targets;
    private int track = 0;

    public ColonelAckermann(int x, int y, ArrayList<Character> targets)
    {
        super("Colonel Ackermann", x, y, 100, 100, 100, 1000 ,
                10, 10, loadTexture("BOSS2.png"));
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
