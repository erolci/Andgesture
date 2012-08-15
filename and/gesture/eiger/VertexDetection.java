package and.gesture.eiger;

import java.util.ArrayList;
import java.util.EmptyStackException;

import android.util.Log;

public final class VertexDetection
{
  
   
    
    private CornerFinder corner_finder = new CornerFinder();
    private ArrayList<Point> verties = new ArrayList<Point>();
    private static final String EMPTY_STRING = "";
	private final  boolean is_top_vertex(Point p1,Point p2,Point p3,Point p4)
	{
		return ((p1.getY() > p2.getY()))&&((p3.getY() < p4.getY()));
	}
	private final  boolean is_bottom_vertex(Point p1,Point p2,Point p3,Point p4)
	{		
		return ((p1.getY() < p2.getY()))&&((p3.getY() > p4.getY()));
	}
	private final  boolean is_left_vertex(Point p1,Point p2,Point p3,Point p4)
	{
		return (p1.getX() > p2.getX())&&(p3.getX() < p4.getX());
	}
	private final  boolean is_right_vertex(Point p1,Point p2,Point p3,Point p4)
	{	
		return ((p1.getX() < p2.getX()))&&(p3.getX() > p4.getX());
	}
	
	
	public final String detect_vertext_points(final ArrayList<Point> points)
    {
		
		if(!verties.isEmpty())verties.clear();
		String pattern = "";
		final int size = points.size();
		
		if(size < 4)
		{
			return "POINT";
		}
		if(size < 15){
			
			Point p0 = points.get(2);
			Point pn = points.get(size-2);
			double angle = Util.angleBetweenTwoPoints(p0, pn);
			pattern = Util.direction(angle).toString();
			if(pattern == "S")return "S";
			if(pattern == "W")return "W";
			if(pattern == "E")return "E";
			if(pattern == "N")return "N";
			if(pattern == "SW")return "SW";
			if(pattern == "SE")return "SE";
			if(pattern == "NW")return "NW";
			else{
				return "NE";
			}
		}
		
		Point p1 = points.get(2);
		Point p2 = points.get(3);
		Point p3;
		Point p4;
		
		
		
		pattern = "";
//		verties.add(points.get(2));
		for(int i=5;i<size-2;i++)
		{
			p3 = points.get(i-1);
			p4 = points.get(i);
			
			if(is_top_vertex(p1, p2, p3, p4))
			{
				
				p1 = p3;
				p2 = p4;
				verties.add(p3);
				pattern += "T";				
				Log.i("V", "top vertex");
				Log.i("V:","X: " + String.valueOf(p3.getX()));
				Log.i("V:","Y: " + String.valueOf(p3.getY()));
			}
			if(is_bottom_vertex(p1,p2,p3,p4))
			{
				
				p1 = p3;
				p2 = p4;
				verties.add(p3);
				pattern += "B";
				Log.i("V", "bottom vertex");
//				Log.i("V:","X: " + String.valueOf(p3.getX()));
//				Log.i("V:","Y: " + String.valueOf(p3.getY()));
			}
			if(is_right_vertex(p1,p2,p3,p4))
			{
				
				p1 = p3;
				p2 = p4;
				verties.add(p3);
				pattern += "R";
				Log.i("V", "right vertex");
//				Log.i("V:","X: " + String.valueOf(p3.getX()));
//				Log.i("V:","Y: " + String.valueOf(p3.getY()));
			}
			if(is_left_vertex(p1,p2,p3,p4))
			{
				
				p1 = p3;
				p2 = p4;
				verties.add(p3);
				pattern += "L";
				Log.i("V", "left vertex");
				Log.i("V:","X: " + p3.getX());
				Log.i("V:","Y: " + p3.getY());
			}
			
			
			
		}
		if(pattern.length() < 2){return corner_finder.find_corner_points(points,pattern);}
		else{return pattern;}
		
	//		return corner_finder.find_corner_points(points);
		
		
		
    
  
  }
	
	
	private String simple_gesture_detection(ArrayList<Point> points){
		int size = points.size();
		Point p0 = points.get(5);
		Point pn = points.get(size-1);
		
		double minX = Double.POSITIVE_INFINITY;
		double maxX = Double.NEGATIVE_INFINITY;
		double minY = Double.POSITIVE_INFINITY;
		double maxY = Double.NEGATIVE_INFINITY;
		
		for(Point point:points){
			if(point.getX() < minX)minX = point.getX();
			if(point.getX() > maxX)maxX = point.getX();
			if(point.getY() < minY)minY = point.getY();
			if(point.getY() > maxY)maxY = point.getY();
		}
		
		
		Point top_left = new Point((float)minX, (float)minY);
		Point top_right = new Point((float)maxX,(float)minY);
		Point bottom_left = new Point((float)minX,(float)maxY);
		Point bottom_right = new Point((float)maxX,(float)maxY);
		
		Log.i("Top-Left",minY + "--" + minX);
		Log.i("Top-Right",minY + "--" + maxX);
		Log.i("Bottom-Left",maxY + "--" + minX);
		Log.i("Bottom-Right",maxY + "--" + maxX);
		
		
		double angle = Util.angleBetweenTwoPoints(top_left, points.get(2));
		String s = Util.direction(angle).toString();
		angle = Util.angleBetweenTwoPoints(top_left, points.get(size-1));
		s = s + " + " + Util.direction(angle).toString();
		return s;
	}
	
}
