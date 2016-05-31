
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

class Player
{
    Point2D.Double location;
    double direction=0;
    double x;
    double y;
    double xVelocity;
    double upVelocity;
    boolean ded=false;
    public Player()
    {
        location=new Point2D.Double(0,0);   
                
    }
    
    public void calcMove(boolean space)
    {
        y=y+(-xVelocity*Math.sin(Math.toRadians(direction)));
        x=x+(xVelocity*Math.cos(Math.toRadians(direction)));
        direction=(360+direction)%360;
       
    }    
    public void moveX(double direction,boolean shift, double acc)
    {        
        if(shift)
        {
            this.direction+=direction*4;
            xVelocity+=acc/8;    
        }
        else
        {
            this.direction+=direction*2;
            xVelocity+=acc/16;
        }
              
        xVelocity*=.95;
        if(Math.abs(xVelocity)<.02)
        {
            xVelocity=0;
        }
       
            if (xVelocity>3||xVelocity<-3)
            {            
                xVelocity*=.9;
                //xVelocity/=10;
            }
            else if ((xVelocity>2||xVelocity<-2)&&!shift)
            {
                xVelocity*=.9;
                //xVelocity/=10;
            }
        
    }        
    public void jump()
    {
        
    }    
    public void bounce(int behavior)//0 is bounce, 1 is tangible, 2 is intangible
    {
    }    
    public void whenTouchingGround(boolean touching, double groundHeight)
    {       
    }
    
    void draw(Graphics2D g2)
    {                
        Rectangle rect=new Rectangle((int)x*4+45,(int)y*4+45,10,10);
        g2.setColor(Color.BLACK);
        g2.fill(rect);
        Line2D.Double l1=new Line2D.Double(x*4+50,y*4+50,30*Math.cos(Math.toRadians(direction))+x*4+50,-30*Math.sin(Math.toRadians(direction))+y*4+50);
        g2.draw(l1);        
    }
    public void stop()
    {
        xVelocity=0;
        upVelocity=0;
    }
    public Point2D.Double getCenter()
    {
        return new Point2D.Double(x,y);
    }
          
    public double angleOnScreen(double x, double y)//should be like -45 for far left
    {        
        double angle=Math.atan((this.y-y)/(this.x-x));       
        angle=((Math.PI*2+angle))%Math.PI*2;
        angle/=2;
        if(this.y-y<0)
        {angle=Math.PI*2-angle;}
        else
        {
            angle=Math.PI-angle;
        }        
        angle=angle-Math.toRadians(direction);
        
        return angle;
    }
    public void setSpeed(int speed)
    {
        upVelocity=speed;
    }       
    public boolean getDed()
    {
        return ded;
    }
    public void setDed(boolean ded)
    {
        this.ded=ded;
    }   
    public double getUpV()
    {
        return upVelocity;
    }
    public double getXV()
    {
        return xVelocity;
    }    
    public double getY()
    {
        return y;
    }
    public double getX()
    {
        return x;
    }
    
    public void move(double x, double y)
    {
        this.x=this.x+x;
        this.y=this.y+y;
    }
    
    public void goTo(double x, double y)
    {
        this.x=x;
        this.y=y;
    }
    
    public boolean isInside(Point2D.Double point)
    {
        return false;
        //return (Math.abs(center.getX()-point.getX())<xradius)&&(Math.abs(center.getY()-point.getY())<yradius);
    }
    
    public boolean isOnTopOfNextFrame(Wall on)
    {
        //Point2D.Double center2=new Point2D.Double(center.getX()+xVelocity,center.getY()-upVelocity);        
        //return(upVelocity<0)&&((on.isInside(new Point2D.Double(center2.getX()+xradius,center2.getY()+yradius)))||(on.isInside(new Point2D.Double(center2.getX()-xradius,center2.getY()+yradius))));
        return false;
    }        
    public boolean isHitNextFrame(Wall on)
    {
        //Point2D.Double center2=new Point2D.Double(center.getX()+xVelocity,center.getY()-upVelocity);        
        //return (on.isInside(new Point2D.Double(center2.getX()+xradius+1,center2.getY()+(yradius*3/4))))||(on.isInside(new Point2D.Double(center2.getX()-xradius-1,center2.getY()+(yradius*.75))))||(on.isInside(new Point2D.Double(center2.getX()-xradius-1,center2.getY()-yradius)))||(on.isInside(new Point2D.Double(center2.getX()+xradius+1,center2.getY()-yradius)));
        return false;
    }        
    public void hitWall(double objX, double objY, double xLength,double yLength, Wall object)
     {
//         if(objX-xLength>center.getX()&&objY+yLength>center.getY()-yradius+1)
//         {            
//             goTo(objX-xLength-xradius,center.getY());
//             xVelocity=-.25;
//             if(DrawingPanel.isJumping&&upVelocity<10)
//             {
//                 upVelocity=7;
//                 xVelocity=-7;
//             }
//             
//         }
//         else if (objX+xLength<center.getX()&&objY+yLength>center.getY()-yradius+1)
//         {            
//             goTo(objX+xLength+xradius,center.getY());
//             xVelocity=.25;
//             if(DrawingPanel.isJumping&&upVelocity<10)
//             {
//                 upVelocity=7;
//                 xVelocity=7;
//             }
//         }
//         else if (objY+yLength<center.getY())
//         {           
//             goTo(center.getX(),objY+yLength+yradius);
//             upVelocity=0;
//             object.hitFromBottom();
//         }   
    }
    
    public void getPowerUp(int identity)
    {        
//         if(identity==powerUpState)
//         {
//             identity++;
//         }
//         if(powerUpState<identity)
//         {
//             powerUpState=identity;
//             yradius=30+(10*powerUpState);
//             xradius=baseRadius;
//         }
        
    }
    public void takeDamage(int type)
    {
//         if(vulnerable)
//         {            
//             if(type==0||type==1)
//             {
//                 vulnerable=false;
//                 vulnerabilityTimer=100;
//                 powerUpState--;     
//                 yradius=30+(10*powerUpState);
//                 if(powerUpState<0)
//                 {
//                     ded=true;
//                     goTo(50, 400);
//                     //scrollX=0;
//                     powerUpState=0;
//                     yradius=30+(10*powerUpState);
//                     xradius=baseRadius;
//                     stop();
//                 }
//             }
//             else if (type==3)
//             {
//                 vulnerable=false;
//                 vulnerabilityTimer=100;
//                 powerUpState--;     
//                 yradius=30+(10*powerUpState);
//                 if(powerUpState<0)
//                 {
//                     ded=true;
//                     goTo(50, 400);
//                     //scrollX=0;
//                     powerUpState=0;
//                     yradius=30+(10*powerUpState);
//                     xradius=baseRadius;
//                     stop();
//                 }
//                 else
//                 {
//                     goTo(center.getX()-200,100);
//                     stop();
//                 }
//                 
//             }
//         }
    }
        
}

