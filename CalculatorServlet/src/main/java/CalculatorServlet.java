import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CalculatorServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        double a = Double.parseDouble(
                request.getParameter("num1"));

        double b = Double.parseDouble(
                request.getParameter("num2"));

        String op = request.getParameter("operator");

        double result = 0;
        boolean valid = true;

        switch (op) {

            case "+":
                result = a + b;
                break;

            case "-":
                result = a - b;
                break;

            case "*":
                result = a * b;
                break;

            case "/":
                if (b == 0) {
                    valid = false;
                } else {
                    result = a / b;
                }
                break;

            default:
                valid = false;
        }

        out.println("<html><body>");
        out.println("<h2>Calculator Result</h2>");

        if (valid) {
            out.println("<h3>Result: " + result + "</h3>");
        } else {
            out.println(
                "<h3>Invalid operation or division by zero!</h3>");
        }

        out.println(
            "<a href='/'>Back to Calculator</a>");

        out.println("</body></html>");
    }
}