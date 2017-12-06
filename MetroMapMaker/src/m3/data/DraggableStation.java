/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.data;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Circle;

/**
 *
 * @author Dan Kim
 */
public class DraggableStation extends Circle{

    private final DoubleProperty topRightX;
    private final DoubleProperty topRightY;
    private final DoubleProperty topLeftX;
    private final DoubleProperty topLeftY;
    private final DoubleProperty botRightX;
    private final DoubleProperty botRightY;
    private final DoubleProperty botLeftX;
    private final DoubleProperty botLeftY;
    int state;
    String name;
    
    public DraggableStation(double x, double y, double radius) {
        super(x, y, radius);
        topRightX = new SimpleDoubleProperty(x+15);
        topRightY = new SimpleDoubleProperty(y-15);
        topLeftX = new SimpleDoubleProperty();
        topLeftY = new SimpleDoubleProperty();
        botRightX = new SimpleDoubleProperty();
        botRightY = new SimpleDoubleProperty();
        botLeftX = new SimpleDoubleProperty();
        botLeftY = new SimpleDoubleProperty();
        
        setOnMouseDragged(e -> {topRightX.set(e.getX()+15);
                                topRightY.set(e.getY()-15);    
                                centerXProperty().set(e.getX());
                                centerYProperty().set(e.getY()); 
        });
        
    }
    
    public DoubleProperty getTopLeftX(){
        return topLeftX;
    }
    
    public DoubleProperty getTopLeftY(){
        return topLeftY;
    }
    
    public DoubleProperty getTopRightX(){
        return topRightX;
    }
    
    public DoubleProperty getTopRightY(){
        return topRightY;
    }
    
    public DoubleProperty getBotRightX(){
        return botRightX;
    }
    
    public DoubleProperty getBotRightY(){
        return botRightY;
    }
    
    public DoubleProperty getBotLeftX(){
        return botLeftX;
    }
    
    public DoubleProperty getBotLeftY(){
        return botLeftY;
    }
    
    public void draggable(){
        if(state == 0){
            setOnMouseDragged(e -> {topRightX.set(e.getX()+15);
                                        topRightY.set(e.getY()-15);    
                                        centerXProperty().set(e.getX());
                                        centerYProperty().set(e.getY()); });
        }
        if(state == 1){
            setOnMouseDragged(e -> {topLeftX.set(e.getX()-50);
                                    topLeftY.set(e.getY()-15);    
                                    centerXProperty().set(e.getX());
                                    centerYProperty().set(e.getY()); });
        }
        if(state == 2){
            setOnMouseDragged(e -> {botLeftX.set(e.getX()-50);
                                    botLeftY.set(e.getY()+25);    
                                    centerXProperty().set(e.getX());
                                    centerYProperty().set(e.getY()); });
        }
        if(state == 3){
            setOnMouseDragged(e -> {botRightX.set(e.getX()+15);
                                    botRightY.set(e.getY()+25);    
                                    centerXProperty().set(e.getX());
                                    centerYProperty().set(e.getY()); });
        }
        
    }
    
    public void disableDrag(){
        this.setOnMouseDragged(e ->{});
    }
    
    public void clickState(){
        if(state == 3){
            state = 0;
        }
        else
            state++;
    }
    
    public int getClicks(){
        return state;
    }
    
    public void setName(String newName){
        name = newName;
    }
    
    public String getName(){
        return name;
    }
    
}
