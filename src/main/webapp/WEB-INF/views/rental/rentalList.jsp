<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>대여 내역 조회</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 40px;
            padding: 0;
        }
        .container {
            width: 600px;
            margin: auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
        }
        input[type="submit"] {
            background-color: #d9534f;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #c9302c;
        }
        a.main-button {
            background-color: #5bc0de;
            color: white;
            padding: 10px;
            text-decoration: none;
            border-radius: 4px;
            text-align: center;
        }
        a.main-button:hover {
            background-color: #31b0d5;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>대여 내역</h2>
        <form action="/rental/delete" method="post">
            <table>
                <thead>
                    <tr>
                        <th>선택</th>
                        <th>노트북 이름</th>
                        <th>시작 시간</th>
                        <th>종료 시간</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${list}">
                        <tr>
                            <td><input type="checkbox" name="rentalId" value="${item.rentalId }"></td>
                            <td>${item.notebook.notebookName }</td>
                            <td>${item.startTime }</td>
                            <td>${item.endTime }</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <input type="submit" value="대여 취소">
            <a href="/" class="main-button">메인으로</a>
        </form>
    </div>
</body>
</html>
