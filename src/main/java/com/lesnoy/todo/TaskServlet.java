package com.lesnoy.todo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("")
public class TaskServlet extends HttpServlet {

    private final TaskDAO taskDAO = new TaskDAO();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("_method");
        if (method != null) {
            if (method.equals("PUT")) {
                doPut(req, resp);
            } else if (method.equals("DELETE")) {
                doDelete(req, resp);
            }
        } else {
            super.service(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().setAttribute("tasks", taskDAO.getAll());
        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String task = req.getParameter("task");
        String deadPar = req.getParameter("deadline"); // 2024-02-08T13:46
        List<Task> tasks;
        if (!deadPar.isEmpty()) {
            Timestamp deadline = Timestamp.valueOf(deadPar.replace('T', ' ') + ":00");
            tasks = taskDAO.save(new Task(task, deadline));
        } else {
            tasks = taskDAO.save(new Task(task));
        }
        getServletContext().setAttribute("tasks", tasks);
        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int taskId = Integer.parseInt(req.getParameter("task_id"));
        if (taskDAO.deleteById(taskId)) {
            List<Task> tasks = (List<Task>) getServletContext().getAttribute("tasks");
            getServletContext().setAttribute("tasks", tasks.stream()
                    .filter(task -> task.getId() != taskId)
                    .collect(Collectors.toList()));
        }
        req.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int taskId = Integer.parseInt(req.getParameter("task_id"));
        if (taskDAO.updateDone(taskId)) {
            List<Task> tasks = (List<Task>) getServletContext().getAttribute("tasks");
            Task task = tasks.stream()
                    .filter(t -> t.getId() == taskId)
                    .findFirst()
                    .get();
            task.setDone(true);
            getServletContext().setAttribute("tasks", tasks);
        }
        req.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
