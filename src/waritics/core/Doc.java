package waritics.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Doc extends Players
{
    public Doc(int x, int y)
    {
        this(x,y, 0,10);
    }

    public Doc(int x, int y, int defence, int damage)
    {
        super("Doctor", x, y, 150, 150, 100, 800, 20, 0, loadTexture("DOC2.png"));

    }

    @Override
    protected void specialAbility()
    {
        health = health + 20;
    }
}
