package m3.gui;

import java.util.Optional;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Dan Kim
 */
public class newLineDialog extends Stage{
    Label messageLabel;
    TextArea text;
    ColorPicker lineColor;
    Button yes;
    Button cancel;
    String printText;
    Color color;
    
    public newLineDialog(){}
    
     public void init(Stage primaryStage) {
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        
        // LABEL TO DISPLAY THE CUSTOM MESSAGE
        messageLabel = new Label("New Map Details");
        
        // FEATURES IN THE DIALOG
        text = new TextArea();
        text.setMaxHeight(10);
        text.setMaxWidth(100);
        lineColor = new ColorPicker();
        yes = new Button("OK");
        cancel = new Button("Cancel");
       
	// MAKE THE EVENT HANDLER FOR THESE BUTTONS
        yes.setOnAction(e -> {           
            setText(text.getText());
            newLineDialog.this.close();
        });
        
        cancel.setOnAction(e ->{
            newLineDialog.this.close();
        });
        
        lineColor.setOnAction(e ->{
            setColor(lineColor.getValue());
        });

        // NOW ORGANIZE OUR BUTTONS
        HBox buttonBox = new HBox(5);
        buttonBox.getChildren().add(yes);
        buttonBox.getChildren().add(cancel);
        
        // WE'LL PUT EVERYTHING HERE
        VBox canvas = new VBox(10);
        
        canvas.getChildren().add(text);
        canvas.getChildren().add(lineColor);
        canvas.getChildren().add(buttonBox);
        
        
        // AND PUT IT IN THE WINDOW
        Scene messageScene = new Scene(canvas, 400, 400);
        this.setScene(messageScene);
     }
     
     public String getText(){
         return printText;
     }
     
     public void setText(String text){
         printText = text;
     }
     
     public void setColor(Color newColor){
         color = newColor;
     }
     
     public Color getColor(){
         return color;
     }
     
     
}
