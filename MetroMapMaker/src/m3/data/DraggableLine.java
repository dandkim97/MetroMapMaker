package m3.data;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
/**
 *
 * @author Dan Kim
 */
public class DraggableLine extends Group{
    
    DraggableStation start;
    DraggableStation end;
    LineTo line;
    MoveTo begin;
    Path path;
    
    DraggableText startText;
    DraggableText endText;
    
    Color lineColor;
    
    public DraggableLine(double startx, double starty, double endx, double endy) {
        path = new Path();
        begin = new MoveTo(startx, starty);
        line = new LineTo(endx, endy);
        start = new DraggableStation(startx, starty, 3);
        end = new DraggableStation(endx, endy, 3);
        startText = new DraggableText();
        endText = new DraggableText();
        
        path.getElements().add(begin);
        path.getElements().add(line);
        path.setStrokeWidth(5);
        init();
    }
    
    private void init() {
        begin.xProperty().bindBidirectional(start.centerXProperty());
        begin.yProperty().bindBidirectional(start.centerYProperty());
        line.xProperty().bindBidirectional(end.centerXProperty());
        line.yProperty().bindBidirectional(end.centerYProperty());
        startText.xProperty().bindBidirectional(start.centerXProperty());
        startText.yProperty().bindBidirectional(start.centerYProperty());
        endText.xProperty().bindBidirectional(end.centerXProperty());
        endText.yProperty().bindBidirectional(end.centerYProperty());
        ObservableList<Node> children = getChildren();
        children.add(path);
        children.add(startText);
        children.add(endText);
    }

    // GETTERS
    
    public DraggableText getStartLabel(){
        return startText;
    }
    
    public DraggableText getEndLabel(){
        return endText;
    }
    
    public Color getColor(){
        return lineColor;
    }
    
    public LineTo getLineTo(){
        return line;
    }
    
    public Path getPath(){
        return path;
    }
    
    public String getText(){
        return startText.getText();
    }
    
    public DraggableStation getStartStation(){
        return start;
    }
    
    public DraggableStation getEndStation(){
        return end;
    }
    
    //SETTERS
    public void setStartLabel(String text) {
        startText.setText(text);
    }
    
    public void setEndLabel(String text) {
        endText.setText(text);
    }
    
    public void setLineColor(Color color){
        path.setStroke(color);
        this.startText.setFill(color);
        this.endText.setFill(color);
        lineColor = color;
    }
    
    
}
