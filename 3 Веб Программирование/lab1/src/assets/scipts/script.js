"use strict";

let x, y, r;

window.onload = function(){

    let buttons = document.querySelectorAll("input[name=R-button]");
  buttons.forEach(setOnClick);

  document.getElementById("outputContainer").innerHTML = localStorage.getItem("session");
};

document.getElementById("checkButton").onclick = function () {
    if (validateX() && validateY() && validateR()) {
      const coords = "x=" + encodeURIComponent(x) + "&y=" + encodeURIComponent(y) + "&r=" + encodeURIComponent(r);
  
      fetch("./php/script.php?" + coords, {
        method: "GET",
        headers: { "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8" }
      }).then(response => response.text()).then(function (serverAnswer) {
        setPointer(serverAnswer);
        localStorage.setItem("session", serverAnswer);
        document.getElementById("outputContainer").innerHTML = serverAnswer;
      }).catch(err => createNotification("Ошибка HTTP " + err.status + ". Повторите попытку позже." + err));
    }
  };

function setPointer(serverAnswer) {
    const parser = new DOMParser();
    const doc = parser.parseFromString(serverAnswer, "text/html");
    const row = doc.querySelectorAll('tr')[1];
    if (!row) return;
  
    const cells = row.getElementsByTagName("td");
    const last = cells[3];
  
    let pointer = document.getElementById("pointer");
  
    pointer.style.visibility = "visible";
    pointer.style.fill = last.innerHTML.includes("success") ? "#09a53d" : "#a50909";
  
    pointer.setAttribute("cx", x * 60 * 2 / r + 150);
    pointer.setAttribute("cy", -y * 60 * 2 / r + 150);
  }

function createNotification(){
    let outputContainer = document.getElementById("outputContainer");
    if (outputContainer.contains(document.querySelector(".notification"))) {
        let stub = document.querySelector(".notification");
        stub.textContent = message;
        stub.classList.replace("outputStub", "errorStub");
    } else {
        let notificationTableRow = document.createElement("h4");
        notificationTableRow.innerHTML = "<span class='notification errorStub'></span>";
        outputContainer.prepend(notificationTableRow);
        let span = document.querySelector(".notification");
        span.textContent = message;
    }
}


function validatingX(){
    try {
        x = document.querySelector("input[type=checkbox]:checked").value;
        return true;
      } catch (err) {
        createNotification("Значение X не выбрано");
        return false;
      }
}

function validatingY(){
    y = document.querySelector("input[name=Y-input]").value.replace(",", ".");
    if (y === undefined) {
        createNotification("Y не введён");
        return false;
    } else if (!isNumeric(y)) {
        createNotification("Y не число");
        return false;
    } else if (!((y > -5) && (y < 3))) {
        createNotification("Y не входит в область допустимых значений");
        return false;
    } else return true;
}

function validatingR(){
    r = document.querySelector("input[name=R-input]").value.replace(",", ".");
    if (y === undefined) {
        createNotification("R не введён");
        return false;
    } else if (!isNumeric(y)) {
        createNotification("R не число");
        return false;
    } else if (!((y > 2) && (y < 5))) {
        createNotification("R не входит в область допустимых значений");
        return false;
    } else return true;
}

function isNumeric(n){
    return !isNaN(parseFloat(n) && isFinite(n));
}