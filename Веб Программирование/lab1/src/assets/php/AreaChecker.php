<?php

class AreaChecker{

    public static function inThisArea($x, $y, $r){

        //For triangle in top-left quadrant
        if($x <= 0 && $y >= 0){
            return ($x <= $r/2) && ($y <= $r) && (2 * $x + $y <= $r);
        }
        //For rectangle in bottom-left quadrant
        if($x < 0 && $y < 0){
            return ($x >= -$r) && ($y >= -$r);
        }
        //For circle in bottom-right quadrant
        if($x >= 0 && $y <= 0){
            return ($x * $x + $y * $y) <= ($r * $r);
        }
        
        //For top-right quadrant, always return false;
        return false;
    }

}