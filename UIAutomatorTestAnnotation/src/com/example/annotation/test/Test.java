package com.example.annotation.test;

import com.android.uiautomator.core.UiObject;
import com.example.annotation.AutomatorManager;
import com.example.annotation.util.Constant;

import static com.example.annotation.util.LoggerUtil.i;
import static com.example.annotation.util.LoggerUtil.e;

/**
 * Created by automation on 7/17/15.
 */
public class Test {

    public boolean signIn(String login, String password) {
        if (!AutomatorManager.waitForUiObjectById(Constant.OobeScreen.Id.BTN_LOGIN, Constant.DEFAULT_TIMEOUT)) {
            e("Sign In Screen was not opened");
            return false;
        }

        AutomatorManager.takeScreenShot("sign_in_screen");
        UiObject uiObject = AutomatorManager.getUiObjectById(Constant.OobeScreen.Id.BTN_LOGIN);
        AutomatorManager.clickOnUiObject(uiObject);
        i("Click on Login button");
        if (!AutomatorManager.waitForUiObjectById(Constant.OobeScreen.Id.ID_LOGIN_EMAIL, 5000)) {
            UiObject secondSignInBtn = AutomatorManager.getUiObjectById(Constant.OobeScreen.Id.BTN_LOGIN_ON_SECOND_SCREEN);
            if (!secondSignInBtn.waitForExists(Constant.DEFAULT_TIMEOUT)) {
                e("Second Sign In screen was not opened");
            }
            AutomatorManager.takeScreenShot("Second Sign In screen (With social)");
            String signInText = AutomatorManager.getUiObjectText(secondSignInBtn);
            AutomatorManager.clickOnUiObject(secondSignInBtn);
            i("Click on " + signInText);
        }

        AutomatorManager.takeScreenShot("credential_screen1");
        if (!enterValue(Constant.OobeScreen.Id.ID_LOGIN_EMAIL, login))
            return false;

        if (!enterValue(Constant.OobeScreen.Id.ID_LOGIN_PASSWORD, password))
            return false;

        if (!AutomatorManager.waitForUiObjectById(Constant.OobeScreen.Id.BTN_SUBMIT, Constant.DEFAULT_TIMEOUT)) {
            e("Sign In button was not found");
            return false;
        }
        AutomatorManager.takeScreenShot("credential_screen2");
        uiObject = AutomatorManager.getUiObjectById(Constant.OobeScreen.Id.BTN_SUBMIT);
        AutomatorManager.clickOnUiObject(uiObject);
        i("Click on the Sign in button");
        return true;
    }

    protected boolean enterValue(String idEditText, String value) {
        if (!AutomatorManager.waitForUiObjectById(idEditText, Constant.DEFAULT_TIMEOUT)){
            e("Email field was not found");
            return false;
        }
        UiObject uiObject = AutomatorManager.getUiObjectById(idEditText);
        AutomatorManager.clickOnUiObject(uiObject);
        i("Click on the " + idEditText);
        AutomatorManager.setText(uiObject, value);
        i("Enter text: " + value);
        return true;
    }
}
