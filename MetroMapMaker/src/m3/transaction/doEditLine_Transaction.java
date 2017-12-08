/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.transaction;

import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import jtps.jTPS_Transaction;
import m3.data.DraggableLine;
import m3.data.m3Data;

/**
 *
 * @author Dan Kim
 */
public class doEditLine_Transaction implements jTPS_Transaction{

    private m3Data data;
    private DraggableLine line;
    private ComboBox<String> combo;
    private String name;
    private String oldN;
    private Color old;
    private Color newC;
    
    public doEditLine_Transaction(m3Data d, DraggableLine l, ComboBox<String> cs, String n, String os, Color o, Color nw){
        data = d;
        line = l;
        combo = cs;
        name = n;
        oldN = os;
        old = o;
        newC = nw;
    }
    
    @Override
    public void doTransaction() {
        data.getLine(oldN).setLineColor(newC);
        data.getLine(oldN).setStartLabel(name);
        data.getLine(oldN).setEndLabel(name);
    }

    @Override
    public void undoTransaction() {
        data.getLine(name).setLineColor(old);
        data.getLine(name).setStartLabel(oldN);
        data.getLine(name).setEndLabel(oldN);
    }
    
}
