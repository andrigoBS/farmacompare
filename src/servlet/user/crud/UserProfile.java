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

@WebServlet(name = "UserProfile", urlPatterns = "/userProfile")
public class UserProfile extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletHelper helper = new ServletHelper(request, response);

        User user = new User();
        user.setFromJson(helper.getFullPostRequestBody());

        try {
            PGConnection connection = new PGConnection(user.getEntityName());
            if(user.havePassword()){
                connection.update("password", "'"+user.getPassword()+"'", user.getId());
            }
            if(user.haveLat()){
                connection.update("lat","'"+user.getLat()+"'", user.getId());
            }
            if(user.haveLng()){
                connection.update("lng", "'"+user.getLng()+"'", user.getId());
            }
            connection.commitAndClose();

            System.out.println("Profile: ");
            System.out.println(user);

            helper.setResponseSuccess();
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            helper.setResponseServerError();
        }
    }
}
