package comd.adobe.aem.kamil.core.models;

import comd.adobe.aem.kamil.core.etc.NasaRandomAnomaly;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
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
import java.time.LocalDate;

@Model(adaptables = Resource.class)
public class NasaComponentModel {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private final String startingURL = "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY";

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String componentTitle;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String anomalyDate;

    private NasaRandomAnomaly nasaRandomAnomaly;

    @PostConstruct
    public void init() {
        nasaRandomAnomaly = generateRandomAnomaly();
    }

    private NasaRandomAnomaly generateRandomAnomaly() {
        StringBuilder response = new StringBuilder();
        try {
            String URL = generateUrl();
            HttpURLConnection httpClient = (HttpURLConnection) new URL(URL).openConnection();
            httpClient.setRequestMethod("GET");
            httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = httpClient.getResponseCode();
            logger.debug("\nSending 'GET' request to URL : " + URL);
            logger.debug("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return populateRandomAnomaly(nasaRandomAnomaly, response.toString());
    }

    private String generateUrl() {
        if (anomalyDate != null) {
            StringBuilder urlBuilder = new StringBuilder();
            String date = anomalyDate.substring(0, anomalyDate.indexOf("T"));
            LocalDate dateToCheck = LocalDate.parse(date);
            if (dateToCheck.compareTo(LocalDate.now()) > 0) {
                return startingURL;
            }
            urlBuilder.append(startingURL).append("&date=").append(date);
            return urlBuilder.toString();
        }
        return startingURL;
    }

    private NasaRandomAnomaly populateRandomAnomaly(NasaRandomAnomaly nasaRandomAnomaly, String response) {
        NasaRandomAnomaly tempNasaRandomAnomaly = new NasaRandomAnomaly();
        try {
            JSONObject jsonObject = new JSONObject(response);
            tempNasaRandomAnomaly.setTitle(getJsonProperties(jsonObject, "title"));
            tempNasaRandomAnomaly.setUrl(getJsonProperties(jsonObject, "url"));
            tempNasaRandomAnomaly.setCopyright(getJsonProperties(jsonObject, "copyright").equals("") ? "" : "Copyright by " + getJsonProperties(jsonObject, "copyright"));
            tempNasaRandomAnomaly.setDate(getJsonProperties(jsonObject, "date"));
            tempNasaRandomAnomaly.setExplanation(getJsonProperties(jsonObject, "explanation"));
            tempNasaRandomAnomaly.setHdurl(getJsonProperties(jsonObject, "hdurl"));
        } catch (JSONException e) {
            logger.error(e.getMessage());
        }
        return tempNasaRandomAnomaly;
    }

    private String getJsonProperties(JSONObject jsonObject, String key) throws JSONException {
        if (jsonObject.has(key)) {
            return jsonObject.getString(key);
        }
        return "";
    }

    public NasaRandomAnomaly getNasaRandomAnomaly() {
        return nasaRandomAnomaly;
    }

    public String getComponentTitle() {
        return componentTitle;
    }
}
