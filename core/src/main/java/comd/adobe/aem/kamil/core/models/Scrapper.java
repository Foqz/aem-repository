package comd.adobe.aem.kamil.core.models;

import comd.adobe.aem.kamil.core.etc.ScrapperItem;
import comd.adobe.aem.kamil.core.services.ScrapperService;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables= Resource.class)
public class Scrapper {
    final String bankierURL = "https://www.bankier.pl/gielda/notowania/akcje";

    @Inject @Named("sling:resourceType") @Default(values="No resourceType")
    protected String resourceType;


    @Inject
    ScrapperService scrapperService;
    private List<ScrapperItem> list;


    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    protected String tableName;

    @PostConstruct
    protected void init() {
        list = scrapperService.getScrapperItemList(bankierURL);
    }

    public String getTableName() {
        return StringUtils.isBlank(this.tableName) ? "" : this.tableName;
    }

    public List<ScrapperItem> getList() {
        return this.list != null ? this.list : new ArrayList<>();
    }
}
