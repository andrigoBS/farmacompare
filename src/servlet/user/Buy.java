package servlet.user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Buy", urlPatterns = "/buy")
public class Buy extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private class Basket{
        private Products[] products;
        private int userId;

        private class Products{
            private int id;
            private double value;
            private int howManyUnits;
            private boolean toTake;
        }
    }
}
