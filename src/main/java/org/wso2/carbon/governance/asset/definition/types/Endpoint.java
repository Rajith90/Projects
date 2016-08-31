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

import org.wso2.carbon.governance.asset.definition.annotations.RegEx;
import org.wso2.carbon.governance.asset.definition.annotations.Type;
import org.wso2.carbon.governance.asset.definition.annotations.TextField;

import javax.validation.constraints.Pattern;

@Type ("vnd.wso2.endpoint")
public class Endpoint implements org.wso2.carbon.governance.asset.definition.types.Type {
    @TextField(label = "version")
    //@RegEx(expression = "^((\\d+\\.)(\\d+\\.)(\\d+))?$")
    @Pattern(regexp = "^((\\d+\\.)(\\d+\\.)(\\d+))?$")
    public String version;

    @TextField(label = "address")
    public String address;

    @TextField(label = "environment")
    public String environment;

    /*@OptionsField(label = "supportedTypes", values = {"HTTP", "HTTPS"})
    String[] types;*/
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }
}
