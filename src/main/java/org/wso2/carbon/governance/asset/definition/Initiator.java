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

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.generic.ClassGen;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.wso2.carbon.governance.asset.definition.annotations.Type;
import org.wso2.carbon.governance.asset.definition.utils.AssetDefinitionValidator;
import org.wso2.carbon.governance.asset.definition.utils.InputProcessor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Initiator {

    public static HashMap<String, ClassGen> classes = new HashMap<String, ClassGen>();
    public static HashMap<String, Class> assetDefinitions = new HashMap<String, Class>();
    private static final String TypeDefinitionsPath = "org.wso2.carbon.governance.asset.definition.types";
    public static void main(String[] args) {
        try {

            readTypeDefinitions();
            org.wso2.carbon.governance.asset.definition.types.Type assetDetails = InputProcessor.readInputs(assetDefinitions);
            AssetInstance assetInstance = new AssetInstance(assetDetails);
            assetInstance.persistAsset();
            //traversAssetDefinitions();
            //readBytes("/home/rajith/GREG/POC/RXT/out/production/RXT/org/wso2/carbon/sample/definitions/extension
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static byte[] getBytes(String javaFileName, String jar) throws IOException {
        // ... inputs check omitted ...
        try (JarFile jarFile = new JarFile(jar)) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();

                // We are only interested in .class files spawned by compiling the argument file.
                if (entry.getName().endsWith(".class") &&
                        entry.getName().contains(javaFileName.substring(0, javaFileName.lastIndexOf(".")))) {
                    try (InputStream inputStream = jarFile.getInputStream(entry)) {
                        return getBytes(inputStream);
                    } catch (IOException ioException) {
                        System.out.println("Could not obtain class entry for " + entry.getName());
                        throw ioException;
                    }
                }
            }
        }
        throw new IOException("File not found");
    }

    private static void traversAssetDefinitions(){
        for (Class cl : assetDefinitions.values()) {
            System.out.println(cl.getName());
            for (Field field : getAllFields(new ArrayList<Field>(),cl)) {
                System.out.println(field.getName());
                for (Annotation annotation : field.getDeclaredAnnotations()) {
                    System.out.println(annotation.toString());
                }
            }
        }
    }

    public static List<Field> getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            fields = getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }


    private static void readTypeDefinitions() {

        List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(""))));
        Set<Class<? extends org.wso2.carbon.governance.asset.definition.types.Type>> classes = reflections.getSubTypesOf(
                org.wso2.carbon.governance.asset.definition.types.Type.class);
        for (Class extended : classes) {
            if (!Modifier.isAbstract(extended.getModifiers())
                    && extended.getAnnotation(Type.class) != null && AssetDefinitionValidator.validateFields(extended)) {
                assetDefinitions.put(extended.getSimpleName(), extended);
                // System.out.println(extended.getSimpleName());
            }
            //System.out.println(Modifier.isAbstract(extended.getModifiers()));

        }
        // ... inputs check omitted ...
        /*final String classPath = System.getProperty("java.class.path", ".");
        System.out.println("classPath" + classPath);*/
        /*Reflections reflections = new Reflections(classPath);
        Set<Class<? extends Type>> classes = reflections.getSubTypesOf(Type.class);
        for(Class extended : classes){
            System.out.println(extended.getName());
        }
        List<Class<?>> typeClasses = ClassFinder.find(TypeDefinitionsPath);
        for (Class classex : typeClasses) {
            //System.out.println("##  " + classex.getAnnotation(GovernanceConfiguration.class));
            assetDefinitions.put(
                    classex.getAnnotation(GovernanceConfiguration.class).toString().split("=")[1].replaceAll("\\)", ""),
                    classex);
        }*/

    }

    private static void readBytes( String jar) throws IOException {
        // ... inputs check omitted ...

        //final String classPath = System.getProperty("java.class.path", ".");
       // System.out.println("classPath" + classPath);
        try (JarFile jarFile = new JarFile(jar)) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                //System.out.println(entry.getName());

                if (entry.getName().endsWith(".class")) {
                    Class definition = entry.getClass();
                    System.out.println(entry.getName());
                    /*for (Field f: definition.getFields()) {
                        System.out.println(f.getName());
                        Required required = f.getAnnotation(Required.class);
                        if (required != null)
                            System.out.println(required.value());
                    }*/
                    for(Annotation annotation : definition.getAnnotations()){
                        System.out.println(annotation.toString());
                    }

                   /* for (Annotation annotation : definition.getAnnotations()) {
                        Class<? extends Annotation> type = annotation.annotationType();
                        System.out.println("Values of " + type.getName());

                        for (Method method : type.getDeclaredMethods()) {
                            Object value = method.invoke(annotation, (Object[])null);
                            System.out.println(" " + method.getName() + ": " + value);
                        }
                    }*/
                    ClassParser cp = new ClassParser(jarFile.getInputStream(entry), entry.getName());

                    JavaClass jc = cp.parse();

                    // gets the bcel classgen of the class.
                    System.out.println(jc.getConstantPool());

                    System.out.println("*******Fields*********");
                    System.out.println(Arrays.toString(jc.getFields()));
                    System.out.println();

                    System.out.println();

                    System.out.println("*******Methods*********");
                    System.out.println(Arrays.toString(jc.getMethods()));

                    ClassGen cg = new ClassGen(jc);

                    // put our classes in a hashmap

                    classes.put(cg.getClassName(), cg);
                }

            }
        }
        catch (Exception e) {
            throw new IOException("File not found");
        }
    }

    private static byte[] getBytes(InputStream is) throws IOException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream();)
        {
            byte[] buffer = new byte[0xFFFF];
            for (int len; (len = is.read(buffer)) != -1;)
                os.write(buffer, 0, len);
            os.flush();
            return os.toByteArray();
        }
    }

    private static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}


