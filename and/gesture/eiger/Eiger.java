package and.gesture.eiger;

import java.util.ArrayList;

import android.util.Log;

public final class Eiger {
	

	CornerDetection corner_detection = new CornerDetection();
	VertexDetection vertex_detection = new VertexDetection();
	CornerFinder corner_finder = new CornerFinder();
	
	public final  boolean isTopVertex(final Point p1,final Point p2,final Point p3,final Point p4){
		return ((p1.getY() > p2.getY()))&&((p3.getY() < p4.getY()));
	}
	
	public final  boolean isBottomVertex(final Point p1,final Point p2, final Point p3,final Point p4){
		
		return ((p1.getY() < p2.getY()))&&((p3.getY() > p4.getY()));
	}
	
	
	public final  boolean isLeftVertex(final Point p1,final Point p2,final Point p3,final Point p4){
		return (p1.getX() > p2.getX())&&(p3.getX() < p4.getX());
	}
	
	public final  boolean isRightVertex(final Point p1,final Point p2,final Point p3,final Point p4){
		
		return ((p1.getX() < p2.getX()))&&(p3.getX() > p4.getX());
	}
	
 
	public final String engine(ArrayList<Point> points){
	  if(points.isEmpty()) return "";
	 
	  
	 String pattern = vertex_detection.detect_vertext_points(points);
	//  String pattern = corner_finder.find_corner_points(points,"HEE");
	 return pattern;
	}
	
	
	
	

}
