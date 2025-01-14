package org.example.fishyboids.Util;

public class Point {
    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Override equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Same reference
        if (obj == null || getClass() != obj.getClass()) return false; // Null or different class

        Point point = (Point) obj; // Typecast

        // Compare fields for equality
        return (x - point.x) < 0.001 && (y - point.y) < 0.001;
    }

    @Override
    public String toString(){
        return "(" + x + ", " + y + ")";
    }
}
