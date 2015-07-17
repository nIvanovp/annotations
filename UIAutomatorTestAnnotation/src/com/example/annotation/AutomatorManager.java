package com.example.annotation;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

import java.io.File;

import static com.example.annotation.util.LoggerUtil.i;

public class AutomatorManager extends UiAutomatorTestCase {

    public static void takeScreenShot(String screenName) {
        try {
            String name = screenName.replaceAll(" ", "_").replaceAll(",", "")
                    .replaceAll("\"", "").replaceAll("'", "").replaceAll(":", "")
                    .replaceAll("/", "").replaceAll("\\(", "").replaceAll("\\)", "");
            long timeInMs = System.currentTimeMillis();
            String time = android.text.format.DateFormat.format("dd-mm-yyyy_hh-mm-ss", timeInMs).toString();
            UiDevice.getInstance().takeScreenshot(new File("sdcard/" + time + "_" + name + ".png"));
            i("Take screenshot: " + time + "_" + name + ".png" );
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static UiObject getUiObjectById(String id){
        return new UiObject(new UiSelector().resourceId(id));
    }

    public static boolean waitForUiObjectById( String id, long timeout){
        UiObject uiObject = new UiObject(new UiSelector().resourceId(id));
        return uiObject.waitForExists(timeout);
    }

    public static void clickOnUiObject(UiObject uiObject){
            try {
                uiObject.click();
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }
    }

    public static String getUiObjectText(UiObject uiObject) {
        try {
            return uiObject.getText();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setText(UiObject uiObject, String text) {
        try {
            uiObject.setText(text);
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }
}