package and.gesture.eiger;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;

public class GestureDetection {
	
	private Path path;
	private Paint mPaint;
	private static final int TOUCH_TOLERANCE = 4;
	private float mX,mY;
	private List<GestureListener> gestureListeners = new ArrayList<GestureListener>();
	
	
	public GestureDetection()
	{
		path = new Path();
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(0xFFFFFF00);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(6.0f);
	}
	
	public void addGestureListener(GestureListener gl)
	{
		if(gestureListeners.contains(gl)) return;
		
		gestureListeners.add(gl);
	}
	
	public void removeGestureListener(GestureListener gl)
	{
		if(!gestureListeners.contains(gl)) return;
		
		gestureListeners.remove(gl);
	}
	
	
	public Path getPath() {
		return path;
	}

	public Paint getPaint()
	{
		return mPaint;
	}


	public void down(float x, float y)
	{
		//path.reset();
		path.moveTo(x, y);
		mX = x;
		mY = y;
	}
	
	public void move(float x, float y)
	{
		float dx = Math.abs(x - mX);
		float dy = Math.abs(y - mY);
		if(dx >=TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE)
		{
			path.quadTo(mX, mY, (x+mX)/2, (y+mY)/2);
			mX = x;
			mY = y;
		}
	}
	public void up()
	{
		
		path.lineTo(mX, mY);
		//path.reset();
	}
	
	public void listenMouseEvent(MotionEvent event)
	{
		float x = event.getX();
		float y = event.getY();
		
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				down(x,y);
				Log.i("EVENT", "Downing");
				break;
			case MotionEvent.ACTION_MOVE:
				move(x,y);
				Log.i("EVENT", "Moving");
				break;
			case MotionEvent.ACTION_UP:
				up();
				Log.i("EVENT", "Upping");
				break;
			default:
				break;
				
		}
		
		
	}
	

}
