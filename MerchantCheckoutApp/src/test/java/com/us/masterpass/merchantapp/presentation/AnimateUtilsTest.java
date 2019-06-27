package com.us.masterpass.merchantapp.presentation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;

import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyFloat;

/**
 * JUnit test for AnimateUtils class
 */

public class AnimateUtilsTest {

    AnimateUtils animateUtils ;

    @Before
    public void setUp(){
        animateUtils= PowerMockito.mock(AnimateUtils.class);
        animateUtils =new AnimateUtils(1,10);
    }

    @Test
    public void testGetInterpolation() {
        float result = animateUtils.getInterpolation(1.0f);
        Assert.assertEquals(1.3, result, 1.5);
    }

}
