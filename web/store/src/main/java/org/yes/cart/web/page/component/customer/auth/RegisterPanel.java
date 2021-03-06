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

package org.yes.cart.web.page.component.customer.auth;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.yes.cart.web.application.ApplicationDirector;
import org.yes.cart.web.page.AbstractWebPage;
import org.yes.cart.web.page.CheckoutPage;
import org.yes.cart.web.page.CustomerSelfCarePage;
import org.yes.cart.web.page.component.BaseComponent;

import java.util.HashMap;

/**
 * Igor Azarny iazarny@yahoo.com
 * Date: 22/10/11
 * Time: 15:58
 */
public class RegisterPanel extends BaseComponent {

    private final long serialVersionUid = 20111016L;

    // ------------------------------------- MARKUP IDs BEGIN ---------------------------------- //
    private static final String EMAIL_INPUT = "email";
    private static final String FIRSTNAME_INPUT = "firstname";
    private static final String LASTNAME_INPUT = "lastname";
    private static final String PHONE_INPUT = "phone";
    private static final String REGISTER_BUTTON = "registerBtn";
    private static final String REGISTER_FORM = "registerForm";
    // ------------------------------------- MARKUP IDs END ---------------------------------- //

    /**
     * Create register panel.
     *
     * @param id         component id.
     * @param isCheckout true if we are on checkout
     */
    public RegisterPanel(final String id, final boolean isCheckout) {

        super(id);

        final Class<? extends Page> successfulPage;
        final PageParameters parameters = new PageParameters();

        if (isCheckout) {
            successfulPage = CheckoutPage.class;
            parameters.set(
                    CheckoutPage.THREE_STEPS_PROCESS,
                    "true"
            ).set(
                    CheckoutPage.STEP,
                    CheckoutPage.STEP_ADDR
            );
        } else {
            successfulPage = CustomerSelfCarePage.class;
        }

        add(
                new RegisterForm(REGISTER_FORM, successfulPage, parameters)
        );

    }

    public class RegisterForm extends BaseAuthForm {

        private final long serialVersionUid = 20111016L;


        private String email;
        private String firstname;
        private String lastname;
        private String phone;


        /**
         * Get email.
         *
         * @return email.
         */
        public String getEmail() {
            return email;
        }

        /**
         * Set email.
         *
         * @param email
         */
        public void setEmail(final String email) {
            this.email = email;
        }

        /**
         * Get first name.
         *
         * @return first name
         */
        public String getFirstname() {
            return firstname;
        }

        /**
         * Set first name.
         *
         * @param firstname first name.
         */
        public void setFirstname(final String firstname) {
            this.firstname = firstname;
        }


        /**
         * Get last name.
         *
         * @return last name.
         */
        public String getLastname() {
            return lastname;
        }

        /**
         * set Last name.
         *
         * @param lastname last name.
         */
        public void setLastname(final String lastname) {
            this.lastname = lastname;
        }

        /**
         * Get phone.
         *
         * @return [hone
         */
        public String getPhone() {
            return phone;
        }

        /**
         * Set phone.
         *
         * @param phone phone .
         */
        public void setPhone(final String phone) {
            this.phone = phone;
        }

        /**
         * Construct form.
         *
         * @param id             form id.
         * @param successfulPage page to go in case of successful
         * @param parameters parameters
         */
        public RegisterForm(final String id,
                            final Class<? extends Page> successfulPage,
                            final PageParameters parameters) {

            super(id);

            setModel(new CompoundPropertyModel<RegisterForm>(RegisterForm.this));


            add(
                    new TextField<String>(EMAIL_INPUT)
                            .setRequired(true)
                            .add(StringValidator.lengthBetween(MIN_LEN, MAX_LEN))
                            .add(EmailAddressValidator.getInstance())
            );

            add(
                    new TextField<String>(FIRSTNAME_INPUT)
                            .setRequired(true)
            );

            add(
                    new TextField<String>(LASTNAME_INPUT)
                            .setRequired(true)
            );

            add(
                    new TextField<String>(PHONE_INPUT)
                            .setRequired(true)
                            .add(StringValidator.lengthBetween(4, 13))
            );

            add(
                    new Button(REGISTER_BUTTON) {


                        @Override
                        public void onSubmit() {

                            if (isCustomerExists(getEmail())) {

                                error(
                                        getLocalizer().getString("customerExists", this)
                                );

                                //and sent the new password to already existing user



                                //CPOINT
                                //this commented out, because of YC-168
                                //but it may be valid behavior for some clients.
                                //Customer customer = getCustomerService().getCustomerByEmail(getEmail());
                                //getCustomerService().resetPassword(customer, ApplicationDirector.getCurrentShop());

                            } else {

                                final String password = getCustomerServiceFacade().registerCustomer(
                                        ApplicationDirector.getCurrentShop(),
                                        email, new HashMap<String, Object>() {{
                                            put("firstname", getFirstname());
                                            put("lastname", getLastname());
                                            put("phone", getPhone());
                                        }}
                                );

                                if (signIn(getEmail(), password)) {

                                    ((AbstractWebPage) getPage()).executeHttpPostedCommands();
                                    ((AbstractWebPage) getPage()).persistCartIfNecessary();
                                    setResponsePage(successfulPage, parameters);

                                } else {

                                    error(
                                            getLocalizer().getString("canNotRegister", this)
                                    );

                                }
                            }
                        }
                    }
            );

        }

    }

}
