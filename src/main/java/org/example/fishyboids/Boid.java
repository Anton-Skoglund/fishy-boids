package org.example.fishyboids;

import java.util.*;

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
    double cohesionWeight = 0.00015;
    double alignmentWeight = 0.05;
    double separationWeight = 0.1;


    private Point centerPoint;


    private Random random = new Random();

    public Boid(double x, double y, double velocity, double visionRadius){
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.visionRadius = visionRadius;

        centerPoint = new Point(x,y);

        directionVector = new Vector(new double[]{1,1});

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
}
