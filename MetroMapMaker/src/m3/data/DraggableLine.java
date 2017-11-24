package m3.data;

import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
/**
 *
 * @author Dan Kim
 */
public class DraggableLine extends Path implements Draggable {
    
    public DraggableLine(){
        
    }
    
    @Override
    public m3State getStartingState() {
        return m3State.STARTING_LINE;
    }

    @Override
    public void start(int x, int y) {
    }

    @Override
    public void drag(int x, int y) {
    }
    
}
