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
import org.wso2.carbon.governance.asset.definition.annotations.Group;
import org.wso2.carbon.governance.asset.definition.annotations.Table;
import org.wso2.carbon.governance.asset.definition.types.Type;
import org.wso2.carbon.governance.asset.definition.utils.CommonUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AssetInstance {
    private Type assetDetails;

    public AssetInstance(Type assetDetails) {
        this.assetDetails = assetDetails;
    }

    public void persistAsset() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class assetClass = assetDetails.getClass();
        Set<String> groupedFieldList = new HashSet<>();;
        for(Field field : CommonUtils.getAllFields(new ArrayList<Field>(), assetClass)){
            if( field.isAnnotationPresent(Group.class)){
                Group group = field.getDeclaredAnnotation(Group.class);
                List<String> groupedFields = Arrays.asList(group.fields());
                System.out.println("Fields with group name : "+ group.name());
                for(String element : groupedFields){
                    try {
                        System.out.println("\t" + element + ":" + PropertyUtils.getProperty(assetDetails, element));
                        groupedFieldList.add(element);
                    } catch (NoSuchMethodException e){
                        System.err.println("No field exist with name \"" + element + "\" for group "+ group.name());
                    }
                }
            } else if(field.isAnnotationPresent(Table.class)){
                Table table = field.getDeclaredAnnotation(Table.class);
                String[][] tableElements = (String [][])PropertyUtils.getProperty(assetDetails, field.getName());
                System.out.println(table.label());
                for (int j=0; j<table.columns(); j++){
                    System.out.print("\t" + table.columnHeadings()[j]);
                }
                System.out.println();
                for (int i=0; i< table.rows(); i++){
                    for (int j=0; j<table.columns(); j++){
                        System.out.print("\t" + tableElements[i][j]);
                    }
                    System.out.println();
                }
            } else {
                if (!groupedFieldList.contains(field.getName())) {
                    System.out.println(field.getName() + ":" + PropertyUtils.getProperty(assetDetails, field.getName()));
                }
            }
        }
    }
}
