public class Doc extends Character
{
    public Doc(int x, int y)
    {
        super("Doctor", x, y, 100, 100, 150, 4,1000 ,30, 0, loadTexture("/DOC2.png"));
        good=true;
    }

    public void update() {}
}
