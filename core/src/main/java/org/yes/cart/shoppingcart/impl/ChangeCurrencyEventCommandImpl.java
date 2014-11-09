/*
 * Copyright 2009 Igor Azarnyi, Denys Pavlov
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.yes.cart.shoppingcart.impl;


import org.yes.cart.domain.entity.ProductSku;
import org.yes.cart.domain.entity.Shop;
import org.yes.cart.service.domain.PriceService;
import org.yes.cart.service.domain.ProductService;
import org.yes.cart.service.domain.ShopService;
import org.yes.cart.shoppingcart.MutableShoppingCart;
import org.yes.cart.shoppingcart.ShoppingCartCommandRegistry;

import java.util.Map;

/**
 * User: Igor Azarny iazarny@yahoo.com
 * Date: 09-May-2011
 * Time: 14:12:54
 */
public class ChangeCurrencyEventCommandImpl extends AbstractSkuCartCommandImpl {

    private static final long serialVersionUID = 20101702L;

    /**
     * Construct sku command.
     *
     * @param registry shopping cart command registry
     * @param priceService price service
     * @param productService product service
     * @param shopService shop service
     */
    public ChangeCurrencyEventCommandImpl(final ShoppingCartCommandRegistry registry,
                                          final PriceService priceService,
                                          final ProductService productService,
                                          final ShopService shopService) {
        super(registry, priceService, productService, shopService);
    }

    /**
     * {@inheritDoc}
     */
    public String getCmdKey() {
        return CMD_CHANGECURRENCY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void execute(final MutableShoppingCart shoppingCart,
                           final ProductSku productSku,
                           final Map<String, Object> parameters) {
        if (parameters.containsKey(getCmdKey())) {
            final String currencyCode = (String) parameters.get(getCmdKey());
            if (currencyCode != null && !currencyCode.equals(shoppingCart.getCurrencyCode())) {
                final Shop shop = getShopService().getShopByCode(shoppingCart.getShoppingContext().getShopCode());
                if (shop.getSupportedCurrenciesAsList().contains(currencyCode)) {
                    shoppingCart.setCurrencyCode(currencyCode);
                    shoppingCart.getOrderInfo().setCarrierSlaId(null); // If we change currency then SLA is no longer eligible
                    shoppingCart.getOrderInfo().setPaymentGatewayLabel(null); // and PG label too
                    recalculatePrice(shoppingCart, productSku);
                    markDirty(shoppingCart);
                }
            }
        }
    }
}
