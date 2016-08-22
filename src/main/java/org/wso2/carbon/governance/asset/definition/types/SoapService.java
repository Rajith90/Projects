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

import org.wso2.carbon.governance.asset.definition.annotations.OptionsField;
import org.wso2.carbon.governance.asset.definition.annotations.Required;
import org.wso2.carbon.governance.asset.definition.annotations.Table;
import org.wso2.carbon.governance.asset.definition.annotations.TextField;
import org.wso2.carbon.governance.asset.definition.annotations.Type;

@Type (name = "Soap Service", mediaType = "vnd.wso2.soapservice")
public class SoapService extends Service {
    @Required(value = true)
    @TextField (name = "namespace")
    private String namespace;
    @OptionsField(name = "transportProtocols", values = {"HTTP", "HTTPS", "SMTP"})
    private String transportProtocol  ;
    @OptionsField(name = "messageFormats", values = {"Soap 1.1", "Soap 1.2", "XML", "JSON"})
    private String messageFormat ;
    @OptionsField(name = "contactValues", values = {"Technical Owner", "Business Owner"})
    private String contactValue ;
    @Table(label = "Doc Links", columns = 3, rows =2, columnHeadings = {"Document Type", "URL", "Comment"})
    private String docLinks[][] = new String[2][3];

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
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

    public String getContactValue() {
        return contactValue;
    }

    public void setContactValue(String contactValue) {
        this.contactValue = contactValue;
    }

    public String[][] getDocLinks() {
        return docLinks;
    }

    public void setDocLinks(String[][] docLinks) {
        this.docLinks = docLinks;
    }
    /*public Map<String, String> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(Map<String, String> endpoints) {
        this.endpoints = endpoints;
    }*/
}


