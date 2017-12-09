package m3.file;

import djf.AppTemplate;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.io.File;
import java.util.ArrayList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javax.imageio.ImageIO;
import m3.data.DraggableLine;
import m3.data.DraggableStation;
import m3.data.DraggableText;
import m3.data.m3Data;


/**
 * This class serves as the file management component for this application,
 * providing all I/O services.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class m3Files implements AppFileComponent {
    // FOR JSON LOADING
    static final String JSON_BG_COLOR = "background_color";
    static final String JSON_RED = "red";
    static final String JSON_GREEN = "green";
    static final String JSON_BLUE = "blue";
    static final String JSON_ALPHA = "alpha";
    static final String JSON_TYPE = "type";
    static final String JSON_SHAPE = "shape";
    static final String JSON_LINE_NAME = "name";
    static final String JSON_CIRC = "circular";
    static final String JSON_STATION = "station_names";
    static final String JSON_STARTX = "start x";
    static final String JSON_STARTY = "start y";
    static final String JSON_ENDX = "end x";
    static final String JSON_ENDY = "end y";
    static final String JSON_RADIUS = "radius";
    static final String JSON_COLOR = "color";
    static final String JSON_NAME = "name";
    static final String JSON_CHECK = "check";
    static final String JSON_OUTLINE_COLOR = "outline_color";
    static final String JSON_OUTLINE_THICKNESS = "outline_thickness";
    
    static final String DEFAULT_DOCTYPE_DECLARATION = "<!doctype html>\n";
    static final String DEFAULT_ATTRIBUTE_VALUE = "";
    
    ArrayList<String> LineNames = new ArrayList<>();
    ArrayList<String> StationNames = new ArrayList<>();

    /**
     * This method is for saving user work, which in the case of this
     * application means the data that together draws the logo.
     * 
     * @param data The data management component for this application.
     * 
     * @param filePath Path (including file name/extension) to where
     * to save the data to.
     * 
     * @throws IOException Thrown should there be an error writing 
     * out data to the file.
     */
    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
	// GET THE DATA
	m3Data dataManager = (m3Data)data;
	
	// FIRST THE BACKGROUND COLOR
	Color bgColor = dataManager.getBackgroundColor();
//	JsonObject bgColorJson = makeJsonColorObject(bgColor);

	// NOW BUILD THE JSON OBJCTS TO SAVE
	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
	ObservableList<Node> shapes = dataManager.getShapes();
	for (Node node : shapes) {
            if (node instanceof DraggableLine){
                DraggableLine shape = (DraggableLine)node;
                DraggableLine dShape = ((DraggableLine)shape);
                String type = "Draggable Line";
                double startX = dShape.getMoveTo().getX();
                double startY = dShape.getMoveTo().getY();
                double endX = dShape.getEndTo().getX();
                double endY = dShape.getEndTo().getY();
                String name = dShape.getStartLabel().getText();
                LineNames.add(name);
                boolean circular = false;
                JsonObject fillColorJson = makeJsonColorObject((Color)dShape.getColor());
                double outlineThickness = dShape.getPath().getStrokeWidth();

                JsonObject shapeJson = Json.createObjectBuilder()
                        .add(JSON_TYPE, type)
//                        .add(JSON_CHECK, 1.0)
                        .add(JSON_STARTX, startX)
                        .add(JSON_STARTY, startY)
                        .add(JSON_ENDX, endX)
                        .add(JSON_ENDY, endY)
                        .add(JSON_NAME, name)
                        .add(JSON_CIRC, circular)
//                        .add(JSON_STATION, stationsJson)
                        .add(JSON_COLOR, fillColorJson)
                        .add(JSON_OUTLINE_THICKNESS, outlineThickness).build();
                arrayBuilder.add(shapeJson);
            }
            else if (node instanceof DraggableStation){
                DraggableStation shape = (DraggableStation)node;
                DraggableStation dShape = ((DraggableStation)shape);
                String type = "Draggable Station";
                double startX = dShape.getCenterX();
                double startY = dShape.getCenterY();
                double radius = dShape.getRadius();
                String name = dShape.getName();
                StationNames.add(name);
                JsonObject fillColorJson = makeJsonColorObject((Color)dShape.getFill());
                double outlineThickness = dShape.getRadius();

                JsonObject shapeJson = Json.createObjectBuilder()
                        .add(JSON_TYPE, type)
//                        .add(JSON_CHECK, 2.0)
                        .add(JSON_STARTX, startX)
                        .add(JSON_STARTY, startY)
                        .add(JSON_RADIUS, radius)
                        .add(JSON_NAME, name)
                        .add(JSON_COLOR, fillColorJson)
                        .add(JSON_OUTLINE_THICKNESS, outlineThickness).build();
                arrayBuilder.add(shapeJson);
            }
	}
	JsonArray shapesArray = arrayBuilder.build();
	
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject dataManagerJSO = Json.createObjectBuilder()
//		.add(JSON_BG_COLOR, bgColorJson)
		.add(JSON_TYPE, shapesArray)
		.build();
	
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
    private JsonObject makeJsonColorObject(Color color) {
	JsonObject colorJson = Json.createObjectBuilder()
		.add(JSON_RED, color.getRed())
		.add(JSON_GREEN, color.getGreen())
		.add(JSON_BLUE, color.getBlue())
		.add(JSON_ALPHA, color.getOpacity()).build();
	return colorJson;
    }
      

    /**
     * This method loads data from a JSON formatted file into the data 
     * management component and then forces the updating of the workspace
     * such that the user may edit the data.
     * 
     * @param data Data management component where we'll load the file into.
     * 
     * @param filePath Path (including file name/extension) to where
     * to load the data from.
     * 
     * @throws IOException Thrown should there be an error reading
     * in data from the file.
     */
    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
	// CLEAR THE OLD DATA OUT
	m3Data dataManager = (m3Data)data;
	dataManager.resetData();
	
	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);
	
	// LOAD THE BACKGROUND COLOR
//	Color bgColor = loadColor(json, JSON_BG_COLOR);
//	dataManager.setBackgroundColor(bgColor);
	
	// AND NOW LOAD ALL THE SHAPES
	JsonArray jsonShapeArray = json.getJsonArray(JSON_TYPE);
	for (int i = 0; i < jsonShapeArray.size(); i++) {
	    JsonObject jsonShape = jsonShapeArray.getJsonObject(i);
//            if(getDataAsDouble(jsonShape, JSON_CHECK) == 1.0){
                DraggableLine shape = loadShape(jsonShape, LineNames.get(i));
                dataManager.getMetroLines().getItems().add(LineNames.get(i));
                dataManager.addShape(shape);
//            }
//            else if(getDataAsDouble(jsonShape, JSON_CHECK) == 2.0){
//                DraggableStation shape = loadStation(jsonShape);
//                dataManager.getMetroStations().getItems().add(StationNames.get(i));
//                DraggableText text = new DraggableText(StationNames.get(i));
//                text.xProperty().bindBidirectional(shape.getTopRightX());
//                text.yProperty().bindBidirectional(shape.getTopRightY());
//                dataManager.addStation(shape, text);
//            }
	}
    }
    
    private double getDataAsDouble(JsonObject json, String dataName) {
	JsonValue value = json.get(dataName);
	JsonNumber number = (JsonNumber)value;
	return number.bigDecimalValue().doubleValue();	
    }
    
    private DraggableLine loadShape(JsonObject jsonShape, String name) {
	// FIRST BUILD THE PROPER SHAPE TYPE
	String type = jsonShape.getString(JSON_TYPE);
        double startX = getDataAsDouble(jsonShape, JSON_STARTX);
        double startY = getDataAsDouble(jsonShape, JSON_STARTY);
        double endX = getDataAsDouble(jsonShape, JSON_ENDX);
        double endY = getDataAsDouble(jsonShape, JSON_ENDY);

	DraggableLine shape = new DraggableLine(startX, startY, endX, endY);
        shape.setStartLabel(name);
        shape.setEndLabel(name);
        
	// THEN LOAD ITS FILL AND OUTLINE PROPERTIES
	Color fillColor = loadColor(jsonShape, JSON_COLOR);
//	Color outlineColor = loadColor(jsonShape, JSON_OUTLINE_COLOR);
	double outlineThickness = getDataAsDouble(jsonShape, JSON_OUTLINE_THICKNESS);
	shape.getPath().setStroke(fillColor);
	shape.getPath().setStrokeWidth(outlineThickness);

	// ALL DONE, RETURN IT
	return shape;
    }
    
    private DraggableStation loadStation(JsonObject jsonShape){
        // FIRST BUILD THE PROPER SHAPE TYPE
	String type = jsonShape.getString(JSON_TYPE);
        double startX = getDataAsDouble(jsonShape, JSON_STARTX);
        double startY = getDataAsDouble(jsonShape, JSON_STARTY);
        double radius = getDataAsDouble(jsonShape, JSON_RADIUS);

	DraggableStation shape = new DraggableStation(startX, startY, radius);
        
	// THEN LOAD ITS FILL AND OUTLINE PROPERTIES
	Color fillColor = loadColor(jsonShape, JSON_COLOR);
//	Color outlineColor = loadColor(jsonShape, JSON_OUTLINE_COLOR);
	double outlineThickness = getDataAsDouble(jsonShape, JSON_OUTLINE_THICKNESS);
	shape.setFill(fillColor);
	shape.setRadius(outlineThickness);
	
	// ALL DONE, RETURN IT
	return shape;
    }

    
    private Color loadColor(JsonObject json, String colorToGet) {
	JsonObject jsonColor = json.getJsonObject(colorToGet);
	double red = getDataAsDouble(jsonColor, JSON_RED);
	double green = getDataAsDouble(jsonColor, JSON_GREEN);
	double blue = getDataAsDouble(jsonColor, JSON_BLUE);
	double alpha = getDataAsDouble(jsonColor, JSON_ALPHA);
	Color loadedColor = new Color(red, green, blue, alpha);
	return loadedColor;
    }

    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }
    
    /**
     * This method is provided to satisfy the compiler, but it
     * is not used by this application.
     */
    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        
    }
    
    /**
     * This method is provided to satisfy the compiler, but it
     * is not used by this application.
     */
    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
	// AGAIN, WE ARE NOT USING THIS IN THIS ASSIGNMENT
    }
}
