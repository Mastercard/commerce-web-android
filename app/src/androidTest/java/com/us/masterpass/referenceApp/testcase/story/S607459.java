package com.us.masterpass.referenceApp.testcase.story;

        import android.support.test.InstrumentationRegistry;
        import android.support.test.rule.ActivityTestRule;
        import android.support.test.runner.AndroidJUnit4;

        import com.us.masterpass.merchantapp.presentation.activity.ItemsActivity;
        import com.us.masterpass.referenceApp.Pojo.Address;
        import com.us.masterpass.referenceApp.component.BaseComponent;

        import org.junit.Before;
        import org.junit.FixMethodOrder;
        import org.junit.Rule;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.junit.runners.MethodSorters;

        import java.io.IOException;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class S607459 {


    private BaseComponent baseComponent;
    public Address address;

    @Rule
    public ActivityTestRule<ItemsActivity> mActivity = new ActivityTestRule<>(ItemsActivity.class);

    @Before
    public void before() throws IOException {
        baseComponent = BaseComponent.getInstance();
    }
    /**
     * This testcase is to verify user  navigate to Remember Me Screen
     * @throws Exception
     */
    @Test
    public void TC1187387() throws Exception {
        baseComponent.init().addItemToCart().waitTillProgressBarDisappear().tapOnSRCMark()
        .addMastercard(InstrumentationRegistry.getContext()).tapOnContinueBtnEnrollScreen()
        .waitForAssociateScreen()
        .addFirstAddress(InstrumentationRegistry.getContext()).enterEmailID().enterMobileNo().tapOnContinueBtnAssociateScreen()
                .waitTillProgressBarDisappear()
        .tapOnContinueBtnReviewScreen()
        .verifyRememberMeScreen();
    }
}
