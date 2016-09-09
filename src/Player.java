import java.lang.Math.*;

public class Player
{
    public static final int RADIUS = World.CELL_SIZE / 2;

    private World world;
    private Body body;
    private boolean alive;

    public Player(World world, int x, int y)
    {
        this.world = world;
        body = new Body(world, x, y, RADIUS);
        alive = true;
    }

    public boolean isAlive() { return alive; }
    public Body getBody() { return body; }
    public Direction getDirection() { return body.getDirection(); }

    public void turn(Direction direction)
    {
        body.turn(direction);
    }

    public void move()
    {
        Item item;
        item = world.getItemUnderPlayer();
        if(item != null)
        {
            eat(item);
        }
        // This is the normal speed of a player
        body.move(4);
    }

    public void eat(Item item)
    {
        item.disappear();
    }

    public void die()
    {
        alive = false;
    }
}
