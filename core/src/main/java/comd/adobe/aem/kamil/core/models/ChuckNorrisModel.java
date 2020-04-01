package comd.adobe.aem.kamil.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class)
public class ChuckNorrisModel {

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String componentTitle;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private boolean factCategory;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private boolean factTextSearch;


    public boolean isEmpty(){
        return true;
    }

    public void setComponentTitle(String componentTitle) {
        this.componentTitle = componentTitle;
    }

    public void setFactCategory(boolean factCategory) {
        this.factCategory = factCategory;
    }

    public void setFactTextSearch(boolean factTextSearch) {
        this.factTextSearch = factTextSearch;
    }
}
