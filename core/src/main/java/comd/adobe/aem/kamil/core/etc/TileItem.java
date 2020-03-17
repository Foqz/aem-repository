package comd.adobe.aem.kamil.core.etc;

import com.day.cq.wcm.foundation.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TileItem {
    private String thumbnailSource;
    private String pageDescription;
    private String pageTitle;
}
