"use strict";

let x, y, r;

window.onload = function () {
    function setOnClick(element) {
        element.onclick = function () {
            r = this.value;
            buttons.forEach(function (element) {
                element.style.boxShadow = "";
                element.style.transform = "";
            });
            this.style.boxShadow = "0 0 40px 5px #f41c52";
            this.style.transform = "scale(1.05)";
        };
    }

    let buttons = document.querySelectorAll("input[name=R-button]");
    buttons.forEach(setOnClick);

    // Загружаем данные из localStorage
    const sessionData = JSON.parse(localStorage.getItem("session")) || [];
    renderHistory(sessionData);
};

document.getElementById("checkButton").addEventListener("click", function (event) {
    event.preventDefault();
    if (validateX() && validateY() && validateR()) {
        const coords = "x=" + encodeURIComponent(x) + "&y=" + encodeURIComponent(y) + "&r=" + encodeURIComponent(r) +
            "&timezone=" + encodeURIComponent(Intl.DateTimeFormat().resolvedOptions().timeZone);

        fetch("http://localhost:8080/fcgi-bin/web1.jar?" + coords, {
            method: "GET",
            headers: { "Content-Type": "application/json; charset=UTF-8" }
        }).then(response => response.json()).then(function (serverAnswer) {
            setPointer(serverAnswer);

            // Сохраняем новый результат
            saveResult(serverAnswer);

            // Обновляем историю
            const sessionData = JSON.parse(localStorage.getItem("session")) || [];
            renderHistory(sessionData);
        }).catch(err => createNotification("Ошибка HTTP " + err.status + ". Повторите попытку позже." + err));
    }
});

function setPointer(serverAnswer) {
    const { x, y, r, result } = serverAnswer;
    let pointer = document.getElementById("pointer");

    pointer.style.visibility = "visible";
    pointer.style.fill = result === "success" ? "#4C956C" : "#D8315B";

    pointer.setAttribute("cx", x * 60 * 2 / r + 150);
    pointer.setAttribute("cy", -y * 60 * 2 / r + 150);
}

function saveResult(serverAnswer) {
    const result = {
        x: serverAnswer.x,
        y: serverAnswer.y,
        r: serverAnswer.r,
        result: serverAnswer.result,
        currentTime: serverAnswer.currentTime,
        executionTime: serverAnswer.executionTime
    };

    // Сохраняем в localStorage
    const sessionData = JSON.parse(localStorage.getItem("session")) || [];
    sessionData.push(result);
    localStorage.setItem("session", JSON.stringify(sessionData));
}

function renderHistory(sessionData) {
    const table = `
        <table id="outputTable">
            <tr>
                <th>X</th>
                <th>Y</th>
                <th>R</th>
                <th>Результат</th>
                <th>Время</th>
                <th>Время работы (ms)</th>
            </tr>
            ${sessionData.map(result => `
                <tr>
                    <td>${result.x}</td>
                    <td>${result.y}</td>
                    <td>${result.r}</td>
                    <td>${result.result}</td>
                    <td>${result.currentTime}</td>
                    <td>${result.executionTime}</td>
                </tr>
            `).join("")}
        </table>
    `;
    document.getElementById("outputContainer").innerHTML = table;
}

function createNotification(message) {
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

function validateX() {
    try {
        x = document.querySelector("select").value;
        return true;
    } catch (err) {
        createNotification("Значение X не выбрано");
        return false;
    }
}

function validateY() {
    y = document.querySelector("input[name=Y-input]").value.replace(",", ".");
    if (y === undefined) {
        createNotification("Y не введён");
        return false;
    } else if (!isNumeric(y)) {
        createNotification("Y не число");
        return false;
    } else if (!((y > -5) && (y < 5))) {
        createNotification("Y не входит в область допустимых значений");
        return false;
    } else return true;
}

function validateR() {
    if (isNumeric(r)) return true;
    else {
        createNotification("Значение R не выбрано");
        return false;
    }
}

function isNumeric(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}