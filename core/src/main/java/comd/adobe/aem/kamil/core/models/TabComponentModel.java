package comd.adobe.aem.kamil.core.models;

import comd.adobe.aem.kamil.core.etc.TabDetails;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.inject.Inject;
import java.util.List;

@Model(adaptables = Resource.class)
public class TabComponentModel {

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String tabContainerTitle;

    @SlingObject
    private Resource resource;

    @Inject
    @Optional
    private List<TabDetails> tabDetails;

    public List<TabDetails> getTabDetails() {
        return tabDetails;
    }

    public String getTabContainerTitle() {
        return tabContainerTitle;
    }

    public boolean isEmpty() {
        return tabDetails == null || tabDetails.isEmpty();
    }

//    old code left for learnig purposes - to compare with existing code

//    @Inject
//    @Optional
//    public Resource tabDetails;

//    @PostConstruct
//    public void init() {
//        if (tabDetails != null) {
//            tabDetailsList = getTabDetailsList(tabDetails);
//        }
//    }

//    public List<TabDetails> getTabDetailsList() {
//        return tabDetails;
//    }
//    private List<TabDetails> getTabDetailsList(Resource tabDetails) {
//        List<TabDetails> tabDetailsList = new ArrayList<>();
//        List<Resource> childNodes = new ArrayList<>();
//        Iterator<Resource> resourceIterator = tabDetails.listChildren();
//        resourceIterator.forEachRemaining(childNodes::add);
//        childNodes.forEach(r -> populateTabDetailsList(tabDetailsList, r.getValueMap()));
//        return tabDetailsList;
//    }
//
//    private void populateTabDetailsList(List<TabDetails> tabDetailsList, ValueMap valueMap) {
//        TabDetails tabDetails = new TabDetails();
//        tabDetails.setTabId((String) valueMap.get("tabId"));
//        tabDetails.setTabTitle((String) valueMap.get("tabTitle"));
//        tabDetails.setTabDescription((String) valueMap.get("tabDescription"));
//        tabDetailsList.add(tabDetails);
//    }

}
