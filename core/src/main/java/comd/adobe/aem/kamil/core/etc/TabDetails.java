package comd.adobe.aem.kamil.core.etc;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.sling.models.annotations.Model;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@NoArgsConstructor
@Getter
@Setter
@Model(adaptables = Resource.class)
public class TabDetails {
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String tabId;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String tabTitle;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String tabDescription;
}
