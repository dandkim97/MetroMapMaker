package m3.gui;

import djf.AppTemplate;
import djf.ui.AppGUI;
import static java.awt.Color.white;
import java.util.Optional;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import m3.data.DraggableLine;
import m3.data.DraggableStation;
import m3.data.DraggableText;
import m3.data.m3Data;

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
    
    public void doAddLine(){
        // open up a dialog box to get name and color of line
        // make a new line with coordinates and size already
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
        
        dataManager.addShape(newLine);      
    }
      
    public void doGetLine(String name){
        DraggableLine line = dataManager.getLine(name);
   
        for(int i = 0; i < dataManager.getLines().size(); i++){
            // HIGHLIGHT THE SELECTED LINE ENABLE EDITING
            if(dataManager.getLines().get(i).getText().equals(name)){
                dataManager.highlightShape(line.getStartLabel());
                dataManager.highlightShape(line.getEndLabel());
                line.getStartLabel().draggable();
                line.getEndLabel().draggable();
                dataManager.setSelectedLine(line);
                this.setLineColor(line.getColor());
            }
            
            // UNHIGHLIGHT THE REST DISABLE EDITING
            else{
                dataManager.unhighlightShape(dataManager.getLines().get(i).getStartLabel());
                dataManager.unhighlightShape(dataManager.getLines().get(i).getEndLabel());
                dataManager.getLines().get(i).getStartLabel().disableDrag();
                dataManager.getLines().get(i).getEndLabel().disableDrag();
            }
        }
    }
    
    public void doRemoveLine(String name){
        dataManager.removeShape(dataManager.getLine(name));
    }
    
    public void doLineEdit(){
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
        
        
    }
    
    public void doAddStation(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Making a New Station");
        dialog.setContentText("Enter your station name:");
        
        Optional<String> result = dialog.showAndWait();
        DraggableText dtext = new DraggableText("    "+ result.get());
        dtext.disableDrag();
        this.setLineText(result.get());
        
        DraggableStation station = new DraggableStation(150, 150, 10);
        dtext.xProperty().bindBidirectional(station.centerXProperty());
        dtext.yProperty().bindBidirectional(station.centerYProperty());
        station.setName(result.get());
        dataManager.addStation(station, dtext);
        
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

    public void doRemoveStation(String name) {
        dataManager.removeStation(dataManager.getStation(name));
        dataManager.removeText(dataManager.getSText(name));
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
}
