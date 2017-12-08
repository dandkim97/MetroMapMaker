/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.transaction;

import javafx.scene.control.ComboBox;
import jtps.jTPS_Transaction;
import m3.data.DraggableLine;
import m3.data.DraggableStation;
import m3.data.DraggableText;
import m3.data.m3Data;

/**
 *
 * @author Dan Kim
 */
public class doAddStation_Transaction implements jTPS_Transaction{
    private m3Data data;
    private DraggableStation station;
    private DraggableText text;
    private ComboBox<String> combo;
    private String name;
    
    public doAddStation_Transaction(m3Data d, DraggableStation s, DraggableText t, ComboBox<String> cs, String n){
        data = d;
        station = s;
        text = t;
        combo = cs;
        name = n;
    }

    @Override
    public void doTransaction() {
        data.addStation(station, text);
        combo.getItems().add(name);
        combo.setValue(name);
        
    }

    @Override
    public void undoTransaction() {
        data.removeShape(station);
        data.removeShape(text);
        combo.getItems().remove(name);
        combo.setValue("");
    }
}
