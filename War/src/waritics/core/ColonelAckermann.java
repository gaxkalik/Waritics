package waritics.core;

import java.util.ArrayList;

public class ColonelAckermann extends Character
{
    public ColonelAckermann(int x, int y, ArrayList<Character> targets)
    {
        super("Colonel Ackermann", x, y, 100, 100, 100, 1000 ,
                10, 10, loadTexture("BOSS2.png"));
         this.targets = targets;

        good=false;
    }
}
