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

import org.wso2.carbon.governance.asset.definition.annotations.Type;
import org.wso2.carbon.governance.asset.definition.annotations.OptionsField;
import org.wso2.carbon.governance.asset.definition.annotations.TextField;

@Type (name = "Rest Service", mediaType = "vnd.wso2.restservice")
public class HTTPService extends Service{
    @TextField(label = "context")
    private String context;
    @TextField( label = "Wadl URL")
    private String wadl;
    @TextField( label = "Swagger URL")
    private String swagger;
    @OptionsField (label = "transportProtocols", values = {"HTTP", "HTTPS", "SMTP"})
    private String transportProtocol ;
    @OptionsField(label = "messageFormats", values =  {"Soap 1.1", "Soap 1.2", "XML", "JSON"})
    private String messageFormat ;
    @OptionsField(label = "contactValues", values = {"Technical Owner", "Business Owner"})
    private String contactValues ;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getWadl() {
        return wadl;
    }

    public void setWadl(String wadl) {
        this.wadl = wadl;
    }

    public String getSwagger() {
        return swagger;
    }

    public void setSwagger(String swagger) {
        this.swagger = swagger;
    }

    public String getTransportProtocol() {
        return transportProtocol;
    }

    public void setTransportProtocol(String transportProtocol) {
        this.transportProtocol = transportProtocol;
    }

    public String getMessageFormat() {
        return messageFormat;
    }

    public void setMessageFormat(String messageFormat) {
        this.messageFormat = messageFormat;
    }

    public String getContactValues() {
        return contactValues;
    }

    public void setContactValues(String contactValues) {
        this.contactValues = contactValues;
    }
}
