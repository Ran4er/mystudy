<?php

class CoordinatesValidator{

    private $x;
    private $y;
    private $r;

    public function __constructor($x, $y, $r){
        $this->x = $x;
        $this->y = $y;
        $this->r = $r;
    }

    public function checkData(){
        return $this->checkX() && $this->checkY() && $this->checkR();
    }

    private function checkX(){
        return in_array($this->x, array(-5, -4, -3, -2, -1, 0, 1, 2, 3));
    }

    private function checkY(){
        return is_numeric($this->y) && ($this->y > -5 && $this->y < 3);
    }

    private function checkR(){
        return is_numeric($this->r) && ($this->r > 2 && $this->r < 5);
    }

}