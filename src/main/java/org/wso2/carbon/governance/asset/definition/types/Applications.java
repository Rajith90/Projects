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

import org.wso2.carbon.governance.asset.definition.annotations.Required;

import java.util.Date;
import java.util.List;
import java.util.Map;

@org.wso2.carbon.governance.asset.definition.annotations.Type (mediaType = "vnd.wso2"
        + ".application")
public class Applications implements Type{

    private enum Category{
        TOOLS,GAMES,SCIENTIFIC
    }
    @Required
    public String name;

    public int numOfDownloads;

    public float rating;

    public boolean isFree;

    public String version;

    public Category category;

    public Date lastUpdated;

    public List<DocLink> docLinks;

    public List<Contact> contacts;

}
