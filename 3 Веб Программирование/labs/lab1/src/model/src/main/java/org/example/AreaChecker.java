package org.example;

public class AreaChecker {
    
    public boolean inArea(int x, Double y, Double r) {
        
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

}