package clientapp.factory;

import clientapp.handler.ProcessFinishedHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PopupFactory {

    public Stage createInfoPopup(String text, ProcessFinishedHandler handler) {
        Stage popupStage = new Stage();
        popupStage.initStyle(StageStyle.UNDECORATED);
        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(10);
        Label label = new Label(text);
        Button okButton = new Button("OK");
        okButton.setStyle(okButtonStyle());
        okButton.setOnMouseEntered(x -> {
            okButton.setStyle(okButtonHoverStyle());
        });
        okButton.setOnMouseExited(x -> {
            okButton.setStyle(okButtonStyle());
        });
        okButton.setOnAction(x -> {
            popupStage.close();
            handler.handle();
        });

        pane.getChildren().addAll(label, okButton);
        popupStage.setScene(new Scene(pane, 300, 200));

        return popupStage;
    }

    private String okButtonStyle() {
        return "-fx-text-fill: #003366;\n" +
                "-fx-background-color: #c7c7c7;\n" +
                "-fx-border-color: #003366;\n" +
                "-fx-background-radius: 0";
    }

    private String okButtonHoverStyle() {
        return "-fx-text-fill: #003366;\n" +
                "-fx-background-color: #e1e1e1;\n" +
                "-fx-border-color: #003366;\n" +
                "-fx-background-radius: 0";
    }

    public Stage createInfoPopup(String text) {
        Stage popupStage = new Stage();
        popupStage.initStyle(StageStyle.UNDECORATED);
        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(10);
        Label label = new Label(text);
        Button okButton = new Button("OK");
        okButton.setStyle(okButtonStyle());
        okButton.setOnMouseEntered(x -> {
            okButton.setStyle(okButtonHoverStyle());
        });
        okButton.setOnMouseExited(x -> {
            okButton.setStyle(okButtonStyle());
        });
        okButton.setOnAction(x -> {
            popupStage.close();
        });

        pane.getChildren().addAll(label, okButton);
        popupStage.setScene(new Scene(pane, 200, 100));

        return popupStage;
    }
}
