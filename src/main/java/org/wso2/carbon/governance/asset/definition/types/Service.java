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

package org.wso2.carbon.governance.asset.definition.types;

import org.wso2.carbon.governance.asset.definition.annotations.Group;
import org.wso2.carbon.governance.asset.definition.annotations.RegEx;
import org.wso2.carbon.governance.asset.definition.annotations.Required;
import org.wso2.carbon.governance.asset.definition.annotations.TextArea;
import org.wso2.carbon.governance.asset.definition.annotations.TextField;
import org.wso2.carbon.governance.asset.definition.annotations.Type;

import java.util.List;

@Type (displayName = "Service", mediaType = "vnd.wso2.service")
public abstract class Service implements org.wso2.carbon.governance.asset.definition.types.Type {

    public List<Integer> a;
    @Group(displayName ="overview")
    @Required()
    @TextField(displayName = "displayName")
    public String name;

    @Group(displayName ="overview")
    @TextField(displayName = "version")
    @RegEx(expression = "^((\\d+\\.)(\\d+\\.)(\\d+))?$")
    public String version;

    @Group(displayName ="overview")
    @TextArea(displayName = "description")
    public String description;


    @TextField(displayName = "scopes")
    public String scopes;

    /*@Required(true)
    @RegEx(expression = "^([0-9]|[0-1][0-8])?$")
    @TextField(displayName = "test")
    private String test;*/
    /*@TextField(displayName = "docLinks", displayName = "Doc Links")
    private String[] docLinks;*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    public List<Integer> getA() {
        return a;
    }

    public void setA(List<Integer> a) {
        this.a = a;
    }
}
