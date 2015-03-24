package org.yes.cart.domain.ro;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * User: denispavlov
 * Date: 23/03/2015
 * Time: 22:49
 */
@XmlRootElement(name = "filtered-navigation-attribute")
public class FilteredNavigationAttributeRO {

    private String code;
    private String name;
    private String displayName;
    private int rank;
    private String navigationType;

    private List<FilteredNavigationAttributeValueRO> fnValues = new ArrayList<FilteredNavigationAttributeValueRO>();

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @XmlElement(name = "display-name")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(final int rank) {
        this.rank = rank;
    }

    @XmlElement(name = "navigation-type")
    public String getNavigationType() {
        return navigationType;
    }

    public void setNavigationType(final String navigationType) {
        this.navigationType = navigationType;
    }

    @XmlElementWrapper(name = "fn-values")
    @XmlElement(name = "fn-value")
    public List<FilteredNavigationAttributeValueRO> getFnValues() {
        return fnValues;
    }

    public void setFnValues(final List<FilteredNavigationAttributeValueRO> fnValues) {
        this.fnValues = fnValues;
    }
}