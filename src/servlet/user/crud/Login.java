package servlet.user.crud;

import com.google.gson.Gson;
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

@WebServlet(name = "Login", urlPatterns = "/login")
public class Login  extends HttpServlet{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletHelper helper = new ServletHelper(request, response);

        User user = new User();
        user.setFromJson(helper.getFullPostRequestBody());

        try {
            PGConnection connection = new PGConnection(user.getEntityName());
            ResultSet resultSet = connection.execQuery(
                    new SqlBuilder(user.getEntityName())
                            .selectAll()
                            .whereAnd("password='"+user.getPassword()+"'",
                                      "cpf='"+user.getCpf()+"'")
                            .toString());
            if(resultSet.next()) {
                user = new User()
                        .setId(resultSet.getInt("id"))
                        .setName(resultSet.getString("name"))
                        .setLng(resultSet.getDouble("lng"))
                        .setLat(resultSet.getDouble("lat"))
                        .setCpf(resultSet.getString("cpf"));

                connection.commitAndClose();

                System.out.println("Login: ");
                System.out.println(user);

                helper.setResponseSuccess(user.toJson());
            }else{
                helper.setResponseUserError();
            }
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            helper.setResponseServerError();
        }
    }
}
