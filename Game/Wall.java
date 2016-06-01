import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Wall
{
    Point2D.Double location;
    double orientation;
    //double radius;
    Color color;    
    public Wall(Point2D.Double center,  Color color, double o)
    {
        location=center;
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
        if(Math.abs(angle2)<Math.PI/2&&distance<700)
        {
            angle2=((angle2+Math.PI/4)/(Math.PI/2))*Screen.windowWidth;
            distance=Math.sqrt(distance);
            int size=(int)(900/(distance));
            //System.out.println(Math.cos(angle+orientation)+Math.sin(orientation));
            int width=size;
            
            Rectangle rect=new Rectangle((int)angle2-width/2,(int)300-size/2,width,size);
            g2.setColor(color);
            g2.fill(rect);
        }
    }
}
