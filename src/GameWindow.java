import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameWindow extends JFrame
{
    private static final Random random = new Random();

    public static final int halfCycle = 4; // frames to open mouth
    public static final int openAngle = 90; // degrees

    private World world;
    private String gameOverMessage;
    private int chompCycle = halfCycle/2;
    private GamePanel gamePanel;
    private boolean[] keyPressed = new boolean[1024];

    public GameWindow(World world)
    {
        this.world = world;
        gamePanel = new GamePanel();
        add(gamePanel, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setFocusable(true);

        addWindowListener(new WindowAdapter() {
                public void windowActivated(WindowEvent e) {
                    gamePanel.requestFocusInWindow();
                }});

        gamePanel.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) { keyPressed[e.getKeyCode()] = true; }
                public void keyReleased(KeyEvent e) { keyPressed[e.getKeyCode()] = false; }
            });

        pack();
        setVisible(true);
    }

    /**
     * This method generates a random number between 0 (inclusive) to range
     * (exclusive).
     * 
     * Here is an example showing how to use it:
     *
     * int n = GameWindow.random(100);
     *
     * if (n < 5)
     * {
     *   System.out.println("This happens 5% of the time");
     * }
     * else
     * {
     *   System.out.println("This happens 95% of the time");
     * }
     */
    public static int random(int range)
    {
        return random.nextInt(range);
    }

    /**
     * This method tests whether the given character is pressed.
     *
     * For example, isPressed('A') returns true if the 'A' key is pressed, or
     * otherwise returns false if the 'A' key is not pressed.
     */
    public boolean isPressed(char c)
    {
        return keyPressed[(int)Character.toUpperCase(c)];
    }

    /**
     * This method is similar to the one above, but it works for any keycode
     * that is defined in the java.awt.event.KeyEvent class. You can use this
     * method if you want to refer to arrow keys.
     */
    public boolean isPressed(int code)
    {
        return keyPressed[code];
    }

    /**
     * This method updates the screen.
     */
    public void updateScreen()
    {
        gamePanel.repaint();
    }

    /**
     * This method displays a game over message.
     */
    public void displayGameOver(String message)
    {
        gameOverMessage = message;
        updateScreen();
    }

    /**
     * This method causes the computer to sleep for "ms" milliseconds.
     */
    public void sleep(long ms)
    {
        try { Thread.sleep(ms); } catch (Exception e) {}
    }

    public class GamePanel extends JPanel
    {
        public GamePanel()
        {
            setPreferredSize(new Dimension(world.getWidth(), world.getHeight()));
        }

        public void paintComponent(Graphics g)
        {
            chompCycle++;
            paintBackground(g);
            paintWalls(g);
            paintItems(g);
            paintPlayer(g);
            paintGhosts(g);
            if (gameOverMessage != null)
                paintMessage(g, gameOverMessage);
        }

        private void paintBackground(Graphics g)
        {
            Dimension size = getSize();
            g.setColor(Color.black);
            g.fillRect(0, 0, size.width, size.height);
        }

        private void paintWalls(Graphics g)
        {
            boolean[][] walls = world.getWalls();
            if (walls == null)
                return;

            for (int row = 0; row < walls.length; row++)
            {
                for (int col = 0; col < walls[row].length; col++)
                {
                    if (walls[row][col])
                    {
                        g.setColor(new Color(10, 10, 10));
                        int x = col*World.CELL_SIZE;
                        int y = row*World.CELL_SIZE;
                        g.fillRect(x, y, World.CELL_SIZE, World.CELL_SIZE);
                        g.setColor(Color.blue);
                        int gap = 4;
                        // left
                        if (col-1 >= 0 && !walls[row][col-1])
                            g.fillRect(x, y, gap, World.CELL_SIZE);
                        // right
                        if (col+1 < World.COL_COUNT && !walls[row][col+1])
                            g.fillRect(x+World.CELL_SIZE-gap, y, gap, World.CELL_SIZE);
                        // top
                        if (row-1 >= 0 && !walls[row-1][col])
                            g.fillRect(x, y, World.CELL_SIZE, gap);
                        // bottom
                        if (row+1 < World.ROW_COUNT && !walls[row+1][col])
                            g.fillRect(x, y+World.CELL_SIZE-gap, World.CELL_SIZE, gap);
                    }
                }
            }
        }

        private void paintItems(Graphics g)
        {
            Item[] items = world.getItems();
            if (items == null)
                return;

            g.setColor(Color.white);
            for (Item item : items)
            {
                if (item.isAvailable())
                {
                    int x = item.getBody().getX();
                    int y = item.getBody().getY();
                    int radius = item.getBody().getRadius();
                    g.fillOval(x-radius, y-radius, radius*2, radius*2);
                }
            }
        }

        private void paintPlayer(Graphics g)
        {
            Player player = world.getPlayer();
            if (player == null)
                return;

            Body body = player.getBody();
            int x = body.getX();
            int y = body.getY();
            int radius = body.getRadius();
            g.setColor(Color.yellow);
            g.fillOval(x-radius, y-radius, radius*2, radius*2);
            int chompAngle = openAngle * Math.abs(chompCycle % (halfCycle*2) - halfCycle) / halfCycle;
            int quadrant;
            switch (body.getDirection())
            {
                case right: quadrant = 0; break;
                case up: quadrant = 90; break;
                case left: quadrant = 180; break;
                case down: quadrant = 270; break;
                default: quadrant = 180; break;
            }
            g.setColor(Color.black);
            g.fillArc(x-radius, y-radius, radius*2, radius*2, quadrant + 360-chompAngle/2, chompAngle);
        }

        private void paintGhosts(Graphics g)
        {
            Ghost[] ghosts = world.getGhosts();
            if (ghosts == null)
                return;

            for (Ghost ghost : ghosts)
            {
                int x = ghost.getBody().getX();
                int y = ghost.getBody().getY();
                int radius = ghost.getBody().getRadius();
                Direction direction = ghost.getBody().getDirection();

                g.setClip(x-radius, y-radius, radius*2, radius*2);

                //Paint body
                g.setColor(ghost.getColor());
                g.fillArc(x-radius, y-radius, radius*2, radius*2, 0, 180);
                g.fillRect(x-radius, y, radius*2, radius/2);
                int footLength = radius*2/3;
                int start = radius*2*(chompCycle % 10)/10;
                while (start > 0)
                    start -= footLength;
                for (int i = 0; i < 4; i++)
                    g.fillOval(x-radius + start + i*footLength, y, footLength, footLength);

                // Paint eyes
                int eyeRadius = radius/3;
                int pupilRadius = eyeRadius/2;
                int leyeX = x - radius/2;
                int reyeX = x + radius/2;
                int eyeY = y-eyeRadius/2;
                g.setColor(Color.white);
                g.fillOval(leyeX-eyeRadius, eyeY-eyeRadius, eyeRadius*2, eyeRadius*2);
                g.fillOval(reyeX-eyeRadius, eyeY-eyeRadius, eyeRadius*2, eyeRadius*2);
                int dx = direction.dx()*2;
                int dy = direction.dy()*2;
                g.setColor(Color.black);
                g.fillOval(leyeX-pupilRadius+dx, eyeY-pupilRadius+dy, pupilRadius*2, pupilRadius*2);
                g.fillOval(reyeX-pupilRadius+dx, eyeY-pupilRadius+dy, pupilRadius*2, pupilRadius*2);

                g.setClip(null);
            }
        }

        private void paintMessage(Graphics g, String message)
        {
            g.setFont(new Font("SansSerif", Font.BOLD, 34));
            FontMetrics fm = g.getFontMetrics();
            int width = fm.stringWidth(message);
            int height = fm.getAscent();
            Dimension size = getSize();
            g.setColor(Color.red);
            g.drawString(message, (size.width-width)/2, (size.height-height)/2 + height);
        }

        // You may use this method to paint an energiser onto the screen.
        private void paintEnergiser(Graphics g, Body body)
        {
            int x = body.getX();
            int y = body.getY();
            int radius = body.getRadius();
            g.setColor(Color.blue);
            g.fillOval(x-radius, y-radius, radius*2, radius*2);
        }
    }
}
