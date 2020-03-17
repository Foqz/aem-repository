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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Model(adaptables = Resource.class)
public class InformationPanelModel {

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String navigationRoot;

    @SlingObject
    private Resource currentResource;

    @SlingObject
    private ResourceResolver resourceResolver;

    private List<TileItem> tileItems;

    @PostConstruct
    protected void init() {
        Resource resource = currentResource.getResourceResolver().getResource(navigationRoot);
        if (resource != null) {
            PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
            tileItems = generateTileItemsFromParentPage(resource, pageManager);
        }
    }

    public List<TileItem> getTileItems() {
        return tileItems;
    }

    public boolean isEmpty() {
        return StringUtils.isBlank(this.navigationRoot);
    }

    private List<TileItem> generateTileItemsFromParentPage(Resource resource, PageManager pageManager) {
        List<TileItem> tempTileItems = new ArrayList<>();
        Iterator<Resource> children = resource.listChildren();
        List<Resource> resources = new ArrayList<>();
        children.forEachRemaining(resources::add);
        resources.stream()
                .filter(r -> r.getResourceType().equals("cq:Page"))
                .forEach(r -> {
                    assert pageManager != null;
                    populateTempTileItemList(tempTileItems, r, pageManager);
                });
        return randomizeTiles(tempTileItems);
    }

    private void populateTempTileItemList(List<TileItem> tempTileItems, Resource currentResource, PageManager pageManager) {
        TileItem tileItem = new TileItem();
        Page currentPage = pageManager.getPage(currentResource.getPath());
        tileItem.setThumbnailSource(new Image(currentPage.getContentResource("image")).getPath() + "/file");
        tileItem.setPageDescription(currentPage.getDescription());
        tileItem.setPageTitle(currentPage.getTitle());
        tempTileItems.add(tileItem);
    }

    private List<TileItem> randomizeTiles(List<TileItem> tempTileItems) {
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
            return randomized4Tiles;
        } else {
            return tempTileItems;
        }
    }
}
