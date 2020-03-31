package comd.adobe.aem.kamil.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class)
public class ChuckNorrisModel {

    public boolean isEmpty(){
        return true;
    }
}
