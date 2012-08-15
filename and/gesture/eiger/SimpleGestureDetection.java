package and.gesture.eiger;

public final class SimpleGestureDetection
{
  private final boolean is_single_vertex(String pattern)
  {
    return (pattern.equals("L") || pattern.equals("R") || pattern.equals("T") || pattern.equals("B"));

  }
  
}
