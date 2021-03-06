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

package org.yes.cart.service.order.impl.handler;

import org.junit.Before;
import org.junit.Test;
import org.yes.cart.constants.Constants;
import org.yes.cart.domain.entity.Customer;
import org.yes.cart.domain.entity.CustomerOrder;
import org.yes.cart.domain.entity.CustomerOrderDelivery;
import org.yes.cart.domain.entity.SkuWarehouse;
import org.yes.cart.service.domain.CustomerOrderService;
import org.yes.cart.service.domain.SkuWarehouseService;
import org.yes.cart.service.order.OrderEventHandler;
import org.yes.cart.service.order.impl.OrderEventImpl;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * User: Igor Azarny iazarny@yahoo.com
 * Date: 09-May-2011
 * Time: 14:12:54
 */
public class DeliveryAllowedByInventoryOrderEventHandlerImplTest extends AbstractEventHandlerImplTest {

    private CustomerOrderService orderService;
    private OrderEventHandler handler;
    private SkuWarehouseService skuWarehouseService;

    @Before
    public void setUp()  {
        handler = (OrderEventHandler) ctx().getBean("deliveryAllowedByInventoryOrderEventHandler");
        orderService = (CustomerOrderService) ctx().getBean("customerOrderService");
        skuWarehouseService = (SkuWarehouseService) ctx().getBean("skuWarehouseService");
        super.setUp();
    }

    @Test
    public void testHandle() throws Exception {
        Customer customer = createCustomer();
        assertFalse(customer.getAddress().isEmpty());
        CustomerOrder customerOrder = orderService.createFromCart(getStdCard(customer.getEmail()), false);
        assertEquals(CustomerOrder.ORDER_STATUS_NONE, customerOrder.getOrderStatus());
        CustomerOrderDelivery delivery = customerOrder.getDelivery().iterator().next();
        //initial 15120 has 9 items on 1 warehouse without reservation - pk 30
        //initial 15121 has 1 item  on 1 warehouse without reservation - pk 31
        assertEquals("Expected one delivery for order", 1, customerOrder.getDelivery().size());
        assertTrue(handler.handle(new OrderEventImpl("", customerOrder, delivery)));

        SkuWarehouse  skuWarehouse = skuWarehouseService.findById(31);
        assertEquals(BigDecimal.ZERO.setScale(Constants.DEFAULT_SCALE), skuWarehouse.getQuantity().setScale(Constants.DEFAULT_SCALE));
        assertEquals(BigDecimal.ZERO.setScale(Constants.DEFAULT_SCALE), skuWarehouse.getReserved().setScale(Constants.DEFAULT_SCALE));
        skuWarehouse = skuWarehouseService.findById(30);
        assertEquals(new BigDecimal("7.00"), skuWarehouse.getQuantity().setScale(Constants.DEFAULT_SCALE));
        assertEquals(new BigDecimal("0.00"), skuWarehouse.getReserved().setScale(Constants.DEFAULT_SCALE));
        assertEquals(CustomerOrderDelivery.DELIVERY_STATUS_INVENTORY_ALLOCATED, delivery.getDeliveryStatus());
        //The equal order can not pefrorm transition , because 1 item on CC_TEST2 sku reserved
        customerOrder = orderService.createFromCart(getStdCard(customer.getEmail()), false);
        assertEquals(CustomerOrder.ORDER_STATUS_NONE, customerOrder.getOrderStatus());
        assertEquals("Expected two deliveries for order", 2, customerOrder.getDelivery().size());

        for (CustomerOrderDelivery cod : customerOrder.getDelivery()) {
            if (CustomerOrderDelivery.STANDARD_DELIVERY_GROUP == cod.getDeliveryGroup() ) {
                assertTrue(handler.handle(new OrderEventImpl("", customerOrder, cod)));  //delivery allowed
                assertEquals(CustomerOrderDelivery.DELIVERY_STATUS_INVENTORY_ALLOCATED, cod.getDeliveryStatus());
            } else if (CustomerOrderDelivery.INVENTORY_WAIT_DELIVERY_GROUP == cod.getDeliveryGroup() ) {
                assertFalse(handler.handle(new OrderEventImpl("", customerOrder, cod)));   // wait for inventory
                assertEquals(CustomerOrderDelivery.DELIVERY_STATUS_ON_FULLFILMENT, cod.getDeliveryStatus());
            }   else {
                assertTrue("Not expected delivery group", false);
            }
        }



        // update qty
        skuWarehouse = skuWarehouseService.findById(31);
        skuWarehouse.setQuantity(new BigDecimal("2"));
        skuWarehouseService.update(skuWarehouse);

        //delivery, than not pass before, now can perform transition

        for (CustomerOrderDelivery cod : customerOrder.getDelivery()) {
            if (CustomerOrderDelivery.STANDARD_DELIVERY_GROUP == cod.getDeliveryGroup() ) {
                assertTrue(handler.handle(new OrderEventImpl("", customerOrder, cod)));  //delivery allowed
                assertEquals(CustomerOrderDelivery.DELIVERY_STATUS_INVENTORY_ALLOCATED, cod.getDeliveryStatus());
            } else if (CustomerOrderDelivery.INVENTORY_WAIT_DELIVERY_GROUP == cod.getDeliveryGroup() ) {
                assertTrue( "Enough qty on warehouse to allow delivery" , handler.handle(new OrderEventImpl("", customerOrder, cod)));
                assertEquals(CustomerOrderDelivery.DELIVERY_STATUS_INVENTORY_ALLOCATED, cod.getDeliveryStatus());
            }   else {
                assertTrue("Not expected delivery group", false);
            }
        }
    }
}
