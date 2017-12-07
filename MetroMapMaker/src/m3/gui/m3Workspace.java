package m3.gui;

import com.sun.glass.ui.Cursor;
import djf.AppTemplate;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import static djf.components.m3Property2.BOLD_ICON;
import static djf.components.m3Property2.DEC_ICON;
import static djf.components.m3Property2.INC_ICON;
import static djf.components.m3Property2.ITALIC_ICON;
import static djf.components.m3Property2.LIST_ICON;
import static djf.components.m3Property2.MINUS_ICON;
import static djf.components.m3Property2.PLUS_ICON;
import static djf.components.m3Property2.ROUTE_ICON;
import static djf.components.m3Property2.TURN_ICON;
import static djf.components.m3Property2.ZOOM_IN_ICON;
import static djf.components.m3Property2.ZOOM_OUT_ICON;
import djf.controller.AppFileController;
import djf.ui.AppGUI;
import static djf.ui.AppGUI.CLASS_BORDERED_PANE;
import static djf.ui.AppGUI.CLASS_CANVAS;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import static javafx.scene.Cursor.HAND;
import static javafx.scene.Cursor.DEFAULT;
import static m3.m3Property.*;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import static m3.css.m3Style.*;
import m3.data.DraggableStation;
import m3.data.m3Data;

/**
 *
 * @author Dan Kim
 */
public class m3Workspace extends AppWorkspaceComponent{

    AppTemplate app;
    AppGUI gui;
    
    VBox editToolbar;
    
    // FIRST ROW
    VBox row1Box;
    
    // TOP
    BorderPane topRow1Box;
    Label text1;
    ComboBox<String> metroLines;
    Button colorButton1;
    
    // MIDDLE 
    HBox midRow1Box;
    Button plusButton1;
    Button minusButton1;
    ToggleButton addButton;
    ToggleButton removeStationButton;
    Button listButton;
            
    // BOTTOM
    Slider slider1;
    
    // SECOND ROW
    VBox row2Box;
    
    // TOP
    BorderPane topRow2Box;
    Label text2;
    ComboBox<String> metroStations;
    ColorPicker colorButton2;
    
    // MIDDLE
    HBox midRow2Box;
    Button plusButton2;
    Button minusButton2;
    Button snapButton;
    Button moveButton;
    Button turnButton;
    
    // BOTTOM
    Slider slider2;
    
    // THIRD ROW
    BorderPane row3Box;
    
    // LEFT
    VBox leftRowBox;
    ComboBox<String> fromButton;
    ComboBox<String> toButton;
    
    // RIGHT
    VBox rightRowBox;
    Button routeButton;
    
    // FOURTH ROW
    VBox row4Box;
    
    // TOP
    BorderPane topRow4Box;
    Label text4;
    ColorPicker colorButton4;
    
    // BOTTOM
    HBox botRow4Box;
    Button imageBgButton;
    Button imageButton;
    Button labelButton;
    Button removeButton;
    
    // FIFTH ROW
    VBox row5Box;
    
    // TOP
    BorderPane topRow5Box;
    Label text5;
    ColorPicker colorButton5;
    
    // BOTTOM
    HBox botRow5Box;
    Button boldButton;
    Button italicButton;
    ComboBox<Integer> fontSize;
    ComboBox<String> fontStyle;
    
    // SIXTH ROW
    VBox row6Box;
    
    // TOP
    BorderPane topRow6Box;
    Label text6;
    CheckBox checkBox;
    
    // BOTTOM
    HBox botRow6Box;
    Button zoomInButton;
    Button zoomOutButton;
    Button incButton;
    Button decButton;
    
    // HERE IS WHERE THE MAPS WILL BE EDITED
    Pane canvas;
    
    // HERE ARE THE CONTROLLERS
    CanvasController canvasController;
    MapEditController mapEditController;    

    // HERE ARE OUR DIALOGS
    AppMessageDialogSingleton messageDialog;
    AppYesNoCancelDialogSingleton yesNoCancelDialog;
    
    // FOR DISPLAYING DEBUG STUFF
    Text debugText;
    
    
    // ACCESSOR METHODS EVENT HANDLERS MAY NEED
    
    public ColorPicker getBackgroundColorPicker() {
        return colorButton4;
    }
    
    public m3Workspace(AppTemplate initApp) {
	// KEEP THIS FOR LATER
	app = initApp;

	// KEEP THE GUI FOR LATER
	gui = app.getGUI();

        // LAYOUT THE APP
        initLayout();
        
//        // HOOK UP THE CONTROLLERS
        initController();
        
        // AND INIT THE STYLE FOR THE WORKSPACE
        initStyle();    
    }
    
   
    private void initLayout() {
        editToolbar = new VBox();
        
        // ROW 1
        row1Box = new VBox(5);
        
        topRow1Box = new BorderPane();
        topRow1Box.setPrefHeight(30);
        text1 = new Label("Metro Lines");
        metroLines = new ComboBox<String>();
        colorButton1 = new Button("#------");
        colorButton1.setStyle("-fx-background-color: " + "mintcream");
        text1.setMinHeight(topRow1Box.getPrefHeight());
        metroLines.setMinHeight(topRow1Box.getPrefHeight());
        colorButton1.setMinHeight(topRow1Box.getPrefHeight());
        topRow1Box.setLeft(text1);
        topRow1Box.setCenter(metroLines);
        topRow1Box.setRight(colorButton1);
        
        midRow1Box = new HBox();
        midRow1Box.setPrefHeight(30);
        plusButton1 = gui.initChildButton(midRow1Box, PLUS_ICON.toString(), false);
        minusButton1 = gui.initChildButton(midRow1Box, MINUS_ICON.toString(), false);;
        addButton = new ToggleButton("Add Station to Line");
        removeStationButton = new ToggleButton("Remove Station from Line");
        listButton = gui.initChildButton(midRow1Box, LIST_ICON.toString(), false);
        midRow1Box.getChildren().add(addButton);
        midRow1Box.getChildren().add(removeStationButton);
        
        plusButton1.setMinHeight(midRow1Box.getPrefHeight());
        minusButton1.setMinHeight(midRow1Box.getPrefHeight());
        addButton.setMinHeight(midRow1Box.getPrefHeight());
        removeStationButton.setMinHeight(midRow1Box.getPrefHeight());
        listButton.setMinHeight(midRow1Box.getPrefHeight());
        
        slider1 = new Slider(1, 10, 20);
        slider1.setValue(1);
        
        row1Box.getChildren().add(topRow1Box);
        row1Box.getChildren().add(midRow1Box);
        row1Box.getChildren().add(slider1);
        
        // ROW 2
        row2Box = new VBox(5);
        
        topRow2Box = new BorderPane();
        topRow2Box.setPrefHeight(30);
        text2 = new Label("Metro Stations");
        metroStations = new ComboBox<String>();
        colorButton2 = new ColorPicker();
        text2.setMinHeight(topRow2Box.getPrefHeight());
        metroStations.setMinHeight(topRow2Box.getPrefHeight());
        colorButton2.setMinHeight(topRow2Box.getPrefHeight());
        topRow2Box.setLeft(text2);
        topRow2Box.setCenter(metroStations);
        topRow2Box.setRight(colorButton2);
        
        midRow2Box = new HBox();
        midRow2Box.setPrefHeight(30);
        plusButton2 = gui.initChildButton(midRow2Box, PLUS_ICON.toString(), false);
        minusButton2 = gui.initChildButton(midRow2Box, MINUS_ICON.toString(), false);
        snapButton = gui.initChildButton2(midRow2Box, "Snap", false);
        moveButton = gui.initChildButton2(midRow2Box, "Move Label", false);
        turnButton = gui.initChildButton(midRow2Box, TURN_ICON.toString(), false);
        plusButton2.setMinHeight(midRow2Box.getPrefHeight());
        minusButton2.setMinHeight(midRow2Box.getPrefHeight());
        snapButton.setMinHeight(midRow2Box.getPrefHeight());
        moveButton.setMinHeight(midRow2Box.getPrefHeight());
        turnButton.setMinHeight(midRow2Box.getPrefHeight());
        slider2 = new Slider(10, 15, 20);
        slider2.setValue(1);
        
        row2Box.getChildren().add(topRow2Box);
        row2Box.getChildren().add(midRow2Box);
        row2Box.getChildren().add(slider2);
        
        // ROW 3 
        row3Box = new BorderPane();
        
        leftRowBox = new VBox(5);
        fromButton = new ComboBox<String>();
        fromButton.setMinSize(200,20);
        toButton = new ComboBox<String>();
        toButton.setMinSize(200,20);
        leftRowBox.getChildren().add(fromButton);
        leftRowBox.getChildren().add(toButton);
        
        rightRowBox = new VBox();
        routeButton = gui.initChildButton(rightRowBox, ROUTE_ICON.toString(), false);
        routeButton.setMinSize(55, 55);
        
        row3Box.setLeft(leftRowBox);
        row3Box.setRight(rightRowBox);
        
        // ROW 4
        row4Box = new VBox(5);
        
        topRow4Box = new BorderPane();
        text4 = new Label("Decor");
        colorButton4 = new ColorPicker();
        text4.setMinHeight(30);
        colorButton4.setMinHeight(30);
        topRow4Box.setLeft(text4);
        topRow4Box.setRight(colorButton4);
        
        botRow4Box = new HBox();
        imageBgButton = gui.initChildButton2(botRow4Box, "Set Image Background", false);
        imageButton = gui.initChildButton2(botRow4Box, "Add Image", false);
        labelButton = gui.initChildButton2(botRow4Box, "Add Label", false);
        removeButton = gui.initChildButton2(botRow4Box, "Remove Element", false);
        imageBgButton.setMinHeight(30);
        imageButton.setMinHeight(30);
        labelButton.setMinHeight(30);
        removeButton.setMinHeight(30);
        
        row4Box.getChildren().add(topRow4Box);
        row4Box.getChildren().add(botRow4Box);
        
        // ROW 5
        row5Box = new VBox(5);
        
        topRow5Box = new BorderPane();
        text5 = new Label("Font");
        colorButton5 = new ColorPicker();
        text5.setMinHeight(30);
        colorButton5.setMinHeight(30);
        topRow5Box.setLeft(text5);
        topRow5Box.setRight(colorButton5);
        
        botRow5Box = new HBox(5);
        boldButton = gui.initChildButton(botRow5Box, BOLD_ICON.toString(), false);
        italicButton = gui.initChildButton(botRow5Box, ITALIC_ICON.toString(), false);
        fontSize = new ComboBox<Integer>();
        fontSize.setValue(12);
        fontStyle = new ComboBox<String>();
        fontStyle.setValue("Times New Roman");
        boldButton.setMinHeight(30);
        italicButton.setMinHeight(30);
        fontSize.setMinHeight(30);
        fontStyle.setMinSize(100, 30);
        botRow5Box.getChildren().add(fontSize);
        botRow5Box.getChildren().add(fontStyle);
        
        row5Box.getChildren().add(topRow5Box);
        row5Box.getChildren().add(botRow5Box);
        
        // ROW 6
        row6Box = new VBox(5);
        
        topRow6Box = new BorderPane();
        text6 = new Label("Navigation");
        checkBox = new CheckBox("Show Grid");
        text6.setMinHeight(30);
        checkBox.setMinHeight(30);
        topRow6Box.setLeft(text6);
        topRow6Box.setRight(checkBox);
        
        botRow6Box = new HBox();
        zoomInButton = gui.initChildButton(botRow6Box, ZOOM_IN_ICON.toString(), false);
        zoomOutButton = gui.initChildButton(botRow6Box, ZOOM_OUT_ICON.toString(), false);
        incButton = gui.initChildButton(botRow6Box, INC_ICON.toString(), false);
        decButton = gui.initChildButton(botRow6Box, DEC_ICON.toString(), false);
        zoomInButton.setMinHeight(30);
        zoomOutButton.setMinHeight(30);
        incButton.setMinHeight(30);
        decButton.setMinHeight(30);
        
        row6Box.getChildren().add(topRow6Box);
        row6Box.getChildren().add(botRow6Box);
        
        editToolbar.getChildren().add(row1Box);
        editToolbar.getChildren().add(row2Box);
        editToolbar.getChildren().add(row3Box);
        editToolbar.getChildren().add(row4Box);
        editToolbar.getChildren().add(row5Box);
        editToolbar.getChildren().add(row6Box);
        
        // WE'LL RENDER OUR STUFF HERE IN THE CANVAS
	canvas = new Pane();
	debugText = new Text();
	canvas.getChildren().add(debugText);
	debugText.setX(100);
	debugText.setY(100);
	
	// AND MAKE SURE THE DATA MANAGER IS IN SYNCH WITH THE PANE
	m3Data data = (m3Data)app.getDataComponent();
	data.setShapes(canvas.getChildren());
        
	// AND NOW SETUP THE WORKSPACE
	workspace = new BorderPane();
	((BorderPane)workspace).setLeft(editToolbar);
        ((BorderPane)workspace).setCenter(canvas);
    }
    
    public void initController(){
        // MAKE THE EDIT CONTROLLER
        mapEditController = new MapEditController(app, gui);
        
        // CONNECT THE BUTTONS TO RESPECTIVE HANDLERS
        plusButton1.setOnAction(e->{
            mapEditController.doAddLine();
            metroLines.getItems().add(mapEditController.getLineText());
            metroLines.setValue(mapEditController.getLineText());
            String color = mapEditController.getLineColor().toString();
                color = "#" + color.substring(2, 8);
                colorButton1.setText(color);
                colorButton1.setStyle("-fx-background-color: " + color);
        });
        
        metroLines.setOnAction(e->{
            String lineName = metroLines.getValue();
            metroLines.setValue(lineName);
            mapEditController.doGetLine(lineName);
            String color = mapEditController.getLineColor().toString();
                color = "#" + color.substring(2, 8);
                colorButton1.setText(color);
                colorButton1.setStyle("-fx-background-color: " + color);
        });
        
        minusButton1.setOnAction(e->{
            String removeLine = metroLines.getValue();
            mapEditController.doRemoveLine(removeLine);
            metroLines.getItems().remove(removeLine);
        });
        
        listButton.setOnAction(e->{
            String line = metroLines.getValue();
            mapEditController.doLineList(line);
        });
        
        colorButton1.setOnAction(e->{
            mapEditController.doLineEdit();
            if(mapEditController.getLineColor() == Color.BLACK){}
            else{
                String color = mapEditController.getLineColor().toString();
                color = "#" + color.substring(2, 8);
                colorButton1.setText(color);
                colorButton1.setStyle("-fx-background-color: " + color);
            }
            if(mapEditController.getLineText().equals(metroLines.getValue())){}
            else{
                metroLines.getItems().remove(metroLines.getValue());
                metroLines.getItems().add(mapEditController.getLineText());
                metroLines.setValue(mapEditController.getLineText());
                
            }
        });
        
        addButton.setOnAction(e->{
            if(addButton.isSelected())
                gui.getPrimaryScene().setCursor(HAND);
            else
                gui.getPrimaryScene().setCursor(DEFAULT);
        });
        
        slider1.setOnMouseDragged(e->{
            String line = metroLines.getValue();
            mapEditController.doLineThickness(slider1.getValue(), line);
        });
        
        slider2.setOnMouseDragged(e->{
            String station = metroStations.getValue();
            mapEditController.doStationThickness(slider2.getValue(), station);
        });
        
        plusButton2.setOnAction(e->{
            mapEditController.doAddStation();
            metroStations.getItems().add(mapEditController.getLineText());
            metroStations.setValue(mapEditController.getLineText());
        });
        
        minusButton2.setOnAction(e->{
            String removeStation = metroStations.getValue();
            mapEditController.doRemoveStation(removeStation);
            metroStations.getItems().remove(removeStation);
        });
        
        moveButton.setOnAction(e->{
            String station = metroStations.getValue();
            mapEditController.doMoveStationLabel(station);
        });
        
        turnButton.setOnAction(e->{
            String station = metroStations.getValue();
            mapEditController.doRotateStationLabel(station);
        });
        
        metroStations.setOnAction(e->{
            String stationName = metroStations.getValue();
            metroStations.setValue(stationName);
            mapEditController.doGetStation(stationName);
        });
        
        colorButton4.setOnAction(e->{
            mapEditController.doSelectBackgroundColor();
        });
        
        imageBgButton.setOnAction(e->{
            mapEditController.doImageBackground(); 
        });
        
        // MAKE THE CANVAS CONTROLLER	
	canvasController = new CanvasController(app);
        m3Data dataManager = (m3Data)app.getDataComponent();
        canvas.setOnMouseClicked(e->{
            if(addButton.isSelected() && e.getTarget() instanceof DraggableStation){
                String station = dataManager.getStationByClick(e.getX(), e.getY()).getName();
                String line = metroLines.getValue();
                mapEditController.doAddStationToLine(station, line);
            }
            else{
                addButton.setSelected(false);
                gui.getPrimaryScene().setCursor(DEFAULT);
            }

        });
//	canvas.setOnMousePressed(e->{
//            if(e.getClickCount() == 2) 
//                canvasController.processCanvasDoubleMousePress((int)e.getX(), (int)e.getY()); 
//            else if(e.getClickCount() == 1)
//                canvasController.processCanvasMousePress((int)e.getX(), (int)e.getY());
//	});
//	canvas.setOnMouseReleased(e->{
//	    canvasController.processCanvasMouseRelease((int)e.getX(), (int)e.getY());
//	});
//	canvas.setOnMouseDragged(e->{
//	    canvasController.processCanvasMouseDragged((int)e.getX(), (int)e.getY());
//	});
    }
    
    public void initStyle() {
	// NOTE THAT EACH CLASS SHOULD CORRESPOND TO
	// A STYLE CLASS SPECIFIED IN THIS APPLICATION'S
	// CSS FILE
	canvas.getStyleClass().add(CLASS_CANVAS);
	
//	// COLOR PICKER STYLE
//	fillColorPicker.getStyleClass().add(CLASS_BUTTON);
//	outlineColorPicker.getStyleClass().add(CLASS_BUTTON);
//	backgroundColorPicker.getStyleClass().add(CLASS_BUTTON);
	
	editToolbar.getStyleClass().add(CLASS_BORDERED_PANE);
	row1Box.getStyleClass().add(CLASS_BORDERED_PANE);
        row2Box.getStyleClass().add(CLASS_BORDERED_PANE);
        row3Box.getStyleClass().add(CLASS_BORDERED_PANE);
	row4Box.getStyleClass().add(CLASS_BORDERED_PANE);
	row5Box.getStyleClass().add(CLASS_BORDERED_PANE);
        row6Box.getStyleClass().add(CLASS_BORDERED_PANE);
    }
    
    
    
    
    public Pane getCanvas() {
        return canvas;
    }

    @Override
    public void resetWorkspace() {
        
    }

    @Override
    public void reloadWorkspace(AppDataComponent data) {
        m3Data dataManager = (m3Data)data;
        plusButton1.setDisable(false);
        minusButton1.setDisable(false);
        addButton.setDisable(false);
        removeStationButton.setDisable(false);
        listButton.setDisable(false);
        plusButton2.setDisable(false);
        minusButton2.setDisable(false);
        snapButton.setDisable(false);
        moveButton.setDisable(false);
        turnButton.setDisable(false);
        plusButton2.setDisable(false);
        minusButton2.setDisable(false);
        snapButton.setDisable(false);
        moveButton.setDisable(false);
        turnButton.setDisable(false);
        routeButton.setDisable(false);
        imageBgButton.setDisable(false);
        imageButton.setDisable(false);
        labelButton.setDisable(false);
        removeButton.setDisable(false);
        boldButton.setDisable(false);
        italicButton.setDisable(false);
        zoomInButton.setDisable(false);
        zoomOutButton.setDisable(false);
        incButton.setDisable(false);
        decButton.setDisable(false);
        
        
    }

}
