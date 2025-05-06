package waritics.core;

import java.util.ArrayList;

public class GeneralSchwartz extends Character
{
    public GeneralSchwartz(int x, int y, ArrayList<Character> targets)
    {
        super("General Schwartz", x, y, 100, 100, 150, 1000, 10,
                0, Character.loadTexture("BOSS1.png"));
        this.targets = targets;

        good=false;
    }
}
