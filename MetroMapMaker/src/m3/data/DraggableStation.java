/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.data;

import javafx.scene.shape.Circle;

/**
 *
 * @author Dan Kim
 */
public class DraggableStation extends Circle{

    String name;
    
    public DraggableStation(double x, double y, double radius) {
        super(x, y, radius);
        setOnMouseDragged(e -> { centerXProperty().set(e.getX());
                                 centerYProperty().set(e.getY()); });
    }
    
    public void draggable(){
        this.setOnMouseDragged(e -> { centerXProperty().set(e.getX());
                                      centerYProperty().set(e.getY()); });
    }
    
    public void disableDrag(){
        this.setOnMouseDragged(e ->{});
    }
    
    public void setName(String newName){
        name = newName;
    }
    
    public String getName(){
        return name;
    }
    
}
