package com.example.annotation;

import android.os.Bundle;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.example.annotation.annotation.PreCondition;
import com.example.annotation.test.Test;
import com.example.annotation.util.Condition;
import junit.framework.Assert;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import static com.example.annotation.util.LoggerUtil.i;

public class BaseTestRunner extends UiAutomatorTestCase {
    protected PreCondition mPreCondition = null;

    @Override
    protected void setUp() throws Exception {
        i("run setUp");
        super.setUp();
        if(!findMethod())
            Assert.assertEquals("Method was not found", false);

        parseAnnotations();
        processPreConditions();
    }

    private boolean findMethod() {
        String methodName = (String) getParams().get("method");
        if(methodName == null)
            Assert.assertEquals("Method was not found", false);

        for(Method m: this.getClass().getMethods()) {
            if(m.getName().equals(methodName))
                return true;
        }
        return false;
    }

    private void parseAnnotations() {
        for(Method m: this.getClass().getMethods()) {
            if (!m.getName().equals(this.getName())) continue;

            i("ANNOTATIONS FOUND!");
            for(Annotation a: m.getDeclaredAnnotations()) {
                if(a instanceof PreCondition) {
                    i("PreCondition FOUND!");
                    mPreCondition = (PreCondition) a;
                }
            }
        }
    }

    private void processPreConditions() {
        if (mPreCondition == null) return;
        Condition[] preconditionActions = mPreCondition.preConditions();
        for(Condition precondition : preconditionActions) {
            executeCondition(precondition);
        }
    }

    private void executeCondition(Condition condition) {
        switch (condition) {
            case LOGIN:
                String login = mPreCondition.login();
                String password = mPreCondition.password();
                new Test().signIn(login, password);
                break;
            default: break;
        }
    }

    @Override
    public Bundle getParams() {
        return super.getParams();
    }

    @Override
    protected void tearDown() throws Exception {
        i("run tearDown");
        super.tearDown();
    }
}