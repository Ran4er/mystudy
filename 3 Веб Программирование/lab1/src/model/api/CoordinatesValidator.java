package model.api;

import java.util.Arrays;
import java.util.List;

public class CoordinatesValidator {
    
    private int x;
    private Double y;
    private Double R;

    public CoordinatesValidator(int x, Double y, Double R) {
        this.x = x;
        this.y = y;
        this.R = R;
    }

    public boolean checkData() {
        return checkX() && checkY() && checkR();
    }

    public boolean checkX() {
        List<Integer> validValues = Arrays.asList(-4, -3, -2, -1, 0, 1, 2, 3, 4);
        return validValues.contains(this.x);
    }

    public boolean checkY() {
        return this.y != null && this.y > -5 && this.y < 5;
    }

    public boolean checkR() {
        List<Double> validValues = Arrays.asList(1.0, 1.5, 2.0, 2.5, 3.0);
        return validValues.contains(this.R);
    }

}

