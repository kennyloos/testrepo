import java.awt.Color;

public class Ghost
{
    public static final int RADIUS = World.CELL_SIZE / 2;
    private static final Color[] colors = { Color.red, Color.pink, Color.cyan, Color.orange };
    private static int colorIdx = 0;

    private World world;
    private Body body;
    private Color color;
    private Direction direction;

    public Ghost(World world, int x, int y)
    {
        this.world = world;
        body = new Body(world, x, y, RADIUS);
        direction=Direction.random();
        body.turn(direction);
        color = colors[colorIdx++ % colors.length];
    }

    public Body getBody() { return body; }
    public Color getColor() { return color; }

    public void autoMove()
    {
        Player player;
        //Direction direction;
        
        player = world.getPlayerUnderGhost();
        if(player != null)
        {
            eat(player);
        }
        
        //preventing 180 turn
        while (body.canMove(direction) == false)
        {
            Direction newDirection=Direction.random();
            
            if(direction==Direction.up || direction==Direction.down )
            {
                if( (newDirection==Direction.left)||(newDirection==Direction.right))
                    direction=newDirection;
            }
            else if(direction==Direction.left || direction==Direction.right )
            {
                if( (newDirection==Direction.up)||(newDirection==Direction.down))
                    direction=newDirection;
            }
            
        }
        body.turn(direction);
        /*direction = direction.random();
        if(world.walls != null)
        {
            body.canMove(direction);
        }*/
        // This is the normal speed of a ghost
        body.move(4);
    }

    private void eat(Player player)
    {
        player.die();
    }
}
