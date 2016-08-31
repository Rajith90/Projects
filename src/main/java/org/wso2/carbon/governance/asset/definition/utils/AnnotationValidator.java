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

package org.wso2.carbon.governance.asset.definition.utils;

import org.wso2.carbon.governance.asset.definition.annotations.OptionsField;
import org.wso2.carbon.governance.asset.definition.annotations.RegEx;
import org.wso2.carbon.governance.asset.definition.types.Type;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Pattern;

public class AnnotationValidator {

    public static boolean requiredFieldAnnotationValidator(String value) {
        if(value == null || value.isEmpty()){
            System.out.println("Required Field. Please Enter a value");
            return false;
        }
        return true;
    }

    public static boolean optionsFieldAnnotationValidator (Field field, String value){
        OptionsField optionsField = field.getDeclaredAnnotation(OptionsField.class);
        if(value != null && Arrays.asList(optionsField.values()).contains(value)){
            return true;
        }
        else if(value.isEmpty()) {
            return true;
        }
        System.out.println("Your input does not match any of the below. Please select only one value from below ");
        for(String optionValue : optionsField.values()){
            System.out.println("* "+ optionValue);
        }
        return false;
    }

    public static boolean regexAnnotationValidator (Field field, String value) {
        Pattern regEx = field.getDeclaredAnnotation(Pattern.class);
        String expression = regEx.regexp();
        if(!value.matches(expression)) {
            System.err.println("The provided value does not match with the regular expression " + expression);
            return false;
        }
        return true;
    }

    public static boolean validate(Type assetInstance){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Type>> violations = validator.validate(assetInstance);
        for(ConstraintViolation constraintViolation : violations){
            System.err.println("The value " +constraintViolation.getInvalidValue() +" does not satisfy the constraint"
                    + constraintViolation.getMessage());
        }
        if(violations.size() ==0){
            return true;
        }
        return false;
    }


}
