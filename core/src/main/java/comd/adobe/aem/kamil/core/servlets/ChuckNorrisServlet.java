package comd.adobe.aem.kamil.core.servlets;

import comd.adobe.aem.kamil.core.models.ChuckNorrisModel;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class,
        property = {
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.resourceTypes=" + "sling/servlet/default",
                "sling.servlet.selectors=" + "getChuckFacts"
        })
public class ChuckNorrisServlet extends SlingSafeMethodsServlet {
    private final String baseURL = "https://api.chucknorris.io/jokes";

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        StringBuilder urlBuilder = new StringBuilder();
        ChuckNorrisModel chuckNorrisModel = new ChuckNorrisModel();
        urlBuilder.append(baseURL);
        if (!request.getParameter("factCategory").equals("")) {
            urlBuilder.append("/random?category=").append(request.getParameter("factCategory"));
        } else if (!request.getParameter("factTextInput").equals("")) {
            urlBuilder.append("/search?query=").append(request.getParameter("factTextInput"));
        } else {
            urlBuilder.append("/random");
        }
        StringBuilder innerResponse = new StringBuilder();
        innerResponse = chuckNorrisModel.getResponseFromURL(innerResponse, urlBuilder.toString());
        response.getWriter().println(innerResponse);
    }
}
