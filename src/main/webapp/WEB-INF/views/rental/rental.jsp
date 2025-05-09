<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="jakarta.tags.core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>노트북 대여하기</title>
    <style>
      body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        margin: 40px;
        padding: 0;
      }
      .container {
        width: 300px;
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
      form {
        display: flex;
        flex-direction: column;
      }
      label {
        margin-bottom: 5px;
        color: #666;
      }
      input[type="text"],
      input[type="datetime-local"],
      select {
        padding: 10px;
        margin-bottom: 15px;
        border: 1px solid #ddd;
        border-radius: 4px;
      }
      input[type="submit"] {
        background-color: #5cb85c;
        color: white;
        padding: 10px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
      }
      input[type="submit"]:hover {
        background-color: #4cae4c;
      }
      a.cancel-button {
        background-color: #d9534f;
        color: white;
        padding: 10px;
        margin: 10px 0px;
        text-decoration: none;
        border-radius: 4px;
        text-align: center;
      }
      a.cancel-button:hover {
        background-color: #c9302c;
      }
    </style>
  </head>
  <body>
    <div class="container">
      <h2>노트북 대여</h2>
      <form action="/rental/add" method="post">
        <label for="userId">사용자 ID</label>
        <input type="text" id="userId" name="userId" value="${userId}" required readonly />

        <label for="notebookId">노트북 선택</label>
        <select id="notebookId" name="notebookId" required>
          <option value="">노트북을 선택하세요</option>
          <c:forEach var="item" items="${list}">
            <option value="${item.notebookId}">${item.notebookName}</option>
          </c:forEach>
        </select>

        <label for="startTime">시작 시간</label>
        <input type="datetime-local" id="startTime" name="startTime" required />

        <label for="endTime">종료 시간</label>
        <input type="datetime-local" id="endTime" name="endTime" required />

        <input type="submit" value="대여하기" />
        <a href="/" class="cancel-button">취소하기</a>
      </form>
    </div>
  </body>
</html>
