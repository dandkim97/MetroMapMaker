/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.data;

import javafx.scene.shape.Rectangle;

/**
 *
 * @author Dan Kim
 */
public class DraggableRectangle extends Rectangle {
    
    double startX;
    double startY;
    
    public DraggableRectangle() {
	setX(0.0);
	setY(0.0);
	setWidth(0.0);
	setHeight(0.0);
	setOpacity(1.0);
	startX = 0.0;
	startY = 0.0;
        setOnMouseDragged(e -> { setX(e.getX()); setY(e.getY()); });
    }
    
    public void start(int x, int y) {
	startX = x;
	startY = y;
    }
    
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
	xProperty().set(initX);
	yProperty().set(initY);
	widthProperty().set(initWidth);
	heightProperty().set(initHeight);
    }
}


