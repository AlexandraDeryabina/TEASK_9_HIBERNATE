package ru.lanit.servlet;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.lanit.entity.Address;
import ru.lanit.entity.Person;
import ru.lanit.provider.SessionProvider;
import ru.lanit.repository.AddressRepository;
import ru.lanit.repository.PersonRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/address")
public class AddressServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Address address = new Address();
            address.setStreet(request.getParameter("street") != "" ? request.getParameter("street") : null);
            address.setHouse(request.getParameter("house") != "" ? request.getParameter("house") : null);
            address.setFlat(request.getParameter("flat"));
            address.setPerson(PersonRepository.getInstance().
                    getById(Integer.parseInt(request.getParameter("person"))));

            AddressRepository.getInstance().save(address);

            request.setAttribute("personList", PersonRepository.getInstance().getList(true));
            request.getRequestDispatcher("result.jsp").forward(request, response);
        } catch (Exception e) {
            response.getWriter().println("<h1>Ошибки</h1>");
            response.getWriter().println(e.getMessage());
        }
    }
}