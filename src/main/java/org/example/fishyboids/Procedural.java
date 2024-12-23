package org.example.fishyboids;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.fishyboids.Point;


public class Procedural extends Application  {
    private Point head;
    private ProceduralBody body;

    private Pane root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        root = new Pane();

        head = new Point(0,0);
        body = new ProceduralBody(head, 25, i -> (1 - Math.sin(i / 20.0)) * 10 + 5);


        root.getChildren().addAll(body.getNodes());

        Scene scene = new Scene(root, 600, 400);

        // Set mouse movement event
        scene.setOnMouseMoved(this::handleMouseMovement);

        primaryStage.setTitle("Mouse Follow Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleMouseMovement(MouseEvent event) {
        head.x = event.getX();
        head.y = event.getY();
        body.move();
    }
}
