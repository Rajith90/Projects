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

@Type (name = "Service", mediaType = "vnd.wso2.service")
public abstract class Service extends org.wso2.carbon.governance.asset.definition.types.Type {

    @Group(name="overview", fields = {"name","version","description"})
    @Required(true)
    @TextField(label = "name")
    private String name;
    @TextField(label = "version")
    private String version;
    @TextArea(label = "description")
    private String description;


    @TextField(label = "scopes")
    private String scopes;

    @Required(true)
    @RegEx(expression = "^([0-9]|[0-1][0-8])?$")
    @TextField(label = "test")
    private String test;
    /*@TextField(name = "docLinks", label = "Doc Links")
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

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
/*public String[] getDocLinks() {
        return docLinks;
    }

    public void setDocLinks(String[] docLinks) {
        this.docLinks = docLinks;
    }*/
}
