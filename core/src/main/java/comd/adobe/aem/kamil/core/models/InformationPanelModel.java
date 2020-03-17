package comd.adobe.aem.kamil.core.models;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.foundation.Image;
import comd.adobe.aem.kamil.core.etc.TileItem;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Model(adaptables = Resource.class)
public class InformationPanelModel {

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    public String navigationRoot;

    @SlingObject
    private Resource currentResource;

    @SlingObject
    private ResourceResolver resourceResolver;

    private List<TileItem> tileItems;

    @PostConstruct
    protected void init() {
        try {
            Resource resource = currentResource.getResourceResolver().getResource(navigationRoot);
            PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
            if (resource != null) {
                Iterator<Resource> children = resource.listChildren();
                // filter only pages
                List<Resource> resources = new ArrayList<>();
                children.forEachRemaining(resources::add);
                List<Resource> resourcePages = resources.stream()
                        .filter(r -> r.getResourceType().equals("cq:Page"))
                        .collect(Collectors.toList());
                // construct TileItem object and add to temp list
                List<TileItem> tempTileItems = new ArrayList<>();
                for (Resource currentResource : resourcePages) {
                    TileItem tileItem = new TileItem();
                    assert pageManager != null;
                    Page currentPage = pageManager.getPage(currentResource.getPath());
                    Image image = new Image(currentPage.getContentResource("image"));
                    tileItem.setThumbnailSource(image.getPath() + "/file");
                    tileItem.setPageDescription(currentPage.getDescription());
                    tileItem.setPageTitle(currentPage.getTitle());
                    tempTileItems.add(tileItem);
                }
                // sort pages if list size exceed 4
                if (tempTileItems.size() > 4) {
                    Random random = new Random();
                    List<TileItem> randomized4Tiles = new ArrayList<>();
                    int numberOfElements = 4;
                    for (int i = 0; i < numberOfElements; i++) {
                        int randomIndex = random.nextInt(tempTileItems.size());
                        TileItem randomElement = tempTileItems.get(randomIndex);
                        randomized4Tiles.add(randomElement);
                        tempTileItems.remove(randomElement);
                    }
                    tileItems = randomized4Tiles;
                } else {
                    tileItems = tempTileItems;
                }
            }
        } catch (Exception e) {
            // test exception printer
            e.printStackTrace();
        }

    }

    public List<TileItem> getTileItems() {
        return tileItems;
    }

    public boolean isEmpty() {
        return StringUtils.isBlank(this.navigationRoot);
    }

}
