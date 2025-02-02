<%--
  Created by IntelliJ IDEA.
  User: Ra4el
  Date: 18.01.2025
  Time: 4:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>

<html>
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <meta name="author" content="Хромов Даниил Тимофеевич">
    <meta name="description" content="Веб-программирование: Лабораторная работа №2. Результаты проверки">
    <meta name="keywords" content="ITMO, ИТМО, ПИиКТ, ВТ, Лабораторная работа, Веб-программирование"/>

    <link href="stylesheets/style.css" rel="stylesheet">
    <link rel="icon" type="image/png" href="images/favicon.png">
    <title>Результаты проверки | Веб-программирование</title>
  </head>
  <body>
  <header class="header-black">

    <h1>Лабораторная работа №2, Хромов Даниил Тимофеевич, Группа: P3218 Вариант: 9484</h1>

  </header>

  <table id="mainTable" class="shaded">
    <thead>
    <td colspan="5">
      <h3>Результаты проверки:</h3>
    </td>
    </thead>

    <tbody>
    <tr>
      <td colspan="5"><hr></td>
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
    <tr>
      <td>
        <div id="goBack">
          <a href="index.jsp">Вернуться к форме</a>
        </div>
      </td>
    </tr>
    </tfoot>

  </table>
  </body>
</html>
