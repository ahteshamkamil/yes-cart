package org.yes.cart.service.image.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.yes.cart.constants.ServiceSpringKeys;
import org.yes.cart.service.domain.impl.BaseCoreDBTestCase;
import org.yes.cart.service.image.ImageNameStrategy;

import java.io.File;

/**
* User: Igor Azarny iazarny@yahoo.com
 * Date: 09-May-2011
 * Time: 14:12:54
 */
public class AbstractImageNameStrategyImplTest extends BaseCoreDBTestCase {


    private ImageNameStrategy imageNameStrategy;



    @Before
    public void setUp() throws Exception {
        super.setUp();
        imageNameStrategy = (ImageNameStrategy)ctx.getBean(ServiceSpringKeys.PRODUCT_IMAGE_NAME_STRATEGY);
    }

    @After
    public void tearDown() {
        imageNameStrategy = null;
        super.tearDown();
    }


    @Test
    public void testGetFullFileNamePath() {

        assertEquals( "file-name_CODE_a.jpej",
                imageNameStrategy.getFullFileNamePath("file-name_CODE_a.jpej", null));



        assertEquals( "C" + File.separator + "CODE" + File.separator + "file-name_CODE_a.jpej",
                imageNameStrategy.getFullFileNamePath("file-name_CODE_a.jpej", "CODE"));

        assertEquals( "10x30" + File.separator + "C" + File.separator + "CODE" + File.separator + "file-name_CODE_a.jpej",
                imageNameStrategy.getFullFileNamePath("file-name_CODE_a.jpej", "CODE", "10", "30"));

    }



    
}
