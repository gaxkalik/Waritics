package waritics.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Doc extends Players
{
    public Doc(int x, int y)
    {
        super("Doctor", x, y, 100, 100, 100, 800 ,10, 0, loadTexture("DOC2.png"));
        id = 0;
    }

    public Doc(int x, int y, int defence, int damage)
    {
        super("Doctor", x, y, 100, 100, 100, 800, 20, 0, loadTexture("DOC2.png"));
        id = 0;
    }

    @Override
    protected void specialAbility()
    {
        health = health + 20;
    }
}
