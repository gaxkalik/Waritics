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

        File file = new File("src/waritics/saves/save.txt");
        file.createNewFile();

        PrintWriter writer = new PrintWriter(new FileOutputStream(file, true));
        writer.print(gamePanel.getPlayerName() + "\t");
        writer.println(level);
        writer.close();
    }

    void loadFromDisc() throws Exception
    {
        Scanner reader = null;
        File file = new File(getClass().getResource("../saves/save.txt").getFile());
        reader = new Scanner(new FileInputStream(file));
        System.out.println("hehehee");
        reader.next();
        level = reader.nextInt();
        System.out.println("noex");
        reader.close();
    }

}
