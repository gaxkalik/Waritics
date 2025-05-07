package waritics.core;

public class Police extends Players
{
    Character boss;
    public Police(int x, int y, Character boss)
    {
        super("Policeman", x, y, 100, 100, 150, 800,
                10, 20, Character.loadTexture("POL.png"));
        this.boss = boss;
        id = 1;

    }

    public Police(int x, int y, int def, int dmg, Character boss)
    {
        super("Policeman", x, y, 100, 100, 150, 800,
                30 + dmg, 20 + def, Character.loadTexture("POL.png"));
        this.boss = boss;
        id = 1;
    }

    @Override
    protected void specialAbility()
    {
        attack(boss, attack+10);
    }
}
