package servlet.user.crud;

import lib.ServletHelper;
import lib.dataBase.PGConnection;
import lib.dataBase.SqlBuilder;
import model.entities.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "SignUp", urlPatterns = "/signUp")
public class SignUp extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletHelper helper = new ServletHelper(request, response);

        User user = new User();
        user.setFromJson(helper.getFullPostRequestBody());

        try {
            PGConnection connection = new PGConnection(user.getEntityName());
            int id = connection.insert(user);
            connection.commitAndClose();

            System.out.println("SIGNUP: ");
            System.out.println(user);

            helper.setResponseSuccess("{ \"id\":"+id+"}");
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            helper.setResponseServerError();
        }
    }
}
