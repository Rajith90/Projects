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

package org.wso2.carbon.governance.asset.definition.validators;

import org.wso2.carbon.governance.asset.definition.annotations.OptionsField;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OptionFieldValidator implements ConstraintValidator<OptionsField,Object>{

    private Object name;
    @Override public void initialize(OptionsField optionsField) {
        this.name = optionsField.label();
    }

    @Override public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if(name instanceof String)
            return true;
        return false;
    }
}
