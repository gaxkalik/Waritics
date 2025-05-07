package waritics.core;

public class Police extends Players
{
    Character boss;
    public Police(int x, int y, Character boss)
    {
        this(x,y, 0,10,boss);

    }

    public Police(int x, int y, int def, int dmg, Character boss)
    {
        super("Policeman", x, y, 150, 150, 150, 800,
                30 + dmg, 20 + def, Character.loadTexture("POL.png"));
        this.boss = boss;
    }

    @Override
    protected void specialAbility()
    {
        attack(boss, attack+10);
    }
}
