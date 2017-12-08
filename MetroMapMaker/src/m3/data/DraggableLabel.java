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
    String text;
    String style = "Regular";
    double size = 12.0;
    boolean isBold = false;
    boolean isItalics = false;
    
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
    
    public void setFStyle(String s){
        style = s;
    }
    
    public void setFSize(double n){
        size = n;
    }
    
    public String getFStyle(){
        return style;
    }
    public double getFSize(){
        return size;
    }
    
    public boolean getBold(){
        return isBold;
    }
    
    public void toBold(){
        if(isBold == false)
            isBold = true;
        else
            isBold = false;
    }

    public boolean getItalics() {
        return isItalics;
    }

    public void toItalics() {
        if(isItalics == false)
            isItalics = true;
        else
            isItalics = false;
    }
    
    public String getTxt(){
        return text;
    }
}
