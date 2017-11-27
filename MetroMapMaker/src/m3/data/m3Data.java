/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.data;

import djf.AppTemplate;
import djf.components.AppDataComponent;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import m3.gui.m3Workspace;
import m3.m3App;

/**
 *
 * @author Dan Kim
 */
public class m3Data implements AppDataComponent{
    // FIRST THE THINGS THAT HAVE TO BE SAVED TO FILES
    
    // ALL THE SHAPES IN THE CANVAS
    ObservableList<Node> shapes;
    
    // BACKGROUND COLOR
    Color backgroundColor;
    
    // EDITING DATA
    
    // THIS IS THE SHAPE CURRENTLY BEING SIZED BUT NOT YET ADDED
    Shape newShape;

    // THIS IS THE SHAPE/LINE CURRENTLY SELECTED
    Shape selectedShape;
    DraggableLine selectedLine;
    
    // THIS IS FOR STORING ALL THE CREATED LINES
    ArrayList<DraggableLine> lineList = new ArrayList<>();
    
    // FOR COLOR
    Color lineColor;
    Color stationColor;
    double lineWidth;
    double stationWidth;
    
    // CURRENT STATE OF THE APP
    m3State state;

    // THIS IS A SHARED REFERENCE TO THE APPLICATION
    AppTemplate app;
    
    // USE THIS WHEN THE SHAPE IS SELECTED
    Effect highlightedEffect;
    
    public static final String WHITE_HEX = "#FFFFFF";
    public static final String BLACK_HEX = "#000000";
    public static final String YELLOW_HEX = "#EEEE00";
    public static final Paint DEFAULT_BACKGROUND_COLOR = Paint.valueOf(WHITE_HEX);
    public static final Paint HIGHLIGHTED_COLOR = Paint.valueOf(YELLOW_HEX);
    public static final int HIGHLIGHTED_STROKE_THICKNESS = 3;

    public m3Data(m3App initApp) {
        // KEEP THE APP FOR LATER
	app = initApp;

	// NO SHAPE STARTS OUT AS SELECTED
	newShape = null;
	selectedShape = null;

	
	// THIS IS FOR THE SELECTED SHAPE
	DropShadow dropShadowEffect = new DropShadow();
	dropShadowEffect.setOffsetX(0.0f);
	dropShadowEffect.setOffsetY(0.0f);
	dropShadowEffect.setSpread(1.0);
	dropShadowEffect.setColor(Color.YELLOW);
	dropShadowEffect.setBlurType(BlurType.GAUSSIAN);
	dropShadowEffect.setRadius(15);
	highlightedEffect = dropShadowEffect;
    }
            
    public ObservableList<Node> getShapes() {
	return shapes;
    }
    
    public ArrayList<DraggableLine> getLines(){
        return lineList;
    }
    
    public Color getLineColor() {
	return lineColor;
    }

    public Color getStationColor(){
        return stationColor;
    }
    
    public double getLineWidth() {
	return lineWidth;
    }
    
    public double getStationWidth(){
        return stationWidth;
    }
    
//    public void setCurrentFillColor(Color initColor) {
//	currentFillColor = initColor;
//	if (selectedShape != null)
//	    selectedShape.setFill(currentFillColor);
//    }
//    
//    public void setCurrentOutlineThickness(int initBorderWidth) {
//	currentBorderWidth = initBorderWidth;
//	if (selectedShape != null) {
//	    selectedShape.setStrokeWidth(initBorderWidth);
//	}
//    }
    
    public void setShapes(ObservableList<Node> initShapes) {
	shapes = initShapes;
    }
    
    public void highlightShape(Shape shape){
        shape.setEffect(highlightedEffect);
    }
    
    public void unhighlightShape(Shape shape) {
	shape.setEffect(null);
    }
    
    public void addShape(Shape shapeToAdd){
        shapes.add(shapeToAdd);
    }
    
    public void resetData() {
    }

    public void setBackgroundColor(Color initBackgroundColor) {
        backgroundColor = initBackgroundColor;
	m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
	Pane canvas = workspace.getCanvas();
	BackgroundFill fill = new BackgroundFill(backgroundColor, null, null);
	Background background = new Background(fill);
	canvas.setBackground(background);
    }

    public void addShape(DraggableLine newLine) {
        shapes.add(newLine);
        lineList.add(newLine);
    }
    
    public void removeShape(Shape shapeToRemove) {
	shapes.remove(shapeToRemove);
    }
    
    public void removeShape(DraggableLine lineToRemove) {
	shapes.remove(lineToRemove);
        lineList.remove(lineToRemove);
    }
    
    public void setSelectedLine(DraggableLine lineSelected){
        selectedLine = lineSelected;
    }
    
    public DraggableLine getSelectedLine(){
        return selectedLine;
    }
    
    // TO GET DRAGGABLE LINE FROM METROLINES COMBOBOX
    public DraggableLine getLine(String name){
        for(int i = 0; i < lineList.size(); i++){
            if (lineList.get(i).getText().equals(name))
                return lineList.get(i);
            
        }
        return null;      
    }
}
