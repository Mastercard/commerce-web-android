package com.us.masterpass.referenceApp.utils;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;
import android.webkit.WebView;

import java.io.File;
import java.io.IOException;
import static android.support.test.InstrumentationRegistry.getInstrumentation;


public class UiAutomatorUtil {
    private static final String TMA_PACKAGE = "com.mastercard.mp.moomerch";
    private static final int LAUNCH_TIMEOUT = 10000;
    private static final String STRING_TO_BE_TYPED = "UiAutomator";
    public static Object startMainActivityFromHomeScreen;
    private static UiDevice mDevice =
            UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

    TestData testData = new TestData();

    public static void pressDeviceHomeBtn() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mDevice.pressHome();
    }

    /**
     * This method is to click recent app button on device
     *
     * @throws UiObjectNotFoundException
     * @throws RemoteException
     * @throws IOException
     */
    public static void pressRecentAppBtn()
            throws UiObjectNotFoundException, RemoteException, IOException {
        mDevice.pressRecentApps();
    }

    /**
     * This method is closing test merchant app.
     *
     * @throws UiObjectNotFoundException
     * @throws RemoteException
     * @throws IOException
     */
    public static void closeApp() throws UiObjectNotFoundException, RemoteException, IOException {
        UiObject app = mDevice.findObject(new UiSelector().text("Merchant Checkout App"));
        app.swipeLeft(10);
    }


    /**
     * This is to prevent null pointer exception, which fails our testcase even though there were no issues
     */
    public static void preventNull() {
        mDevice.waitForIdle(500);
    }

    /**
     * This method is to rotate device to landscape mode
     *
     * @throws UiObjectNotFoundException
     * @throws RemoteException
     */
    public static void rotateDeviceToLandscape() throws UiObjectNotFoundException, RemoteException {
        mDevice.setOrientationRight();
    }

    /**
     * This method will turn off WiFi. Set flag = false to turn off WiFi.
     *
     * @param ctx
     */
    public static void WifiOff(Context ctx) {
        WifiManager wifi = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(false);
    }

    /**
     * This method will turn on WiFi. Set flag = true to turn on WiFi. 30 seconds wait is added after turning on WiFi as device takes time to connect to network.
     *
     * @param ctx
     */
    public static void WifiOn(Context ctx) {
        WifiManager wifi = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(true);
        SystemClock.sleep(30000);
    }


    /**
     * This method is to clear app data
     */
    public static void clearAppData() {
        File root = InstrumentationRegistry.getTargetContext().getFilesDir().getParentFile();
        String[] sharedPreferencesFileNames = new File(root, "shared_prefs").list();
        for (String fileName : sharedPreferencesFileNames) {
            InstrumentationRegistry.getTargetContext().getSharedPreferences(fileName.replace(".xml", ""), Context.MODE_PRIVATE).edit().clear().apply();
        }
    }

    /**
     * This method is to click device back button
     */
    public static boolean pressBackButton() {
        return UiDevice.getInstance(getInstrumentation()).pressBack();
    }

    /**
     * This method can be used to click an element by its resource ID
     *
     * @param rID
     * @throws UiObjectNotFoundException
     */
    public static void clickByResourceID(String rID) throws UiObjectNotFoundException {
        UiObject clickByResourceID = mDevice.findObject(new UiSelector().resourceId(rID));
        clickByResourceID.click();
        return;
    }

    /**
     * This method can be used to click an element by its Text
     *
     * @param text
     * @throws UiObjectNotFoundException
     */
    public static void clickByText(String text) throws UiObjectNotFoundException {
        UiObject clickByText = mDevice.findObject(new UiSelector().text(text));
        clickByText.click();
        return;
    }

    /**
     * This method can be used to click an element by its Description
     *
     * @param desc
     * @throws UiObjectNotFoundException
     */
    public static void clickByDesc(String desc) throws UiObjectNotFoundException {
        UiObject clickByDescription = mDevice.findObject(new UiSelector().description(desc));
        clickByDescription.click();
        return;
    }

    /**
     * This method can be used to click an element if it contains a certain text
     *
     * @param text
     * @throws UiObjectNotFoundException
     */
    public static void clickByContainText(String text) throws UiObjectNotFoundException {
        UiObject clickByContainText = mDevice.findObject(new UiSelector().textContains(text));
        clickByContainText.click();
        return;
    }

    /**
     * This method can be used to click an element if it starts with a certain text
     *
     * @param text
     * @throws UiObjectNotFoundException
     */
    public static void clickByStartText(String text) throws UiObjectNotFoundException {
        UiObject clickByStartText = mDevice.findObject(new UiSelector().textStartsWith(text));
        clickByStartText.click();
        return;
    }

    /**
     * This method can be used to click an element by using its class
     *
     * @param className
     * @throws UiObjectNotFoundException
     */
    public static void clickByClassName(String className) throws UiObjectNotFoundException {
        UiObject clickByClassName = mDevice.findObject(new UiSelector().className(className));
        clickByClassName.click();
        return;
    }

    /**
     * The purpose of ths method is to verify whether a certain text exists
     *
     * @param text
     * @throws UiObjectNotFoundException
     */
    public static void checkTextExists(String text) throws UiObjectNotFoundException {
        UiObject clickByText = mDevice.findObject(new UiSelector().text(text));

    }

    /**
     * The purpose of this method is to verify if a certain text contains a specific text
     *
     * @param text
     * @throws UiObjectNotFoundException
     */
    public static void checkContainsTextExists(String text) throws UiObjectNotFoundException {
        UiObject clickByContainText = mDevice.findObject(new UiSelector().textContains(text));

    }

    /**
     * The purpose of this method is to verify that a certain element is enabled using the resource ID
     *
     * @param rID
     * @return
     * @throws UiObjectNotFoundException
     */
    public static boolean checkElementExistByResoureID(String rID) throws UiObjectNotFoundException {
        UiObject el = mDevice.findObject(new UiSelector().resourceId(rID));
        return el.isEnabled();
    }

    /**
     * This method is used to scroll till a certain Text is within display
     *
     * @param text
     * @throws UiObjectNotFoundException
     */
    public static void scrollTillTextDisplay(String text) throws UiObjectNotFoundException {
        UiScrollable scrollDown = new UiScrollable(new UiSelector().scrollable(true));
        scrollDown.setAsVerticalList();
        UiObject checkText = scrollDown.getChildByText(new UiSelector().text(text), text);
    }


    public static void sendTextWithResourceID(String ID, String text){
        final UiDevice mDevice =
                UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        final int timeOut = 100 * 60;
        mDevice.wait(Until.findObject(By.clazz(WebView.class)), timeOut);
        try {
                UiObject textBox = mDevice.findObject(new UiSelector().instance(0).resourceId(ID));
                textBox.setText(text);
        }catch (Exception e){
            Log.d("no password", "sendText: ");
        }
    }

    public static void tapOnButtonWithContentDesc(String desc){
        try {
            UiObject button = mDevice.findObject(new UiSelector().instance(0).description(desc));
            button.click();
        }catch (Exception e)
        {
            Log.d("", "tapOnSignIn: ");
        }
    }


     /*
       This method is used to press submit button on 3DS screen (web view)
     */

    public static void submit3SDcode() throws UiObjectNotFoundException {
        final UiDevice mDevice =
                UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        final int timeOut = 100 * 60;
        mDevice.wait(Until.findObject(By.clazz(WebView.class)), timeOut);
        {

            try{
                UiObject buttonLogin = mDevice.findObject(new UiSelector()
                        .instance(0)
                        .text("Submit"));
                buttonLogin.click();
            }
            catch (Exception E)
            {
                Log.d("3DS", "3DS is not enabled");
            }
        }

    }
}