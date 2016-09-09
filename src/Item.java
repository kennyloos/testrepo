public class Item
{
    public static final int RADIUS = 2;

    private World world;
    private Body body;
    private boolean available;

    public Item(World world, int x, int y)
    {
        this.world = world;
        body = new Body(world, x, y, RADIUS);
        available = true;
    }

    public boolean isAvailable() { return available; }
    public void disappear() { available = false; }
    public Body getBody() { return body; }
}
