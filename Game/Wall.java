import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Line2D;

public class Wall
{
    //Point2D.Double location;
    Point2D.Double c1;
    Point2D.Double c2;
    double angle;
    double len;
    //double angleratio;
    Color color;    
    public Wall(Point2D.Double p1,  Point2D.Double p2, Color color, double direction)//uhh 200 is above
    {
        //location=(p1.getX()+p2.getX())/2;
        c1=p1;
        c2=p2;
        len=p1.distance(p2);
//         if(c2.getY()>c1.getY())
//         {
//             Point2D.Double temp=c2;
//             c2=c1;
//             c1=temp;
//         }
        
        angle=Math.atan((c2.getY()-c1.getY())*-1/(c2.getX()-c1.getX()));
        if(angle<0)
        {angle+=3.14159;}
       
        angle+=direction;
        
        this.color=color;
    }
    
//      public Point2D.Double getCenter()
//      {
//          return new Point2D.Double(getX(),getY());
//      }   
    public Point2D.Double getPoint1()
    {
        return c1;
    }   
    public Point2D.Double getPoint2()
    {
        return c2;
    }   
    
    Color getColor()
    {
        return color;
    }
       
    double getX()
    {
        if(c1.getX()>c2.getX())
        return c2.getX();
        else
        return c1.getX();    
    }
    double getX1()
    {
        return c1.getX();
    }
    double getX2()
    {
        return c2.getX();
    }
    
    double getY1()
    {
        return c1.getY();
    }
    double getY2()
    {
        return c2.getY();
    }
    
    double getLen()
    {
        return len;
    }
    double getAngle()
    {
        return angle;
    }
    double isInside(Point2D.Double player)
    {
        double temp=player.distanceSq((c1.getX()+c2.getX())/2,(c1.getY()+c2.getY())/2);
        if (temp<len*4)
        {
            if(temp<2)//optimization opprotunity?
            {                
                return angle;
            }
            if(player.distanceSq(c2)<2)
            {                
                return angle;
            }
            if(player.distanceSq(c1)<2)
            {
                return angle;
            }
        }
        return 99;
    }
    boolean whichSide(Point2D.Double player)//true is above/left
    {
        return false;
    }
    
        
    public void draw(Graphics2D g2,double distance1, double distance2, double angle1, double angle2)
    {
         double angle12=angle1-Math.toRadians(Player.direction);
         if(angle12>4)
         {
             angle12-=6.28;
         }
         else if(angle12<-4)
         {
             angle12+=6.28;
         }
                  
         angle12*=-1;
         
        
         double angle22=angle2-Math.toRadians(Player.direction);
         if(angle22>4)
         {
             angle22-=6.28;
         }
         else if(angle22<-4)
         {
             angle22+=6.28;
         }
         angle22*=-1;
          //Sytem.out.pritln(angle22);
//          double fov=2;
//         angle2*=-fov;
//         //Systm.out.prntln(angle2);
//         if(angle2>0)
//         {
//             angle2+=.2;
//             angle2=Math.sqrt(angle2);
//             angle2-=.447;
//         }
//         else if(angle2<0)
//         {
//             angle2-=.2;   
//             angle2*=-1;
//             angle2=Math.sqrt(angle2);
//             angle2*=-1;
//             angle2+=.447;
//         }
//         angle2/=fov;
        
        
        
        
         angle12=(angle12+Math.PI/4/1.571)*Screen.windowWidth;
         distance1=Math.sqrt(distance1);
         int size1=(int)((800/(distance1)));
//         
         angle22=(angle22+Math.PI/4/1.571)*Screen.windowWidth;
         distance2=Math.sqrt(distance2);
         int size2=(int)((800/(distance2)));
//         angle12*=Screen.windowWidth;
//         angle22*=Screen.windowWidth;
         
        
    
        int[] x={(int)(angle12),
            (int)(angle22),
            (int)(angle22),
            (int)(angle12)};
        g2.setColor(color);
        int[] y={300-(size1/2),300-(size2/2),300+(size2/2),300+(size1/2)};                
        Polygon poly=new Polygon(x,y,4);
        g2.fill(poly);
        g2.setColor(Color.BLACK);
        Line2D.Double l1=new Line2D.Double(angle12,300-(size1/2),angle12,300+(size1/2));
        g2.draw(l1);
         Line2D.Double l2=new Line2D.Double(angle22,300-(size2/2),angle22,300+(size2/2));
        g2.draw(l2);
        Line2D.Double l3=new Line2D.Double(angle12,300-(size1/2),angle22,300-(size2/2));
        g2.draw(l3);
        Line2D.Double l4=new Line2D.Double(angle12,300+(size1/2),angle22,300+(size2/2));
        g2.draw(l4);
//         if(slopeD)
//         {
//             int[] y={300-size/2-sizeDiff,
//                 300-size/2+sizeDiff,
//                 300+size/2-sizeDiff,
//                 300+size/2+sizeDiff};
//            Polygon poly=new Polygon(x,y,4);
//             g2.fill(poly);
//             
//         }
//         else
//         {
//             int[] y={300-size/2+sizeDiff,
//                 300-size/2-sizeDiff,
//                 300+size/2+sizeDiff,
//                 300+size/2-sizeDiff};                
//             Polygon poly=new Polygon(x,y,4);
//             g2.fill(poly);
//         }
        
        
            
//         
    }
}
