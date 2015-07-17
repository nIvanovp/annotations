package com.example.annotation;

import com.example.annotation.annotation.PreCondition;
import com.example.annotation.util.Condition;

import static com.example.annotation.util.LoggerUtil.i;

/**
 * Created by automation on 7/17/15.
 */
public class TestRunner extends BaseTestRunner {

    @PreCondition(preConditions = {Condition.LOGIN},
            login = "avatar_unified_small2@books.com",
            password = "access")
    public void testCase1() {
        i("Run test 1");
    }

}
