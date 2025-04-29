public class Weapons
{
    protected int attackBoost; // Boosting damage output by a percent
    protected int defenseBoost; // Boosting defence by a percent

    public Weapons(int attackBoost, int defenseBoost) {
        this.attackBoost = attackBoost;
        this.defenseBoost = defenseBoost;
    }

    public Weapons(Weapons other) {
        this.attackBoost = other.attackBoost;
        this.defenseBoost = other.defenseBoost;
    }
}
