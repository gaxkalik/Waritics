package waritics.core;

import java.io.*;
import java.util.Scanner;

public class Config
{
    int level;
    GamePanel gamePanel;

    public Config(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }

    void saveToDisc() throws Exception
    {
        this.level = gamePanel.getLevel();
        PrintWriter writer = null;
        File file = new File(getClass().getResource("../saves/save.txt").getFile());
        writer = new PrintWriter(new FileOutputStream(file));
        writer.println(level);
        writer.close();
    }

    void loadFromDisc() throws Exception
    {
        Scanner reader = null;
        File file = new File(getClass().getResource("../saves/save.txt").getFile());
        reader = new Scanner(new FileInputStream(file));
        level = reader.nextInt();
        reader.close();
    }

    //in game panel it was:
        /*
    private void saveToDisc()
    {
        PrintWriter writer = null;
        try
        {
            File file = new File(getClass().getResource("../saves/save.txt").getFile());
            writer = new PrintWriter(new FileOutputStream(file));
            writer.println(level);
            writer.close();
        }
        catch (FileNotFoundException e)
        {
            loadLevel(0);
            statusMessage = "Error saving game!";
            System.out.println("sdfg");
        }
    }

    private void loadFromDisc()
    {
        Scanner reader = null;
        try
        {
            File file = new File(getClass().getResource("../saves/save.txt").getFile());
            reader = new Scanner(new FileInputStream(file));
            level = reader.nextInt();
            loadLevel(level);
            reader.close();
        }
        catch (Exception e)
        {
            loadLevel(0);
            statusMessage = "Error loading game!";
            System.out.println("ertyjyktyrt");
        }
    }

     */

}
