/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.data;

import javafx.scene.text.Text;

/**
 *
 * @author Dan Kim
 */
public class DraggableLabel extends Text {
    
    public DraggableLabel() {
        this("");
    }
    
    public DraggableLabel(String text) {
        super(text);
            setOnMouseDragged(e -> { setX(e.getX()); setY(e.getY()); });
    }
    
    public void draggable(){
        this.setOnMouseDragged(e -> { setX(e.getX()); setY(e.getY()); });
    }
    
    public void disableDrag(){
        this.setOnMouseDragged(e ->{});
    }
}
