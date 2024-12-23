package org.example.fishyboids;

import org.example.fishyboids.Point;

import java.util.Optional;

public class Vector {
    private final double[] array;
    private final int length;

    public Vector(int length) {
        if(length <= 0){
            throw new IllegalArgumentException("Not a valid length");
        }

        this.length = length;
        this.array = new double[length];
    }

    public Vector(double[] array) {
        this.length = array.length;
        this.array = array;
    }

    //TODO: generlize
    public Vector(Point p1, Point p2) {
        this.length = 2;

        double newX = Math.abs(p1.x - p2.x);
        double newY = Math.abs(p1.y - p2.y);

        if(p1.x > p2.x){
            newX = -newX;
        }
        if(p1.y > p2.y){
            newY = -newY;
        }

        this.array = new double[] {newX, newY};
    }


    public int amountElements() {
        return length;
    }

    public double getLength(){
        double sum  = 0;
        for(int i = 0; i < this.amountElements(); i++){
            sum += Math.pow(this.get(i), 2);
        }
        return Math.sqrt(sum);

    }

    public double get(int index) {
        if(index < 0 || index >= length){
            throw new IndexOutOfBoundsException();
        }
        return this.array[index];
    }

    // from inclusive -> to non-inclusive
    public double[] get(int from, int to) {
        if(from < 0 || from >= length){
            throw new IndexOutOfBoundsException();
        }

        if(to < from || to > length){
            throw new IndexOutOfBoundsException();
        }

        if(to == from){
            throw new IllegalArgumentException();
        }


        int currentIndex = from;
        int newLength = to - from;

        double[] returnArray = new double[newLength];

        while(currentIndex < to){
            returnArray[currentIndex - from] = this.get(currentIndex);
            currentIndex++;
        }

        return returnArray;
    }

    public double[] getArray(){
        return array.clone();
    }



    public void set(int index, double value) {
        if(index < 0 || index >= length){
            throw new IndexOutOfBoundsException();
        }

        this.array[index] = value;
    }

    public void set(int from, int to, double value) {
        if(from < 0 || from >= length){
            throw new IndexOutOfBoundsException();
        }

        if(to < from || to > length){
            throw new IndexOutOfBoundsException();
        }

        if(to == from){
            throw new IllegalArgumentException();
        }

        int currentIndex = from;
        int newLength = to - from;


        while(currentIndex < to){
            this.array[currentIndex] = value;
            currentIndex++;
        }
    }



    public Vector add(Vector inputVector){
        if(this.length != inputVector.length){
            throw new IllegalArgumentException("Arguments not of same length");
        }

        Vector sumVector = new Vector(this.length);

        for(int i = 0; i < this.length; i++){
            sumVector.set(i, this.get(i) + inputVector.get(i));
        }
        return sumVector;
    }

    public Optional<Vector> scale(double factor){
        for(int i = 0; i < this.length; i++){
            this.array[i] *= factor;
        }
        return Optional.of(this);
    }

    public Vector dotProduct(Vector inputVector){
        if(this.length != inputVector.length){
            throw new IllegalArgumentException("Arguments not of same length");
        }

        Vector sumVector = new Vector(this.length);

        for(int i = 0; i < this.length; i++){
            sumVector.set(i, this.get(i) * inputVector.get(i));
        }
        return sumVector;
    }

    public Vector crossProduct(Vector inputVector){
        if(this.length != inputVector.length){
            throw new IllegalArgumentException("Arguments not of same length");
        }

        if(this.length != 3){
            throw new IllegalArgumentException("Arguments not of length 3");
        }


        double ax = this.get(0);
        double ay = this.get(1);
        double az = this.get(2);

        double bx = inputVector.get(0);
        double by = inputVector.get(1);
        double bz = inputVector.get(2);

        Vector returnVector = new Vector(3);

        returnVector.set(0, ay*bz - az*by);
        returnVector.set(1, az*bx - ax*bz);
        returnVector.set(2, ax*by - ay*bx);


        return returnVector;
    }
}
