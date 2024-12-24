package org.example.fishyboids;

import java.util.Set;

public class DebugBoid extends Boid{

    public DebugBoid(double x, double y, double velocity, double visionRadius) {
        super(x, y, velocity, visionRadius);
    }

    public Set<Boid> getNeighbors(){
        return this.neighborsBoids;
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

    public double getAlignmentWeight(){
        return alignmentWeight;
    }

    public double getCohesionWeight(){
        return cohesionWeight;
    }

    public double getSeparationWeight(){
        return separationWeight;
    }


}
