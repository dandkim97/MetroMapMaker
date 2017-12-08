/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.gui;

import djf.AppTemplate;
import java.util.Optional;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.shape.Shape;
import m3.data.Draggable;
import m3.data.DraggableText;
import m3.data.m3Data;
import m3.data.m3State;
import static m3.data.m3State.*;

/**
 *
 * @author Dan Kim
 */
public class CanvasController {
    AppTemplate app;

    public CanvasController(AppTemplate initApp) {
        app = initApp;
    }
    /**
     * Respond to mouse presses on the rendering surface, which we call canvas,
     * but is actually a Pane.
     */
    public void processCanvasDoubleMousePress(int x, int y){
//        m3Data dataManager = (m3Data) app.getDataComponent();
//        m3Workspace workspace = (m3Workspace) app.getWorkspaceComponent();
//        if (dataManager.isInState(SELECTING_SHAPE)) {
//            // SELECT THE TOP SHAPE + CHECK IF IT IS TEXT SHAPE
//            Shape shape = dataManager.selectTopShape(x, y);
//
//            if(dataManager.getSelectedShape() instanceof DraggableText){
//                // GET THE TYPED TEXT AND EITHER CHANGE OR DON'T CHANGE IT
//                TextInputDialog dialog = new TextInputDialog("");
//                if(app.getName().equals("app_properties_EN.xml")){
//                    dialog.setTitle("Text Input Dialog");
//                    dialog.setHeaderText("Text Box Input");
//                }
//                else{
//                    dialog.setTitle("Boîte de dialogue de saisie de texte");
//                    dialog.setHeaderText("Entrée de boîte de texte");
//                }
//                Optional<String> result = dialog.showAndWait();
//                String text = result.get()+"";
//                
//                DraggableText selectText = (DraggableText)(dataManager.getSelectedShape());
//                DraggableText t = new DraggableText(x, y, text);
//                
//                t.setFont(selectText.getFont());
//                dataManager.addShape(t);
//                dataManager.highlightShape(t);
//                dataManager.removeShape(shape);
//                
//            }
//        }    
//        workspace.reloadWorkspace(dataManager);
    }
    
    public void processCanvasMousePress(int x, int y) {
        m3Data dataManager = (m3Data) app.getDataComponent();
//        if (dataManager.isInState(SELECTING_SHAPE)) {
            // SELECT THE TOP SHAPE
            Shape shape = dataManager.selectTopShape(x, y);
            Scene scene = app.getGUI().getPrimaryScene();

            // AND START DRAGGING IT
            if (shape != null) {
                scene.setCursor(Cursor.MOVE);
                dataManager.setState(m3State.DRAGGING_SHAPE);
                app.getGUI().updateToolbarControls(false);
            } else {
                scene.setCursor(Cursor.DEFAULT);
                dataManager.setState(DRAGGING_NOTHING);
                app.getWorkspaceComponent().reloadWorkspace(dataManager);
            }
//        } else if (dataManager.isInState(m3State.STARTING_RECTANGLE)) {
//            dataManager.startNewRectangle(x, y);
//        } else if (dataManager.isInState(m3State.STARTING_ELLIPSE)) {
//            dataManager.startNewEllipse(x, y);
//        }
        m3Workspace workspace = (m3Workspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
        
    }

    /**
     * Respond to mouse dragging on the rendering surface, which we call canvas,
     * but is actually a Pane.
     */
    public void processCanvasMouseDragged(int x, int y) {
        m3Data dataManager = (m3Data) app.getDataComponent();
        if (dataManager.isInState(SIZING_SHAPE)) {
//            Draggable newDraggableShape = (Draggable) dataManager.getNewShape();
//            newDraggableShape.size(x, y);
        } else if (dataManager.isInState(DRAGGING_SHAPE)) {
//            Draggable selectedDraggableShape = (Draggable) dataManager.getSelectedShape();
//            selectedDraggableShape.drag(x, y);
            app.getGUI().updateToolbarControls(false);
        }
    }

    /**
     * Respond to mouse button release on the rendering surface, which we call canvas,
     * but is actually a Pane.
     */
    public void processCanvasMouseRelease(int x, int y) {
        m3Data dataManager = (m3Data) app.getDataComponent();
        if (dataManager.isInState(SIZING_SHAPE)) {
            dataManager.selectSizedShape();
            app.getGUI().updateToolbarControls(false);
        } else if (dataManager.isInState(m3State.DRAGGING_SHAPE)) {
            dataManager.setState(SELECTING_SHAPE);
            Scene scene = app.getGUI().getPrimaryScene();
            scene.setCursor(Cursor.DEFAULT);
            app.getGUI().updateToolbarControls(false);
        } else if (dataManager.isInState(m3State.DRAGGING_NOTHING)) {
            dataManager.setState(SELECTING_SHAPE);
        }
    }
    
}
