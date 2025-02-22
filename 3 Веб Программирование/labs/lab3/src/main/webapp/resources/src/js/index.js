import { drawCanvasGraph } from './graph';
import "../css/main.css";

document.addEventListener('DOMContentLoaded', function() {
    drawCanvasGraph([], 1);

    const rSpinner = document.querySelector('#r'); // Находим элемент spinner для R
    if (rSpinner) {
        rSpinner.addEventListener('change', function() {
            const newR = parseFloat(rSpinner.value); // Новое значение R
            drawCanvasGraph([], newR); // Перерисовка графика
        });
    }

})

window.drawDots = drawCanvasGraph
function updateErrorMessageR(r) {
    document.querySelector('#error-message').innerHTML = r ? "" : "R не установлено";
}

window.updateErrorMessageR = updateErrorMessageR;