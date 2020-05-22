package ch.supsi.pss;

import ch.supsi.pss.bundles.ResourceBundlePss;
import ch.supsi.pss.model.Language;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class TestDrawing extends Application {
    // Set default parameters
    private final double DEFAULTSTROKE = 3.0;
    private final double MAXSTROKE = 30.0;
    private final double MINSTROKE = 1.0;
    private final double SCENE_WIDTH = 1200;
    private final double SCENE_HEIGHT = 800;
    private final double TOOLBAR_HEIGHT = 54;
    private final Color CANVAS_BACKGROUD_COLOR = Color.WHITE;
    private final Color PENCIL_COLOR = Color.BLACK;

    // ToolBar and ToolBar elements
    private ToolBar toolBar;
    private RadioButton btnPencil;
    private RadioButton btnEraser;
    private Button btnClear;
    private Button btnSave;
    private Button btnNewSketch;
    private Slider strokeSlider;
    private Line sampleLine;

    // Canvas and Canvas elements
    private Canvas canvas;
    private GraphicsContext path;

    // Language
    private ResourceBundle resourceBundlePss;

    // TestDrawing Constructor
    // Prepare all Graphical elements
    public TestDrawing(){
        // Set the language
        resourceBundlePss = ResourceBundlePss.getInstance().getLangBundles();

        // Create And Fill ToolBar
        createAndFillToolBar();

        // Build the canvas
        buildCanvas();
    }

    private void createAndFillToolBar(){
        // Build toolBar
        toolBar = new ToolBar();
        toolBar.setMinHeight(TOOLBAR_HEIGHT);
        toolBar.setMaxHeight(TOOLBAR_HEIGHT);
        //toolBar.setStyle("-fx-background-color: dimgray");

        // Build buttons
        /*
        btnClear = new Button("Clear All");
        btnSave = new Button("Save");
        btnNewSketch = new Button("New");
        btnPencil = new RadioButton("Pencil");
        btnEraser = new RadioButton("Eraser");
        */
        btnClear = new Button(resourceBundlePss.getString("btnClearKey"));
        btnSave = new Button(resourceBundlePss.getString("btnSaveKey"));
        btnNewSketch = new Button(resourceBundlePss.getString("btnNewSketchKey"));
        btnPencil = new RadioButton(resourceBundlePss.getString("btnPencilKey"));
        btnEraser = new RadioButton(resourceBundlePss.getString("btnEraserKey"));

        ToggleGroup toggleGroupBtn = new ToggleGroup();
        btnPencil.setToggleGroup(toggleGroupBtn);
        btnEraser.setToggleGroup(toggleGroupBtn);
        btnPencil.setSelected(true);
        btnEraser.setSelected(false);

        // Build the sample line size chooser
        strokeSlider = new Slider(MINSTROKE, MAXSTROKE, DEFAULTSTROKE);
        strokeSlider.setMaxWidth(250);
        Label labelStroke = new Label(resourceBundlePss.getString("labelStrokeKey"));
        VBox utilBox = new VBox(10);
        utilBox.setAlignment(Pos.BASELINE_LEFT);
        utilBox.getChildren().addAll(labelStroke, strokeSlider);

        // Build the sample line
        sampleLine = new Line(0, 0, 140, 0);
        sampleLine.strokeWidthProperty().bind(strokeSlider.valueProperty());
        sampleLine.setStroke(PENCIL_COLOR);
        //sampleLine.setStyle("-webkit-fx-stroke-color: #DC143C");

        // Add all elements to toolBar
        toolBar.getItems().addAll(btnNewSketch, new Separator(), btnSave, new Separator(), btnPencil, new Separator(), btnEraser, new Separator(), btnClear, new Separator(), utilBox, sampleLine);
    }

    private void buildCanvas(){
        // Build the canvas
        canvas = new Canvas(SCENE_WIDTH - 5, SCENE_HEIGHT - TOOLBAR_HEIGHT - 5);
        canvas.setCursor(Cursor.CROSSHAIR);
        path = canvas.getGraphicsContext2D();
        path.setFill(CANVAS_BACKGROUD_COLOR);
        path.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Set the primaryStage title
        primaryStage.setTitle("Drawing Tool");

        // Build the main container
        BorderPane container = new BorderPane();
        container.setTop(toolBar);
        container.setCenter(canvas);

        // Build the main scene
        Scene scene = new Scene(container, SCENE_WIDTH, SCENE_HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        // Clear all canvas
        btnClear.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                path.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                path.setFill(CANVAS_BACKGROUD_COLOR);
                path.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            }
        });

        // Set pencil color
        btnPencil.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                sampleLine.setStroke(PENCIL_COLOR);
            }
        });

        // Set eraser color
        btnEraser.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                sampleLine.setStroke(Color.WHITE);
            }
        });

        // start drawing
        canvas.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent me) {
                path.setLineWidth(sampleLine.getStrokeWidth());
                path.setStroke(sampleLine.getStroke());
                path.beginPath();
                path.moveTo(me.getX(), me.getY());
                path.stroke();
            }
        });

        // keep drawing
        canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent me) {
                path.lineTo(me.getX(), me.getY());
                path.stroke();
            }
        });
    }
}
