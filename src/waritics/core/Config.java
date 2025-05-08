package waritics.core;

import java.io.*;
import java.util.ArrayList;
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
        writer.print("\n" + gamePanel.getPlayerName() + ": ");
        writer.print(level);
        writer.close();
    }

    void loadFromDisc() throws Exception
    {
        Scanner reader = null;
        File file = new File(getClass().getResource("../saves/save.txt").getFile());
        reader = new Scanner(new FileInputStream(file));
        while (reader.hasNextLine())
        {
            reader.nextLine();
            try
            {
                reader.next();
                level = reader.nextInt();
            }
            catch (Exception e)
            {
                continue;
            }

        }

        reader.close();
    }

    ArrayList<String> loadStatistics() throws Exception
    {
        ArrayList<String> result = new ArrayList<>();
        result.add("Name \t\t\t Score\n");
        File file = new File(getClass().getResource("../saves/save.txt").getFile());
        Scanner reader = new Scanner(new FileInputStream(file));
        while (reader.hasNextLine())
        {
            String[] line = reader.nextLine().split(":");

            if (line.length != 2)
                break;
            else
            {
                String currentName = line[0];

                if (!currentName.equals("") && !currentName.equals("null") && !currentName.equals("Enter your name here"))
                {
                    result.add(currentName + "\t \t" + line[1]);
                }
            }
        }
        return result;
    }
}
