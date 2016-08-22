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

import org.apache.commons.beanutils.PropertyUtils;
import org.wso2.carbon.governance.asset.definition.annotations.Table;
import org.wso2.carbon.governance.asset.definition.types.Type;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class InputProcessor {

    public static Type readInputs(HashMap<String, Class> assetDefinitions) {
        Type assetInstance = null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Please select the asset type you want to add : ");
            for (String assetTypes : assetDefinitions.keySet()) {
                System.out.println("* " + assetTypes);
            }
            String assetType = br.readLine();
            Class assetDefinition = assetDefinitions.get(assetType);
            Constructor constructor = assetDefinition.getConstructor();
            assetInstance = (Type) constructor.newInstance();
            for (Field field : CommonUtils.getAllFields(new ArrayList<Field>(), assetDefinition)) {
                try {
                    if (field.isAnnotationPresent(Table.class)) {
                        String[][] table = AnnotationProcessor.tableAnnotationProcessor(field);
                        PropertyUtils.setProperty(assetInstance, field.getName(), table);
                    } else {
                        System.out.println("Please enter value for " + field.getName());
                        CommonUtils.preProcessField(field);
                        String enteredValue = null;
                        do {
                            enteredValue = br.readLine();
                        } while (!CommonUtils.validateField(field, enteredValue));

                        PropertyUtils.setProperty(assetInstance, field.getName(), enteredValue);
                    }
                } catch (NoSuchMethodException e) {
                   e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }  catch (InvocationTargetException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }  catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return assetInstance;
    }

}
