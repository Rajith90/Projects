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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jdk.nashorn.internal.scripts.JS;
import org.json.JSONObject;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.wso2.carbon.governance.asset.definition.annotations.Type;
import org.wso2.carbon.governance.asset.definition.factories.BaseFactoryClass;
import org.wso2.carbon.governance.asset.definition.types.Service;
import org.wso2.carbon.governance.asset.definition.types.SoapService;
import org.wso2.carbon.governance.asset.definition.utils.AnnotationValidator;
import org.wso2.carbon.governance.asset.definition.utils.AssetDefinitionValidator;
import org.wso2.carbon.governance.asset.definition.utils.CommonUtils;
import org.wso2.carbon.governance.asset.definition.utils.InputProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Initiator {

    private static final String TypeDefinitionsPath = "org.wso2.carbon.governance.asset.definition.types";
    public static HashMap<String, Class> assetDefinitions = new HashMap<String, Class>();
    public static HashMap<Class, BaseFactoryClass> baseFactoryClassHashMap = new HashMap<>();

    public static void main(String[] args) {
        try {

            readTypeDefinitions();
            /*String jsonInString = "{'name' : 'Soap1','version' : '2.3.4', namespace : 'www.wso2.com', "
                    + "'transportProtocol': 'HTTP12', 'date' : '2016-02-23',"
                    + "docs : [{'docType' : 'PDF', 'url' : 'www.pdf.com', 'comment' : 'test comment'}]}";
            JSONObject object = new JSONObject(jsonInString);

            Gson gson = new Gson();
            org.wso2.carbon.governance.asset.definition.types.Type sp = gson.fromJson(jsonInString, SoapService
            .class);*/
            org.wso2.carbon.governance.asset.definition.types.Type assetDetails = InputProcessor
                    .readInputs(assetDefinitions);
            System.out.println(AnnotationValidator.validate(assetDetails));
            AssetInstance assetInstance = new AssetInstance(assetDetails);
            assetInstance.persistAsset();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void traversAssetDefinitions() {
        for (Class cl : assetDefinitions.values()) {
            System.out.println(cl.getName());
            for (Field field : CommonUtils.getAllFields(new LinkedHashMap<>(), cl).values()) {
                System.out.println(field.getName());
                for (Annotation annotation : field.getDeclaredAnnotations()) {
                    System.out.println(annotation.toString());
                }
            }
        }
    }

    private static void readFactoryClasses(){
        List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(""))));
        Set<Class<? extends BaseFactoryClass>> classes = reflections
                .getSubTypesOf(BaseFactoryClass.class);
        for (Class factoryClass : classes) {
            System.out.println(((ParameterizedType) factoryClass
                    .getGenericSuperclass()).getActualTypeArguments()[0]);
        }
    }

    private static void readTypeDefinitions() {

        List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(""))));
        Set<Class<? extends org.wso2.carbon.governance.asset.definition.types.Type>> classes = reflections
                .getSubTypesOf(org.wso2.carbon.governance.asset.definition.types.Type.class);
        for (Class extended : classes) {
            if (!Modifier.isAbstract(extended.getModifiers()) && extended.getAnnotation(Type.class) != null
                    /*&& AssetDefinitionValidator.validateFields(extended)*/) {
                assetDefinitions.put(extended.getSimpleName(), extended);
            }

        }
    }

}


