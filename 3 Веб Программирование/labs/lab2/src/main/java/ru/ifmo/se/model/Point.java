package model;

import java.util.Objects;

public class Point {

    private final double x;
    private final double y;
    private final int r;

    private final boolean inArea;

    public Point(double x, double y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.inArea = isInside(x, y, r);
    }

    private boolean isInside(double x, double y, int r) {

        if (x <= 0 && y <= 0) {
            return (x >= -(r / 2)) && (y >= -r) && (- (2 * x) - y <= r);
        }
        // Rectangle in bottom-right quadrant
        if (x >= 0 && y <= 0) {
            return (x <= r / 2) && (y >= -r);
        }
        // Circle in top-left quadrant
        if (x <= 0 && y >= 0) {
            return (x * x + y * y) <= (r * r);
        }

        // For bottom-left quadrant, always return false
        return false;

    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getR() {
        return r;
    }

    public boolean isInArea() {
        return inArea;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(x, point.x) == 0 && Double.compare(y, point.y) == 0 && r == point.r;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, r);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", r=" + r +
                ", inArea=" + inArea +
                '}';
    }
}
