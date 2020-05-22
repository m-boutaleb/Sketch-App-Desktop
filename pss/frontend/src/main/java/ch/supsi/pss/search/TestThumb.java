package ch.supsi.pss.search;

import ch.supsi.pss.model.Author;
import ch.supsi.pss.model.Sketch;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

public class TestThumb extends Application {

    public static Stage stageTest=null;
    private byte[] parseArray(InputStream stream){
        try {
            BufferedImage bImage  = ImageIO.read(stream);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage,"jpeg",bos);
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void start(Stage stage){
        InputStream inputStream = getClass().getResourceAsStream("/test/index.jpeg");
        byte [] image = parseArray(inputStream);
        stageTest=stage;

        Set<Sketch> prova = new HashSet<>();
        prova.add(new Sketch(image,UUID.randomUUID(),new Author("nicolas","gregori"),
                LocalDateTime.now(),new HashSet<>()));


        Scene scene = new Scene(new SearchResult().getResultTable(prova));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
