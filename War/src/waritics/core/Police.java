package waritics.core;

public class Police extends Character
{
    public Police(int x, int y)
    {
        super("Policeman", x, y, 100, 100, 150, 800,
                30, 20, Character.loadTexture("POL.png"));
        good = true;
    }

    public Police(int x, int y, int def, int dmg)
    {
        super("Policeman", x, y, 100, 100, 150, 800,
                30 + dmg, 20 + def, Character.loadTexture("POL.png"));
        good=true;
    }
}
