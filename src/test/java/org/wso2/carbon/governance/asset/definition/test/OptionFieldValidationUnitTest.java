/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.governance.asset.definition.test;

import org.junit.Test;
import org.wso2.carbon.governance.asset.definition.annotations.OptionsField;
import org.wso2.carbon.governance.asset.definition.annotations.TextField;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.Assert.assertEquals;

public class OptionFieldValidationUnitTest {

    /*@Test
    public void testMemberWithNoValues() {
        Example ex = new Example();

        // validate the input
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Example>> violations = validator.validate(ex);
        assertEquals(violations.size(), 0);
    }*/
}

class Example {
    @OptionsField( label = "", values = {})
    private int country;

}
