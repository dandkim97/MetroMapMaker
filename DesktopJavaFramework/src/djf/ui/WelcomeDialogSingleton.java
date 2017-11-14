package djf.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import djf.AppTemplate;
import javafx.scene.control.TextInputDialog;


/**
 *
 * @author Dan Kim
 */
public class WelcomeDialogSingleton extends Stage{
    
    static WelcomeDialogSingleton singleton;
    
    HBox canvas;
    VBox messagePane;
    VBox rMessagePane;
    Scene messageScene;
    Label messageLabel;
    Label rMessageLabel;
    Label msg1;
    Label msg2;
    Button newMapButton;
    Boolean truth = true;
    
    private WelcomeDialogSingleton(){}
    
    public boolean getTruth(){
        return truth;
    }
    public void setTruth(){
        truth = false;
    }
    public static WelcomeDialogSingleton getSingleton(){
        if (singleton == null)
            singleton = new WelcomeDialogSingleton();
        return singleton;
    }
    
    public void init(Stage primaryStage) {
        // MAKE THIS DIALOG MODAL, MEANING OTHERS WILL WAIT
        // FOR IT WHEN IT IS DISPLAYED
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        
        // LABEL TO DISPLAY THE CUSTOM MESSAGE
        messageLabel = new Label();
        rMessageLabel = new Label("Recent Projects");
        msg1 = new Label("Map1");
        msg2 = new Label("Map2");

        // BUTTONS
        newMapButton = new Button("Create New Map");
	
	// MAKE THE EVENT HANDLER FOR THESE BUTTONS
        newMapButton.setOnAction(e -> {
            WelcomeDialogSingleton.this.close();
            setTruth();
        });
        

        // NOW ORGANIZE OUR BUTTONS
        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(newMapButton);
        
        // WE'LL PUT EVERYTHING HERE
        canvas = new HBox(50);
        messagePane = new VBox();
        messagePane.setAlignment(Pos.CENTER);
        messagePane.getChildren().add(messageLabel);
        messagePane.getChildren().add(buttonBox);
        
        rMessagePane = new VBox(20);
        rMessagePane.getChildren().add(rMessageLabel);
        rMessagePane.getChildren().add(msg1);
        rMessagePane.getChildren().add(msg2);
        
        // MAKE IT LOOK NICE
        messagePane.setPadding(new Insets(10, 20, 20, 20));
        messagePane.setSpacing(10);
        canvas.getChildren().add(rMessagePane);
        canvas.getChildren().add(messagePane);

        // AND PUT IT IN THE WINDOW
        messageScene = new Scene(canvas);
        this.setScene(messageScene);
    }
    
    public void show(String title, String message) {
	// SET THE DIALOG TITLE BAR TITLE
	setTitle(title);
	
	// SET THE MESSAGE TO DISPLAY TO THE USER
        messageLabel.setText(message);
	
	// AND OPEN UP THIS DIALOG, MAKING SURE THE APPLICATION
	// WAITS FOR IT TO BE RESOLVED BEFORE LETTING THE USER
	// DO MORE WORK.
        showAndWait();
    }
}
