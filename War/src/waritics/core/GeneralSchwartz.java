package waritics.core;

import javax.swing.*;
import java.util.ArrayList;

public class GeneralSchwartz extends Character
{
    public GeneralSchwartz(int x, int y, ArrayList<Players> targets)
    {
        super("General Schwartz", x, y, 150, 150, 150, 1000, 10,
                0, Character.loadTexture("BOSS1.png"));
        this.targets = targets;

    }

}
