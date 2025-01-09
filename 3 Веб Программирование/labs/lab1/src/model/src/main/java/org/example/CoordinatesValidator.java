package org.example;

import java.util.Arrays;
import java.util.List;

public class CoordinatesValidator {
    
    private int x;
    private Double y;
    private Double R;
    private String errorMessage;

    public CoordinatesValidator() {}

    public CoordinatesValidator(int x, Double y, Double R) {
        this.x = x;
        this.y = y;
        this.R = R;
    }

    public boolean check(int x, Double y, Double R) {
        this.x = x;
        this.y = y;
        this.R = R;
        return checkData() && checkR() && checkX() && checkY();
    }

    public boolean checkData() {
        return checkX() && checkY() && checkR();
    }

    public boolean checkX() {
        List<Integer> validValues = Arrays.asList(-4, -3, -2, -1, 0, 1, 2, 3, 4);
        if(!validValues.contains(this.x)) {
            errorMessage = "Coordinates must be between -4 and 4";
        }
        return validValues.contains(this.x);
    }

    public boolean checkY() {
        if (!(this.y != null && this.y > -5 && this.y < 5)) errorMessage = "Coordinates must be between -5 and 5";
        return this.y != null && this.y > -5 && this.y < 5;
    }

    public boolean checkR() {
        List<Double> validValues = Arrays.asList(1.0, 1.5, 2.0, 2.5, 3.0);
        if (!validValues.contains(this.R)) errorMessage = "Coordinates must be choice at 1.0 to 3.0";
        return validValues.contains(this.R);
    }

    public String getErrorMessage() {
        return "Error:" + errorMessage;
    }
}

