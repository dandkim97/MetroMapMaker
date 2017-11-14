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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextFlow;


/**
 *
 * @author Dan Kim
 */
public class WelcomeDialogSingleton extends Stage{
    
    static WelcomeDialogSingleton singleton;
    
    BorderPane canvas;
    BorderPane messagePane;
    BorderPane rightPane;
    VBox rMessagePane;
    Scene messageScene;
    Label messageLabel;
    Label rMessageLabel;
    Hyperlink msg1;
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
        rMessageLabel.setStyle("-fx-text-fill: #000000");
        msg1 = new Hyperlink("Fun Place");
        
        // BUTTONS
        newMapButton = new Button("Create New Map");
	
	// MAKE THE EVENT HANDLER FOR THESE BUTTONS
        newMapButton.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog("");
                dialog.setTitle("Making a New Map");
                dialog.setHeaderText("Make a New Map");
                dialog.setContentText("Type here:");
                dialog.showAndWait();
            WelcomeDialogSingleton.this.close();    
            setTruth();
        });
        
        msg1.setOnAction(e ->{
            WelcomeDialogSingleton.this.close();
            setTruth();
        });
        

        // NOW ORGANIZE OUR BUTTONS
        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(newMapButton);
        
        // WE'LL PUT EVERYTHING HERE
        canvas = new BorderPane();
        
        rightPane = new BorderPane();
        messagePane = new BorderPane();
        messagePane.setStyle("-fx-background-color: #FFFFFF;\n" +
                                "    -fx-padding: 15;\n" +
                                "    -fx-spacing: 10;  ");
        messagePane.setTop(messageLabel);
        messagePane.setCenter(buttonBox);
        
        Image image = new Image("welcomeLogo.png");
        rightPane.setTop(new ImageView(image));
        rightPane.setCenter(messagePane);
        
        rMessagePane = new VBox(20);
        rMessagePane.setStyle("-fx-background-color: #98b78f;\n" +
                                "    -fx-background-radius: 5.0;\n" +
                                "    -fx-padding: 15;\n" +
                                "    -fx-spacing: 10;\n" +
                                "    -fx-border-width: 2px;\n" +
                                "    -fx-border-color: #4c8c4a;");
        rMessagePane.getChildren().add(rMessageLabel);
        rMessagePane.getChildren().add(msg1);
        
        // MAKE IT LOOK NICE
//        messagePane.setPadding(new Insets(10, 20, 20, 20));
//        messagePane.setSpacing(10);
        canvas.setLeft(rMessagePane);
        canvas.setCenter(rightPane);
        
        // AND PUT IT IN THE WINDOW
        messageScene = new Scene(canvas, 800, 400);
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
