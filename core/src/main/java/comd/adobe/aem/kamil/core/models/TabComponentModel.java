package comd.adobe.aem.kamil.core.models;

import comd.adobe.aem.kamil.core.etc.TabDetails;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Model(adaptables = Resource.class)
public class TabComponentModel {

    private List<TabDetails> tabDetailsList;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String tabContainerTitle;
    @SlingObject
    private Resource resource;

    @Inject
    public Resource tabDetails;

    @PostConstruct
    public void init() {
        tabDetailsList = getTabDetailsList(tabDetails);
    }

    public List<TabDetails> getTabDetailsList() {
        return tabDetailsList;
    }

    public String getTabContainerTitle() {
        return tabContainerTitle;
    }

    public boolean isEmpty() {
        return tabDetailsList == null || tabDetailsList.isEmpty();
    }

    private List<TabDetails> getTabDetailsList(Resource tabDetails) {
        List<TabDetails> tabDetailsList = new ArrayList<>();
        Iterator<Resource> iterator = tabDetails.listChildren();
        List<Resource> childNodes = new ArrayList<>();
        iterator.forEachRemaining(childNodes::add);
        childNodes.forEach(r -> populateTabDetailsList(tabDetailsList, r.getValueMap()));
        return tabDetailsList;
    }

    private void populateTabDetailsList(List<TabDetails> tabDetailsList, ValueMap valueMap) {
        TabDetails tabDetails = new TabDetails();
        tabDetails.setTabId((String) valueMap.get("tabId"));
        tabDetails.setTabTitle((String) valueMap.get("tabTitle"));
        tabDetails.setTabDescription((String) valueMap.get("tabDescription"));
        tabDetailsList.add(tabDetails);
    }
}
