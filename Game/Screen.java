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

public class Screen extends JPanel
{
    ArrayList<Wall> area = new ArrayList<Wall>();
    Player player=new Player();
    //RectObj finish=new RectObj(new Point2D.Double(3000,100),50,1000,Color.BLACK);    
    boolean isShift=false;
    int angleDirection=0;
    
    static int windowWidth=0;
    int linearDirection=0;
    int currentLevel=1;
    int lorr=0;
    public Screen()
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
        {
            Rectangle rect=new Rectangle(0,0,windowWidth,(int)getSize().getHeight()/2);
            Rectangle rect2=new Rectangle(0,(int)getSize().getHeight()/2,windowWidth,(int)getSize().getHeight()/2);
            g2.setColor(new Color(120,150,222));
            g2.fill(rect);
            g2.setColor(new Color(150,200,20));
            g2.fill(rect2);
        }
        double[][] disOrder=new double[area.size()][5];
        int count=0;
        
        for(Wall wall:area)
        {            
            double angle1=player.angleOnScreen(wall.getX1(),wall.getY1());
            double angle2=player.angleOnScreen(wall.getX2(),wall.getY2());
            double diff=Math.abs(angle1-Math.toRadians(Player.direction));
            double diff2=Math.abs(angle2-Math.toRadians(Player.direction));
            int centered=1;
            if (diff2<diff){
                diff=diff2;
                centered=2;         
            }
            //System.out.println(centered);
            if(diff>5)
            {
                diff-=6.28;
            }
            
            if(diff<1)
            {                                   
                Rectangle rect=new Rectangle((int)wall.getPoint1().getX()*10+45,(int)wall.getPoint1().getY()*10+45,(int)(1+((90-wall.getAngle())*wall.getLen()/9)),(int)(1+wall.getAngle()*wall.getLen()/9));               
                g2.setColor(wall.getColor());
                g2.fill(rect);
                double distance1=wall.getPoint1().distanceSq(player.getCenter());
                double distance2=wall.getPoint2().distanceSq(player.getCenter());
                
                disOrder[count][0]=distance1;
                disOrder[count][1]=count;
                disOrder[count][2]=angle1;        
                disOrder[count][3]=angle2;
                disOrder[count][4]=distance2;
            }
            else
            {
                disOrder[count][0]=1000;
                disOrder[count][1]=count;
                disOrder[count][2]=180;  
                disOrder[count][3]=180;
                disOrder[count][4]=1000;
            }
            count++;
         }          
        
        double drawDist=0;
        int which=-1;
        for(int i=0;i<area.size();i++)
        {            
            for(double[] j:disOrder)
            {
                if(j[0]>drawDist)
                {
                    drawDist=j[0];
                    which=(int)j[1];
                }
            }
            drawDist=0;
            if(disOrder[which][0]<700)
            {
                area.get(which).draw(g2,disOrder[which][0],disOrder[which][4],disOrder[which][2],disOrder[which][3]);                  
            }
            disOrder[which][0]=-1;
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
            if(lorr!=0)
            {
                player.moveXSide(isShift,lorr);     
            }
            
            
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
        //area.add(new Wall(new Point2D.Double(6,6),new Point2D.Double(5,6),Color.RED));
        area.add(new Wall(new Point2D.Double(0,0),new Point2D.Double(8,0),Color.BLUE));
        //area.add(new Wall(new Point2D.Double(0,0),new Point2D.Double(0,12),Color.YELLOW));
        //area.add(new Wall(new Point2D.Double(6,6),new Point2D.Double(6,5),Color.GREEN));
        //area.add(new Wall(new Point2D.Double(0,12),new Point2D.Double(8,12),Color.GREEN));
        //area.add(new Wall(new Point2D.Double(8,0),new Point2D.Double(8,12),Color.RED));
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
            else if (e.getKeyCode()==KeyEvent.VK_L)
            {                
                 angleDirection=-1;
            }
            else if (e.getKeyCode()==KeyEvent.VK_J)
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
            else if (e.getKeyCode()==KeyEvent.VK_A)
            {
                lorr=-1;
            }
            else if (e.getKeyCode()==KeyEvent.VK_D)
            {
                lorr=1;
            }
            requestFocusInWindow();           
        }
        public void keyReleased(KeyEvent e)
        {
            if (e.getKeyCode()==16)
            {
                isShift=false;
            }
            else if (e.getKeyCode()==KeyEvent.VK_L)
            {
                 angleDirection=0;
            }
            else if (e.getKeyCode()==KeyEvent.VK_J)
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
            else if (e.getKeyCode()==KeyEvent.VK_A)
            {
                lorr=0;
            }
            else if (e.getKeyCode()==KeyEvent.VK_D)
            {
                lorr=0;
            }
        }
        public void keyTyped(KeyEvent e)
        {
            
        }
    }
}
