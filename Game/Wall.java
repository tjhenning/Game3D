import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

public class Wall
{
    Point2D.Double location;
    //Point2D.Double c1;
    //Point2D.Double c2;
    double orientation;
    double len;
    Color color;    
    public Wall(Point2D.Double center,  Color color, double o, double length)
    {
        location=center;
        //c1=new Point2D.Double(center.getX()+(length*Math.cos(o)),center.getY()+(length*Math.sin(0)));
        //c2=new Point2D.Double(center.getX()-(length*Math.cos(o)),center.getY()-(length*Math.sin(0)));
        len=length;
        orientation=o;
        
        this.color=color;
    }
    
    public Point2D.Double getCenter()
    {
        return location;
    }   
    
    Color getColor()
    {
        return color;
    }
       
    double getX()
    {
        return location.getX();
    }
    
    double getY()
    {
        return location.getY();
    }
    
    public void move(double x, double y)
    {
        location=new Point2D.Double(location.getX()+x,location.getY()+y);
    }
    
    public void goTo(double x, double y)
    {
        location=new Point2D.Double(x,y);
    }
    
    public void goToX(double x)
    {
        location=new Point2D.Double(x,location.getY());
    }    
    
    
        
    public void draw(Graphics2D g2,double distance, double angle)
    {
        double angle2=angle-Math.toRadians(Player.direction);
        if(angle2>4)
            {
                angle2-=6.28;
            }
            else if(angle2<-4)
            {
                angle2+=6.28;
            }
            //System.out.println(angle2);
            angle2=((angle2+Math.PI/4)/(Math.PI/2))*Screen.windowWidth;
            distance=Math.sqrt(distance);
            double size=(900/(distance))*len;
            //calculate facing angle
            double facingAngle=-1;
            if(orientation==0)
            {
                facingAngle=((angle/1.571)%2)*1.571;
                
            }
            else if(orientation==90)
            {
                facingAngle=(((1.571+angle)/1.571)%2)*1.571;
            }
            if(facingAngle>1.571)
            {
                facingAngle=3.141-facingAngle;
            }            
            double sizeDiff=Math.cos(facingAngle)*size/4; 
            facingAngle=Math.sin(facingAngle);
                       
            int width=(int)(size*facingAngle);
            Line2D.Double l1=new Line2D.Double(angle2-width/2,300-size/2-sizeDiff,angle2+width/2,300-size/2+sizeDiff);
            Line2D.Double l2=new Line2D.Double(angle2-width/2,300+size/2+sizeDiff,angle2+width/2,300+size/2-sizeDiff);
            Rectangle rect=new Rectangle((int)angle2-width/2,(int)(300-size/2),width,(int)size);
            g2.setColor(color);
            g2.fill(rect);
            g2.setColor(Color.BLACK);
            g2.draw(l1);
            g2.draw(l2);
        
    }
}
