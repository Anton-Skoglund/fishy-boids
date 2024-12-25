package org.example.fishyboids;

import java.util.*;

public class Boid{
    private double x;
    private double y;
    private double velocity;
    Vector directionVector;
    private double visionRadius;

    Set<Boid> neighborsBoids;

    Vector cohesionVector;
    Vector alignmentVector;
    Vector separationVector;
    double cohesionWeight = 0.00015;
    double alignmentWeight = 0.05;
    double separationWeight = 0.0002;


    private Point centerPoint;


    public Boid(double x, double y, double velocity, double visionRadius){
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.visionRadius = visionRadius;

        centerPoint = new Point(x,y);

        directionVector = randomUnitVector();

        separationVector = new Vector(2);
        alignmentVector = new Vector(2);
        cohesionVector = new Vector(2);



        neighborsBoids = new HashSet<>();
    }

    public Boid(double x, double y, double velocity, double visionRadius, double cohesionWeight, double alignmentWeight, double separationWeight){
        this(x, y, velocity, visionRadius);

        this.cohesionWeight = cohesionWeight;
        this.alignmentWeight = alignmentWeight;
        this.separationWeight = separationWeight;
    }

    public void updatePosition() {
        steerLogic();

        moveBoidsFromAngle();
    }

    private void steerLogic() {
        cohesionVector = cohesionVector();
        alignmentVector = alignmentVector();
        separationVector = separationVector();


        directionVector = directionVector.add(separationVector.scale(separationWeight).get());
        directionVector = directionVector.add(alignmentVector.scale(alignmentWeight).get());
        directionVector = directionVector.add(cohesionVector.scale(cohesionWeight).get());


        double normalizedVector = velocity / directionVector.getLength();
        directionVector.scale(normalizedVector);
    }
    private Vector separationVector() {
        Vector separationVector = new Vector(2);

        if (neighborsBoids.isEmpty()) {
            return separationVector; // Return zero vector if no neighbors
        }

        for (Boid boid : neighborsBoids) {
            Vector direction = new Vector(getCenter(), boid.getCenter());
            double distance = direction.getLength();

            // Avoid division by zero and skip very far boids
            if (distance > 0 && distance < visionRadius) {
                direction.scale(1 / (distance * distance)); // Weight inversely by squared distance
                separationVector = separationVector.add(direction);
            }
        }

        double oldLength = separationVector.getLength();

        if (oldLength > 0) {
            separationVector.scale(1 / oldLength); // Normalize
            double scaleFactor = visionRadius - oldLength;
            separationVector.scale(scaleFactor);
            separationVector.scale(-1); // Reverse direction to repel
        }

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

    public void moveBoid(double x, double y){
        this.x = x;
        this.y = y;
    }

    private Vector randomUnitVector() {
        Random random = new Random();
        double angle = random.nextDouble() * 2 * Math.PI; // Random angle in radians
        double x = Math.cos(angle); // X component
        double y = Math.sin(angle); // Y component
        return new Vector(new double[]{x, y}); // Return unit vector
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

    private Vector getDirectionVector(){
        return directionVector;
    }

}
