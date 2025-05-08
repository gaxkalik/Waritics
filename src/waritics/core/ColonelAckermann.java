package waritics.core;

import javax.swing.*;
import java.util.ArrayList;

public class ColonelAckermann extends Character
{
    public ColonelAckermann(int x, int y, ArrayList<Players> targets)
    {
        super("Colonel Ackermann", x, y, 150, 150, 100, 1000,
                10, 10, loadTexture("BOSS2.png"));
        this.targets = targets;
    }
}
