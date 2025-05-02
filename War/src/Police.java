public class Police extends Character
{
    public Police(int x, int y)
    {
        super("Policeman", x, y, 100, 100, 150, 4, 800,
                30, 20, loadTexture("/POL.png"));
        good=true;
    }


    public void update()
    {
    }
}
