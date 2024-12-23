package org.example.fishyboids;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import org.example.fishyboids.Point;
import org.example.fishyboids.Vector;

import java.util.*;

import static javafx.scene.paint.Color.BLACK;

public class Boid{
    private double x;
    private double y;
    private double velocity;
    private Vector directionVector;
    private double visionRadius;

    private Set<Boid> neighborsBoids;
    Vector cohesionVector;
    Vector alignmentVector;
    Vector separationVector;

    private Point centerPoint;

    private Circle circle;
    private double width;
    private double height;

    private ProceduralBody body;

    private Circle visionCircle;




    private Random random = new Random();

    public Boid(double x, double y, double velocity, double visionRadius, double size, double width, double height){
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.visionRadius = visionRadius;
        this.width = width;
        this.height = height;

        centerPoint = new Point(x,y);

        directionVector = new Vector(new double[]{random.nextInt(-5,5), random.nextInt(-5, 5)});
        separationVector = new Vector(2);
        alignmentVector = new Vector(2);
        cohesionVector = new Vector(2);


        body = new ProceduralBody(getCenter());

        neighborsBoids = new HashSet<>();

        circle = new Circle(x, y, size, Color.RED);

        visionCircle = new Circle(visionRadius);
        visionCircle.setFill(null);
        visionCircle.setStroke(BLACK);

    }

    public void updatePosition() {
        steerLogic();

        moveBoidsFromAngle();
        screenWrapping();

        updateCirclePosition();
        body.move();
    }

    private void steerLogic() {
        cohesionVector = cohesionVector();
        alignmentVector = alignmentVector();
        separationVector = separationVector();

        double cohesionWeight = 0.00015;
        double alignmentWeight = 0.05;
        double separationWeight = 0.1;


        // angle += random.nextDouble(-0.011, 0.011);
        Vector randomVector = new Vector(new double[]{directionVector.get(0) * random.nextDouble(-0.011, 0.011), directionVector.get(1) * random.nextDouble(-0.011, 0.011)});
        //directionVector = directionVector.add(randomVector);

        directionVector = directionVector.add(separationVector.scale(separationWeight).get());
        directionVector = directionVector.add(alignmentVector.scale(alignmentWeight).get());
        directionVector = directionVector.add(cohesionVector.scale(cohesionWeight).get());


        double normalizedVector = velocity / directionVector.getLength();
        directionVector.scale(normalizedVector);
    }

    private Vector separationVector() {
        // Initialize separationVector with 2 dimensions
        Vector separationVector = new Vector(2);

        // If there are no neighbors, return the zero vector
        if (neighborsBoids.isEmpty()) {
            return separationVector;
        }

        // Compute the separation vector by summing direction vectors from neighbors
        for (Boid boid : neighborsBoids) {
            Vector hold = new Vector(getCenter(), boid.getCenter());
            separationVector = separationVector.add(hold);
        }

        // Store the old length of the separation vector
        double oldLength = separationVector.getLength();

        // Avoid division by zero
        if (oldLength > 0) {
            // Normalize the vector
            separationVector.scale( 1/ (oldLength));

            // Scale it inversely proportional to the distance
            double scaleFactor = visionRadius - oldLength;
            separationVector.scale(scaleFactor);

            // Reverse the vector direction to "push away"
            separationVector.scale(-1);
        }

        // Debugging output
        System.out.println("Length difference: " + (oldLength - separationVector.getLength()));

        return separationVector;
    }


    private Vector alignmentVector() {
        Vector aligmentVector = new Vector(2);
        for(Boid boid : neighborsBoids){
            aligmentVector = aligmentVector.add(boid.getDirectionVector());
        }
        return aligmentVector;
    }

    private Vector cohesionVector() {
        Vector cohesionVector = new Vector(2);

        if(neighborsBoids.isEmpty()) {
            return cohesionVector;
        }


        for(Boid boid : neighborsBoids){
            cohesionVector = cohesionVector.add(new Vector(getCenter(), new Point(boid.getCenter().x, boid.getCenter().y)));
        }

        return cohesionVector;
    }

    private void moveBoidsFromAngle() {
        x  += velocity * directionVector.get(0);
        y += velocity * directionVector.get(1);
    }

    private void updateCirclePosition() {
        circle.setCenterX(x);
        circle.setCenterY(y);

        visionCircle.setCenterX(x);
        visionCircle.setCenterY(y);
    }

    private void screenWrapping() {
        if(x > width){
            x = 0;
        }
        if(y > height){
            y = 0;
        }

        if(x < 0){
            x = width;
        }

        if(y < 0){
            y = height;
        }
    }


    public void addNeighborBoid(Boid boid){
        neighborsBoids.add(boid);
    }

    public void removeNeighborBoid(Boid boid){
        neighborsBoids.remove(boid);
    }

    public Point getCenter() {
        centerPoint.x = x;
        centerPoint.y = y;

        return centerPoint;
    }

    public double getVisionRadius() {
        return visionRadius;
    }


    public List<Line> getNeighborsLines(){
        List<Line> lines = new ArrayList<>();

        neighborsBoids.forEach(neighbor ->{
            lines.add(new Line(this.x, this.y, neighbor.getCenter().x, neighbor.getCenter().y));
        });

        return lines;
    }

    public Vector getDirectionVector(){
        return directionVector;
    }

    public Vector getAligmentVector(){
        return alignmentVector;
    }

    public Vector getCohesionVector(){
        return cohesionVector;
    }

    public Vector getSeparationVector(){
        return separationVector;
    }

    public Circle getCircle() {
        return circle;
    }

    public Circle getVisionCircle() {
        return visionCircle;
    }

    public ProceduralBody getBody() {
        return body;
    }
}
