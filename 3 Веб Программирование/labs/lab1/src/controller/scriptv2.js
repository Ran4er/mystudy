"use strict";

let x, y, r;

/*
.then(response => {
            if (!response.ok) {
                throw new Error(`${response.status}`);
            }
            return response.text();
        })
        .then(function (answer) {
            localStorage.setItem("session", answer);
            var res = JSON.parse(answer);
            var table = document.getElementById("outputContainer"),
                tbody = table.getElementsByTagName("tbody")[0];
            var row = document.createElement("tr");
            var isHit = document.createElement("td");
            var x = document.createElement("td");
            var y = document.createElement("td");
            var r = document.createElement("td");
            var time = document.createElement("td");
            var worktime = document.createElement("td");
            if (res.error === 'all ok') {
                if (res.result === "true"){
                    isHit.innerText = "Точно в цель";
                }
                else {
                    isHit.innerText = "Попробуйте ещё раз";
                }

                x.innerText = res.x;
                y.innerText = res.y;
                r.innerText = res.r;
                time.innerText = res.time;
                worktime.innerText = res.workTime;
                row.appendChild(isHit);
                row.appendChild(x);
                row.appendChild(y);
                row.appendChild(r);
                row.appendChild(time);
                row.appendChild(worktime);
                table.appendChild(row);

                setPointer(answer.json());
            } else {
                throw new Error(`${response.status}`);
            }
        })
*/

window.onload = function () {
    function setOnClick(element) {
        element.onclick = function () {
            r = this.value;
            buttons.forEach(function (element) {
                element.style.transform = "";
            });
            this.style.transform = "scale(1.05)";
        };
    }

    let buttons = document.querySelectorAll("input[name=R-button]");
    buttons.forEach(setOnClick);
};

document.getElementById("checkButton").addEventListener("click", function (event) {
    event.preventDefault();
    if (validateX() && validateY() && validateR()) {
        const coords = "x=" + encodeURIComponent(x) + "&y=" + encodeURIComponent(y) + "&r=" + encodeURIComponent(r);

        fetch("http://localhost:8080/fcgi-bin/web1.jar?" + coords, {
            method: "GET",
            headers: { "Content-Type": "application/json; charset=UTF-8" }
        }).then(response => {
            if (!response.ok) {
                throw new Error(`${response.status}`);
            }
            return response.json();
        }).then(function (serverAnswer) {
            let sessionData = JSON.parse(localStorage.getItem("session"));
            
            if (!sessionData || !Array.isArray(sessionData)) {
                sessionData = [];
            }

            sessionData.push(serverAnswer);
            localStorage.setItem("session", JSON.stringify(sessionData));
            
            renderHistory(sessionData);
            setPointer(serverAnswer);
        })
        .catch(err => createNotification("Ошибка HTTP " + err.status + ". Повторите попытку позже." + err));
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