package org.example.fishyboids;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application {
    private Pane root;
    private final int WIDTH = 1920;
    private final int HEIGHT = 1080;



    private List<Boid> boids;

    private Random random = new Random();

    @Override
    public void start(Stage primaryStage) {
        root = new Pane();

        boids = new ArrayList<>();

        for(int i = 0; i < 50; i++){
            boids.add(new Boid(i * 10 + random.nextDouble() * WIDTH, i * 10 + random.nextDouble() * HEIGHT, 0.40, 50, 10, WIDTH, HEIGHT));
        }


        // Animation timer for updating boids
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateBoids();
            }
        };
        timer.start();

        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));


        Scene scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setTitle("Boids Simulation with Animation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to update the boids' positions
    private void updateBoids() {
        // canvas is better
        root.getChildren().clear();

        for (Boid currentBoid : boids) {
            // root.getChildren().add(currentBoid.getCircle());
            // root.getChildren().add(currentBoid.getVisionCircle());

            Vector directionVector = currentBoid.getDirectionVector();
            // root.getChildren().add(new Line(currentBoid.getCenter().x, currentBoid.getCenter().y, currentBoid.getCenter().x + directionVector.get(0) * 100, currentBoid.getCenter().y + directionVector.get(1) * 100));

            root.getChildren().addAll(currentBoid.getBody().getNodes());



            currentBoid.updatePosition(); // Move each boid
            boids.forEach(neighborBoid -> {
                if (currentBoid == neighborBoid) {
                    return; // can't use continues
                }

                double distance = new Vector(currentBoid.getCenter(), neighborBoid.getCenter()).getLength();

                if(currentBoid.getVisionRadius() > distance){
                    currentBoid.addNeighborBoid(neighborBoid);
                    return;
                }

                currentBoid.removeNeighborBoid(neighborBoid);
            });


            // currentBoid.getNeighborsLines().forEach(line -> root.getChildren().add(line));
        }


    }

    public static void main(String[] args) {
        launch(args);
    }
}
