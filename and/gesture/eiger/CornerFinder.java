package and.gesture.eiger;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

import android.R;
import android.graphics.Rect;
import android.util.Log;

public class CornerFinder {

	private static final int DIAGONAL_INTERVAL = 40;
	private static final int WINDOW = 3;
	private static final float MEDIAN_THRESHOLD = 0.95f;
	private static final float LINE_THRESHOLD = 0.95f;
	
	/*
	 *  this is the main method to find the corner point. 
	 */
	
	
	
	public  String find_corner_points(ArrayList<Point> points,String in){
		Point p1,p2;
		double s = find_space(points);
		ArrayList<Point> resampled = resample_points(points, s);
		ArrayList<Integer> corners = get_corners(resampled);
		
		ArrayList<Point> cornerPoints = new ArrayList<Point>();
		
		for(int i =0;i<corners.size();i++){
			Point p  = resampled.get(corners.get(i));
			cornerPoints.add(new Point(p.getX(),p.getY()));
		
		}
		
		if(cornerPoints.size() >=4)return in;
		String pattern = "";
		double angle;
		for(int i=1;i<cornerPoints.size();i++){
			p1 = cornerPoints.get(i-1);
			p2 = cornerPoints.get(i);
			angle = Util.angleBetweenTwoPoints(p1, p2);
			if(cornerPoints.size() == 2)return Util.direction(angle).toString();
			pattern += " " + Util.direction(angle).toString();
		}
		
		if(pattern.equals(" NE SE") || pattern.equals(" NE S"))return "T";
		return pattern;
		
	}
	

	private  double find_space(ArrayList<Point> points){
		Rect r = create_bounding_box(points);
//		Log.i("Width:", String.valueOf(r.width()));
//		double distance = Math.sqrt( Math.pow((r.bottom  - r.top) ,2)+ Math.pow(r.right -r.left, 2));
//		Log.i("Distance:", String.valueOf(distance/DIAGONAL_INTERVAL));
		
		
		Point p1 = new Point(r.left,r.top);
		Point p2 = new Point(r.right + r.width(),r.bottom + r.height());
		double distance = Util.distance_among_points(p1, p2);
		return distance/DIAGONAL_INTERVAL;
		
		
		
	}
	
	//getCorners
	private  ArrayList<Integer> get_corners(ArrayList<Point> points){
		
		ArrayList<Integer> corners = new ArrayList<Integer>();
		corners.add(0);
		int w = WINDOW;
		ArrayList<Double> straws = new ArrayList<Double>();
		int i;
	    for (i = w; i < points.size() - w; i++) {
	        straws.add(Util.distance_among_points(points.get(i-w), points.get(i+w)));
	        Log.i("Resampled X", String.valueOf(points.get(i).getX()));
	        Log.i("Resampled Y", String.valueOf(points.get(i).getY()));
	    }
	    
	    double t = median(straws) * MEDIAN_THRESHOLD;
	    Log.i("T:",String.valueOf(t));
	    for (i = w; i < points.size() - w; i++) {
	       double s  = straws.get(i-w);
	       if(i < straws.size()-1) Log.i("S:",String.valueOf(straws.get(i)));
	        if (s < t) {
	            double localMin = Double.POSITIVE_INFINITY;
	            int localMinIndex = i;
	            while (i < straws.size() && s< t) {
	                if (s< localMin) {
	                    localMin = s;
	                    localMinIndex = i;
	                }
	                i++;
	               s = straws.get(i-w);
	            }
	            corners.add(localMinIndex);
	        }
	    }
	    corners.add( (points.size() - 1));
	    corners = last_process_corners(points, corners, straws);
	    
	    Log.i("Corner Size: ",String.valueOf(corners.size()));
	    return corners;
	}
	
	
	//postProcessCorners
	private  ArrayList<Integer> last_process_corners(ArrayList<Point> points, ArrayList<Integer> corners, ArrayList<Double> straws){
		boolean go = false;
		int i;
		int c1,c2;
		while(!go){
			go = true;
			for(i =1; i<corners.size();i++){
				c1 = corners.get(i-1);
				c2 = corners.get(i);
				if(!is_line(points, c1, c2)){
					int newCorner = halfway_corner(straws, c1, c2);
					if(newCorner > c1 && newCorner < c2){
						corners.add(i,newCorner);
						go = false;
					}
				}
			}
		}
		for(i = 1; i<corners.size() -1; i++){
			c1 = corners.get(i-1);
			c2 = corners.get(i+1);
			if(is_line(points,c1,c2)){
				corners.remove(i);
				i--;
			}
		}
		return corners;
	}
	
	private int halfway_corner(ArrayList<Double> straws, int a, int b){
		int quarter = (b-a)/4;
		double minValue = Double.POSITIVE_INFINITY;
		int minIndex = 0;
		int w = WINDOW;
		for(int i = a+quarter;i<b-quarter;i++){
			//double s = straws.get(i-w);
			
			if(i<straws.size() && straws.get(i)<minValue){
				minValue =straws.get(i);
				minIndex = i;
			}
		}
		
		return minIndex;
	}
	//resamplePoints
	private  ArrayList<Point> resample_points(ArrayList<Point> points, double s){
		
		double distance = 0;
		double interval  = s;
		ArrayList<Point> resampled = new ArrayList<Point>();
		resampled.add(points.get(0));
		
		for(int i =1; i < points.size();i++){
			Point p1 = points.get(i-1);
			Point p2 = points.get(i);
			double d = Util.distance_among_points(p1, p2);
			
			if((distance + d) >= interval){
				double qx = p1.getX() + ((interval - distance)/d) * (p2.getX() - p1.getX());
				double qy = p1.getY() + ((interval - distance)/d) * (p2.getY() - p1.getY());
				Point p = new Point((float)qx,(float)qy);
				resampled.add(p);
				points.add(i,p);
				distance = 0;
			}else {
				distance +=d;
			}
		}
		
		Log.i("Resampled points size: ", String.valueOf(resampled.size()));
		return resampled;
	}
	

	private  Rect create_bounding_box(ArrayList<Point> points){
		double minX = Double.POSITIVE_INFINITY;
		double maxX = Double.NEGATIVE_INFINITY;
		double minY = Double.POSITIVE_INFINITY;
		double maxY = Double.NEGATIVE_INFINITY;
		
		for(Point point:points){
			if(point.getX() < minX) {
				minX = point.getX();
			}
			
			if(point.getX() > maxX){
				maxX = point.getX();
			}
			
			if(point.getY() < minY){
				minY = point.getY();
			}
			
			if(point.getY() > maxY){
				maxY = point.getY();
			}
		}
		
		
		Log.i("Rectangle","Left: " + minX + "Top: " + minY + "Right: " + (maxX) + "Bottom: " + (maxY ));
		return new Rect((int)minX, (int)maxY - (int)minY, (int)(maxX)- (int)minX,(int)minY);
	}
	
	private  double median(ArrayList<Double> distances){
		if(distances.isEmpty())return 0;
		int size = distances.size();
		//Collections.sort(distances);
		int m;
		if(size %2 == 0){
			m = size/2;
			return (distances.get(m-1) + distances.get(m))/2;
		}else{
			m = (size+1)/2;
			return distances.get(m-1);
		}
		
	}
	
	private  boolean is_line(ArrayList<Point> points, int a, int b){
		double distance = Util.distance_among_points(points.get(a), points.get(b));
		double path_distance = path_distance(points, a, b);
		
		return (distance/path_distance) > LINE_THRESHOLD;
	}
	
	private double path_distance(ArrayList<Point> points, int a,  int b){
		double d = 0;
		for(int i = a;i<b; i++){
			d += Util.distance_among_points(points.get(i), points.get(i+1));
		}
		return d;
	}
}
