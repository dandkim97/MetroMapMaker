package djf.ui;

import djf.AppTemplate;
import djf.controller.AppFileController;
import static djf.settings.AppPropertyType.*;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import java.net.URL;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import static djf.components.m3Property2.*;
import javafx.scene.control.Label;

/**
 *
 * @author Dan Kim
 */
public class AppGUI {
    
    protected AppFileController fileController;
    protected Stage primaryStage;
    protected Scene primaryScene;
    protected BorderPane appPane;
    protected BorderPane topToolbarPane;
    protected FlowPane fileToolbar;
    protected FlowPane fileToolbar2;
    protected FlowPane fileToolbar3;
    
    protected Button newButton;
    protected Button loadButton;
    protected Button saveButton;
    protected Button saveAsButton;
    protected Button exportButton;
    
    protected Button undoButton;
    protected Button redoButton;
    
    protected Button aboutButton;
    
    protected AppYesNoCancelDialogSingleton yesNoCancelDialog;
    /*
    ----------------------------------
    */
    
    VBox editToolbar;
    
    // FIRST ROW
    VBox row1Box;
    
    // TOP
    BorderPane topRow1Box;
    Label text1;
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
    BorderPane workspace;
    
    /*
    ---------------------------------
    */
    protected String appTitle;
    
    public AppGUI(  Stage initPrimaryStage, 
		    String initAppTitle, 
		    AppTemplate app){
	// SAVE THESE FOR LATER
	primaryStage = initPrimaryStage;
	appTitle = initAppTitle;
	       
        // INIT THE TOOLBAR
        initTopToolbar(app);
        initTopToolBar2(app);
        initTopToolBar3(app);
//        initLayout(app);
        		
        // AND FINALLY START UP THE WINDOW (WITHOUT THE WORKSPACE)
        initWindow();
        
        // INIT THE STYLESHEET AND THE STYLE FOR THE FILE TOOLBAR
        initStylesheet(app);
//        initStyle();
        initFileToolbarStyle();
        initFileToolbarStyle2();
        initFileToolbarStyle3();

    }
    private void initLayout(AppTemplate app) {
        editToolbar = new VBox();
        fileController = new AppFileController(app);
        // ROW 1
        row1Box = new VBox(5);
        
        topRow1Box = new BorderPane();
        topRow1Box.setPrefHeight(30);
        text1 = new Label("Metro Lines");
        metroLines = new ComboBox<String>();
        colorButton1 = new ColorPicker();
        text1.setMinHeight(topRow1Box.getPrefHeight());
        metroLines.setMinHeight(topRow1Box.getPrefHeight());
        colorButton1.setMinHeight(topRow1Box.getPrefHeight());
        topRow1Box.setLeft(text1);
        topRow1Box.setCenter(metroLines);
        topRow1Box.setRight(colorButton1);
        
        midRow1Box = new HBox();
        midRow1Box.setPrefHeight(30);
        plusButton1 = initChildButton(midRow1Box, PLUS_ICON.toString(), false);
        minusButton1 = initChildButton(midRow1Box, MINUS_ICON.toString(), false);;
        addButton = initChildButton2(midRow1Box, "Add Station", false);
        removeStationButton = initChildButton2(midRow1Box, "Remove Station", false);
        listButton = initChildButton(midRow1Box, LIST_ICON.toString(), false);
        
        plusButton1.setMinHeight(midRow1Box.getPrefHeight());
        minusButton1.setMinHeight(midRow1Box.getPrefHeight());
        addButton.setMinHeight(midRow1Box.getPrefHeight());
        removeStationButton.setMinHeight(midRow1Box.getPrefHeight());
        listButton.setMinHeight(midRow1Box.getPrefHeight());
        
        slider1 = new Slider();
        
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
        plusButton2 = initChildButton(midRow2Box, PLUS_ICON.toString(), false);
        minusButton2 = initChildButton(midRow2Box, MINUS_ICON.toString(), false);
        snapButton = initChildButton2(midRow2Box, "Snap", false);
        moveButton = initChildButton2(midRow2Box, "Move Label", false);
        turnButton = initChildButton(midRow2Box, TURN_ICON.toString(), false);
        plusButton2.setMinHeight(midRow2Box.getPrefHeight());
        minusButton2.setMinHeight(midRow2Box.getPrefHeight());
        snapButton.setMinHeight(midRow2Box.getPrefHeight());
        moveButton.setMinHeight(midRow2Box.getPrefHeight());
        turnButton.setMinHeight(midRow2Box.getPrefHeight());
        slider2 = new Slider();
        
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
        routeButton = initChildButton(rightRowBox, ROUTE_ICON.toString(), false);
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
        imageBgButton = initChildButton2(botRow4Box, "Set Image Background", false);
        imageButton = initChildButton2(botRow4Box, "Add Image", false);
        labelButton = initChildButton2(botRow4Box, "Add Label", false);
        removeButton = initChildButton2(botRow4Box, "Remove Element", false);
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
        boldButton = initChildButton(botRow5Box, BOLD_ICON.toString(), false);
        italicButton = initChildButton(botRow5Box, ITALIC_ICON.toString(), false);
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
        zoomInButton = initChildButton(botRow6Box, ZOOM_IN_ICON.toString(), false);
        zoomOutButton = initChildButton(botRow6Box, ZOOM_OUT_ICON.toString(), false);
        incButton = initChildButton(botRow6Box, INC_ICON.toString(), false);
        decButton = initChildButton(botRow6Box, DEC_ICON.toString(), false);
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
//	debugText = new Text();
//	canvas.getChildren().add(debugText);
//	debugText.setX(100);
//	debugText.setY(100);
	
	// AND MAKE SURE THE DATA MANAGER IS IN SYNCH WITH THE PANE
	//m3Data data = (m3Data)app.getDataComponent();
	//data.setShapes(canvas.getChildren());
        
	// AND NOW SETUP THE WORKSPACE
	workspace = new BorderPane();
	workspace.setLeft(editToolbar);
        workspace.setRight(canvas);
    }
    
    
    public AppFileController getFileController() { return fileController; }
    
    public BorderPane getAppPane() { return appPane; }
    
    public BorderPane getTopToolbarPane() {
        return topToolbarPane;
    }
    
    public FlowPane getFileToolbar() {
        return fileToolbar;
    }
    
    public Scene getPrimaryScene() { return primaryScene; }
    
    public Stage getWindow() { return primaryStage; }
    
    public void updateToolbarControls(boolean saved) {
        // THIS TOGGLES WITH WHETHER THE CURRENT COURSE
        // HAS BEEN SAVED OR NOT
        saveButton.setDisable(saved);

        // ALL THE OTHER BUTTONS ARE ALWAYS ENABLED
        // ONCE EDITING THAT FIRST COURSE BEGINS
	newButton.setDisable(false);
        loadButton.setDisable(false);

        // NOTE THAT THE NEW, LOAD, AND EXIT BUTTONS
        // ARE NEVER DISABLED SO WE NEVER HAVE TO TOUCH THEM
    }
    
    private void initTopToolbar(AppTemplate app) {
        fileToolbar = new FlowPane();

        // HERE ARE OUR FILE TOOLBAR BUTTONS, NOTE THAT SOME WILL
        // START AS ENABLED (false), WHILE OTHERS DISABLED (true)
        newButton = initChildButton2(fileToolbar,	"New",	    	false);
        loadButton = initChildButton2(fileToolbar,	"Load",	    	false);
        saveButton = initChildButton2(fileToolbar,	"Save",	    	false);
        saveAsButton = initChildButton2(fileToolbar,	"Save As",    	false);
        exportButton = initChildButton2(fileToolbar,	"Export",       false);
        
        
	// AND NOW SETUP THEIR EVENT HANDLERS
        fileController = new AppFileController(app);
        newButton.setOnAction(e -> {
            fileController.handleNewRequest();
        });
        loadButton.setOnAction(e -> {
            fileController.handleLoadRequest();
        });
        saveButton.setOnAction(e -> {
            fileController.handleSaveRequest();
        });
//        saveAsButton.setOnAction(e -> {
//            fileController.handleSaveAsRequest();
//        })
        exportButton.setOnAction(e -> {
            fileController.handleExportRequest();
        });	
        
        // NOW PUT THE FILE TOOLBAR IN THE TOP TOOLBAR, WHICH COULD
        // ALSO STORE OTHER TOOLBARS
        topToolbarPane = new BorderPane();
        topToolbarPane.setLeft(fileToolbar);
    }
    
    private void initTopToolBar2(AppTemplate app) {
        fileToolbar2 = new FlowPane();
        
        undoButton = initChildButton2(fileToolbar2,     "Undo",     false);
        redoButton = initChildButton2(fileToolbar2,     "Redo",     false);
        
        fileController = new AppFileController(app);
        
        undoButton.setOnAction(e -> {
            fileController.handleUndoRequest();
        });
        redoButton.setOnAction(e -> {
            fileController.handleRedoRequest();
        });
        topToolbarPane = getTopToolbarPane();
        topToolbarPane.setCenter(fileToolbar2);
    }
    
    private void initTopToolBar3(AppTemplate app){
        fileToolbar3 = new FlowPane();
        
        aboutButton = initChildButton2(fileToolbar3, "About", false);
        
        aboutButton.setOnAction(e -> {
            fileController.handleAboutRequest();
        });
        
        topToolbarPane = getTopToolbarPane();
        topToolbarPane.setRight(fileToolbar3);
    }
    
    private void initWindow() {
	PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // SET THE WINDOW TITLE
        primaryStage.setTitle(appTitle);

        // START FULL-SCREEN OR NOT, ACCORDING TO PREFERENCES
        primaryStage.setMaximized("true".equals(props.getProperty(START_MAXIMIZED)));

        // ADD THE TOOLBAR ONLY, NOTE THAT THE WORKSPACE
        // HAS BEEN CONSTRUCTED, BUT WON'T BE ADDED UNTIL
        // THE USER STARTS EDITING A COURSE
        appPane = new BorderPane();
        appPane.setTop(topToolbarPane);
        appPane.setCenter(workspace);
        primaryScene = new Scene(appPane);

        // SET THE APP PANE PREFERRED SIZE ACCORDING TO THE PREFERENCES
        double prefWidth = Double.parseDouble(props.getProperty(PREF_WIDTH));
        double prefHeight = Double.parseDouble(props.getProperty(PREF_HEIGHT));
        appPane.setPrefWidth(prefWidth);
        appPane.setPrefHeight(prefHeight);

        // SET THE APP ICON
        String appIcon = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(APP_LOGO);
        primaryStage.getIcons().add(new Image(appIcon));

        // NOW TIE THE SCENE TO THE WINDOW
        primaryStage.setScene(primaryScene);
    }
    
    public Button initChildButton(Pane toolbar, String icon, boolean disabled) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
	
	// LOAD THE ICON FROM THE PROVIDED FILE
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(icon);
        Image buttonImage = new Image(imagePath);
	
	// NOW MAKE THE BUTTON
        Button button = new Button();
        button.setDisable(disabled);
        button.setGraphic(new ImageView(buttonImage));
//        Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip));
//        button.setTooltip(buttonTooltip);
	
	// PUT THE BUTTON IN THE TOOLBAR
        toolbar.getChildren().add(button);
	
	// AND RETURN THE COMPLETED BUTTON
        return button;
    }
    
    public Button initChildButton2(Pane toolbar, String name, boolean disabled) {
	// NOW MAKE THE BUTTON
        Button button = new Button(name);
        button.setDisable(disabled);
//        Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip));
//        button.setTooltip(buttonTooltip);
	
	// PUT THE BUTTON IN THE TOOLBAR
        toolbar.getChildren().add(button);
	
	// AND RETURN THE COMPLETED BUTTON
        return button;
    }
    public static final String CLASS_CANVAS = "color_chooser_pane";
    public static final String CLASS_BORDERED_PANE = "bordered_pane";
    public static final String CLASS_FILE_BUTTON = "file_button";
    
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
    
    private void initStylesheet(AppTemplate app) {
	// SELECT THE STYLESHEET
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	String stylesheet = props.getProperty(APP_PATH_CSS);
	stylesheet += props.getProperty(APP_CSS);
        Class appClass = app.getClass();
	URL stylesheetURL = appClass.getResource(stylesheet);
	String stylesheetPath = stylesheetURL.toExternalForm();
	primaryScene.getStylesheets().add(stylesheetPath);	
    }
    
    private void initFileToolbarStyle() {
	topToolbarPane.getStyleClass().add(CLASS_BORDERED_PANE);
        fileToolbar.getStyleClass().add(CLASS_BORDERED_PANE);
	newButton.getStyleClass().add(CLASS_FILE_BUTTON);
	loadButton.getStyleClass().add(CLASS_FILE_BUTTON);
	saveButton.getStyleClass().add(CLASS_FILE_BUTTON);
	saveAsButton.getStyleClass().add(CLASS_FILE_BUTTON);
        exportButton.getStyleClass().add(CLASS_FILE_BUTTON);
    }
    
    private void initFileToolbarStyle2() {
	topToolbarPane.getStyleClass().add(CLASS_BORDERED_PANE);
        fileToolbar2.getStyleClass().add(CLASS_BORDERED_PANE);
	undoButton.getStyleClass().add(CLASS_FILE_BUTTON);
        redoButton.getStyleClass().add(CLASS_FILE_BUTTON);
    }
    
    private void initFileToolbarStyle3() {
	topToolbarPane.getStyleClass().add(CLASS_BORDERED_PANE);
        fileToolbar3.getStyleClass().add(CLASS_BORDERED_PANE);
	aboutButton.getStyleClass().add(CLASS_FILE_BUTTON);
    }
    
}
