package org.example.fishyboids;

public class DebugBoid extends Boid{

    public DebugBoid(double x, double y, double velocity, double visionRadius) {
        super(x, y, velocity, visionRadius);
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
