import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Wall
{
    Point2D.Double location;
    //double radius;
    Color color;    
    public Wall(Point2D.Double center,  Color color)
    {
        location=center;
        
        
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
    
    
        
    public void draw(Graphics2D g2,double distance,double angle)
    {
        
        if(Math.abs(angle)<Math.PI/2)
        {
            angle=((angle+Math.PI/4)/(Math.PI/2))*DrawingPanel.windowWidth;
            //System.out.println((DrawingPanel.windowWidth*(int)angle));
            distance=Math.sqrt(distance);
            int size=(int)(700/(distance));
            System.out.println(size);
            Rectangle rect=new Rectangle((int)angle-size/2,(int)300-size/2,size,size);
            g2.setColor(color);
            g2.fill(rect);
        }
    }
}
