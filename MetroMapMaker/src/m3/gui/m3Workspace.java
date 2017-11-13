package m3.gui;

import djf.AppTemplate;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import djf.ui.AppGUI;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import static m3.m3Property.*;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import static m3.css.m3Style.*;
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
    BorderPane row1Box;
    
    // TOP
    BorderPane topRow1Box;
    TextField text1;
    ComboBox<String> metroLines;
    ColorPicker colorButton1;
    
    // MIDDLE 
    HBox midRow1Box;
    Button plusButton1;
    Button minusButton1;
    Button addButton;
    Button removeStationButton;
    Button listButton;
            
    // BOTTOM
    Slider slider1;
    
    // SECOND ROW
    BorderPane row2Box;
    
    // TOP
    BorderPane topRow2Box;
    TextField text2;
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
    BorderPane row4Box;
    
    // TOP
    BorderPane topRow4Box;
    TextField text4;
    ColorPicker colorButton4;
    
    // BOTTOM
    HBox botRow4Box;
    Button imageBgButton;
    Button imageButton;
    Button labelButton;
    Button removeButton;
    
    // FIFTH ROW
    BorderPane row5Box;
    
    // TOP
    BorderPane topRow5Box;
    TextField text5;
    ColorPicker colorButton5;
    
    // BOTTOM
    HBox botRow5Box;
    Button boldButton;
    Button italicButton;
    ComboBox<Integer> fontSize;
    ComboBox<String> fontStyle;
    
    // SIXTH ROW
    BorderPane row6Box;
    
    // TOP
    BorderPane topRow6Box;
    TextField text6;
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
    MapEditController logoEditController;    

    // HERE ARE OUR DIALOGS
    AppMessageDialogSingleton messageDialog;
    AppYesNoCancelDialogSingleton yesNoCancelDialog;
    
    // FOR DISPLAYING DEBUG STUFF
    Text debugText;
    
    public m3Workspace(AppTemplate initApp) {
	// KEEP THIS FOR LATER
	app = initApp;

	// KEEP THE GUI FOR LATER
	gui = app.getGUI();

        // LAYOUT THE APP
        initLayout();
        
//        // HOOK UP THE CONTROLLERS
//        initControllers();
//        
        // AND INIT THE STYLE FOR THE WORKSPACE
        initStyle();    
    }
    
   
    private void initLayout() {
        editToolbar = new VBox();
        
        // ROW 1
        row1Box = new BorderPane();
        
        topRow1Box = new BorderPane();
        text1 = new TextField("Metro Lines");
        metroLines = new ComboBox<String>();
        colorButton1 = new ColorPicker();
        topRow1Box.setLeft(text1);
        topRow1Box.setCenter(metroLines);
        topRow1Box.setRight(colorButton1);
        
        midRow1Box = new HBox();
        plusButton1 = gui.initChildButton(midRow1Box, PLUS_ICON.toString(), false);
        minusButton1 = gui.initChildButton(midRow1Box, MINUS_ICON.toString(), false);;
        addButton = gui.initChildButton2(midRow1Box, "Add Station", false);
        removeStationButton = gui.initChildButton2(midRow1Box, "Remove Station", false);
        listButton = gui.initChildButton(midRow1Box, LIST_ICON.toString(), false);
        
        slider1 = new Slider();
        
        row1Box.setTop(topRow1Box);
        row1Box.setCenter(midRow1Box);
        row1Box.setBottom(slider1);
        
        // ROW 2
        row2Box = new BorderPane();
        
        topRow2Box = new BorderPane();
        text2 = new TextField("Metro Stations");
        metroStations = new ComboBox<String>();
        colorButton2 = new ColorPicker();
        topRow2Box.setLeft(text2);
        topRow2Box.setCenter(metroStations);
        topRow2Box.setRight(colorButton2);
        
        midRow2Box = new HBox();
        plusButton2 = gui.initChildButton(midRow2Box, PLUS_ICON.toString(), false);
        minusButton2 = gui.initChildButton(midRow2Box, MINUS_ICON.toString(), false);
        snapButton = gui.initChildButton2(midRow2Box, "Snap", false);
        moveButton = gui.initChildButton2(midRow2Box, "Move Label", false);
        turnButton = gui.initChildButton(midRow2Box, TURN_ICON.toString(), false);
        
        slider2 = new Slider();
        
        row2Box.setTop(topRow2Box);
        row2Box.setCenter(midRow2Box);
        row2Box.setBottom(slider2);
        
        // ROW 3 
        row3Box = new BorderPane();
        
        leftRowBox = new VBox();
        fromButton = new ComboBox<String>();
        toButton = new ComboBox<String>();
        leftRowBox.getChildren().add(fromButton);
        leftRowBox.getChildren().add(toButton);
        
        rightRowBox = new VBox();
        routeButton = gui.initChildButton(rightRowBox, ROUTE_ICON.toString(), false);
        
        row3Box.setLeft(leftRowBox);
        row3Box.setRight(rightRowBox);
        
        // ROW 4
        row4Box = new BorderPane();
        
        topRow4Box = new BorderPane();
        text4 = new TextField("Decor");
        colorButton4 = new ColorPicker();
        topRow4Box.setLeft(text4);
        topRow4Box.setRight(colorButton4);
        
        botRow4Box = new HBox();
        imageBgButton = gui.initChildButton2(botRow4Box, "Set Image Background", false);
        imageButton = gui.initChildButton2(botRow4Box, "Add Image", false);
        labelButton = gui.initChildButton2(botRow4Box, "Add Label", false);
        removeButton = gui.initChildButton2(botRow4Box, "Remove Element", false);
        
        // ROW 5
        row5Box = new BorderPane();
        
        topRow5Box = new BorderPane();
        text5 = new TextField("Font");
        colorButton5 = new ColorPicker();
        topRow5Box.setLeft(text5);
        topRow5Box.setRight(colorButton5);
        
        botRow5Box = new HBox();
        boldButton = gui.initChildButton(botRow5Box, BOLD_ICON.toString(), false);
        italicButton = gui.initChildButton(botRow5Box, ITALIC_ICON.toString(), false);
        fontSize = new ComboBox<Integer>();
        fontStyle = new ComboBox<String>();
        botRow5Box.getChildren().add(fontSize);
        botRow5Box.getChildren().add(fontStyle);
        
        // ROW 6
        row6Box = new BorderPane();
        
        topRow6Box = new BorderPane();
        text6 = new TextField("Navigation");
        checkBox = new CheckBox("Show Grid");
        topRow6Box.setLeft(text6);
        topRow6Box.setRight(checkBox);
        
        botRow6Box = new HBox();
        zoomInButton = gui.initChildButton(botRow6Box, ZOOM_IN_ICON.toString(), false);
        zoomOutButton = gui.initChildButton(botRow6Box, ZOOM_OUT_ICON.toString(), false);
        incButton = gui.initChildButton(botRow6Box, INC_ICON.toString(), false);
        decButton = gui.initChildButton(botRow6Box, DEC_ICON.toString(), false);
        
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
	//m3Data data = (m3Data)app.getDataComponent();
	//data.setShapes(canvas.getChildren());
        
	// AND NOW SETUP THE WORKSPACE
	workspace = new BorderPane();
	((BorderPane)workspace).setLeft(editToolbar);
	((BorderPane)workspace).setCenter(canvas);
    }
    
    public void initStyle() {
	// NOTE THAT EACH CLASS SHOULD CORRESPOND TO
	// A STYLE CLASS SPECIFIED IN THIS APPLICATION'S
	// CSS FILE
	canvas.getStyleClass().add(CLASS_RENDER_CANVAS);
	
//	// COLOR PICKER STYLE
//	fillColorPicker.getStyleClass().add(CLASS_BUTTON);
//	outlineColorPicker.getStyleClass().add(CLASS_BUTTON);
//	backgroundColorPicker.getStyleClass().add(CLASS_BUTTON);
	
	editToolbar.getStyleClass().add(CLASS_EDIT_TOOLBAR);
	row1Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        row2Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        row3Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
	row4Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
	row5Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        row6Box.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
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
