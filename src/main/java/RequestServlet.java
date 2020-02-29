import dao.DataAccessObject;
import entities.RequestsEntityImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

//@WebServlet(name = "Request", urlPatterns = "/sendReq")
public class RequestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String patronymic = request.getParameter("patronymic");
        String gender = request.getParameter("gender");
        String birtDate = request.getParameter("birthDate");

        Date date = new Date(1998, 1, 27);

        DataAccessObject<RequestsEntityImpl> requestDao
                = new DataAccessObject<RequestsEntityImpl>(RequestsEntityImpl.class);

        RequestsEntityImpl requestsEntity
                = new RequestsEntityImpl(firstName, lastName, patronymic, "M", date, date);
        requestDao.create(requestsEntity);


        System.out.println(firstName + " " +
                lastName + " " +
                patronymic + " " +
                gender + " " +
                birtDate);

        response.setStatus(response.SC_MOVED_PERMANENTLY);
        response.setHeader("Location", "localhost:8080");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("GetPage");
    }

}
