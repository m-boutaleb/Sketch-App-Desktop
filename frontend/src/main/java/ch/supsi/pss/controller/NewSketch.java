package ch.supsi.pss.controller;

import ch.supsi.pss.model.Author;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static ch.supsi.pss.utils.CanvasUtils.*;

public class NewSketch {
    @FXML
    private Canvas canvas;
    @FXML
    private Button save;
    @FXML
    private Button erase;
    @FXML
    private Button draw;
    @FXML
    private Button eraseAll;
    @FXML
    private FlowPane canvasPane;
    private final Stage stage;
    private final boolean HD;
    private final Set<String> alltags;
    private GraphicsContext path;
    private boolean alreadySaved;
    private boolean drawing;

    NewSketch(final String canvasFormat, final Stage stage) {
        this.stage=stage;
        this.HD= canvasFormat.equalsIgnoreCase("HD");
        alltags = new HashSet<>();
    }

    @FXML
    public void initialize() {
        save.setOnMouseClicked(this::save);
        erase.setOnMouseClicked(this::erase);
        draw.setOnMouseClicked(this::draw);
        eraseAll.setOnMouseClicked(this::eraseAll);
        path=canvas.getGraphicsContext2D();
        canvas.setHeight(HD?CANVAS_HEIGHT_HD:CANVAS_HEIGHT_PORTRAIT);
        canvas.setWidth(HD?CANVAS_WIDTH_HD:CANVAS_WIDTH_PORTRAIT);
        canvasPane.setMaxWidth(canvas.getWidth());
        canvasPane.setMaxHeight(canvas.getHeight());
        stage.setWidth(canvasPane.getMaxWidth()+20);
        stage.setHeight(canvasPane.getMaxHeight()+120);
        stage.setResizable(false);
        stage.show();
    }

    private void eraseAll(final MouseEvent mouseEvent) {
        drawing=true;
        path.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
    }

    public void save(final MouseEvent mouseEvent) {
        SnapshotParameters snapshotParameters = new SnapshotParameters();
        WritableImage writableImage = new WritableImage((HD? CANVAS_WIDTH_HD : CANVAS_WIDTH_PORTRAIT),(HD? CANVAS_HEIGHT_HD : CANVAS_HEIGHT_PORTRAIT));
        WritableImage snapshot=canvas.snapshot(null, writableImage);
        BufferedImage image= SwingFXUtils.fromFXImage(snapshot, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        checkAndSave(baos.toByteArray());
        drawing=false;
    }

    private void checkAndSave(final byte[] updatedSketch) {
        var defaultSystemUser=System.getProperty("user.name");
        if(!alreadySaved) {
            SketchController.getInstance().newSketch(updatedSketch, alltags, new Author(defaultSystemUser));
            alreadySaved=true;
            return;
        }
        SketchController.getInstance().updateSketch(updatedSketch,alltags,new Author(defaultSystemUser));
        drawing=false;
    }

    private void draw(final MouseEvent mouseEvent) {
        path.setLineWidth(PEN_WIDTH);
        drawing=true;
        canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                drawing=true;
                path.beginPath();
                path.moveTo(me.getX(), me.getY());
                path.stroke();
            }
        });
        canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                drawing=true;
                path.lineTo(me.getX(), me.getY());
                path.stroke();
            }
        });
        path.setStroke(Color.BLACK);
    }

    private void erase(final MouseEvent mouseEvent) {
        path.setStroke(Color.WHITE);
        path.setLineWidth(ERASER_WIDTH);
        drawing=true;
    }

    boolean isDrawing() {
        return drawing;
    }

    Set<String> getAllTags() {
        return alltags;
    }
}
