import javax.swing.JPanel;
//import java.awt.event.MouseListener;
//import java.awt.event.MouseMotionListener;
//import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
//import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Rectangle;

public class DrawingPanel extends JPanel
{
    ArrayList<Wall> area = new ArrayList<Wall>();
    Player player=new Player();
    //RectObj finish=new RectObj(new Point2D.Double(3000,100),50,1000,Color.BLACK);    
    boolean isShift=false;
    int angleDirection=0;
    
    static int MMC=4;
    static int windowWidth=0;
    int linearDirection=0;
    int currentLevel=1;
        
    public DrawingPanel()
    {        
        
        setBackground(Color.WHITE);             
        //addMouseListener(new ClickListener());
        //addMouseMotionListener(new MovementListener());
        setFocusable(true);
        addKeyListener(new KeysListener());
        loadLevel(currentLevel);
        
    }     
    
    public Dimension getPreferredSize()
    {
        Dimension d=new Dimension(350,300);
        return d;
    }
    
    public void finishLevel()
    {           
        
        try {
            Thread.sleep(30);                 //I took this bit here from stackoverflow - just the pause
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        
        currentLevel++;
        nextLevel();
    }
   
    public void nextLevel()
    {
        
           
        player.goTo(0,0);
        loadLevel(currentLevel);
        
    }
           
    public void paintComponent(Graphics g)
    {
        windowWidth=(int)getSize().getWidth();
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(4));
        
        for(Wall wall:area)
        {
            Rectangle rect=new Rectangle((int)wall.getX()*4+50,(int)wall.getY()*4+50,10,10);
            g2.setColor(wall.getColor());
            g2.fill(rect);
            double distance=wall.getCenter().distanceSq(player.getCenter());
            double angle=player.angleOnScreen(wall.getX(),wall.getY());            
            //System.out.println(windowWidth);
            wall.draw(g2,distance,angle);
            
        }
        player.draw(g2);      
    }
    public void nextFrame()
    {
            
            //for (Shape shape:groundBlocks)
           // {            
            //    if(player.isHitNextFrame(shape))
            //    {
            //        player.hitWall(shape.getCenter().getX(),shape.getCenter().getY(),shape.getXL(),shape.getYL(),shape);
            //      
            //    }
            //    boolean b=player.isOnTopOfNextFrame(shape);
            //    int top=(int)(shape.getCenter().getY()-shape.getYL());
            //    player.whenTouchingGround(b,top);
            //}         
                                
            player.calcMove(false);      
            
           
            player.moveX(angleDirection,isShift,linearDirection);                
            
            
            
            
            if(player.getDed())
            {
                    
                player.goTo(0,0);
                
            }            
            repaint();
            requestFocusInWindow();
            
        
    }    
     public void makeProjectile()
     {
//         if(projectiles.size()+1<=player.getPowerUpLevel()*2)
//         {    
//             projectiles.add(new Projectile(player, player.getPowerUpLevel(),0));
//             if (player.getPowerUpLevel()==3)
//             {
//                 projectiles.add(new Projectile(player, player.getPowerUpLevel(),player.getUpV()+.01));
//             }
//         }
         
    }
    public void loadLevel(int which)
    {
        area = new ArrayList<Wall>();
        area.add(new Wall(new Point2D.Double(5,5),Color.RED));
        area.add(new Wall(new Point2D.Double(5,6),Color.RED));
        area.add(new Wall(new Point2D.Double(6,5),Color.RED));
        
    }
    public Color randomColor()
    {
        return new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
    }
    public class KeysListener implements KeyListener
    {        
        public void keyPressed(KeyEvent e)
        {                         
            if (e.getKeyCode()==KeyEvent.VK_SPACE)
            {
                player.jump();
                                
            }
            else if (e.getKeyCode()==16)
            {                
                isShift=true;
            }
            else if (e.getKeyCode()==KeyEvent.VK_A)
            {                
                 angleDirection=-1;
            }
            else if (e.getKeyCode()==KeyEvent.VK_D)
            {
                 angleDirection=1;
            }            
            else if (e.getKeyCode()==KeyEvent.VK_S)
            {
                 linearDirection=-1;
            }       
            else if (e.getKeyCode()==KeyEvent.VK_W)
            {
                 linearDirection=1;
            }           
            requestFocusInWindow();           
        }
        public void keyReleased(KeyEvent e)
        {
            if (e.getKeyCode()==16)
            {
                isShift=false;
            }
            else if (e.getKeyCode()==KeyEvent.VK_A)
            {
                 angleDirection=0;
            }
            else if (e.getKeyCode()==KeyEvent.VK_D)
            {
                 angleDirection=0;
            }          
            else if (e.getKeyCode()==KeyEvent.VK_SPACE)
            {
                //isJumping=false;
            }
            else if (e.getKeyCode()==KeyEvent.VK_S)
            {
                 linearDirection=0;
            }               
            else if (e.getKeyCode()==KeyEvent.VK_W)
            {
                 linearDirection=0;
            }
        }
        public void keyTyped(KeyEvent e)
        {
            
        }
    }
}
