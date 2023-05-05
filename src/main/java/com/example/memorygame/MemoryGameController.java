package com.example.memorygame;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

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
            System.out.println(cartesCliques);
            if(urlsCliques.get(0).equals(urlsCliques.get(1))) {
                correct++;
                labelCorrect.setText("Correct : " + correct);
            } else {
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished((ActionEvent event2) -> {
                    annulerOpaciteCartes();
                });
                pause.play();
            }
            cartesCliques.clear();
            urlsCliques.clear();
        }
    }




    private void annulerOpaciteCartes() {
        for(Group groupe : cartesCliques) {
            ImageView verso = (ImageView) groupe.getChildren().get(1);
            verso.setOpacity(1);
        }
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
