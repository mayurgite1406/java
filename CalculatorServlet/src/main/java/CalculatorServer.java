import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;

public class CalculatorServer {

    public static void main(String[] args) throws Exception {

        Tomcat tomcat = new Tomcat();

        tomcat.setPort(8080);

        tomcat.getConnector();

        File webapp = new File("src/main/webapp");

        Context context = tomcat.addContext(
                "",
                webapp.getAbsolutePath()
        );

        Wrapper servlet = Tomcat.addServlet(
                context,
                "CalculatorServlet",
                new CalculatorServlet()
        );

        servlet.setLoadOnStartup(1);

        context.addServletMappingDecoded(
                "/calculator",
                "CalculatorServlet"
        );

        tomcat.start();

        System.out.println(
                "Calculator running at http://localhost:8080/"
        );

        tomcat.getServer().await();
    }
}