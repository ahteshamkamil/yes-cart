package org.yes.cart.web.page.component.filterednavigation;

import org.apache.lucene.search.BooleanQuery;
import org.yes.cart.domain.query.ProductSearchQueryBuilder;
import org.yes.cart.domain.query.impl.BrandSearchQueryBuilder;
import org.yes.cart.domain.queryobject.FiteredNavigationRecord;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Simple product filtering by brand component.
 *
 * User: Igor Azarny iazarny@yahoo.com
 * Date: 7/18/11
 * Time: 10:00 PM
 */
public class BrandProductFilter extends AbstractProductFilter {

    private boolean filteredNavigationByBrand = false;

    /**
     * Construct filtered navigation by brand.
     *
     * @param id         panel id
     * @param query      current query.
     * @param categoryId current category id
     */
    public BrandProductFilter(final String id, final BooleanQuery query, final long categoryId) {

        super(id, query, categoryId);

        if (categoryId > 0) {

            filteredNavigationByBrand = getCategory().getNavigationByBrand() == null ? false : getCategory().getNavigationByBrand();

            if (filteredNavigationByBrand) {

                setNavigationRecords(
                        getFilteredNavigationRecords(
                                getProductService().getDistinctBrands(getCategories())
                        )
                );

            }
        }

    }

    /**
     * {@inheritDoc}
     */
    List<FiteredNavigationRecord> getFilteredNavigationRecords(
            final List<FiteredNavigationRecord> allNavigationRecords) {

        final List<FiteredNavigationRecord> navigationList = new ArrayList<FiteredNavigationRecord>();

        if (!isAttributeAlreadyFiltered(ProductSearchQueryBuilder.BRAND_FIELD)) {

            final BrandSearchQueryBuilder queryBuilder = new BrandSearchQueryBuilder();

            for (FiteredNavigationRecord record : allNavigationRecords) {
                BooleanQuery candidateQuery = getLuceneQueryFactory().getSnowBallQuery(
                        getQuery(),
                        queryBuilder.createQuery(getCategories(), record.getValue())
                );
                int candidateResultCount = getProductService().getProductQty(candidateQuery);
                if (candidateResultCount > 0) {
                    record.setName(getLocalizer().getString("brand", this));
                    record.setCode(ProductSearchQueryBuilder.BRAND_FIELD);
                    record.setCount(candidateResultCount);
                    navigationList.add(record);
                }
            }

        }
        return navigationList;
    }


    /**
     * {@inheritDoc}
     */
    public boolean isVisible() {
        return  super.isVisible()
                && filteredNavigationByBrand
                && getNavigationRecords() != null
                && !getNavigationRecords().isEmpty();
    }

}