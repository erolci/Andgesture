package and.gesture.eiger;

import java.util.ArrayList;

import android.util.Log;

public final  class CornerDetection
{
	
  private int num_0_degree = 0;
  private int num_90_degree = 0;
  private int num_180_degree = 0;
  private int num_270_degree = 0;
  
  private final boolean is_top_right(final float a1,final float a2)
  {
	  if((a1 == 0 && a2 == 270) ||(a1 == 90 && a2 == 180))return true;
	  return false;
  }
  
  private final boolean is_top_left(final float a1, final float a2)
  {
	  if((a1 == 90 && a2 == 0) ||(a1 == 180 && a2 == 270))return true;
	  return false;
  }
  private final boolean is_bottom_right(final float a1,final float a2)
  {
	  if((a1 == 0 && a2 == 90) ||(a1 == 270 && a2 == 180))return true;
	  return false;
  }
  private final boolean is_bottom_left(final float a1, final float a2 )
  {
	  if((a1 == 270 && a2 == 0) ||(a1 == 180 && a2 == 90))return true;
	  return false;
  }

  private final boolean is_corner_angle(final float angle)
  {
	  if(angle == 0 || angle == 90 || angle == 180 || angle == 270 || angle == 360) return true;
	  return false;
  }
  private void change_angle_position(final float a1,final float a2)
  {
	  
  }
  private String compare_angles(final float a1, final float a2)
  {
    if(is_bottom_right(a1,a2))  return "BR";
    if(is_top_right(a1,a2)) return "TR";
    if(is_top_left(a1,a2)) return "TL";
    else return "BL";
    

  }
  
  private void reset()
  {
	num_0_degree =0;
	num_180_degree = 0;
	num_90_degree = 0;
	num_270_degree =0;
  }
  
  private boolean is_bigger_than_10()
  {
	  if(num_0_degree > 6 || num_90_degree > 6 || num_180_degree > 6 || num_270_degree > 6)return true;
	  return false;
  }
  private void statistic(int angle)
  {
	  
	  switch(angle)
	  {
	  case 0:
		  num_0_degree++;
		  break;
	  case 90:
		  num_90_degree++;
		  break;
	  case 180:
		  num_180_degree++;
		  break;
	  case 270:
		  num_270_degree++;
		  break;
	  default:
		  break;
	  }
  }
  public final String detect_corner(ArrayList<Point> points)
  {
     String pattern = "";
     boolean is_first_angle_detected = false;
     final int size = points.size();
     Point p1, p2, p3,p4;
     float angle;
     float angle1 = 720;
     float angle2 = 720;
     for(int i =4;i<size-4;i++)
     {
       p3 = points.get(i-1);
       p4 = points.get(i);
       angle = Util.find_angle(p3,p4);
       //Log.i("Angle",String.valueOf((int)angle));
       statistic((int)angle);
       if(is_bigger_than_10() && is_corner_angle(angle))
       {
	       if(!is_first_angle_detected)
	       {
	          angle1 = angle;
	          is_first_angle_detected = true;
	       }
	       if(is_first_angle_detected && angle1 !=angle)
	       {
	         angle2 = angle;
	         is_first_angle_detected = false;
	         pattern += compare_angles(angle1,angle2);
	       }
	       
	       reset();
       }

     }
     Log.i("Angle 1", String.valueOf(angle1));
     Log.i("Angle 2", String.valueOf(angle2));
     return pattern;
  }

  
}

