public enum Direction
{
    left, right, up, down;

    public static Direction random()
    {
        Direction[] directions = values();
        return directions[GameWindow.random(directions.length)];
    }

    public int dx()
    {
        switch (this)
        {
            case left: return -1;
            case right: return 1;
            default: return 0;
        }
    }

    public int dy()
    {
        switch (this)
        {
            case up: return -1;
            case down: return 1;
            default: return 0;
        }
    } 
  
 
}
