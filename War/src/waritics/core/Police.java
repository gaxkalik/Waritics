package waritics.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Police extends Character
{
    public Police(int x, int y)
    {
        super("Policeman", x, y, 100, 100, 150, 800,
                30, 20, Character.loadTexture("POL.png"));
        good = true;

        if(id == 0)
            this.id=1;
        else
            this.id=0;
    }

    @Override
    public void addAttackButon(GamePanel panel)
    {
        JButton attackButton = new JButton("<html>Attack<br>" + name + "</html>");

        attackButton.setFont(new Font("Arial", Font.PLAIN, 8));
        attackButton.setFocusable(false);
        attackButton.setBounds(70*id, 550, 70, 50);

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

        panel.add(attackButton) ;
    }

    public Police(int x, int y, int def, int dmg)
    {
        super("Policeman", x, y, 100, 100, 150, 800,
                30 + dmg, 20 + def, Character.loadTexture("POL.png"));
        good=true;
    }
}
