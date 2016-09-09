import javax.swing.*;


public class Main
{
    public static void main(String[] args)
    {
        int level = Integer.parseInt(JOptionPane.showInputDialog(null, "Please choose a level between 0 and 15"));
        World world = new World(level);
        
        world.play();
       
       //world.setGhosts(getGhost("Please choose a level between 0 and 15"));
       // JOptionPane.showInputDialog(null, "Please choose a level between 0 and 15"); 
       
      
      // for (int i = 0; )
      //  {
        
      //  }
    }
    
    //private static int getGhosts(String prompt)
   // {
   //     String str = JOptionPane.showInputDialog(null, prompt);
   //     return Integer.parseInt(str);
   // }
    
}
