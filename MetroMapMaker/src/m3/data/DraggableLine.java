package m3.data;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
/**
 *
 * @author Dan Kim
 */
public class DraggableLine extends Group{
    
    DraggableStation start;
    DraggableStation end;
    Line line;
    
    Text startText;
    Text endText;
    
    public DraggableLine(double startx, double starty, double endx, double endy) {
        line = new Line(startx, starty, endx, endy);
        start = new DraggableStation(startx, starty, 3);
        end = new DraggableStation(endx, endy, 3);
        startText = new DraggableText();
        endText = new DraggableText();
        init();
    }
    
    private void init() {
        line.startXProperty().bindBidirectional(start.centerXProperty());
        line.startYProperty().bindBidirectional(start.centerYProperty());
        line.endXProperty().bindBidirectional(end.centerXProperty());
        line.endYProperty().bindBidirectional(end.centerYProperty());
        startText.xProperty().bindBidirectional(start.centerXProperty());
        startText.yProperty().bindBidirectional(start.centerYProperty());
        endText.xProperty().bindBidirectional(end.centerXProperty());
        endText.yProperty().bindBidirectional(end.centerYProperty());
        ObservableList<Node> children = getChildren();
        children.add(line);
        children.add(startText);
        children.add(endText);
    }
    
    public void setStartLabel(String text) {
        startText.setText(text);
    }
    
    public void setEndLabel(String text) {
        endText.setText(text);
    }
    
    public void setLineColor(Color color){
        this.line.setStroke(color);
        this.startText.setFill(color);
        this.endText.setFill(color);
    }
    
    public Line getLine(){
        return line;
    }
    
}
