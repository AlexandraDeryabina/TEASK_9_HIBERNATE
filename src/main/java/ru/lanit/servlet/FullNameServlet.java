package ru.lanit.servlet;

import ru.lanit.entity.Person;
import ru.lanit.repository.PersonRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/fullName")
public class FullNameServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Person person = new Person(
                    request.getParameter("name") != "" ? request.getParameter("name") : null,
                    request.getParameter("surname") != "" ? request.getParameter("surname") : null,
                    LocalDate.parse(request.getParameter("dateOfBirth")));
            person.setPatronymic(request.getParameter("patronymic"));

            PersonRepository.getInstance().save(person);

            request.setAttribute("personList", PersonRepository.getInstance().getList());
            request.getRequestDispatcher("address.jsp").forward(request, response);
        } catch (Exception e) {
            response.getWriter().println("<h1>Ошибки</h1>");
            response.getWriter().println(e.getMessage());
        }
    }
}