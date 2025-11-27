package edu.upc.backend.database.util;

// Una estructura Pair tradicional
public class Pair<F,S> {

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    private F first;
    private S second;

    public Pair(F first, S second)
    {
        this.first = first;
        this.second = second;
    }


}
