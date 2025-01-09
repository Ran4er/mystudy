package org.example;

public class Result {
    private final int x;
    private final Double y;
    private final Double r;
    private final String coordsStatus;
    private final String currentTime;
    private final double benchmarkTime;

    public Result(int x, Double y, Double r, String coordsStatus, String currentTime, double benchmarkTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.coordsStatus = coordsStatus;
        this.currentTime = currentTime;
        this.benchmarkTime = benchmarkTime;
    }

    public int getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public Double getR() {
        return r;
    }

    public String getCoordsStatus() {
        return coordsStatus;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public double getBenchmarkTime() {
        return benchmarkTime;
    }
}
