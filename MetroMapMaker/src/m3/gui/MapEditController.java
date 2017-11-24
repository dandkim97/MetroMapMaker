package m3.gui;

import djf.AppTemplate;
import djf.ui.AppGUI;
import static java.awt.Color.white;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    
    public MapEditController(AppTemplate initApp, AppGUI initGUI){
        app = initApp;
        dataManager = (m3Data)app.getDataComponent();
        appGUI = initGUI;
    }
    
    public void doAddLine(){
        // open up a dialog box to get name and color of line
        // make a new line with coordinates and size already
        newLineDialog dialog = new newLineDialog();
        dialog.init(appGUI.getWindow());
        dialog.setTitle("Creating a New Line");
        dialog.showAndWait();
        
        this.setText(dialog.getText());
        
        Line l = new Line(100, 100, 500, 100);
        Text t = new Text(this.getText());
        Text t2 = new Text(this.getText());
        Circle c1 = new Circle(80, 100, 50);
        Circle c2 = new Circle(510, 100, 50);
        t.xProperty().bind(c1.centerXProperty() );
        t.yProperty().bind(c1.centerYProperty());
        t2.xProperty().bind(c2.centerXProperty());
        t2.yProperty().bind(c2.centerYProperty());
        
        dataManager.addShape(l);
        dataManager.addShape(t);
        dataManager.addShape(t2);
        
    }
    
    // HELPER FOR doAddLine    
    public void setText(String newText){
        text = newText;
    }
    
    public String getText(){
        return text;
    }
}
