/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.transaction;

import javafx.scene.control.ComboBox;
import jtps.jTPS_Transaction;
import m3.data.DraggableLine;
import m3.data.m3Data;

/**
 *
 * @author Dan Kim
 */
public class doRemoveLine_Transaction implements jTPS_Transaction{
    private m3Data data;
    private DraggableLine line;
    private ComboBox<String> combo;
    private String name;
    
    public doRemoveLine_Transaction(m3Data d, DraggableLine l, ComboBox<String> cs, String n){
        data = d;
        line = l;
        combo = cs;
        name = n;
    }

    @Override
    public void doTransaction() {
        data.removeShape(line);
        combo.getItems().remove(name);
        combo.setValue("");
    }

    @Override
    public void undoTransaction() {
        data.addShape(line);
        combo.getItems().add(name);
        combo.setValue(name);
    }
}
