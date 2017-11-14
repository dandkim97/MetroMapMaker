/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.data;

import djf.AppTemplate;
import djf.components.AppDataComponent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import m3.gui.m3Workspace;
import m3.m3App;

/**
 *
 * @author Dan Kim
 */
public class m3Data implements AppDataComponent{

    Color backgroundColor;
    
    AppTemplate app;

    public m3Data(m3App aThis) {
        
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
    
}
