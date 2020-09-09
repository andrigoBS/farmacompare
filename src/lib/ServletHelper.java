package lib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class ServletHelper {
    private final HttpServletResponse response;
    private final HttpServletRequest request;

    public ServletHelper(HttpServletRequest request, HttpServletResponse response){
        this.response = response;
        this.request = request;
    }

    public String getFullPostRequestBody() throws IOException {
        return request.getReader().lines().collect(Collectors.joining());
    }

    public void setResponseSuccess(String bodyJson) throws  IOException{
        setResponseWithBody(200, bodyJson);
    }

    public void setResponseSuccess() throws IOException {
       setResponse(200, true);
    }

    public void setResponseUserError() throws IOException {
        setResponse(400, false);
    }

    public void setResponseServerError() throws IOException {
        setResponse(500, false);
    }

    private void setResponse(int status, boolean success) throws IOException{
        setResponseWithBody(status, "{ \"success\": "+success+" }");
    }

    private void setResponseWithBody(int status, String body) throws IOException{
        response.addHeader("Access-Control-Allow-Origin","*");
        response.setContentType("text/html; charset=UTF-8"); // Tomcat defaults to ISO-8859-1 unless explicictly told not to.
        response.setStatus(status);
        response.getWriter().write(body);
        response.getWriter().flush();
        response.getWriter().close();
    }

    public static boolean haveIntField(Integer field){
        return field != null && field > 0;
    }

    public static boolean haveStringField(String field){
        return field != null && !field.isEmpty();
    }
}
