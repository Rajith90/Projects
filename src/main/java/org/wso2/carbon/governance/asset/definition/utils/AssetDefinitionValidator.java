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

import org.wso2.carbon.governance.asset.definition.annotations.Group;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class AssetDefinitionValidator {



    public static boolean validateFields(Class assetDefinition){
        boolean isValid = true;
        for (Field field : CommonUtils.getAllFields(new ArrayList<Field>(), assetDefinition)){
            for (Annotation annotation : field.getDeclaredAnnotations()) {
                String annotationName = annotation.annotationType().getSimpleName();
                if (annotation.equals(Constants.TEXT_FIELD_ANNOTATION) && !field.getType().isAssignableFrom(Constants
                        .STRING_CLASS)) {
                    System.err.println("Text Field should be declared as String Type.");
                    isValid = false;
                } else if (annotationName.equals(Constants.TEXT_AREA_ANNOTATION) && !field.getType()
                        .isAssignableFrom(Constants
                                .STRING_CLASS)) {
                    System.err.println("Text Area should be declared as String Type");
                    isValid = false;
                } else if (annotationName.equals(Constants.OPTIONS_FIELD_ANNOTATION) && !field.getType()
                        .isAssignableFrom(Constants
                                .STRING_CLASS)) {
                    System.err.println("Options field should be declared as String Type");
                    isValid = false;
                } else if (annotationName.equals(Constants.TABLE_ANNOTATION) && !field.getType()
                        .isAssignableFrom(Constants
                                .STRING_DOUBLE_ARRAY_CLASS)) {
                    System.err.println("Table should be declared as String[][] Type");
                    isValid = false;
                } else if (annotationName.equals(Constants.GROUP_ANNOTATION)) {
                    Group group = field.getDeclaredAnnotation(Group.class);
                    for (String groupedElement : group.fields()) {
                        if (!CommonUtils.isFieldPresent(assetDefinition, groupedElement)) {
                            isValid = false;
                            System.err.println("Asset Definition " + assetDefinition.getSimpleName() + " does not "
                                    + "contain a field with name " + groupedElement);
                            break;
                        }
                    }
                } else {
                    isValid = true;
                }
                if(!isValid){
                    System.err.println(assetDefinition.getName() + " : Invalid Definition");
                    return isValid;
                }
            }

        }
        return isValid;
    }
}
