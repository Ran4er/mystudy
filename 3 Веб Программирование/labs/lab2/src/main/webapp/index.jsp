<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="ru.ifmo.se.model.Point" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <meta name="author" content="Даниил Хромов">
    <meta name="description" content="Веб-программирование: Лабораторная работа №2">
    <meta name="keywords" content="ITMO, ИТМО, ПИиКТ, ВТ, Лабораторная работа, Веб-программирование">

    <link rel="stylesheet" href="stylesheets/style.css">
    <link rel="icon" type="image/png" href="images/favicon.png">

    <title>Лабораторная работа №2 | Веб-программирование</title>
</head>

<body>
<header class="header-black">

    <h1>Лабораторная работа №2, Хромов Даниил Тимофеевич, Группа: P3218 Вариант: 9484</h1>

</header>

<table id="mainTable">
    <thead>
    <td colspan="5">
        <h3>Валидация введённых значений:</h3>
    </td>
    </thead>
    <tbody>
    <tr>
        <td colspan="5">
            <hr>
        </td>
    </tr>

    <tr>
        <td>Введите X:</td>
        <td colspan="3"><input required name="X-input" class="illuminated animated" type="text"
                               placeholder="(от -5 до 5)" maxlength="6" pattern="^-[0-5][0-5]"></td>
        <td rowspan="6">
            <svg id="svg" class="graph" xmlns="http://www.w3.org/2000/svg">
                <line x1="0" y1="150" x2="300" y2="150" stroke="#0A2463"></line>
                <line x1="150" y1="0" x2="150" y2="300" stroke="#0A2463"></line>
                <line x1="270" y1="148" x2="270" y2="152" stroke="#0A2463"></line>
                <text x="265" y="140">R</text>
                <line x1="210" y1="148" x2="210" y2="152" stroke="#0A2463"></line>
                <text x="200" y="140">R/2</text>
                <line x1="90" y1="148" x2="90" y2="152" stroke="#0A2463"></line>
                <text x="75" y="140">-R/2</text>
                <line x1="30" y1="148" x2="30" y2="152" stroke="#0A2463"></line>
                <text x="20" y="140">-R</text>
                <line x1="148" y1="30" x2="152" y2="30" stroke="#0A2463"></line>
                <text x="156" y="35">R</text>
                <line x1="148" y1="90" x2="152" y2="90" stroke="#0A2463"></line>
                <text x="156" y="95">R/2</text>
                <line x1="148" y1="210" x2="152" y2="210" stroke="#0A2463"></line>
                <text x="156" y="215">-R/2</text>
                <line x1="148" y1="270" x2="152" y2="270" stroke="#0A2463"></line>
                <text x="156" y="275">-R</text>

                <polygon points="300,150 295,155 295, 145" fill="#0A2463" stroke="#0A2463"></polygon>
                <polygon points="150,0 145,5 155,5" fill="#0A2463" stroke="#0A2463"></polygon>


                <polygon points="150,150 150,270 90,150" fill-opacity="0.4" stroke="navy" fill="#3E92CC"></polygon>

                <rect x="150" y="150" width="60" height="120" fill-opacity="0.4" stroke="navy" fill="#3E92CC"></rect>

                <path d="M 150 150 L 30 150 C 30 90 90 30 150 30 Z" fill-opacity="0.4" stroke="navy" fill="#3E92CC"></path>

                <circle id="pointer" r="5" cx="150" cy="150" fill-opacity="0.9" fill="red" stroke="firebrick"
                        visibility="hidden"></circle>
            </svg>
        </td>
    </tr>

    <tr>
        <td>Введите Y:</td>
        <td colspan="3"><input required name="Y-input" class="illuminated animated" type="text"
                               placeholder="(от -3 до 3)" maxlength="6" pattern="^-[0-5][0-5]"></td>
    </tr>

    <tr>
        <td rowspan="2">Выберите R:</td>
        <td>1<input name="R-radio-group" class="illuminated animated" type="radio" value="1"></td>
        <td>2<input name="R-radio-group" class="illuminated animated" type="radio" value="2"></td>
        <td>3<input name="R-radio-group" class="illuminated animated" type="radio" value="3"></td>
    </tr>
    <tr>
        <td>4<input name="R-radio-group" class="illuminated animated" type="radio" value="4"></td>
        <td>5<input name="R-radio-group" class="illuminated animated" type="radio" value="5"></td>
    </tr>

    <tr>
        <td colspan="5">
            <button type="submit" id="checkButton">Проверить</button>
        </td>
    </tr>

    <tr>
        <td colspan="5">
            <hr>
        </td>
    </tr>
    </tbody>

    <tfoot>
    <tr>
        <td colspan="5" id="outputContainer">
            <% ServletContext context = application;
                List<String> results = (List<String>) context.getAttribute("results");
                if (results == null) {
            %>
            <h4>
                <span class="outputStub notification">Нет результатов</span>
            </h4>
            <table id="outputTable">
                <tr>
                    <th>X</th>
                    <th>Y</th>
                    <th>R</th>
                    <th>Точка входит в ОДЗ</th>
                </tr>
            </table>
            <% } else { %>
            <h4>
                <span class="notification"></span>
            </h4>
            <table id="outputTable">
                <tr>
                    <th>X</th>
                    <th>Y</th>
                    <th>R</th>
                    <th>Точка входит в ОДЗ</th>
                </tr>
                <% for (String result : results) {
                    String pointString = result;
                    pointString = pointString.replace("Point{", "").replace("}", "");
                    String[] attributes = pointString.split(", ");

                    String x = attributes[0].split("=")[1];
                    String y = attributes[1].split("=")[1];
                    String r = attributes[2].split("=")[1];
                    String inArea = attributes[3].split("=")[1];
                %>
                <tr>
                    <td><%= x %></td>
                    <td><%= y %></td>
                    <td><%= r %></td>
                    <td><%= inArea.equals("true") ? "Попал" : "Промазал" %></td>
                </tr>
                <%
                        }
                    }
                %>
            </table>
        </td>
    </tr>
    </tfoot>

</table>
<script
        src="https://code.jquery.com/jquery-3.7.1.min.js"
        integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
        crossorigin="anonymous"></script>
<script src="script.js"></script>
</body>

</html>