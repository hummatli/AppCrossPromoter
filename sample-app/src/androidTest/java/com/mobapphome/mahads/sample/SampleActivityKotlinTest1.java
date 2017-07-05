package com.mobapphome.mahads.sample;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
//import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SampleActivityKotlinTest1 {

    @Rule
    public ActivityTestRule<SampleActivityKotlin> mActivityTestRule = new ActivityTestRule<>(SampleActivityKotlin.class);

    @Test
    public void sampleActivityKotlinTest1() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btnProgramsDlgTest), withText("Test open Programs Dlg"), isDisplayed()));
        appCompatButton.perform(click());

//        ViewInteraction appCompatButton2 = onView(
//                allOf(withId(R.id.btnErrorRefreshMAHAds), withText("Retry"),
//                        withParent(withId(R.id.lytErrorF1)),
//                        isDisplayed()));
//        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.btnClose), withText("Close"), isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.btnExitDlgTest), withText("Test open Exit Dlg"), isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.btnNo), withText("Stay"), isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.btnExitDlgTest), withText("Test open Exit Dlg"), isDisplayed()));
        appCompatButton6.perform(click());

//        ViewInteraction appCompatButton7 = onView(
//                allOf(withId(R.id.btnYes), withText("Exit"), isDisplayed()));
//        appCompatButton7.perform(click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.btnProgramsDlgTest), withText("Test open Programs Dlg"), isDisplayed()));
        appCompatButton8.perform(click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.btnClose), withText("Close"), isDisplayed()));
        appCompatButton9.perform(click());

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.mahBtnOpenJavaSample), withText("Open Java Activity"), isDisplayed()));
        appCompatButton10.perform(click());

        pressBack();

//        ViewInteraction appCompatButton11 = onView(
//                allOf(withId(R.id.btnYes), withText("Exit"), isDisplayed()));
//        appCompatButton11.perform(click());
        pressBack();

        ViewInteraction appCompatButton12 = onView(
                allOf(withId(R.id.btnProgramsDlgTest), withText("Test open Programs Dlg"), isDisplayed()));
        appCompatButton12.perform(click());

        ViewInteraction appCompatButton13 = onView(
                allOf(withId(R.id.btnClose), withText("Close"), isDisplayed()));
        appCompatButton13.perform(click());

        ViewInteraction appCompatButton14 = onView(
                allOf(withId(R.id.btnProgramsDlgTest), withText("Test open Programs Dlg"), isDisplayed()));
        appCompatButton14.perform(click());

//        ViewInteraction recyclerView = onView(
//                allOf(withId(R.id.rvProgram), isDisplayed()));
//        recyclerView.perform(actionOnItemAtPosition(5, click()));

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.imgBtnMore), isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.title), withText("Open in Google Play"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.ivBtnInfo), isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.ivBtnCancel), isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction appCompatButton15 = onView(
                allOf(withId(R.id.btnExitDlgTest), withText("Test open Exit Dlg"), isDisplayed()));
        appCompatButton15.perform(click());

        ViewInteraction linearLayout = onView(
                withId(R.id.lytBtnOther));
        linearLayout.perform(scrollTo(), click());

        ViewInteraction appCompatButton16 = onView(
                allOf(withId(R.id.btnProgramsDlgTest), withText("Test open Programs Dlg"), isDisplayed()));
        appCompatButton16.perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_mahads), withContentDescription("Recommended applications"), isDisplayed()));
        actionMenuItemView.perform(click());

        pressBack();

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.title), withText("Settings"), isDisplayed()));
        appCompatTextView2.perform(click());

        pressBack();

        ViewInteraction linearLayout2 = onView(
                withId(R.id.lytBtnOther));
        linearLayout2.perform(scrollTo(), click());

        pressBack();

        pressBack();

        pressBack();

        ViewInteraction appCompatButton17 = onView(
                allOf(withId(R.id.btnYes), withText("Exit"), isDisplayed()));
        appCompatButton17.perform(click());

    }

}
