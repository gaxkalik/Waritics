public class Archer extends Character
{
    private Character target;

    public Archer(int x, int y, Character target)
    {
        super("Archer", x, y, 100, 100, 120, 1, 20, 10, loadTexture("/BOSS2.png"));
        this.target = target;
    }

    public void move() {
/*        if (target != null && target.isAlive())
        {
            if (x < target.x) x += speed;
            if (x > target.x) x -= speed;
            if (y < target.y) y += speed;
            if (y > target.y) y -= speed;
        }*/
    }
}
//comment
//NewComment