public class Doc extends Character
{
    int superAttackTimer;
    public Doc(int x, int y)
    {
        super("Doctor", x, y, 100, 100, 100, 800 ,20, 0, loadTexture("/DOC2.png"));
        good=true;
        superAttackTimer=0;
    }

    public void update() {}
}
