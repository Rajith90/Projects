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
package org.wso2.carbon.governance.asset.definition;

import org.apache.commons.beanutils.PropertyUtils;
import org.w3c.dom.Document;
import org.wso2.carbon.governance.asset.definition.annotations.Custom;
import org.wso2.carbon.governance.asset.definition.annotations.Group;
import org.wso2.carbon.governance.asset.definition.annotations.Table;
import org.wso2.carbon.governance.asset.definition.types.Type;
import org.wso2.carbon.governance.asset.definition.utils.CommonUtils;
import org.wso2.carbon.governance.asset.definition.utils.Constants;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class AssetInstance {

    private Type assetDetails;

    public AssetInstance(Type assetDetails) {
        this.assetDetails = assetDetails;
    }

    public void persistAsset() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        System.out.println("***************************   Asset Details ***********************************");
        Class assetClass = assetDetails.getClass();
        Set<String> groupedFieldList = new HashSet<>();
        ;
        for (Field field : CommonUtils.getAllFields(new ArrayList<Field>(), assetClass)) {
            if ((Type.class.isAssignableFrom(field.getType()) || !Constants.PRIMITIVE_TYPES
                    .contains(field.getType().getSimpleName())) && !field.getType().isAssignableFrom(List.class)
                    && !field.getType().isEnum()) {
                System.out.println("*********  " + field.getType().getSimpleName() + " Details ***********");
                Class genericClass = field.getType();

                compositeAssetPersist(field.get(assetDetails), genericClass);
                //compositeAssetPersist((Type) PropertyUtils.getProperty(assetDetails, field.getName()));
                System.out.println("*********************************************************************");
            } else if (field.getType().isAssignableFrom(List.class)) {
                ParameterizedType listType = (ParameterizedType) field.getGenericType();
                Class<?> genericClass = (Class<?>) listType.getActualTypeArguments()[0];
                if (!Constants.PRIMITIVE_TYPES.contains(genericClass.getSimpleName()) || Type.class
                        .isAssignableFrom(genericClass)) {

                    System.out.println("*********  " + genericClass.getSimpleName() + " Details ***********");
                    for (Object object : (List<Object>) field.get(assetDetails)) {

                        compositeAssetPersist(object, genericClass);
                    }
                    System.out.println("*********************************************************************");
                } else {
                    System.out.println(field.getName() + ":" + field.get(assetDetails));
                }
            } else if (field.isAnnotationPresent(Group.class)) {
                Group group = field.getDeclaredAnnotation(Group.class);
                /*List<String> groupedFields = Arrays.asList(group.fields());
                System.out.println("Fields with group displayName : " + group.name());
                for (String element : groupedFields) {
                    try {
                        System.out.println("\t" + element + ":" + PropertyUtils.getProperty(assetDetails, element));
                        groupedFieldList.add(element);
                    } catch (NoSuchMethodException e) {
                        System.err.println(
                                "No field exist with displayName \"" + element + "\" for group " + group.name());
                    }
                }**/
                System.out.println(group.displayName() + " " +
                        "\t" + field.getName() + ":" + field.get(assetDetails));
                /*try {

                    System.out.println(group.displayName() +" "+
                            "\t" + field.getName() + ":" + PropertyUtils.getProperty(assetDetails, field.getName()));
                } catch (NoSuchMethodException e) {
                    System.err.println(
                            "No field exist with displayName \"" + field.getName() + "\" for group " + group.displayName());
                }*/
            } else if (field.isAnnotationPresent(Table.class)) {
                Table table = field.getDeclaredAnnotation(Table.class);
                String[][] tableElements = (String[][]) field.get(assetDetails);
                //String[][] tableElements = (String[][]) PropertyUtils.getProperty(assetDetails, field.getName());
                System.out.println(table.label());
                for (int j = 0; j < table.columns(); j++) {
                    System.out.print("\t" + table.columnHeadings()[j]);
                }
                System.out.println();
                for (int i = 0; i < table.rows(); i++) {
                    for (int j = 0; j < table.columns(); j++) {
                        System.out.print("\t" + tableElements[i][j]);
                    }
                    System.out.println();
                }
            } else if(field.getType().isAssignableFrom(Document.class)){
                try
                {
                    DOMSource domSource = new DOMSource((Document)field.get(assetDetails));
                    StringWriter writer = new StringWriter();
                    StreamResult result = new StreamResult(writer);
                    TransformerFactory tf = TransformerFactory.newInstance();
                    Transformer transformer = tf.newTransformer();
                    transformer.transform(domSource, result);
                    System.out.println(field.getName()+ ":"+ writer.toString());
                }
                catch(TransformerException ex)
                {
                    ex.printStackTrace();
                }
            }
            else {

                System.out.println(field.getName() + ":" + field.get(assetDetails));
                    /*System.out
                            .println(field.getName() + ":" + PropertyUtils.getProperty(assetDetails, field.getName())
                            );*/

            }
        }
    }

    private void compositeAssetPersist(Object assetCompositeDetails, Class assetClass)
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if (assetCompositeDetails != null) {
            //Class assetClass = assetCompositeDetails.getClass();
            Set<String> groupedFieldList = new HashSet<>();

            for (Field field : CommonUtils.getAllFields(new ArrayList<Field>(), assetClass)) {
                if (Type.class.isAssignableFrom(field.getType()) || field.isAnnotationPresent(Custom.class)) {
                    System.out.println(field.getClass().getSimpleName() + " Details :");
                    compositeAssetPersist(field.get(assetCompositeDetails), field.getType());
                    //compositeAssetPersist((Type) PropertyUtils.getProperty(assetCompositeDetails, field.getName()));
                    System.out.println("*********************************************************************");
                } else if (field.isAnnotationPresent(Group.class)) {
                    Group group = field.getDeclaredAnnotation(Group.class);
                    /*List<String> groupedFields = Arrays.asList(group.fields());
                    System.out.println("Fields with group displayName : " + group.name());
                    for (String element : groupedFields) {
                        try {
                            System.out.println(
                                    "\t" + element + ":" + PropertyUtils.getProperty(assetCompositeDetails, element));
                            groupedFieldList.add(element);
                        } catch (NoSuchMethodException e) {
                            System.err.println(
                                    "No field exist with displayName \"" + element + "\" for group " + group.name());
                        }
                    }*/
                    System.out.println(group.displayName() + " " +
                            "\t" + field.getName() + ":" + field.get(assetCompositeDetails));
                   /* try {
                        System.out.println(group.displayName() +" "+
                                "\t" + field.getName() + ":" + PropertyUtils.getProperty(assetDetails, field.getName()));
                    } catch (NoSuchMethodException e) {
                        System.err.println(
                                "No field exist with displayName \"" + field.getName() + "\" for group " + group.displayName());
                    }*/
                } else if (field.getType().isAssignableFrom(List.class)) {
                    ParameterizedType listType = (ParameterizedType) field.getGenericType();
                    Class<?> genericClass = (Class<?>) listType.getActualTypeArguments()[0];
                    if (!Constants.PRIMITIVE_TYPES.contains(genericClass.getSimpleName()) || Type.class
                            .isAssignableFrom(genericClass)) {

                        System.out.println("*********  " + genericClass.getSimpleName() + " Details ***********");
                        for (Object object : (List<Object>) field.get(assetCompositeDetails)) {

                            compositeAssetPersist(object, genericClass);
                        }
                        System.out.println("*********************************************************************");
                    } else {
                        System.out.println(field.getName() + ":" + field.get(assetCompositeDetails));
                    }
                }else if (field.isAnnotationPresent(Table.class)) {
                    Table table = field.getDeclaredAnnotation(Table.class);
                    String[][] tableElements = (String[][]) PropertyUtils
                            .getProperty(assetCompositeDetails, field.getName());
                    System.out.println(table.label());
                    for (int j = 0; j < table.columns(); j++) {
                        System.out.print("\t" + table.columnHeadings()[j]);
                    }
                    System.out.println();
                    for (int i = 0; i < table.rows(); i++) {
                        for (int j = 0; j < table.columns(); j++) {
                            System.out.print("\t" + tableElements[i][j]);
                        }
                        System.out.println();
                    }
                } else if(field.getType().isAssignableFrom(Document.class)){
                    try
                    {
                        DOMSource domSource = new DOMSource((Document)field.get(assetCompositeDetails));
                        StringWriter writer = new StringWriter();
                        StreamResult result = new StreamResult(writer);
                        TransformerFactory tf = TransformerFactory.newInstance();
                        Transformer transformer = tf.newTransformer();
                        transformer.transform(domSource, result);
                        System.out.println(field.getName()+ ":"+ writer.toString());
                    }
                    catch(TransformerException ex)
                    {
                        ex.printStackTrace();
                    }
                } else {
                    System.out.println(field.getName() + ":" + field.get(assetCompositeDetails));
                    /*System.out.println(field.getName() + ":" + PropertyUtils.getProperty(assetCompositeDetails,
                            field.getName()));*/
                    /*if (!groupedFieldList.contains(field.getName())) {
                        System.out.println(field.getName() + ":" + PropertyUtils
                                .getProperty(assetCompositeDetails, field.getName()));
                    }*/
                }
            }
        }
    }
}
