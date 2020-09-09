package servlet.user.crud;

import lib.ServletHelper;
import lib.dataBase.PGConnection;
import model.entities.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DeleteProfile", urlPatterns = "/deleteProfile")
public class DeleteProfile extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletHelper helper = new ServletHelper(request, response);

        User user = new User();
        user.setFromJson(helper.getFullPostRequestBody());

        try {
            PGConnection connection = new PGConnection(user.getEntityName());
            connection.deleteById(user.getId());
            connection.commitAndClose();

            System.out.println("DELETE: ");
            System.out.println(user);

            helper.setResponseSuccess();
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            helper.setResponseServerError();
        }
    }
}
