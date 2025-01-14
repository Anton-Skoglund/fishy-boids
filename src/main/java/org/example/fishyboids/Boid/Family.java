package org.example.fishyboids.Boid;

public class Family {
    private String name;

    public Family(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return this.name.equals(((Family) obj).getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
