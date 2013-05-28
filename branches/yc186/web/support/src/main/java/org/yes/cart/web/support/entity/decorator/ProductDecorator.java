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

package org.yes.cart.web.support.entity.decorator;

import org.yes.cart.domain.entity.Product;
import org.yes.cart.domain.entity.ProductSku;
import org.yes.cart.domain.entity.Seo;
import org.yes.cart.service.domain.CategoryService;
import org.yes.cart.service.domain.ImageService;
import org.yes.cart.service.domain.ProductService;
import org.yes.cart.web.support.service.AttributableImageService;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * User: Igor Azarny iazarny@yahoo.com
 * Date: 7/13/11
 * Time: 9:39 PM
 */
public interface ProductDecorator extends  Product, ObjectDecorator {

    /**
     * Attache to context after deserialization in case of cache overflow.
     *
     * @param imageService image serice to get the image seo info
     * @param attributableImageService category image service to get the image.
     * @param categoryService          to get image width and height
     * @param productService           product service
     */
    public void attachToContext(
             ImageService imageService,
             AttributableImageService attributableImageService,
             CategoryService categoryService,
             ProductService productService);
    /*
    //product.getId()
    long getId();

    long getProductId();

    //product.getSeo()
    Seo getSeo();

    //product.getCode()

    String getCode();

    //product.getAvailability(),
    int getAvailability();

    //product.getQtyOnWarehouse()
    BigDecimal getQtyOnWarehouse();

    Collection<ProductSkuDecorator> getSku();

    //product.isMultiSkuProduct()
    boolean  isMultiSkuProduct();

    //ProductSku getDefaultSku();
    ProductSkuDecorator getDefaultSku();
    */

}
