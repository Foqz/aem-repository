package comd.adobe.aem.kamil.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(service = Servlet.class,
        property = {
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.resourceTypes=" + "sling/servlet/default",
                "sling.servlet.selectors="+"getChuckFacts"
        })
public class ChuckNorrisServlet extends SlingSafeMethodsServlet {
    private final String baseURL = "https://api.chucknorris.io/jokes";
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(baseURL);
        //działa
        String randomFactCheckboxValue = request.getParameter("randomFactCheckbox");
        String factTextInputValue = request.getParameter("factTextInput");
        String factCategoryValue = request.getParameter("factCategory");
//        for(Map.Entry<String,String> mapEntry : parameteresMap.entrySet())
        if(!factCategoryValue.equals("")){
            urlBuilder.append("/random?category=").append(factCategoryValue);
        } else if (!factTextInputValue.equals("")) {
            urlBuilder.append("/search?query=").append(factTextInputValue);
        } else {
            urlBuilder.append("/random");
        }
        StringBuilder innerResponse = new StringBuilder();
        try {
            HttpURLConnection httpClient = (HttpURLConnection) new URL(urlBuilder.toString()).openConnection();
            httpClient.setRequestMethod("GET");
            httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader in = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                innerResponse.append(line);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        response.getWriter().println(innerResponse);
        System.out.println("TEST");
    }
}
