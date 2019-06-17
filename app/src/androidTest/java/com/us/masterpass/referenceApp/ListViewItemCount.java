package com.us.masterpass.referenceApp;

import android.support.test.espresso.ViewInteraction;
import android.view.View;
import android.widget.ListView;

import com.us.masterpass.merchantapp.R;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by akhildixit on 10/19/17.
 */

public class ListViewItemCount {
    static int count = 0;

    public static ViewInteraction getCountFromListView(final int id) {
        return onView(withId(R.id.items_list_view)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                ListView view = (ListView) item;
                count = view.getChildCount();
                return true;
            }

            @Override
            public void describeTo(Description description) {
            }
        }));

    }
}

