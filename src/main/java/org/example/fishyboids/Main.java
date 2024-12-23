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


    private List<Fish> fishes;

    private Random random = new Random();

    @Override
    public void start(Stage primaryStage) {
        root = new Pane();

        fishes = new ArrayList<>();

        for(int i = 0; i < 50; i++){
            Boid head = new Boid(i * 10 + random.nextDouble() * WIDTH, i * 10 + random.nextDouble() * HEIGHT, 0.40, 50);
            ProceduralBody body = new ProceduralBody(head.getCenter(), 25, k -> (1 - Math.sin(k / 20.0)) * 10 + 5);

            fishes.add(new Fish(head, body));
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

        for (Fish currentFish : fishes) {
            root.getChildren().addAll(currentFish.getBody().getNodes());

            currentFish.update();
            screenWrapping(currentFish.getHead());


            fishes.forEach(neighborBoid -> {
                if (currentFish == neighborBoid) {
                    return; // can't use continues
                }

                double distance = new Vector(currentFish.getHead().getCenter(), neighborBoid.getHead().getCenter()).getLength();

                if(currentFish.getHead().getVisionRadius() > distance){
                    currentFish.getHead().addNeighborBoid(neighborBoid.getHead());
                    return;
                }

                currentFish.getHead().removeNeighborBoid(neighborBoid.getHead());
            });
        }


    }

    private void screenWrapping(Boid boid) {
        double x = boid.getCenter().x;
        double y = boid.getCenter().y;


        if(x > WIDTH){
            x = 0;
        }
        if(y > HEIGHT){
            y = 0;
        }

        if(x < 0){
            x = WIDTH;
        }

        if(y < 0){
            y = HEIGHT;
        }

        boid.moveBoid(x,y);
    }



    public static void main(String[] args) {
        launch(args);
    }
}
