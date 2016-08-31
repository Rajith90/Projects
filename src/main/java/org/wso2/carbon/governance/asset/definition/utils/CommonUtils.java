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

import org.wso2.carbon.governance.asset.definition.annotations.FieldType;
import org.wso2.carbon.governance.asset.definition.annotations.OptionsField;
import org.wso2.carbon.governance.asset.definition.annotations.RegEx;
import org.wso2.carbon.governance.asset.definition.annotations.Required;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CommonUtils {

    public static Map<String,Field> getAllFields(Map<String,Field> fields, Class<?> type) {

        if (type.getSuperclass() != null) {
            fields = getAllFields(fields, type.getSuperclass());
        }
        for (Field field : Arrays.asList(type.getDeclaredFields())) {
            fields.put(field.getName(),field);
        }
//        BeanInfo info = null;
//        try {
//            info = Introspector.getBeanInfo(type);
//
//            PropertyDescriptor[] props = info.getPropertyDescriptors();
//            for (PropertyDescriptor pd : props) {
//                String pdName = pd.getName();
//                Method getter = pd.getReadMethod();
//                System.out.println(pdName);
//
//            }
//        }catch (IntrospectionException e) {
//                e.printStackTrace();
//            }




        return fields;
    }

    /*public static boolean isFieldPresent (Class<?> type,String fieldName){
        return getFieldsNameList(getAllFields(new ArrayList<Field>(), type)).contains(fieldName);
    }*/

    private static List<String> getFieldsNameList(List<Field> fields){
        List<String> fieldNameList = new ArrayList<>();
        for (Field field : fields) {
            fieldNameList.add(field.getName());
        }
        return fieldNameList;
    }

    public static boolean validateField(Field field, String value) {
        boolean isValidField = true;
        for (Annotation annotation : field.getDeclaredAnnotations()){
            if(annotation.annotationType().equals(NotNull.class)){
                isValidField = AnnotationValidator.requiredFieldAnnotationValidator(value);
            } else if (annotation.annotationType().equals(OptionsField.class)) {
                isValidField = AnnotationValidator.optionsFieldAnnotationValidator(field, value);
            } else if(annotation.annotationType().equals(Pattern.class)) {
                isValidField = AnnotationValidator.regexAnnotationValidator(field, value);
            } else {
                isValidField = true;
            }
            if(!isValidField){
                return false;
            }
        }
        return isValidField;
    }

    public static void preProcessField (Field field) {
        for (Annotation annotation : field.getDeclaredAnnotations()){
             if (annotation.annotationType().equals(OptionsField.class)) {
                AnnotationProcessor.optionFieldAnnotationProcessor(field);
            }
        }
        if(field.getType().isEnum()){
            AnnotationProcessor.enumFieldAnnotationProcessor(field);
        }
    }

}
