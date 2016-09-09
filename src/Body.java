import java.lang.Math.*;

public class Body
{
    private World world;
    private int x;
    private int y;
    private int radius;
    private Direction direction;
    private Direction nextDirection;

    public Body(World world, int x, int y, int radius)
    {
        this.world = world;
        this.x = x;
        this.y = y;
        this.radius = radius;

        direction = nextDirection = Direction.left;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getRadius() { return radius; }
    public Direction getDirection() { return direction; }

    public int getDistanceTo(Body other) { return (int)Math.sqrt(Math.pow(other.x-x,2)+Math.pow(other.y-y,2)); }
    public boolean intersects(Body other) { return getDistanceTo(other) < radius + other.radius; }

    public void turn(Direction direction)
    {
        nextDirection = direction;
    }

    public void move(int distance)
    {
        for (int i = 0; i < distance; i++)
        {
            boolean canMoveNext = canMove(nextDirection);
            if (canMoveNext)
                direction = nextDirection;

            if (canMoveNext || canMove(direction))
            {
                x += direction.dx();
                y += direction.dy();
            }
        }
    }

    public boolean canMove(Direction direction)
    {
        int dx = direction.dx();
        int dy = direction.dy();
        x += dx;
        y += dy;
        boolean success = !world.bodyIntersectsWithWall(this);
        x -= dx;
        y -= dy;
        return success;
    }
}
