package org.example.fishyboids;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class Main extends Application {
    private Pane root;
    private final int WIDTH = 1920;
    private final int HEIGHT = 1080;

    private List<Fish> fishes;

    private final Random random = new Random();

    @Override
    public void start(Stage primaryStage) {
        root = new Pane();

        fishes = new ArrayList<>();

        for(int i = 0; i < 50; i++){
            Boid head = new Boid(i * 10 + random.nextDouble() * WIDTH, i * 10 + random.nextDouble() * HEIGHT, 0.40, 50);

            int amount = 25;
            double scale = 10;
            double offset = 5;
            List<Function<Double, Double>> bodyFunctions = List.of(
                    k -> Math.abs(Math.cos((k / (amount + 10.0)) * Math.PI)) * scale + offset,
                    k -> Math.abs(Math.sin((k / (amount + 10.0)) * Math.PI)) * scale + offset,
                    k -> (Math.abs(((k / (amount + 10.0)) % 1) - 0.5) * 2) * scale + offset,
                    k -> Math.pow(Math.abs(k / (amount + 10.0) - 0.5) * 2, 2) * scale + offset,
                    k -> Math.exp(-Math.abs(k / (amount + 10.0) - 0.5) * 2) * scale + offset,
                    k -> (1 - Math.abs(((k / (amount + 10.0)) % 2) - 1)) * scale + offset,
                    k -> Math.log(Math.abs(k / (amount + 10.0) - 0.5) * 10 + 1) * scale + offset,
                    k -> Math.exp(-Math.pow((k / (amount + 10.0) - 0.5) * 2, 2)) * scale + offset
            );

            List<Function<Integer, Color>> colorFunctions = List.of(
                    k -> Color.rgb(0, 0 ,  Math.max(k * (255 / amount), (amount - k) * (255 / amount))),
                    k -> Color.rgb(0, Math.max(k * (255 / amount), (amount - k) * (255 / amount)) ,  0),
                    k -> Color.rgb(Math.max(k * (255 / amount), (amount - k) * (255 / amount)), 0 ,  0)
            );

            int randomIndex = random.nextInt(bodyFunctions.size());

            ProceduralBody body = new ProceduralBody(head.getCenter(), amount, bodyFunctions.get(randomIndex), colorFunctions.get(randomIndex % 3));
            // RectangleBody body = new RectangleBody(head.getCenter(), amount, bodyFunctions.get(randomIndex), colorFunctions.get(randomIndex % 3));

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

        LinearGradient gradient = new LinearGradient(
                0, 0, 1, 1, // startX, startY, endX, endY
                true, // proportional (true means relative to the container size)
                CycleMethod.NO_CYCLE, // gradient will not repeat
                new Stop(0, Color.BLACK), // starting color
                new Stop(1, Color.rgb(0, 8 ,50)) // ending color
        );

        root.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, null)));

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

                if(currentFish.getHead().   getVisionRadius() > distance){
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
