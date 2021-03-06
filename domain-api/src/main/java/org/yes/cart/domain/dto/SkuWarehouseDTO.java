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

package org.yes.cart.domain.dto;

import org.yes.cart.domain.entity.Identifiable;

import java.math.BigDecimal;

/**
 * Represent sku quantity on particular warehouse.
 * <p/>
 * User: Igor Azarny iazarny@yahoo.com
 * Date: 07-May-2011
 * Time: 11:12:54
 */
public interface SkuWarehouseDTO extends Identifiable {

    /**
     * Primary key.
     *
     * @return ph value.
     */
    long getSkuWarehouseId();

    /**
     * Set pk value.
     *
     * @param skuWarehouseId value to set
     */
    void setSkuWarehouseId(long skuWarehouseId);

    /**
     * The sku pk.
     *
     * @return sku pk.
     */
    long getProductSkuId();

    /**
     * Set sku pk.
     *
     * @param productSkuId sku pk value.
     */
    void setProductSkuId(long productSkuId);

    /**
     * Get sku code.
     *
     * @return sku code.
     */
    String getSkuCode();

    /**
     * Set sku code.
     *
     * @param skuCode sku code.
     */
    void setSkuCode(String skuCode);

    /**
     * Get sku name.
     *
     * @return sku name.
     */
    String getSkuName();

    /**
     * Set sku name
     *
     * @param skuName sku name.
     */
    void setSkuName(String skuName);

    /**
     * Get the warehouse id.
     *
     * @return warehouse id
     */
    long getWarehouseId();

    /**
     * Set warehouse id.
     *
     * @param warehouseId warehouse id
     */
    void setWarehouseId(long warehouseId);

    /**
     * Get warehouse code.
     *
     * @return warehouse code.
     */
    String getWarehouseCode();

    /**
     * Set warehouse code.
     *
     * @param warehouseCode warehouse code.
     */
    void setWarehouseCode(String warehouseCode);

    /**
     * Get warehouse name.
     *
     * @return warehouse name.
     */
    String getWarehouseName();

    /**
     * Set warehouse name.
     *
     * @param warehouseName warehouse name.
     */
    void setWarehouseName(String warehouseName);

    /**
     * Sku quantity.
     *
     * @return sku quantity.
     */
    BigDecimal getQuantity();

    /**
     * Set sku quantity
     *
     * @param quantity ku quantity.
     */
    void setQuantity(BigDecimal quantity);


    /**
     * Get reserved quantity during payment transaction.
     *
     * @return reserved quantity during payment transaction.
     */
    BigDecimal getReserved();

    /**
     * Set reserved quantity during payment transaction.
     *
     * @param reserved reserved quantity during payment transaction.
     */
    void setReserved(BigDecimal reserved);


}
