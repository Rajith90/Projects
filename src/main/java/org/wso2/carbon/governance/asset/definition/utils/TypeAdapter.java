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
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class TypeAdapter {

    public static void assignToFieldsBasedOnType(Object asset, Field field, Object object)
            throws IllegalArgumentException {
        if (object != null && !object.toString().isEmpty()) {
            try {
                if (field.getType().isAssignableFrom(int.class)) {
                    field.setInt(asset, Integer.parseInt(object.toString()));
                    //PropertyUtils.setProperty(asset, field.getName(), Integer.parseInt((String) object));
                } else if (field.getType().isAssignableFrom(short.class)) {
                    field.setShort(asset, Short.parseShort(object.toString()));
                    //PropertyUtils.setProperty(asset, field.getName(), Integer.parseInt((String) object));
                } else if (field.getType().isAssignableFrom(boolean.class)) {
                    field.setBoolean(asset, Boolean.valueOf(object.toString()));
                    //PropertyUtils.setProperty(asset, field.getName(), Integer.parseInt((String) object));
                } else if (field.getType().isAssignableFrom(long.class)) {
                    field.setLong(asset, Long.parseLong((String) object));
                    //PropertyUtils.setProperty(asset, field.getName(), Long.parseLong((String) object));
                } else if (field.getType().isAssignableFrom(double.class)) {
                    field.setDouble(asset, Double.parseDouble(object.toString()));
                    //PropertyUtils.setProperty(asset, field.getName(), Double.parseDouble((String) object));
                } else if (field.getType().isAssignableFrom(float.class)) {
                    field.setFloat(asset, Float.parseFloat(object.toString()));
                    //PropertyUtils.setProperty(asset, field.getName(), Float.parseFloat((String) object));
                } else if (field.getType().isAssignableFrom(byte.class)) {
                    field.setByte(asset, Byte.valueOf(object.toString()));
                    //PropertyUtils.setProperty(asset, field.getName(), Byte.valueOf((String) object));
                } else if (field.getType().isEnum()) {
                    Enum<?> convertedValue = Enum.valueOf((Class<Enum>) field.getType(), object.toString());
                    field.set(asset, convertedValue);
                } else if (field.getType().isArray()) {
                    Object array = Array.newInstance(field.getType().getComponentType(), 1);
                    Array.set(array, 0, object);
                    field.set(asset, array);
                } else if (field.getType().isAssignableFrom(List.class)) {
                    List<Object> list = new ArrayList<>();
                    list.add(object);
                    field.set(asset, list);
                    // populateList(field, asset, object);
                } else if (field.getType().isAssignableFrom(Date.class)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
                    Date date = sdf.parse(object.toString());
                    field.set(asset, date);
                } else if (field.getType().isAssignableFrom(Document.class)) {
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder;
                    try
                    {
                        builder = factory.newDocumentBuilder();
                        Document document = builder.parse( new InputSource( new StringReader( object.toString() ) ) );
                        field.set(asset, document);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                } else {
                    field.set(asset, object);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } /*catch (IllegalArgumentException e){
            System.err.println("Error while assigning value to field " + field.getName() + ". " + e.getMessage());
            throw new Exception(e);
        } *//*catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }*/ catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    private static void populateList(Field field, Object asset, Object value) throws IllegalAccessException {
        ParameterizedType stringListType = (ParameterizedType) field.getGenericType();
        Class<?> genericClass = (Class<?>) stringListType.getActualTypeArguments()[0];
        if (genericClass.isAssignableFrom(Integer.class)) {
            List<Integer> list = new ArrayList<>();
            list.add(Integer.parseInt(value.toString()));
            field.set(asset, list);
        } else if (genericClass.isAssignableFrom(Long.class)) {
            List<Long> list = new ArrayList<>();
            list.add(Long.parseLong(value.toString()));
            field.set(asset, list);
        } else if (genericClass.isAssignableFrom(Double.class)) {
            List<Double> list = new ArrayList<>();
            list.add(Double.parseDouble(value.toString()));
            field.set(asset, list);
            //PropertyUtils.setProperty(asset, field.getName(), Double.parseDouble((String) object));
        } else if (genericClass.isAssignableFrom(Float.class)) {
            List<Float> list = new ArrayList<>();
            list.add(Float.parseFloat(value.toString()));
            field.set(asset, list);
            //PropertyUtils.setProperty(asset, field.getName(), Float.parseFloat((String) object));
        } else if (field.getType().isAssignableFrom(Enum.class)) {
            field.set(asset, value);

        } else {
            try {
                new ArrayList<>().add(value);
                PropertyUtils.setProperty(asset, field.getName(), new ArrayList<>().add(value));
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            //field.set(asset, value);
        }

    }
}
