<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com"/>
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin/>
    <link
            href="https://fonts.googleapis.com/css2?family=Play&family=Poppins&display=swap"
            rel="stylesheet"
    />
    <meta charset="UTF-8"/>
    <link rel="stylesheet" href="static/style.css">
    <title>ToDoList</title>
</head>
<body>
<div class="container">
    <div class="todo-app">
        <h2>To-Do List <img src="/static/todo-icon.png"/></h2>
        <form method="POST">
            <div class="row">
                <input class="task-inp" type="text" name="task" placeholder="Add your mission"/>
                <input class="date-inp" type="datetime-local" name="deadline">
                <button class="add-btn" type="submit">Add</button>
            </div>
        </form>
        <ul class="list-bl">
            <c:forEach var="t" items="${tasks}">
                    <li class=${t.isDone() ? "checked" : "list-i"}>
                        <form method="POST" class="circle-btn">
                            <input type="hidden" name="_method" value="PUT">
                            <input type="hidden" name="task_id" value="${t.getId()}">
                            <button class=${t.isDone() ? "checked-circle" : "task-circle"}></button>
                        </form>
                            ${t.getTask()}
                        <div class="deadline">
                            <div class="deadline">${t.getFormatDeadline()}</div>
                        </div>
                        <form method="POST">
                            <input type="hidden" name="_method" value="DELETE">
                            <input type="hidden" name="task_id" value="${t.getId()}">
                            <button class="task-del"></button>
                        </form>
                    </li>
            </c:forEach>
        </ul>
    </div>
</div>
</body>
</html>
