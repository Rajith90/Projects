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

import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final Class STRING_CLASS = String.class;
    public static final Class STRING_ARRAY_CLASS = String[].class;
    public static final Class STRING_DOUBLE_ARRAY_CLASS = String[][].class;
    public static final String TEXT_FIELD_ANNOTATION = "TextField";
    public static final String TEXT_AREA_ANNOTATION = "TextArea";
    public static final String OPTIONS_FIELD_ANNOTATION = "OptionsField";
    public static final String TABLE_ANNOTATION = "Table";
    public static  final String GROUP_ANNOTATION = "Group";
    public static final List<String> PRIMITIVE_TYPES = Arrays.asList("int", "float", "double", "boolean", "short",
            "byte", "long", "char","String", "Object", "Integer", "Float", "Double", "Character", "Boolean", "Short, "
                    + "Byte", "Long", "Date", "Enum", "Document");

}
