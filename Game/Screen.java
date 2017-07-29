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
import java.awt.geom.Line2D;

public class Screen extends JPanel
{
    ArrayList<Wall> area = new ArrayList<Wall>();
    Player player=new Player(1,1);
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
        
           
        player.goTo(2,2);
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
        double[][] disOrder=new double[area.size()][6];
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
                //Rectangle rect=new Rectangle(wall.getX1()*10+45,wall.getY1()*10+45,wall.getX2()*10+45,wall.getY2()*10+45);
                Line2D.Double l1=new Line2D.Double(wall.getX1()*10+45,wall.getY1()*10+45,wall.getX2()*10+45,wall.getY2()*10+45);
                g2.setColor(wall.getColor());
                g2.draw(l1);
                
                double distance1=wall.getPoint1().distanceSq(player.getCenter());
                double distance2=wall.getPoint2().distanceSq(player.getCenter());
                
                disOrder[count][0]=distance1;
                disOrder[count][1]=count;
                disOrder[count][2]=angle1;        
                disOrder[count][3]=angle2;
                disOrder[count][4]=distance2;
                disOrder[count][5]=(distance2*distance2)+(distance1*distance1);
            }
            else
            {
                disOrder[count][0]=1000;
                disOrder[count][1]=count;
                disOrder[count][2]=180;  
                disOrder[count][3]=180;
                disOrder[count][4]=1000;
                disOrder[count][5]=999000;
            }
            count++;
         }          
        
        double drawDist=0;
        int which=-1;
        for(int i=0;i<area.size();i++)
        {            
            for(double[] j:disOrder)
            {
                if(j[5]>drawDist)
                {
                    drawDist=j[5];
                    which=(int)j[1];
                }
            }
            drawDist=0;
            if(disOrder[which][0]<700)
            {
                area.get(which).draw(g2,disOrder[which][0],disOrder[which][4],disOrder[which][2],disOrder[which][3]);                  
            }
            disOrder[which][5]=-1;
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
            
           
                       
            if(player.getXSpeed()>.001)
            {                    
                ArrayList<Double> insideList = new ArrayList<Double>();
                double inside2=99;
                for(Wall wall:area)
                {      
                    inside2=wall.isInside(player.getCenter());
                    if (inside2!=99)
                    {
                        insideList.add(inside2);
                        wall.whichSide(player.getCenter());
                        inside2=99;
                    }
                }                           
                //inside contains an angle, the one returned by isInside
                for(double inside:insideList)
                {   
                    
                    if(inside>=200)
                    {
                        
                        inside-=200;           
                        System.out.println(-Math.sin(inside)*player.getXSpeed()*Math.cos(Math.toRadians(player.direction))+" "+-Math.cos(inside)*player.getXSpeed());
                        player.move(-Math.sin(inside)*player.getXSpeed()*Math.cos(Math.toRadians(player.direction)),Math.cos(inside)*player.getXSpeed()*Math.sin(Math.toRadians(player.direction)));
                        //player.move(-Math.sin(inside)*player.getXSpeed()*1.01,-Math.cos(inside)*player.getXSpeed()*1.01);
                    }
                    else
                    {
                        
                        player.move(-Math.sin(inside)*player.getXSpeed()*Math.cos(Math.toRadians(player.direction)),Math.cos(inside)*player.getXSpeed()*Math.sin(Math.toRadians(player.direction)));
                        
                        //System.out.println(Math.sin(inside)*player.getXSpeed()*1.01);
                    }
                }
                
            }
                      
            if(lorr!=0)
            {
                ArrayList<Double> insideList = new ArrayList<Double>();
                double inside2=99;
                for(Wall wall:area)
                {      
                    inside2=wall.isInside(player.getCenter());
                    if (inside2!=99)
                    {
                        insideList.add(inside2);
                        inside2=99;
                    }
                }                           
                
                for(double inside:insideList)
                {                      
                   if(player.getSideSpeed()>=0)
                   {
                         if(inside>=200)
                    {
                        inside-=200;
                        player.move(-Math.sin(inside)*player.getSideSpeed(),-Math.cos(inside)*player.getSideSpeed());
                    }
                    else
                    {
                        player.move(Math.sin(inside)*player.getSideSpeed(),Math.cos(inside)*player.getSideSpeed());
                    }}
                    else
                    {     if(inside>=200)
                    {
                        inside-=200;
                        player.move(Math.sin(inside)*player.getSideSpeed(),Math.cos(inside)*player.getSideSpeed());
                    }
                    else
                    {
                        player.move(-Math.sin(inside)*player.getSideSpeed(),-Math.cos(inside)*player.getSideSpeed());
                    }}
                    
                }
                //                
                
                //    player.moveXSide(false,lorr*-.3);
                
            }
            player.moveX(angleDirection,isShift,linearDirection);     
            player.moveXSide(isShift,lorr);
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
        area.add(new Wall(new Point2D.Double(0,0),new Point2D.Double(2,0),Color.BLUE,0));
        area.add(new Wall(new Point2D.Double(2,0),new Point2D.Double(4,0),Color.BLUE,0));
        area.add(new Wall(new Point2D.Double(4,0),new Point2D.Double(6,0),Color.BLUE,0));
        area.add(new Wall(new Point2D.Double(6,0),new Point2D.Double(8,0),Color.BLUE,0));
         area.add(new Wall(new Point2D.Double(0,0),new Point2D.Double(0,3),Color.YELLOW,0));
         area.add(new Wall(new Point2D.Double(0,3),new Point2D.Double(0,6),Color.YELLOW,0));
         area.add(new Wall(new Point2D.Double(0,6),new Point2D.Double(0,9),Color.YELLOW,0));
         area.add(new Wall(new Point2D.Double(0,9),new Point2D.Double(0,12),Color.YELLOW,0));
         area.add(new Wall(new Point2D.Double(0,12),new Point2D.Double(2,12),Color.GREEN,200));
         area.add(new Wall(new Point2D.Double(2,12),new Point2D.Double(4,12),Color.GREEN,200));
         area.add(new Wall(new Point2D.Double(4,12),new Point2D.Double(6,12),Color.GREEN,200));
         area.add(new Wall(new Point2D.Double(6,12),new Point2D.Double(8,12),Color.GREEN,200));
         area.add(new Wall(new Point2D.Double(8,0),new Point2D.Double(8,2),Color.RED,200));
         area.add(new Wall(new Point2D.Double(8,2),new Point2D.Double(8,4),Color.RED,200));
         area.add(new Wall(new Point2D.Double(8,4),new Point2D.Double(8,6),Color.RED,200));
         area.add(new Wall(new Point2D.Double(8,6),new Point2D.Double(8,8),Color.RED,200));
//         area.add(new Wall(new Point2D.Double(8,6),new Point2D.Double(9,6),Color.GRAY,200));
//         area.add(new Wall(new Point2D.Double(9,3),new Point2D.Double(9,6),Color.GRAY));
//         area.add(new Wall(new Point2D.Double(9,3),new Point2D.Double(12,3),Color.BLACK));
//        area.add(new Wall(new Point2D.Double(11,3),new Point2D.Double(10,4),Color.WHITE,200));
//        area.add(new Wall(new Point2D.Double(10,4),new Point2D.Double(9,5),Color.WHITE,200));
//        area.add(new Wall(new Point2D.Double(9,5),new Point2D.Double(8,6),Color.WHITE,200));
//        area.add(new Wall(new Point2D.Double(8,6),new Point2D.Double(7,7),Color.WHITE));
//         area.add(new Wall(new Point2D.Double(6,6),new Point2D.Double(6.5,6),Color.ORANGE));
//         area.add(new Wall(new Point2D.Double(5.5,6),new Point2D.Double(6,6),Color.ORANGE));
//         area.add(new Wall(new Point2D.Double(5,6),new Point2D.Double(5.5,6),Color.ORANGE));
//         area.add(new Wall(new Point2D.Double(0,2),new Point2D.Double(1,4),Color.WHITE,0));
//         area.add(new Wall(new Point2D.Double(1,4),new Point2D.Double(2,6),Color.WHITE,0));
//         area.add(new Wall(new Point2D.Double(2,6),new Point2D.Double(3,8),Color.WHITE,0));
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
