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

package org.yes.cart.payment.impl;

import org.yes.cart.payment.PaymentModule;
import org.yes.cart.payment.persistence.entity.Descriptor;
import org.yes.cart.payment.persistence.entity.PaymentGatewayDescriptor;

import java.util.Collection;
import java.util.Map;

/**
 * User: Igor Azarny iazarny@yahoo.com
 * Date: 1/2/12
 * Time: 3:20 PM
 */
public class PaymentModuleGswmSetImpl  implements PaymentModule {

    private Descriptor descriptor;
    private Map<String, PaymentGatewayDescriptor> gateways;

    /** {@inheritDoc} */
    public Descriptor getPaymentModuleDescriptor() {
        return descriptor;
    }


    /** {@inheritDoc} */
    public Collection<PaymentGatewayDescriptor> getPaymentGateways() {
        return gateways.values();
    }

    /** {@inheritDoc} */
    public PaymentGatewayDescriptor getPaymentGateway(final String label) {
        return gateways.get(label);
    }



    /**
     * IoC module descriptor.
     * @param descriptor     to use
     */
    public void setDescriptor(final Descriptor descriptor) {
        this.descriptor = descriptor;
    }

    /**
     *  Get payment gateway gateways.
     * @return  payment gateway gateways.
     */
    public Map<String, PaymentGatewayDescriptor> getGateways() {
        return gateways;
    }

    /**
     * IoC payment gateways.
     * @param gateways  gateways.
     */
    public void setGateways(Map<String, PaymentGatewayDescriptor> gateways) {
        this.gateways = gateways;
    }

}
