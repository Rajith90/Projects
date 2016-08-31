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
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@org.wso2.carbon.governance.asset.definition.annotations.Type ("vnd.wso2.application")
public class Applications extends SoapService{

    private enum Category{
        TOOLS,GAMES,SCIENTIFIC
    }

    @NotNull
    private String name;

    private int numOfDownloads;

    @Max(5)
    private float rating;

    private boolean isFree;

    @Pattern (regexp = "^((\\d+\\.)(\\d+\\.)(\\d+))?$")
    private String version;

    private Category category;

    private Date lastUpdated;

    @Size(max = 2)
    private DocLink[] docLinks;

    private List<Contact> contacts;

}
