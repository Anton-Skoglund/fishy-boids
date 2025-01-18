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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.example.fishyboids.Body.DebugBody;
import org.example.fishyboids.Body.ProceduralBody;
import org.example.fishyboids.Boid.Boid;
import org.example.fishyboids.Boid.DebugBoid;
import org.example.fishyboids.Boid.Family;
import org.example.fishyboids.Util.Point;
import org.example.fishyboids.Util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class Main extends Application {
    private Pane root;
    private final int WIDTH = 1920;
    private final int HEIGHT = 1080;

    private List<Fish> fishes;
    private List<Drawable> drawables;
    private List<Barrier> barriers;



    private final Random random = new Random();

    @Override
    public void start(Stage primaryStage) {
        root = new Pane();
        fishes = new ArrayList<>();
        drawables = new ArrayList<>();
        barriers = new ArrayList<>();


        setupBackground();
        createFishes();

        for(int i = 0; i < 10; i++){

            Polygon randomPolygon = generateRandomPolygon((random.nextDouble() * WIDTH), (int) (random.nextDouble() * HEIGHT),200, 100);

            // Create a Barrier with the random polygon
            Barrier<Polygon> barrier = new Barrier<>(randomPolygon);

            drawables.add(barrier);
            barriers.add(barrier);
        }



        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateFishes();
                drawFishes();
            }
        };
        timer.start();


        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setTitle("Boids Simulation with Animation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createFishes() {
        Family red = new Family("red");
        Family blue = new Family("blue");
        Family green = new Family("greessn");

        Family[] families = {blue, red, green};

        for(int i = 0; i < 50; i++){

            Boid head = new Boid(i * 10 + random.nextDouble() * WIDTH, i * 10 + random.nextDouble() * HEIGHT, 0.4, 50, families[random.nextInt(families.length)]);

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

            // RectangleBody body = new RectangleBody(head.getCenter(), amount, bodyFunctions.get(randomIndex), colorFunctions.get(randomIndex % 3));

            ProceduralBody body = new ProceduralBody(head, amount, bodyFunctions.get(randomIndex), colorFunctions.get(head.getFamily().hashCode() % 3));

            // DebugBody body = new DebugBody(head);

            Fish fish = new Fish(head, body);

            fishes.add(fish);
            drawables.add(fish);
        }
    }

    private void updateFishes() {
        for (Fish currentFish : fishes) {
            Boid currentHead = currentFish.getHead();

            /*
            for(Barrier barrier : barriers){
                ArrayList<Boolean> result = new ArrayList<>();
                int i = 0;
                for(Line line : currentFish.getVissionLines()){
                    result[i] = Detection.lineIntersection(line, barrier);
                    i++;
                }

                currentFish.setObsticalsInView(result);
            }
            */

            currentFish.update();
            screenWrapping(currentFish.getHead());

            for(Point point : currentHead.getRay().getRayPoints()){
                for(Barrier barrier : barriers){
                    if(barrier.inside(point)){
                        currentHead.collision();

                    }

                }
            }

            for(Fish neighborFish : fishes){
                Boid neighborHead = neighborFish.getHead();

                if (currentFish == neighborFish) {
                    continue;
                }

                Vector distanceVector = new Vector(currentHead.getCenter(), neighborHead.getCenter());
                double distance = distanceVector.getLength();

                if (currentHead.getVisionRadius() > distance) {
                    currentHead.addNeighborBoid(neighborFish.getHead());
                }else{
                    currentHead.removeNeighborBoid(neighborFish.getHead());
                }
            }
        }
    }

    private void drawFishes() {
        //TODO
        // - canvas is better
        root.getChildren().clear();

        for (Drawable currentFish : drawables) {
            root.getChildren().addAll(currentFish.getNodes());
        }
    }

    private void setupBackground() {
        LinearGradient gradient = new LinearGradient(
                0, 0, 1, 1, // startX, startY, endX, endY
                true, // proportional (true means relative to the container size)
                CycleMethod.NO_CYCLE, // gradient will not repeat
                new Stop(0, Color.BLACK), // starting color
                new Stop(1, Color.rgb(0, 8 ,50)) // ending color
        );

        root.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, null)));
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
    private static Polygon generateRandomPolygon(double startX, double startY, int numVertices, double stepSize) {
        Random random = new Random();
        Polygon polygon = new Polygon();

        double angleStep = 2 * Math.PI / numVertices; // Fixed angle step for uniform distribution
        double currentAngle = 0; // Random starting angle

        // Generate vertices
        for (int i = 0; i < numVertices; i++) {
            double x = startX + Math.cos(currentAngle) * stepSize;
            double y = startY + Math.sin(currentAngle) * stepSize;

            // Add point to the polygon
            polygon.getPoints().addAll(x, y);

            // Increment angle
            currentAngle += angleStep;

            // Randomly adjust step size (optional, small variation)
            stepSize += random.nextDouble(-0.075 * stepSize, 0.075 * stepSize);
        }


        polygon.setFill(Color.rgb(255, 255 ,255));
        return polygon;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
