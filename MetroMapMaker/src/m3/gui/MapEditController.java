package m3.gui;

import djf.AppTemplate;
import djf.ui.AppGUI;
import static java.awt.Color.white;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jtps.JTPS;
import m3.data.DraggableLabel;
import m3.data.DraggableLine;
import m3.data.DraggableRectangle;
import m3.data.DraggableStation;
import m3.data.DraggableText;
import m3.data.m3Data;
import m3.transaction.*;

/**
 *
 * @author Dan Kim
 */
public class MapEditController {
    AppTemplate app;
    AppGUI appGUI;
    m3Data dataManager;
    
    String text;
    Color lineColor;
    String image = "";
    
    public MapEditController(AppTemplate initApp, AppGUI initGUI){
        app = initApp;
        dataManager = (m3Data)app.getDataComponent();
        appGUI = initGUI;
    }
      
    public void setLineText(String newText){
        text = newText;
    }
    
    public String getLineText(){
        return text;
    }
    
    public void setLineColor(Color newColor){
        lineColor = newColor;
    }
    
    public Color getLineColor(){
        return lineColor;
    }
    
    public void doLineThickness(double value, String line){
        dataManager.getLine(line).getPath().setStrokeWidth(value);
    }
    
    public void doStationThickness(double value, String station){
        dataManager.getStation(station).setRadius(value);
    }
    
    public void doAddStationToLineTransaction(String l, LineTo nl, String s, LineTo lt, int i){
        doAddStationToLine_Transaction trans = new doAddStationToLine_Transaction(dataManager, l, nl, s, lt, i);
        JTPS jtps = app.getJTPS();
        jtps.addTransaction(trans);
    }
    
    public void doAddStationToLine(String station, String line){
        // CUT INITIAL LINE
        dataManager.getLine(line).getPath().getElements().remove(dataManager.getLine(line).getLineTo());
        
        // ADD NEW LINE TO STATION ADDED
        LineTo newLine = new LineTo(dataManager.getStation(station).getCenterX(), 
                                            dataManager.getStation(station).getCenterY());
        dataManager.getLine(line).getPath().getElements().add(newLine);
        newLine.xProperty().bindBidirectional(dataManager.getStation(station).centerXProperty());
        newLine.yProperty().bindBidirectional(dataManager.getStation(station).centerYProperty());
        
//        int index = 1; 
//        for(int i = 1; i < dataManager.getLine(line).getPath().getElements().size();i++){
//            if(dataManager.getLine(line).getPath().getElements().get(i) instanceof LineTo){
//                LineTo newerLine = (LineTo)dataManager.getLine(line).getPath().getElements().get(i);
//                if(newerLine.getX() == dataManager.getStation(station).getCenterX() &&
//                        newerLine.getY() == dataManager.getStation(station).getCenterY()){
//                    index = i;
//                }
//            }
//        }
        // PASTE CUT LINE
        dataManager.getLine(line).getPath().getElements().add(dataManager.getLine(line).getLineTo());
        
        // ADD TO LIST OF STATIONS IN LINE
        dataManager.getLine(line).getLineList().add(station);
        
//        doAddStationToLineTransaction(line, newLine, station, dataManager.getLine(line).getLineTo(), index);
        
    }
    
    public void doRemoveStationToLine(String station, String line){
        for(int i = 1; i < dataManager.getLine(line).getPath().getElements().size();i++){
            if(dataManager.getLine(line).getPath().getElements().get(i) instanceof LineTo){
                LineTo newLine = (LineTo)dataManager.getLine(line).getPath().getElements().get(i);
                if(newLine.getX() == dataManager.getStation(station).getCenterX() &&
                        newLine.getY() == dataManager.getStation(station).getCenterY()){
                    dataManager.getLine(line).getPath().getElements().remove(i);
                    dataManager.getLine(line).getLineList().remove(station);
                }
            }
        }
    }
    
    public void doAddLineTransaction(DraggableLine l, ComboBox<String> cs, String n){
        doAddLine_Transaction trans = new doAddLine_Transaction(dataManager, l, cs, n);
        JTPS jtps = app.getJTPS();
        jtps.addTransaction(trans);
    }
            
    public void doAddLine(){
        // open up a dialog box to get name and color of line
        // make a new line with coordinates and size already
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        newLineDialog dialog = new newLineDialog();
        dialog.init(appGUI.getWindow());
        dialog.setTitle("Creating a New Line");
        dialog.showAndWait();
        
        this.setLineText(dialog.getText());
        this.setLineColor(dialog.getColor());
        
        DraggableLine newLine = new DraggableLine(100, 100, 500, 100);
        newLine.setLineColor(this.getLineColor());
        newLine.setEndLabel(this.getLineText());
        newLine.setStartLabel(this.getLineText());
        
        doAddLineTransaction(newLine, workspace.getMetroLines(), this.getLineText());
//        dataManager.addShape(newLine);
    }
      
    public void doGetLine(String name){
        DraggableLine line = dataManager.getLine(name);
   
        for(int i = 0; i < dataManager.getLines().size(); i++){
            // HIGHLIGHT THE SELECTED LINE ENABLE EDITING
            if(dataManager.getLines().get(i).getText().equals(name)){
//                dataManager.highlightShape(line.getStartLabel());
//                dataManager.highlightShape(line.getEndLabel());
                line.getStartLabel().draggable();
                line.getEndLabel().draggable();
                dataManager.setSelectedLine(line);
                this.setLineColor(line.getColor());
            }
            
            // UNHIGHLIGHT THE REST DISABLE EDITING
            else{
//                dataManager.unhighlightShape(dataManager.getLines().get(i).getStartLabel());
//                dataManager.unhighlightShape(dataManager.getLines().get(i).getEndLabel());
                dataManager.getLines().get(i).getStartLabel().disableDrag();
                dataManager.getLines().get(i).getEndLabel().disableDrag();
            }
        }
    }
    
    public void doRemoveLineTransaction(DraggableLine l, ComboBox<String> cs, String n){
        doRemoveLine_Transaction trans = new doRemoveLine_Transaction(dataManager, l, cs, n);
        JTPS jtps = app.getJTPS();
        jtps.addTransaction(trans);
    }
    
    public void doRemoveLine(String name){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        
        doRemoveLineTransaction(dataManager.getLine(name), workspace.getMetroLines(), name);
//        dataManager.removeShape(dataManager.getLine(name));
    }
    
    public void doLineList(String name){
        String message = "";
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Metro Line List");
        alert.setHeaderText("List of Stations for Line " + name);
        for(int i = 0; i < dataManager.getLine(name).getLineList().size(); i++){
            message += dataManager.getLine(name).getLineList().get(i) + "\n";
        }
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public void doEditLine_Transaction(DraggableLine l, ComboBox<String> cs, String n, String os, Color o, Color nw){
        doEditLine_Transaction trans = new doEditLine_Transaction(dataManager, l, cs, n, os, o, nw);
        JTPS jtps = app.getJTPS();
        jtps.addTransaction(trans);
    }
    
    public void doLineEdit(){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        newLineDialog dialog = new newLineDialog();
        dialog.setInitText(dataManager.getSelectedLine().getText());
        dialog.init(appGUI.getWindow());
        dialog.setTitle("Editing a Line");
        
        dialog.showAndWait();
        
        if(dialog.isColorSelected())
            this.setLineColor(dialog.getColor());
        this.setLineText(dialog.getText());
        
        dataManager.getSelectedLine().setLineColor(this.getLineColor());
        dataManager.getSelectedLine().setEndLabel(this.getLineText());
        dataManager.getSelectedLine().setStartLabel(this.getLineText());
        
//        doEditLine_Transaction(dataManager.getSelectedLine(), workspace.getMetroLines(), this.getLineText(),
//                dataManager.getSelectedLine().getText(), dataManager.getSelectedLine().getColor(), this.getLineColor());
        
        
    }
    
    public void doAddStation_Transaction(DraggableStation s, DraggableText t, ComboBox<String> cs, String n){
        doAddStation_Transaction trans = new doAddStation_Transaction(dataManager, s, t, cs, n);
        JTPS jtps = app.getJTPS();
        jtps.addTransaction(trans);
    }
    
    public void doAddStation(){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Making a New Station");
        dialog.setContentText("Enter your station name:");
        
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent())
        {
            DraggableText dtext = new DraggableText(result.get());
            dtext.disableDrag();
            this.setLineText(result.get());

            DraggableStation station = new DraggableStation(150, 150, 10);
            dtext.xProperty().bindBidirectional(station.getTopRightX());
            dtext.yProperty().bindBidirectional(station.getTopRightY());
            station.setName(result.get());
            doAddStation_Transaction(station, dtext, workspace.getMetroStations(), result.get() );
//            dataManager.addStation(station, dtext);
        }
        
    }
    
    public void doGetStation(String name){
        DraggableStation station = dataManager.getStation(name);
        
        
        for(int i = 0; i < dataManager.getStations().size(); i++){
            // HIGHLIGHT THE SELECTED LINE ENABLE EDITING
            if(dataManager.getStations().get(i).getName().equals(name)){
                station.draggable();
                dataManager.setSelectedStation(station);
//                this.setStationColor(station.getColor());
            }
            
            // UNHIGHLIGHT THE REST DISABLE EDITING
            else{
                dataManager.getStations().get(i).disableDrag();

            }
        }
    }

    public void doRemoveStation_Transaction(DraggableStation s, DraggableText t, ComboBox<String> cs, String n){
        doRemoveStation_Transaction trans = new doRemoveStation_Transaction(dataManager, s, t, cs, n);
        JTPS jtps = app.getJTPS();
        jtps.addTransaction(trans);
    }
    
    public void doRemoveStation(String name) {
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        doRemoveStation_Transaction(dataManager.getStation(name), dataManager.getSText(name), workspace.getMetroStations(), name);
//        dataManager.removeStation(dataManager.getStation(name));
//        dataManager.removeText(dataManager.getSText(name));
    }
    
    public void doMoveStationLabel(String name){
        dataManager.getStation(name).clickState();
        double centerX = dataManager.getStation(name).getCenterX();
        double centerY = dataManager.getStation(name).getCenterY();
        dataManager.getStation(name).draggable();
        
        // TOP RIGHT
        if(dataManager.getStation(name).getClicks() == 0){
            dataManager.getStation(name).getTopRightX().set(centerX+15);
            dataManager.getStation(name).getTopRightY().set(centerY-15);
            dataManager.getSText(name).xProperty().bindBidirectional
                    (dataManager.getStation(name).getTopRightX());
            dataManager.getSText(name).yProperty().bindBidirectional
                    (dataManager.getStation(name).getTopRightY());
        }
        // TOP LEFT
        if(dataManager.getStation(name).getClicks() == 1){
            dataManager.getStation(name).getTopLeftX().set(centerX-50);
            dataManager.getStation(name).getTopLeftY().set(centerY-15);
            dataManager.getSText(name).xProperty().bindBidirectional
                    (dataManager.getStation(name).getTopLeftX());
            dataManager.getSText(name).yProperty().bindBidirectional
                    (dataManager.getStation(name).getTopLeftY());
        }
        // BOT LEFT
        if(dataManager.getStation(name).getClicks() == 2){
            dataManager.getStation(name).getBotLeftX().set(centerX-50);
            dataManager.getStation(name).getBotLeftY().set(centerY+25);
            dataManager.getSText(name).xProperty().bindBidirectional
                    (dataManager.getStation(name).getBotLeftX());
            dataManager.getSText(name).yProperty().bindBidirectional
                    (dataManager.getStation(name).getBotLeftY());
        }
        // BOT RIGHT
        if(dataManager.getStation(name).getClicks() == 3){
            dataManager.getStation(name).getBotRightX().set(centerX+15);
            dataManager.getStation(name).getBotRightY().set(centerY+25);
            dataManager.getSText(name).xProperty().bindBidirectional
                    (dataManager.getStation(name).getBotRightX());
            dataManager.getSText(name).yProperty().bindBidirectional
                    (dataManager.getStation(name).getBotRightY());
        }
        
    }
    
    public void doRotateStationLabel(String name){
        if(dataManager.getSText(name).getRotate() == 0)
            dataManager.getSText(name).setRotate(90);
        else
            dataManager.getSText(name).setRotate(0);
    }
    
    public void doColorStation(String name, Color color){
        dataManager.getStation(name).setFill(color);
    }
    
    public void doSelectBackgroundColor(){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
	Color selectedColor = workspace.getBackgroundColorPicker().getValue();
	if (selectedColor != null) {
            if(image.equals("")){
                dataManager.setBackgroundColor(selectedColor);
                app.getGUI().updateToolbarControls(false);
            }
            else{
                dataManager.setBackgroundWithImage(selectedColor, this.getImage());
            }
	}
    }
    
    public void doImageBackground(){
        image = app.getGUI().getFileController().promptToOpenPic();
        this.setImage(image);
        if(!image.equals("")){
            m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
            workspace.getCanvas().setStyle("-fx-background-image: url('" +image+ "');"
                                          +  "-fx-background-position: center center; " 
                                          +  "-fx-background-repeat: stretch;");
        }
    }
    
    public void setImage(String newImage){
        image = newImage;
    }
    
    public String getImage(){
        return image;
    }
    
    public void doAddImage(){
        // GET THE PICTURE  
        String str = "";
        str = app.getGUI().getFileController().promptToOpenPic();
        Image image1 = new Image(str);
        ImagePattern imagePattern = new ImagePattern(image1);

        // FILL IT IN A RECTANGLE
        DraggableRectangle d = new DraggableRectangle();
        d.setFill(imagePattern);
        d.setLocationAndSize(5, 5, image1.getWidth(), image1.getHeight());

//        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        dataManager.addShape(d);
    }
    
    public void doAddLabel(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Making a New Label");
        dialog.setContentText("Enter your label name:");
        
        Optional<String> result = dialog.showAndWait();
        if(!result.isPresent())
        {}
        else{
            DraggableLabel label = new DraggableLabel(result.get());
            label.setX(100);
            label.setY(100);
            System.out.println(label.getFont().toString());
            dataManager.addShape(label);
        }
    }
    
    public void doRemoveElement(){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        Shape newShape = dataManager.getSelectedShape();
        if (newShape instanceof DraggableRectangle)
            dataManager.removeShape(newShape);
        else if (newShape instanceof DraggableLabel)
            dataManager.removeShape(newShape);    
    }
    
    public void doBold(){
        if (dataManager.getSelectedShape() instanceof DraggableText){          
            DraggableText t = (DraggableText)(dataManager.getSelectedShape());
            if(t.getBold() == false && t.getItalics() == false){
                t.setFont(Font.font(t.getFStyle(), 
                    FontWeight.BOLD, t.getFSize()));
                t.toBold();
            }
            else if(t.getBold() == true && t.getItalics() == false){
                t.setFont(Font.font(t.getFStyle(), 
                    FontWeight.NORMAL, t.getFSize()));
                t.toBold();
            }
            else if(t.getBold() == true && t.getItalics() == true){
                t.setFont(Font.font(t.getFStyle(), 
                    FontWeight.NORMAL, FontPosture.ITALIC, t.getFSize()));
                t.toBold();
            }
            else{
                t.setFont(Font.font(t.getFStyle(), 
                    FontWeight.BOLD, FontPosture.ITALIC, t.getFSize()));
                t.toBold();
            }
        }
        else if (dataManager.getSelectedShape() instanceof DraggableLabel){          
            DraggableLabel t = (DraggableLabel)(dataManager.getSelectedShape());
            if(t.getBold() == false && t.getItalics() == false){
                t.setFont(Font.font(t.getFStyle(), 
                    FontWeight.BOLD, t.getFSize()));
                t.toBold();
            }
            else if(t.getBold() == true && t.getItalics() == false){
                t.setFont(Font.font(t.getFStyle(), 
                    FontWeight.NORMAL, t.getFSize()));
                t.toBold();
            }
            else if(t.getBold() == true && t.getItalics() == true){
                t.setFont(Font.font(t.getFStyle(), 
                    FontWeight.NORMAL, FontPosture.ITALIC, t.getFSize()));
                t.toBold();
            }
            else{
                t.setFont(Font.font(t.getFStyle(), 
                    FontWeight.BOLD, FontPosture.ITALIC, t.getFSize()));
                t.toBold();
            }
        }
    }
    
    public void doItalic(){
        if (dataManager.getSelectedShape() instanceof DraggableText){          
            DraggableText t = (DraggableText)(dataManager.getSelectedShape());
            if(t.getItalics() == false && t.getBold() == false){
                t.setFont(Font.font(t.getFStyle(), 
                    FontPosture.ITALIC, t.getFSize()));
                t.toItalics();
            }
            else if(t.getItalics() == true && t.getBold() == false){
                t.setFont(Font.font(t.getFStyle(), 
                    FontWeight.NORMAL, t.getFSize()));
                t.toItalics();
            }
            else if(t.getItalics() == false && t.getBold() == true){
                t.setFont(Font.font(t.getFStyle(), 
                    FontWeight.BOLD, FontPosture.ITALIC, t.getFSize()));
                t.toItalics();
            }
            else{
                t.setFont(Font.font(t.getFStyle(), 
                    FontWeight.BOLD, t.getFSize()));
                t.toItalics();
            }
        }
        else if (dataManager.getSelectedShape() instanceof DraggableLabel){
            DraggableLabel t = (DraggableLabel)(dataManager.getSelectedShape());
            if(t.getItalics() == false && t.getBold() == false){
                t.setFont(Font.font(t.getFStyle(), 
                    FontPosture.ITALIC, t.getFSize()));
                t.toItalics();
            }
            else if(t.getItalics() == true && t.getBold() == false){
                t.setFont(Font.font(t.getFStyle(), 
                    FontWeight.NORMAL, t.getFSize()));
                t.toItalics();
            }
            else if(t.getItalics() == false && t.getBold() == true){
                t.setFont(Font.font(t.getFStyle(), 
                    FontWeight.BOLD, FontPosture.ITALIC, t.getFSize()));
                t.toItalics();
            }
            else{
                t.setFont(Font.font(t.getFStyle(), 
                    FontWeight.BOLD, t.getFSize()));
                t.toItalics();
            }
        }
    }
    
    public void doStyle(String s){
        if (dataManager.getSelectedShape() instanceof DraggableText){
            DraggableText t = (DraggableText)(dataManager.getSelectedShape());
            t.setFStyle(s);
            if(t.getItalics() == false && t.getBold() == false){
                t.setFont(Font.font(t.getFStyle(), t.getFSize()));
            }
            else if(t.getItalics() == true && t.getBold() == false){
                t.setFont(Font.font(t.getFStyle(), 
                    FontPosture.ITALIC, t.getFSize()));
            }
            else if(t.getItalics() == false && t.getBold() == true){
                t.setFont(Font.font(t.getFStyle(), 
                    FontWeight.BOLD, t.getFSize()));
            }
            else{
                t.setFont(Font.font(t.getFStyle(), FontWeight.BOLD,
                    FontPosture.ITALIC, t.getFSize()));
            }
        }
        else if (dataManager.getSelectedShape() instanceof DraggableLabel){
            DraggableLabel t = (DraggableLabel)(dataManager.getSelectedShape());
            t.setFStyle(s);
            if(t.getItalics() == false && t.getBold() == false){
                t.setFont(Font.font(t.getFStyle(), t.getFSize()));
            }
            else if(t.getItalics() == true && t.getBold() == false){
                t.setFont(Font.font(t.getFStyle(), 
                    FontPosture.ITALIC, t.getFSize()));
            }
            else if(t.getItalics() == false && t.getBold() == true){
                t.setFont(Font.font(t.getFStyle(), 
                    FontWeight.BOLD, t.getFSize()));
            }
            else{
                t.setFont(Font.font(t.getFStyle(), FontWeight.BOLD,
                    FontPosture.ITALIC, t.getFSize()));
            }
        }
    }
    
    public void doSize(double n){
        if (dataManager.getSelectedShape() instanceof DraggableText){
            DraggableText t = (DraggableText)(dataManager.getSelectedShape());
            t.setFSize(n);
            if(t.getItalics() == false && t.getBold() == false){
                t.setFont(Font.font(t.getFStyle(), t.getFSize()));
            }
            else if(t.getItalics() == true && t.getBold() == false){
                t.setFont(Font.font(t.getFStyle(), 
                    FontPosture.ITALIC, t.getFSize()));
            }
            else if(t.getItalics() == false && t.getBold() == true){
                t.setFont(Font.font(t.getFStyle(), 
                    FontWeight.BOLD, t.getFSize()));
            }
            else{
                t.setFont(Font.font(t.getFStyle(), FontWeight.BOLD, 
                    FontPosture.ITALIC, t.getFSize()));
            }
        }
        else if (dataManager.getSelectedShape() instanceof DraggableLabel){
            DraggableLabel t = (DraggableLabel)(dataManager.getSelectedShape());
            t.setFSize(n);
            if(t.getItalics() == false && t.getBold() == false){
                t.setFont(Font.font(t.getFStyle(), t.getFSize()));
            }
            else if(t.getItalics() == true && t.getBold() == false){
                t.setFont(Font.font(t.getFStyle(), 
                    FontPosture.ITALIC, t.getFSize()));
            }
            else if(t.getItalics() == false && t.getBold() == true){
                t.setFont(Font.font(t.getFStyle(), 
                    FontWeight.BOLD, t.getFSize()));
            }
            else{
                t.setFont(Font.font(t.getFStyle(), FontWeight.BOLD, 
                    FontPosture.ITALIC, t.getFSize()));
            }
        }     
    }
    
    public void doColorText(Color color){
        if(dataManager.getSelectedShape() instanceof DraggableText){
            DraggableText text = (DraggableText)dataManager.getSelectedShape();
            text.setFill(color);
        }
        else if(dataManager.getSelectedShape() instanceof DraggableLabel){
            DraggableLabel text = (DraggableLabel)dataManager.getSelectedShape();
            text.setFill(color);
        }
    }
    
    public void doZoomIn(){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        if(workspace.getCanvas().getScaleX() >= 2.99){}
        else{
            workspace.getCanvas().setScaleX(workspace.getCanvas().getScaleX()*1.1);
            workspace.getCanvas().setScaleY(workspace.getCanvas().getScaleY()*1.001);
        }
        if(workspace.getCanvas().getScaleX() > 1){
        
            appGUI.getAppPane().setOnKeyPressed(ke->{
                if(ke.getCode() == KeyCode.A){
                    workspace.getCanvas().setTranslateX
                        (workspace.getCanvas().getTranslateX() - 0.1 * workspace.getCanvas().getWidth());
                }
                else if(ke.getCode() == KeyCode.D){
                    workspace.getCanvas().setTranslateX
                        (workspace.getCanvas().getTranslateX() + 0.1 *workspace.getCanvas().getWidth());
                }
                else if(ke.getCode() == KeyCode.W){
                    workspace.getCanvas().setTranslateY
                        (workspace.getCanvas().getTranslateY() - 0.1 *workspace.getCanvas().getHeight());
                }
                else if(ke.getCode() == KeyCode.S){
                    workspace.getCanvas().setTranslateY
                        (workspace.getCanvas().getTranslateY() + 0.1 *workspace.getCanvas().getHeight());
                }
                else{}
            });
              
        }
    }
    
    public void doZoomOut(){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        if(workspace.getCanvas().getScaleX() < 0.2){}
        else{
            workspace.getCanvas().setScaleX(workspace.getCanvas().getScaleX()/1.1);
            workspace.getCanvas().setScaleY(workspace.getCanvas().getScaleY()/1.001);
        }
        if(workspace.getCanvas().getScaleX() <= 1){
            appGUI.getAppPane().setOnKeyPressed(ke->{});
        }
    }
    
    public void doInc(){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        
        workspace.getCanvas().resize(workspace.getCanvas().getWidth()+(.1*workspace.getCanvas().getWidth()), 
                workspace.getCanvas().getHeight()+(.1*workspace.getCanvas().getHeight()));       
        workspace.getCanvas().setMaxSize(workspace.getCanvas().getWidth(), workspace.getCanvas().getHeight() );
    }
    
    public void doDec(){
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();

        workspace.getCanvas().resize(workspace.getCanvas().getWidth()-(.1*workspace.getCanvas().getWidth()), 
                workspace.getCanvas().getHeight()-(.1*workspace.getCanvas().getHeight()));     
        workspace.getCanvas().setMaxSize(workspace.getCanvas().getWidth(), workspace.getCanvas().getHeight() );
    }
}
