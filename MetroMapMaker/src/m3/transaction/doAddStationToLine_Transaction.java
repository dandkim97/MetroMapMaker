/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.transaction;

import javafx.scene.shape.LineTo;
import jtps.jTPS_Transaction;
import m3.data.m3Data;

/**
 *
 * @author Dan Kim
 */
public class doAddStationToLine_Transaction implements jTPS_Transaction{
    private m3Data data;
    private String line;
    private LineTo newLine;
    private String station;
    private LineTo lineTo;
    private int index;
    
    
    public doAddStationToLine_Transaction(m3Data d, String l, LineTo nl, String s, LineTo lt, int i){
        data = d;
        line = l;
        newLine = nl;
        station = s;
        lineTo = lt;
        index = i;
    }

    @Override
    public void doTransaction() {
        data.getLine(line).getPath().getElements().remove(data.getLine(line).getLineTo());
        data.getLine(line).getPath().getElements().add(newLine);
        newLine.xProperty().bindBidirectional(data.getStation(station).centerXProperty());
        newLine.yProperty().bindBidirectional(data.getStation(station).centerYProperty());
        data.getLine(line).getPath().getElements().add(data.getLine(line).getLineTo());
        data.getLine(line).getLineList().add(station);
        
    }

    @Override
    public void undoTransaction() {
        data.getLine(line).getPath().getElements().remove(index);
        data.getLine(line).getLineList().remove(station);
    }
    
}
