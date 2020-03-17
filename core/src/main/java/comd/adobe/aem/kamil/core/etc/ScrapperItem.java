package comd.adobe.aem.kamil.core.etc;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class ScrapperItem {
    private String value;
    private String exchange;
    private String change;
    private String percentChange;
    private String amountOfTransactions;
    private String rotation;
    private String open;
    private String max;
    private String min;
    private String time;
}
