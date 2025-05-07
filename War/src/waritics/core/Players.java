package waritics.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public abstract class Players extends Character
{
    protected int id;
    int attaksDone;

    public Players(String name, int x, int y, int width, int height, int health,
                   int attackSpeed, int attack, int defence, BufferedImage texture)
    {
        super(name, x, y, width, height, health, attackSpeed, attack, defence, texture);
    }

    protected abstract void specialAbility();



    public void attack(Character target)
    {
        super.attack(target);
        attaksDone++;
    }

    public void addAttackButon(GamePanel panel)
    {


        JButton attackButton = new JButton("<html>Attack<br>" + name + "</html>");

        attackButton.setFont(new Font("Arial", Font.PLAIN, 8));
        attackButton.setFocusable(false);
        attackButton.setBounds(70 * id, 550, 70, 50);

        Timer attackTimer = new Timer(attackSpeed, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                attackButton.setEnabled(true);
                attackButton.setVisible(true);
            }
        });
        attackTimer.setRepeats(false);

        attackButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                attackButton.setEnabled(false);
                attackButton.setVisible(false);
                int gridIndex = (int) (Math.random() * 40);


                attackButton.setBounds(panel.attackButtonGrid.get(gridIndex)[0], panel.attackButtonGrid.get(gridIndex)[1], 75, 50);
                attackTimer.start();

                if (isAlive() && panel.boss.isAlive())
                {

                    attack(panel.boss);
                    //statusMessage = players.get(i).name + " attacked " + boss.name + "!";
                }
            }
        });

        panel.add(attackButton);

    }

    public JButton addSuperAttackButon(GamePanel panel)
    {
        JButton superAttackButton = new JButton("<html>SUPER<br>" + name + "</html>");

        superAttackButton.setFont(new Font("Arial", Font.PLAIN, 8));
        superAttackButton.setFocusable(false);
        superAttackButton.setForeground(Color.RED);
        superAttackButton.setBounds(70 * id, 550, 70, 50);

        superAttackButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                superAttackButton.setEnabled(false);
                superAttackButton.setVisible(false);

                if (isAlive() && panel.boss.isAlive())
                {
                    specialAbility();
                    //statusMessage = players.get(i).name + " attacked " + boss.name + "!";
                }
            }
        });

        return superAttackButton;

    }


}
