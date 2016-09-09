import javax.swing.*;

public class World
{
    public static final int CELL_SIZE = 30;
    public static final int ROW_COUNT = 19;
    public static final int COL_COUNT = 17;
    public static final int centre = COL_COUNT/2;
    public GameWindow gamePanel;
    
    private boolean _ = false;
    private boolean X = true;
    private boolean[][] walls = {{X,X,X,X,X,X,X,X,X,X,X,X,X,X,X,X,X},
                                 {X,_,_,_,_,_,_,_,X,_,_,_,_,_,_,_,X},
                                 {X,_,X,_,X,X,X,_,X,_,X,X,X,_,X,_,X},
                                 {X,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,X},
                                 {X,_,X,_,X,_,X,X,X,X,X,_,X,_,X,_,X},
                                 {X,_,_,_,X,_,_,_,X,_,_,_,X,_,_,_,X},
                                 {X,X,X,_,X,X,X,_,X,_,X,X,X,_,X,X,X},
                                 {X,X,X,_,X,_,_,_,_,_,_,_,X,_,X,X,X},
                                 {X,X,X,_,X,_,X,X,X,X,X,_,X,_,X,X,X},
                                 {X,_,_,_,_,_,X,X,X,X,X,_,_,_,_,_,X},
                                 {X,X,X,_,X,_,X,X,X,X,X,_,X,_,X,X,X},
                                 {X,X,X,_,X,_,_,_,_,_,_,_,X,_,X,X,X},
                                 {X,X,X,_,X,X,X,_,X,_,X,X,X,_,X,X,X},
                                 {X,_,_,_,X,_,_,_,X,_,_,_,X,_,_,_,X},
                                 {X,_,X,_,X,_,X,X,X,X,X,_,X,_,X,_,X},
                                 {X,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,X},
                                 {X,_,X,_,X,X,X,_,X,_,X,X,X,_,X,_,X},
                                 {X,_,_,_,_,_,_,_,X,_,_,_,_,_,_,_,X},
                                 {X,X,X,X,X,X,X,X,X,X,X,X,X,X,X,X,X}
                                };
           
    private boolean o = true;
    private boolean[][] itemmap = {{_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_},
                                   {_,o,o,o,o,o,o,o,_,o,o,o,o,o,o,o,_},
                                   {_,o,_,o,_,_,_,o,_,o,_,_,_,o,_,o,_},
                                   {_,o,o,o,o,o,o,o,o,o,o,o,o,o,o,o,_},
                                   {_,o,_,o,_,o,_,_,_,_,_,o,_,o,_,o,_},
                                   {_,o,o,o,_,o,o,o,_,o,o,o,_,o,o,o,_},
                                   {_,_,_,o,_,_,_,_,_,_,_,_,_,o,_,_,_},
                                   {_,_,_,o,_,_,_,_,_,_,_,_,_,o,_,_,_},
                                   {_,_,_,o,_,_,_,_,_,_,_,_,_,o,_,_,_},
                                   {_,_,_,o,_,_,_,_,_,_,_,_,_,o,_,_,_},
                                   {_,_,_,o,_,_,_,_,_,_,_,_,_,o,_,_,_},
                                   {_,_,_,o,_,_,_,_,_,_,_,_,_,o,_,_,_},
                                   {_,_,_,o,_,_,_,_,_,_,_,_,_,o,_,_,_},
                                   {_,o,o,o,_,o,o,o,_,o,o,o,_,o,o,o,_},
                                   {_,o,_,o,_,o,_,_,_,_,_,o,_,o,_,o,_},
                                   {_,o,o,o,o,o,o,o,o,o,o,o,o,o,o,o,_},
                                   {_,o,_,o,_,_,_,o,_,o,_,_,_,o,_,o,_},
                                   {_,o,o,o,o,o,o,o,_,o,o,o,o,o,o,o,_},
                                   {_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_,_}    
                                };
                                
    private int level;                        
    private Item[] items;
    private Ghost[] ghosts;
    private Player player;                           
    private GameWindow gameWindow;

    public World(int level)
    {
        player = new Player(this, coordinate(centre), coordinate(15));
        
        if (level < 0)
        {
            this.level = 0;
        }
        else if (level > 15)
        {
            this.level = 15;
        }
        else
        {
            this.level = level;
        }
        ghosts = new Ghost[this.level];
        int start = centre - this.level/2;
        for (int i=0; i<this.level; i++)
        {
            Ghost ghost = new Ghost(this, coordinate(start+i), coordinate(3));
            ghosts[i] = ghost;
        }
        
        int k =  0;
        this.items = new Item[120];
        for(int i = 0; i < ROW_COUNT; i++)
        {
            for (int j = 0 ; j < COL_COUNT; j++)
            {                
                if(itemmap[i][j]==true)
                {
                    Item item = new Item(this, coordinate(j), coordinate(i));
                    items[k]=item;
                    k++;
                }                
            }
        }
    
        
        // Put all of your constructor code before this line.
        gameWindow = new GameWindow(this); // Keep this line.
      
    }
    
    public int coordinate(int rowcol)
    {
        return rowcol*CELL_SIZE + CELL_SIZE/2;
    }
    
    public boolean iAvailable()
    {
      for (int i = 0; i < items.length; i++)
      {
          if (items[i].isAvailable())
          {
             return true;  
          }    
      }
      return false;
    }

    public void play()
    {
        // Here is an example of how to make the player turn down:
        while (player.isAlive() && iAvailable())
        {                     
           if(gameWindow.isPressed('s'))
           {
                player.turn(Direction.down);
           }
           else if (gameWindow.isPressed('w'))
           {
                player.turn(Direction.up);
           }
           else if (gameWindow.isPressed('a'))
           {
                player.turn(Direction.left);
           }
           else if (gameWindow.isPressed('d'))
           {
                player.turn(Direction.right);
           }
        player.move();
        for (int i=0; i < ghosts.length; i++)
          {
              ghosts[i].autoMove();
          }
        gameWindow.updateScreen();
        gameWindow.sleep(50);
        }
        
        if(iAvailable()==false)
       {
          gameWindow.displayGameOver("WIN!");     
       }
       else if (player.isAlive()==false)
       {
          gameWindow.displayGameOver("LOSE!"); 
       }
        
    }

    public Player getPlayer()
    {
        return player;
    }

    public Item[] getItems()
    {        
        return items;
    }
    
    public Ghost[] getGhosts()
    {
        return ghosts;
    }

    public boolean[][] getWalls()
    {
        return walls;
    }

    public Item getItemUnderPlayer()
    {
        for(int i = 0; i < items.length; i++)
        {
            if(player.getBody().intersects(items[i].getBody()))
            return items[i];
        }
        return null; 
    }
    
    public Player getPlayerUnderGhost()
    {
        for(int i = 0; i < level; i++)
        {
            if(ghosts[i].getBody().intersects(player.getBody()))
            return player;
        }
        return null; 
    }

    public boolean bodyIntersectsWithWall(Body body)
    {
        int top = body.getY()-body.getRadius();
        int left = body.getX()-body.getRadius();
        int row = top/CELL_SIZE;
        int col = left/CELL_SIZE;
        int rowSpan = top % CELL_SIZE == 0 ? 1 : 2;
        int colSpan = left % CELL_SIZE == 0 ? 1 : 2;
        for (int i = 0; i < rowSpan; i++)
            for (int j = 0; j < colSpan; j++)
                if (wallAt(row+i, col+j))
                    return true;
        return false;
    }
    private boolean wallAt(int row, int col)
    { return row >= 0 && col >= 0 && row < ROW_COUNT && col < COL_COUNT && walls[row][col]; }
    public int getWidth()
    { return CELL_SIZE * COL_COUNT; }
    public int getHeight()
    { return CELL_SIZE * ROW_COUNT; }    
}
