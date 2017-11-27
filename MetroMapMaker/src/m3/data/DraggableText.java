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
public class DraggableText extends Text{

    public DraggableText() {
        this("");
    }
    
    public DraggableText(String text) {
        super(text);
        setOnMouseDragged(e -> { setX(e.getX()); setY(e.getY()); });
    }
}
