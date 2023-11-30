package GUI.view;

import GUI.actions.SetProperties;
import GUI.view.fx.AutoModeController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.IOException;

public class MainPage {
    private Stage primaryStage;

    public void start() {
        JFrame currentFrame = new JFrame() {
        };
        currentFrame.setVisible(true);
        currentFrame.setTitle("Меню выбора");
        currentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        currentFrame.setSize(400, 180);
        currentFrame.setLocation(900, 250);
        currentFrame.setLayout(null);

        final JButton autoMod = new JButton();
        autoMod.setText("Автоматический режим");
        currentFrame.getContentPane().add(autoMod).setBounds(125, 20, 150, 30);
        autoMod.addActionListener(x -> {
            Platform.startup(() -> {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AutoModeWindow.fxml"));
                    Parent root1 = null;
                    root1 = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.setTitle("ABC");
                    stage.setScene(new Scene(root1));
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        });

        final JButton stepMode = new JButton();
        stepMode.setText("Пошаговый режим");
        currentFrame.getContentPane().add(stepMode).setBounds(125, 80, 150, 30);
        stepMode.addActionListener(x -> {
            InputProperties inputProperties = new InputProperties();
            inputProperties.start();
            currentFrame.setVisible(false);
        });
    }
}
