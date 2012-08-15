package and.gesture.widget;





import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import and.gesture.eiger.CornerFinder;
import and.gesture.eiger.Eiger;
import and.gesture.eiger.Gesture;
import and.gesture.eiger.GestureEvent;
import and.gesture.eiger.GestureListener;
import and.gesture.eiger.Point;
import and.gesture.eiger.Util;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;

import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;


public class DrawView extends EditText {

	private boolean is_capslock_on = false;
	private boolean is_shift_punc_on = false;
	private boolean is_shift_numeric_on = false;
	CornerFinder c_finder = new CornerFinder();
	
	
    private Path    mPath;
    private Paint   mPaint;
    private Paint 	linePaint;
    private Rect mRect;
    private float mX, mY;
    private Eiger eiger;
    private ArrayList<Point> points = new ArrayList<Point>();
    private Point point;
    private static final float TOUCH_TOLERANCE = 4;
    private Set<Gesture> gestures;
    private Pattern pattern;
    private Matcher matcher;
    private ArrayList<Point> modifiedPoints = new ArrayList<Point>();
    private Context mContext;
	public DrawView(Context context) {
		super(context);
		mContext = context;
		init();
		
        
	}
	public DrawView(Context context, AttributeSet attr)
	{
		super(context,attr);
		mContext = context;
		init();
	}
	
	
	private void init()
	{
		eiger = new Eiger();
		mRect = new Rect();
		
		
		//setBackgroundColor(Color.TRANSPARENT);
		mPath = new Path();
		mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(0xFFFFFF00);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(15.0f);
        
        
		linePaint = new Paint();
		linePaint.setAntiAlias(true);
		linePaint.setDither(true);
		linePaint.setColor(0x800000FF);
		linePaint.setStyle(Paint.Style.STROKE);
		linePaint.setStrokeJoin(Paint.Join.ROUND);
		linePaint.setStrokeCap(Paint.Cap.ROUND);
		linePaint.setStrokeWidth(1.0f);
        
        gestures = Gesture.loadAlphabet();
        
        gestures.add(Gesture.BACKSPACE);
        gestures.add(Gesture.SPACE);
        setGravity(Gravity.TOP | Gravity.LEFT);
        
	}
	@Override
    protected void onDraw(Canvas canvas) 
	{
		
        int count = getLineCount();
        Rect r = mRect;
        Paint paint = linePaint;

      
        for (int i = 0; i < count; i++) {
            int baseline = getLineBounds(i, r);
            canvas.drawLine(r.left, baseline + 1, r.right, baseline + 1, paint);
        }

       
        super.onDraw(canvas);
		
		
        canvas.drawPath(mPath, mPaint);
    }
	
	
    public void down(float x, float y)
	{
		
		mPath.moveTo(x, y);
		mX = x;
		mY = y;
	}
	
	public void move(float x, float y)
	{
		float dx = Math.abs(x - mX);
		float dy = Math.abs(y - mY);
		if(dx >=TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE)
		{
			mPath.quadTo(mX, mY, (x+mX)/2, (y+mY)/2);
			mX = x;
			mY = y;
		}
	}
	public void up()
	{
		
		mPath.lineTo(mX, mY);
		mPath.reset();
	}
	public void up2()
	{
		mPath.lineTo(mX, mY);
	}
	int count = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	
        float x = event.getX();
        float y = event.getY();
        Log.i("X: ", String.valueOf(x));
        Log.i("Y: ", String.valueOf(y));
        
    	point = new Point(x,y);
        points.add(point);
        
        
        String p = "";
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:            	
                down(x, y);
                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                move(x, y);
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                up();
                invalidate();
                
                
                p = eiger.engine(points);
                
               // p = c_finder.find_corner_points(points);
                Log.i("PATTERN", p);
                iterator(p);	
                
                /*
                ArrayList<Point> new_point =  c_finder.find_corner_points(points);
                Log.i("NEW SIZE: ", String.valueOf(new_point.size()));
                Point p1,p2,p3;
                
                p1 = new_point.get(0);
                p2 = new_point.get(1);
                p3 = new_point.get(2);
                double angle1 = Util.angleBetweenTwoPoints(p1, p2);
                double angle2 = Util.angleBetweenTwoPoints(p2, p3);
                
                Log.i("Angle1", String.valueOf(Util.direction(angle1)));
                Log.i("Angle2", String.valueOf(Util.direction(angle2)));
                */
                if(!points.isEmpty())points.clear();
                count = 0;
               
                return true;
            default:
            	return false;
        }
        
    
        
    }
    
  
    
    @SuppressWarnings("static-access")
	public void iterator(String p)
    {
    	if(pattern.matches(Gesture.ENTER.pattern, p)){
    		getText().insert(getSelectionEnd(), "\n");
			return;
    	}
    	
    	if(pattern.matches(Gesture.BACKSPACE.pattern, p))
		{
			if(getSelectionStart()>0)
			{
				getText().delete(getSelectionStart() - 1, getSelectionEnd() );
				return;	
			}
		}
    	if(pattern.matches(Gesture.SPACE.pattern, p))
		{
    		if(!is_shift_numeric_on){
    			getText().insert(getSelectionEnd(), " ");
    			return;
    		}
		}
    	if(pattern.matches(Gesture.Letter_I.pattern, p)){
    		getText().insert(getSelectionEnd() , Gesture.Letter_I.value);
    		return;
    	}
    	
    	
    	
    	if(pattern.matches(Gesture.COMMA.pattern, p)){
    		if(!is_shift_numeric_on){
    			getText().insert(getSelectionEnd() , Gesture.COMMA.value);
    			return;
    		}
    	}
    	if(pattern.matches(Gesture.POINT.pattern, p)){
    		getText().insert(getSelectionEnd() , Gesture.POINT.value);
    		return;
    	}
    	
    	if(pattern.matches(Gesture.SHIFT_PUNC.pattern, p)){
    		if(!is_shift_punc_on){
    			is_shift_punc_on = true;
    			is_shift_numeric_on = false;
    			Toast.makeText(mContext.getApplicationContext(), "PUNCTUATION MODE ON", 1000).show();
    			gestures = Gesture.loadPunctuation();
    			return;
    		}
    		is_shift_punc_on = false;
    		Toast.makeText(mContext.getApplicationContext(), "PUNCTUATION MODE OFF", 1000).show();
    		gestures = Gesture.loadAlphabet();
			return;
    		
    		
    	}
    	
    	if(pattern.matches(Gesture.SHIFT_NUMERIC.pattern, p)){
    		if(!is_shift_numeric_on){
    			is_shift_numeric_on = true;
    			is_shift_punc_on = false;
    			Toast.makeText(mContext.getApplicationContext(), "NUMERIC MODE ON", 1000).show();
    			gestures = Gesture.loadNumbers();
    			return;
    		}
    		is_shift_numeric_on = false;
    		Toast.makeText(mContext.getApplicationContext(), "NUMERIC MODE OFF", 1000).show();
    		gestures = Gesture.loadAlphabet();
			return;
    		
    		
    	}
    	if(pattern.matches(Gesture.CAPS_LOCK.pattern, p)){
    		if(!is_capslock_on){
    			
    			is_capslock_on = true;
    			Toast.makeText(mContext.getApplicationContext(), "CAPSLOCK ON", 1000).show();
    			return;
    		}
    		
    		is_capslock_on = false;
    		Toast.makeText(mContext.getApplicationContext(), "CAPSLOCK OFF", 1000).show();
    		return;
    	}
    	for(Gesture gesture : gestures)
    	{
    		
    		
    		if(Pattern.compile(gesture.pattern).matcher(p).matches())
    		{
    			
    			if(is_capslock_on){
    			getText().insert(getSelectionEnd() , gesture.value.toUpperCase());
    			break;
    			}else{
    				getText().insert(getSelectionEnd() , gesture.value);	
        			break;
    			}
    				
    		}
    		
    		
    	}
    }	
}


