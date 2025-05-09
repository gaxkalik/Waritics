package waritics.core;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

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
        this.level = gamePanel.getLevel() - 1;
        File file;

        try                             //works on unix
        {
            file = new File("src/waritics/saves/save.txt");
            file.createNewFile();

        }
        catch (IOException e)           //works on windows
        {
            file = new File(getClass().getResource("../saves/save.txt").getFile());
        }

        String playerName = gamePanel.getPlayerName();
        if (playerName == null || playerName.isBlank() || playerName.equals("Enter your name here")) return;

        HashMap<String, Integer> scores = new HashMap<>();
        Scanner reader = new Scanner(new FileInputStream(file));
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split(":");
            if(line.length == 2) {
                int lvl = Integer.parseInt(line[1]);
                if (!scores.containsKey(line[0]) || scores.get(line[0]) < lvl)
                    scores.put(line[0], lvl);
            }
        }

        PrintWriter writer = new PrintWriter(new FileOutputStream(file, false));

        if (!scores.containsKey(playerName) || scores.get(playerName) <= level)
            scores.put(gamePanel.getPlayerName(), level);

        for (String key : scores.keySet()) {
            writer.println(key + ":" + scores.get(key));
        }
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
