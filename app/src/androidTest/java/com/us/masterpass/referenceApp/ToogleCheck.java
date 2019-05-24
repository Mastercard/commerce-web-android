package com.us.masterpass.referenceApp;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;
import android.widget.Checkable;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static org.hamcrest.CoreMatchers.isA;

/**
 * Created by akhildixit on 11/2/17.
 */

public class ToogleCheck {
    public static ViewAction setChecked(final boolean checked) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return new Matcher<View>() {

                    @Override
                    public void describeTo(Description description) {
                    }

                    @Override
                    public boolean matches(Object item) {
                        return isA(Checkable.class).matches(item);
                    }

                    @Override
                    public void describeMismatch(Object item, Description mismatchDescription) {
                    }

                    @Override
                    public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {
                    }
                };
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public void perform(UiController uiController, View view) {
                Checkable checkableView = (Checkable) view;
                checkableView.setChecked(checked);
            }
        };
    }

}
