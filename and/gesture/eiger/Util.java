package and.gesture.eiger;

import java.util.ArrayList;

import android.util.Log;

public class Util 
{
	
	private static final int MINIMUM_NUMBER_OF_POINTS = 10;
	private static final String TAG = "Expression"; 
	
	public static final double angleBetweenTwoPoints(Point p1, Point p2)
	{
		double alpha = (-1)*(Math.atan2((p2.getY()-p1.getY()),(p2.getX() - p1.getX())));
		double angle = (alpha*180)/Math.PI;
		
		if(angle<0)return 360 + angle;		
		return angle;
	}
	
	public static final String getExpression(ArrayList<Point> points)
	{
		
		if(points.size() <= MINIMUM_NUMBER_OF_POINTS) throw new EigerException("there is no enough points");
		
		int size = points.size();
		String expression = "";
		
		for(int i=0;i<size;i++)
		{
			expression +=points.get(i) + ",";
		}
		Log.i(TAG, expression);
		return expression;
	}
	
	public static final double totalStrokeDistance(ArrayList<Point> points)
	{
		final Point[] aPoints = new Point[points.size()];
		int j =0;
		for(final Point p:points)
		{
			aPoints[j] = p;
			++j;
		}
	
		double result = 0;
		for(int i=0;i<points.size()-1;i++)
		{
			final double deltaX = aPoints[i+1].getX() - aPoints[i].getX();
			final double deltaY = aPoints[i+1].getY() - aPoints[i].getY();
			result += Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
		}
		Log.i("Total Distance", String.valueOf(result));
		return result;
	}
  public static final String generate_pattern(Point p1, Point p2)
  {
    double angle  = find_angle(p1,p2);
    return direction(angle).toString();
  }
  
  public static Direction direction(double angle)
	{ 
		if((angle >=0 && angle <10) || angle >=350)return Direction.E;
		if(angle >=10 && angle < 80)return Direction.NE;
		if(angle >=80 && angle < 100)return Direction.N;
		if(angle >=100 && angle <170)return Direction.NW;
		if(angle >=170 && angle< 190)return Direction.W;
		if(angle >=190 && angle < 260)return Direction.SW;
		if(angle >=260 && angle <280)return Direction.S;
		return Direction.SE;

	}
  public static final float find_angle(Point p1,Point p2)
  {
    double alpha = (-1)*(Math.atan2((p2.getY() - p1.getY()),(p2.getX() - p1.getX())));
    double angle = (alpha*180)/Math.PI;
    if(angle <0) return 360 + (float) angle;
    return (float)angle;

  }
  
  public static final double distance_among_points(Point p1, Point p2)
  {
    if(p1 == null && p2 == null) return 1;
    return Math.sqrt(Math.pow(p2.getX() - p1.getX(),2) + Math.pow(p2.getY() -p1.getY(), 2));
  }

  public static final boolean is_same_point(Point p1, Point p2)
  {
    return (p1.getX() == p2.getX()) && (p1.getY() == p2.getY());
  }
  
  public static final boolean is_single_direction(String direction){
	  return direction.length() == 1;
  }
  
}
