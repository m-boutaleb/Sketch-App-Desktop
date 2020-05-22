package ch.supsi.pss.controller;

import ch.supsi.pss.bundles.ResourceBundlePss;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class About implements Initializable {
    @FXML
    public Label h2Version;
    @FXML
    public Label h1Version;
    @FXML
    public Label build;

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        var res=ResourceBundlePss.getInstance().getPssBundles();
        final String appVersion= res.getString("app.pss.version");
        final String artifactId=res.getString("app.pss.artifactid");
        final String modelVersion=res.getString("app.pss.model.version");
        final String javaVersion=res.getString("app.pss.java.version");
        h2Version.setText(h2Version.getText()+" "+appVersion);
        h1Version.setText(h1Version.getText()+" "+ appVersion);
        this.build.setText(artifactId+", maven " + modelVersion+", JDK "+javaVersion);
    }
}
