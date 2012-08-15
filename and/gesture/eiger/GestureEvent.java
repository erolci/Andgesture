package and.gesture.eiger;

import java.util.EventObject;

public class GestureEvent extends EventObject {
	
	
	
	private static final long serialVersionUID = 1L;
	Gesture gesture;
	public GestureEvent(Object source,Gesture gesture)
	{
		super(source);
		this.gesture = gesture;
	}
	
	public Gesture getGesture()
	{
		return gesture;
	}
}
