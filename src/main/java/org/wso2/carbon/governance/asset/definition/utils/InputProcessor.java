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

import org.wso2.carbon.governance.asset.definition.annotations.Custom;
import org.wso2.carbon.governance.asset.definition.annotations.Table;
import org.wso2.carbon.governance.asset.definition.types.Type;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputProcessor {

    public static Type readInputs(HashMap<String, Class> assetDefinitions) throws IOException {
        Type assetInstance = null;
        InputStreamReader is = null;
        BufferedReader br = null;
        try {
            is = new InputStreamReader(System.in);
            br = new BufferedReader(is);
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

                    if (Type.class.isAssignableFrom(field.getType())) {
                        System.out.println("#######   " + field.getType().getSimpleName() + "Details #######");
                        String compositeAssetType = field.getType().getSimpleName();
                        Class compositeAssetClass = assetDefinitions.get(compositeAssetType);
                        Object compositeAsset = buildCompositeField(compositeAssetClass, br);
                        TypeAdapter.assignToFieldsBasedOnType(assetInstance, field, compositeAsset);
                        //field.set(assetInstance,compositeAsset);
                        //PropertyUtils.setProperty(assetInstance, field.getName(), compositeAsset);
                    } else if (field.isAnnotationPresent(Table.class)) {
                        String[][] table = AnnotationProcessor.tableAnnotationProcessor(field, br);
                        TypeAdapter.assignToFieldsBasedOnType(assetInstance, field, table);
                        //field.set(assetInstance, table);
                        //PropertyUtils.setProperty(assetInstance, field.getName(), table);
                    } else if (field.getType().isArray()) {
                        // field.
                        String enteredValue;
                        System.out.println("#################### " + field.getType().getComponentType());
                        do {
                            enteredValue = br.readLine();
                        } while (!CommonUtils.validateField(field, enteredValue));
                        Object array = (Object) enteredValue;
                        TypeAdapter.assignToFieldsBasedOnType(assetInstance, field, array);
                    } else if (field.getType().isAssignableFrom(List.class)) {
                        ParameterizedType listType = (ParameterizedType) field.getGenericType();
                        Class<?> genericClass = (Class<?>) listType.getActualTypeArguments()[0];

                        if (Type.class.isAssignableFrom(genericClass) || !Constants.PRIMITIVE_TYPES.contains
                                (genericClass.getSimpleName())) {
                            System.out.println("#######   " + genericClass.getSimpleName() + " Details #######");
                            Object compositeAsset = buildCompositeField(genericClass, br);
                            TypeAdapter.assignToFieldsBasedOnType(assetInstance, field, compositeAsset);
                            System.out.println("##################################################################");
                        } else {
                            System.out.println("Please enter value for " + field.getName());
                            String enteredValue;

                            do {
                                enteredValue = br.readLine();
                            } while (!CommonUtils.validateField(field, enteredValue));
                            TypeAdapter.assignToFieldsBasedOnType(assetInstance, field, enteredValue);
                        }
                    } else if (field.getType().isAssignableFrom(Map.class)) {
                        ParameterizedType listType = (ParameterizedType) field.getGenericType();
                        Class<?> genericKeyClass = (Class<?>) listType.getActualTypeArguments()[0];
                        Class<?> genericValueClass = (Class<?>) listType.getActualTypeArguments()[1];
                        System.out.println( genericKeyClass+" %%%%%  "+ genericValueClass);

                        if (Type.class.isAssignableFrom(genericKeyClass) || !Constants.PRIMITIVE_TYPES.contains
                                (genericKeyClass.getSimpleName())) {
                            System.out.println("#######   " + genericKeyClass.getSimpleName() + " Details #######");
                            Object compositeAsset = buildCompositeField(genericKeyClass, br);
                            TypeAdapter.assignToFieldsBasedOnType(assetInstance, field, compositeAsset);
                            System.out.println("##################################################################");
                        } else {
                            System.out.println("Please enter value for " + field.getName());
                            String enteredValue;

                            do {
                                enteredValue = br.readLine();
                            } while (!CommonUtils.validateField(field, enteredValue));
                            TypeAdapter.assignToFieldsBasedOnType(assetInstance, field, enteredValue);
                        }
                        if (Type.class.isAssignableFrom(genericValueClass) || !Constants.PRIMITIVE_TYPES.contains
                                (genericKeyClass.getSimpleName())) {
                            System.out.println("#######   " + genericValueClass.getSimpleName() + " Details #######");
                            Object compositeAsset = buildCompositeField(genericValueClass, br);
                            TypeAdapter.assignToFieldsBasedOnType(assetInstance, field, compositeAsset);
                            System.out.println("##################################################################");
                        } else {
                            System.out.println("Please enter value for " + field.getName());
                            String enteredValue;

                            do {
                                enteredValue = br.readLine();
                            } while (!CommonUtils.validateField(field, enteredValue));
                            TypeAdapter.assignToFieldsBasedOnType(assetInstance, field, enteredValue);
                        }
                    } else if (!field.getType().isEnum() && !Constants.PRIMITIVE_TYPES.contains(field.getType()
                            .getSimpleName())) {
                        Object customFieldValue = buildNonPrimitiveField(field.getType(), br);
                        TypeAdapter.assignToFieldsBasedOnType(assetInstance, field, customFieldValue);
                    } else {
                        System.out.println("Please enter value for " + field.getName());
                        CommonUtils.preProcessField(field);
                        String enteredValue = null;
                        do {
                            enteredValue = br.readLine();
                        } while (!CommonUtils.validateField(field, enteredValue));
                        TypeAdapter.assignToFieldsBasedOnType(assetInstance, field, enteredValue);
                        //field.set(assetInstance, enteredValue);
                        //PropertyUtils.setProperty(assetInstance, field.getName(), enteredValue);
                    }
                 /*catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }*/
                } catch (IllegalArgumentException e) {
                    System.err
                            .println("Error while assigning value to field " + field.getName() + ". " + e.getMessage());
                    //System.out.println("Please re enter value for " + field.getName());
                }
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return assetInstance;
    }

    public static Object buildCompositeField(Class assetDefinition, BufferedReader br) throws IOException {
        Object assetInstance = null;
        Constructor constructor = null;
        if (assetDefinition != null) {
            try {

                constructor = assetDefinition.getConstructor();
                assetInstance = constructor.newInstance();
                for (Field field : CommonUtils.getAllFields(new ArrayList<Field>(), assetDefinition)) {
                    try {
                        if (field.isAnnotationPresent(Table.class)) {
                            String[][] table = AnnotationProcessor.tableAnnotationProcessor(field, br);
                            TypeAdapter.assignToFieldsBasedOnType(assetInstance, field, table);
                            //field.set(assetInstance,table);
                            //PropertyUtils.setProperty(assetInstance, field.getName(), table);
                        } else {
                            System.out.println("Please enter value for " + field.getName());
                            CommonUtils.preProcessField(field);
                            String enteredValue;
                            do {
                                enteredValue = br.readLine();
                            } while (!CommonUtils.validateField(field, enteredValue));
                            TypeAdapter.assignToFieldsBasedOnType(assetInstance, field, enteredValue);
                            //field.set(assetInstance, enteredValue);
                            //PropertyUtils.setProperty(assetInstance, field.getName(), enteredValue);
                        }
                    } /*catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } */ catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return assetInstance;
    }

    public static Object buildNonPrimitiveField(Class assetDefinition, BufferedReader br) {
        Object fieldInstance = null;
        Constructor constructor = null;
        if (assetDefinition != null) {
            try {

                constructor = assetDefinition.getConstructor();
                fieldInstance = constructor.newInstance();
                for (Field field : CommonUtils.getAllFields(new ArrayList<Field>(), assetDefinition)) {
                    try {
                        if (field.isAnnotationPresent(Table.class)) {
                            String[][] table = AnnotationProcessor.tableAnnotationProcessor(field, br);
                            TypeAdapter.assignToFieldsBasedOnType(fieldInstance, field, table);
                            //field.set(assetInstance,table);
                            //PropertyUtils.setProperty(assetInstance, field.getName(), table);
                        } else {
                            System.out.println("Please enter value for " + field.getName());
                            CommonUtils.preProcessField(field);
                            String enteredValue;
                            do {
                                enteredValue = br.readLine();
                            } while (!CommonUtils.validateField(field, enteredValue));
                            TypeAdapter.assignToFieldsBasedOnType(fieldInstance, field, enteredValue);
                            //field.set(assetInstance, enteredValue);
                            //PropertyUtils.setProperty(assetInstance, field.getName(), enteredValue);
                        }
                    } /*catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } */ catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        }
        return fieldInstance;
    }
}
