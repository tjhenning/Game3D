import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.Graphics2D;
import java.awt.Polygon;
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
        
        
        double fov=2;
        angle2*=-fov;
        System.out.println(angle2);
        if(angle2>0)
        {
            angle2+=.2;
            angle2=Math.sqrt(angle2);
            angle2-=.447;
        }
        else if(angle2<0)
        {
            angle2-=.2;   
            angle2*=-1;
            angle2=Math.sqrt(angle2);
            angle2*=-1;
            angle2+=.447;
        }
        angle2/=fov;
        
        angle2=(angle2+Math.PI/4/1.571)*Screen.windowWidth;
        distance=Math.sqrt(distance);
        int size=(int)((800/(distance)));
        double facingAngle=((((orientation/90*1.571)+Math.toRadians(Player.direction))/1.571)%2)*1.571;
        double fA2=((((orientation/90*1.571)+angle)/1.571)%2)*1.571;
        
        
        //if(fA2>1.571)
        //{
        //    fA2=3.141-fA2;
        //}       
        //int sizeDiff=(int)(size-(800/(distance+(Math.cos(facingAngle)))));
        boolean slopeD=(facingAngle>0&&facingAngle<1.572);
        if(facingAngle>1.571)
        {
            facingAngle=3.141-facingAngle;
        }       
        int sizeDiff=(int)(size-(800/(distance+(Math.cos(facingAngle)))));
        
        
        
        
        sizeDiff/=2;
        facingAngle=Math.sin(facingAngle);
                   
        int width=(int)(size*facingAngle*len);
        
        g2.setColor(color);
       
           
        int[] x={(int)(angle2-width/2),
            (int)(angle2+width/2),
            (int)(angle2+width/2),
            (int)(angle2-width/2)};
        
        
        if(slopeD)
        {
            int[] y={300-size/2-sizeDiff,
                300-size/2+sizeDiff,
                300+size/2-sizeDiff,
                300+size/2+sizeDiff};
           Polygon poly=new Polygon(x,y,4);
            g2.fill(poly);
            
        }
        else
        {
            int[] y={300-size/2+sizeDiff,
                300-size/2-sizeDiff,
                300+size/2+sizeDiff,
                300+size/2-sizeDiff};                
            Polygon poly=new Polygon(x,y,4);
            g2.fill(poly);
        }
        
        
            
        
    }
}
