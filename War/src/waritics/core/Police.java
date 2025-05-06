package waritics.core;

public class Police extends Character
{
    public Police(int x, int y)
    {
        super("Policeman", x, y, 100, 100, 150, 800,
                30, 20, Character.loadTexture("POL.png"));
        good = true;
    }
}
