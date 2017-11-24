package m3.data;

/**
 * This interface represents a family of draggable shapes.
 * 
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public interface Draggable {
    public static final String LINE = "LINE";
    public static final String STATION = "STATION";
    public static final String TEXT = "TEXT";
    public static final String RECTANGLE = "RECTANGLE";
    public m3State getStartingState();
    public void start(int x, int y);
    public void drag(int x, int y);
}