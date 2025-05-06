package waritics.core;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * An abstract class representing a character in the game.
 * It provides basic attributes and functionalities for all game characters,
 * including position, health, attack, defense, and drawing.
 */
public abstract class Character
{
    /**The x-coordinate of the character's position on the game panel.*/
    protected int x;
    /**The y-coordinate of the character's position on the game panel.*/
    protected int y;
    /**The width of the character's visual representation.*/
    protected int width;
    /**The height of the character's visual representation.*/
    protected int height;
    /**The current health points of the character.*/
    protected int health;
    /**The maximum health points the character can have.*/
    protected int maxHealth;
    /**The delay in milliseconds between the character's attacks.*/
    protected int attackSpeed;
    /**The base attack power of the character.*/
    protected int attack;
    /**The base defense value of the character, reducing incoming damage.*/
    protected int defence;
    /**The image texture used to visually represent the character.*/
    protected BufferedImage texture;
    /**The weapon currently equipped by the character, potentially modifying attack power.*/
    protected Equipment weapon;
    /**The armor currently equipped by the character, potentially modifying defense.*/
    protected Equipment armor;
    /**The name of the character.*/
    protected String name;
    /**The rarity of the character (e.g., Common, Rare, Epic).*/
    protected Rarity rarity;
    /**A boolean indicating if the character is considered "good" (e.g., a player character).*/
    public boolean good;
    /**The timestamp of the last attack performed by the character, used for attack speed regulation.*/
    private long lastAttackTime = 0;
    /**A list of target characters that this character can attack.*/
    protected ArrayList<Character> targets;
    /**An internal tracker used when the character has multiple targets to alternate between them.*/
    protected int track = 0;

    /**
     * Constructs a new {@code Character} with the specified attributes.
     *
     * @param name        The name of the character.
     * @param x           The initial x-coordinate of the character.
     * @param y           The initial y-coordinate of the character.
     * @param width       The width of the character's texture.
     * @param height      The height of the character's texture.
     * @param health      The initial and maximum health of the character.
     * @param attackSpeed The delay between attacks in milliseconds.
     * @param attack      The base attack power of the character.
     * @param defence     The base defense value of the character.
     * @param texture     The image texture for the character.
     */
    public Character(String name, int x, int y, int width, int height, int health,
                     int attackSpeed, int attack, int defence, BufferedImage texture)
    {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.health = health;
        this.maxHealth = health;
        this.attackSpeed = attackSpeed;
        this.texture = texture;
        this.attack = attack;
        this.defence = defence;
        this.weapon = null;
    }

    /**
     * Initiates an attack on one or more targets.
     * If there's only one target, it attacks that target.
     * If there are multiple targets, it alternates between them.
     */
    public void attack ()
    {
        if (targets == null || targets.size() == 0)
            return;
        if (targets.size() == 1)
            attack(targets.get(0));
        else
        {
            track = 1 - track;
            attack(targets.get(track));
        }
    }

    /**
     * Attacks a specific target character, applying damage based on this character's attack power.
     * The attack is only performed if the attack cooldown has elapsed.
     *
     * @param target The {@code Character} to attack.
     */
    public void attack(Character target)
    {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastAttackTime >= attackSpeed)
        {
            target.takeDamage(this.getAttack());
            lastAttackTime = currentTime;
        }
    }

    /**
     * Draws the character on the game panel, including its texture, health bar, attack speed bar, and name.
     *
     * @param g The {@code Graphics} context used for drawing.
     */
    public void draw(Graphics g)
    {
        long currentTime = System.currentTimeMillis();

        g.drawImage(texture, x, y, width, height, null);

        // Health bar
        g.setColor(Color.GRAY);
        g.fillRect(x, y - 10, width, 5);
        g.setColor(Color.GREEN);
        int healthBarWidth = (int) (((double) health / maxHealth) * width);
        g.fillRect(x, y - 10, healthBarWidth, 5);

        //Attack speed bar
        g.setColor(Color.RED);
        g.fillRect(x, y - 20, width, 5);
        g.setColor(Color.BLUE);
        long attackBarWidth = (int) (((double)(currentTime - lastAttackTime) /attackSpeed) * width);
        if (attackBarWidth >= width)
            attackBarWidth =width;
        g.fillRect(x, y - 20, (int)attackBarWidth, 5);

        g.setColor(Color.WHITE);
        g.drawString(name, x, y - 25);
    }

    /**
     * Checks if the character is currently alive (health > 0).
     *
     * @return {@code true} if the character's health is greater than 0, {@code false} otherwise.
     */
    public boolean isAlive()
    {
        return health > 0;
    }

    /**
     * Reduces the character's health by the specified damage amount, taking defense into account.
     * The final damage is calculated as {@code damage * (100 - defense) / 100}.
     * Health cannot drop below zero.
     *
     * @param damage The amount of damage to take.
     */
    public void takeDamage(int damage)
    {
        health -= damage * (100 - getDefence()) / 100;
        if (health < 0) health = 0;
    }

    /**
     * Loads an image texture from the specified file name in the {@code ../textures/} directory.
     * If the file is not found or an error occurs during loading, a default red square texture is returned.
     *
     * @param fileName The name of the image file to load.
     * @return The loaded {@code BufferedImage}, or a default red square if loading fails.
     */
    protected static BufferedImage loadTexture(String fileName)
    {
        try
        {
            return ImageIO.read(Character.class.getResource("../textures/"+ fileName));
        }
        catch (Exception e)
        {
            BufferedImage img = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = img.createGraphics();
            g.setColor(Color.RED);
            g.fillRect(0, 0, 40, 40);
            g.dispose();
            return img;
        }
    }

    /**
     * Equips the character with the given equipment.
     * If the equipment is a weapon, it updates the {@code weapon} attribute.
     * If the equipment is armor, it updates the {@code armor} attribute.
     *
     * @param equipment The {@code Equipment} to equip.
     */
    public void setEquipment(Equipment equipment) {
        if (equipment.equipmentType == Equipment.EquipmentType.WEAPON) this.weapon = equipment;
        if (equipment.equipmentType == Equipment.EquipmentType.ARMOR) this.armor = equipment;
    }

    /**
     * Gets the character's effective defense, taking into account any equipped armor.
     * If no armor is equipped, the base defense is returned.
     *
     * @return The effective defense value.
     */
    public int getDefence() {
        if (armor == null) return defence;
        return defence * (100 + armor.defenseBoost) / 100;
    }

    /**
     * Gets the character's effective attack power, taking into account any equipped weapon.
     * If no weapon is equipped, the base attack is returned.
     *
     * @return The effective attack power.
     */
    public int getAttack() {
        if (weapon == null) return attack;
        return attack * (100 + weapon.attackBoost) / 100;
    }
}