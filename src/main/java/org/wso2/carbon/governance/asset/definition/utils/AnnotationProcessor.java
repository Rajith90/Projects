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
import org.wso2.carbon.governance.asset.definition.annotations.Required;
import org.wso2.carbon.governance.asset.definition.annotations.Table;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;

public class AnnotationProcessor {

    public static void optionFieldAnnotationProcessor(Field field) {
        OptionsField optionsField = field.getDeclaredAnnotation(OptionsField.class);
        System.out.println("Please select only one value from below ");
        for(String optionValue : optionsField.values()){
            System.out.println("* "+ optionValue);
        }
    }

    public static void optionsTextFieldAnnotationProcessor (){

    }

    public static void enumFieldAnnotationProcessor(Field field){
        Class<Enum> enumField = (Class<Enum>) field.getType();
        System.out.println("Please select one value from below list");
        System.out.println(Arrays.asList(enumField.getEnumConstants()));

    }


    public static String[][] tableAnnotationProcessor (Field field, BufferedReader br){
        Table table = field.getDeclaredAnnotation(Table.class);
        int rowCount = table.rows();
        int columnCount = table.columns();
        String tableElements[][] = new String[rowCount][columnCount];
        try {
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < columnCount; j++) {
                    System.out.println("Enter a value for " + table.columnHeadings()[j]);
                    tableElements[i][j]= br.readLine();
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return tableElements;
    }
}
