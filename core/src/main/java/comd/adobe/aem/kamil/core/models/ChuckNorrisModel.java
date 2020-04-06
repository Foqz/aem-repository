package comd.adobe.aem.kamil.core.models;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Model(adaptables = Resource.class)
public class ChuckNorrisModel {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private final String factCategoriesURL = "https://api.chucknorris.io/jokes/categories";
    private final String emptyCategoryString = "None";

    private List<String> factCategories = new ArrayList<>();

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String componentTitle;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private boolean isFactCategory;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private boolean isFactTextSearch;


    @PostConstruct
    public void init() {
        factCategories = getCategoriesFromAPI();
    }

    private List<String> getCategoriesFromAPI() {
        StringBuilder response = new StringBuilder();
        response = getResponseFromURL(response, factCategoriesURL);
        if (!StringUtils.equals("", response.toString())) {
            String[] valuesInQuotes = StringUtils.substringsBetween(response.toString(), "\"", "\"");
            if (valuesInQuotes.length > 0) {
                factCategories.add(emptyCategoryString);
                factCategories.addAll(Arrays.asList(valuesInQuotes));
            }
        }
        return factCategories;
    }

    public StringBuilder getResponseFromURL(StringBuilder response, String URL) {
        try {
            HttpURLConnection httpClient = (HttpURLConnection) new URL(URL).openConnection();
            httpClient.setRequestMethod("GET");
            httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader in = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return response;
    }

    public boolean isEmpty() {
        return true;
    }

    public String getComponentTitle() {
        return componentTitle;
    }

    public List<String> getFactCategories() {
        return factCategories;
    }

    public boolean isFactCategory() {
        return isFactCategory;
    }

    public boolean isFactTextSearch() {
        return isFactTextSearch;
    }
}
