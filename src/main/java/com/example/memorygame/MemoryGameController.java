package com.example.memorygame;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MemoryGameController implements Initializable {
    @FXML
    private Pane pane;

    @FXML
    private ImageView imageViewVerso;

    @FXML
    private ImageView imageViewRecto;

    @FXML
    private Label labelCorrect;




    @FXML
    private List<Group> cartesCliques = new ArrayList<>();
    private List<String> urlsCliques = new ArrayList<>();
    private int correct = 0;

    public void retournerCarte(MouseEvent event) {
        Group clickedGroup = (Group) event.getSource();
        ImageView verso = (ImageView) clickedGroup.getChildren().get(1);
        verso.setOpacity(0);
        urlsCliques.add(((ImageView)clickedGroup.getChildren().get(0)).getImage().getUrl()); // stocke l'URL de l'image
        cartesCliques.add(clickedGroup);

        if(cartesCliques.size() == 2) {
            if(urlsCliques.get(0).equals(urlsCliques.get(1))) {
                correct++;
                labelCorrect.setText("Correct : " + correct);
                cartesCliques.clear();
                urlsCliques.clear();
            } else {
                System.out.println("raté");
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished((ActionEvent event2) -> {
                    annulerOpaciteCartes();
                });
                pause.play();
            }
        }
        if (correct == 12) {
            try {
                Window parentWindow = labelCorrect.getScene().getWindow();
                endGame(parentWindow);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void annulerOpaciteCartes() {
        if(cartesCliques.isEmpty()) {
            return; // la liste est vide, il n'y a rien à faire
        }
        for(Group groupe : cartesCliques) {
            ImageView verso = (ImageView) groupe.getChildren().get(1);
            verso.setOpacity(1);
        }
        cartesCliques.clear();
        urlsCliques.clear();
    }

    private void endGame(Window parentWindow){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Félicitations !");
        alert.setHeaderText("Vous avez terminé le jeu !");
        alert.setContentText("Bravo, vous avez gagné !");
        alert.initOwner(parentWindow);

        alert.setOnCloseRequest(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) alert.getOwner();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        alert.showAndWait();
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
