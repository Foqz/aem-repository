package comd.adobe.aem.kamil.core.services;

import comd.adobe.aem.kamil.core.etc.ScrapperItem;
import comd.adobe.aem.kamil.core.models.Scrapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.osgi.service.component.annotations.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

@Component(service = ScrapperService.class, immediate = true)
public class ScrapperService {
    private final String sortTableClass = "sortTable";
    private final String colWalorClass = "colWalor";
    private final String colKursClass = "colKurs";
    private final String colZmianaClass = "colZmiana";
    private final String colZmianaProcentowaClass = "colZmianaProcentowa";
    private final String colLiczbaTransakcjiClass = "colLiczbaTransakcji";
    private final String colObrotClass = "colObrot";
    private final String colOtwarcieClass = "colOtwarcie";
    private final String calMaxiClass = "calMaxi";
    private final String calMiniClass = "calMini";
    private final String colAktualizacjaClass = "colAktualizacja";

    public String[] getColumn(String desiredURL, String columnClass) {
        String[] resultArray = null;
        String[] currentColumnArray = null;
        try {
            Document doc = Jsoup.connect(desiredURL).get();
            Elements elements = doc.getElementsByClass("sortTable");
            for (Element element : elements) {
                String currentColumn = element.getElementsByClass(columnClass).text();
                currentColumnArray = currentColumn.split(" ");

                if (columnClass.equals("colZmianaProcentowa") || columnClass.equals("colLiczbaTransakcji")) {
                    resultArray = Arrays.copyOfRange(currentColumnArray, 3, currentColumnArray.length);
                } else {
                    resultArray = Arrays.copyOfRange(currentColumnArray, 2, currentColumnArray.length);
                }
                if (columnClass.equals("colAktualizacja")) {
                    System.out.println("test");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ;
        }
        return resultArray;
    }

    public List<ScrapperItem> getScrapperItemList(String desiredURL) {
        List<ScrapperItem> resultList = new ArrayList<>();

        try {
            Document document = Jsoup.connect(desiredURL).get();
            Elements tableElements = document.getElementsByClass(sortTableClass);
            for (Element currentElement : tableElements) {
                ListIterator<Node> listIterator = currentElement.childNodes().get(3).childNodes().listIterator();
                while (listIterator.hasNext()) {
                    Node current = listIterator.next();
                    if (current instanceof Element) {
                        ScrapperItem scrapperItem = new ScrapperItem();
                        scrapperItem.setValue(((Element) current).getElementsByClass(colWalorClass).text());
                        scrapperItem.setExchange(((Element) current).getElementsByClass(colKursClass).text());
                        scrapperItem.setChange(((Element) current).getElementsByClass(colZmianaClass).text());
                        scrapperItem.setRotation(((Element) current).getElementsByClass(colObrotClass).text());
                        scrapperItem.setPercentChange(((Element) current).getElementsByClass(colZmianaProcentowaClass).text());
                        scrapperItem.setAmountOfTransactions(((Element) current).getElementsByClass(colLiczbaTransakcjiClass).text());
                        scrapperItem.setOpen(((Element) current).getElementsByClass(colOtwarcieClass).text());
                        scrapperItem.setMax(((Element) current).getElementsByClass(calMaxiClass).text());
                        scrapperItem.setMin(((Element) current).getElementsByClass(calMiniClass).text());
                        scrapperItem.setTime(((Element) current).getElementsByClass(colAktualizacjaClass).text());
                        if (scrapperItem != null && !scrapperItem.getValue().equals("")) {
                            resultList.add(scrapperItem);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
