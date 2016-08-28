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
import org.wso2.carbon.governance.asset.definition.annotations.TextField;
import org.wso2.carbon.governance.asset.definition.annotations.Type;

import java.util.Date;
import java.util.List;

@Type (displayName = "Soap Service", mediaType = "vnd.wso2.soapservice")
public class SoapService extends Service {
    private enum TransportProtocols {
        HTTP, HTTPS, SMTP
    }
    @Required()
    @TextField (displayName = "namespace")
    public String namespace;


    public List<EndPoint> endPoint;    // composition (related to association)

    public Policy policy;               // aggregation (related to association)


    public TransportProtocols transportProtocol ;

    @OptionsField(displayName = "messageFormats", values = {"Soap 1.1", "Soap 1.2", "XML", "JSON"})
    public String messageFormat ;

    public List<Contact> contacts;      // aggregation


    public List<DocLink> docs;


    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public TransportProtocols getTransportProtocol() {
        return transportProtocol;
    }

    public void setTransportProtocol(TransportProtocols transportProtocol) {
        this.transportProtocol = transportProtocol;
    }

    public String getMessageFormat() {
        return messageFormat;
    }

    public void setMessageFormat(String messageFormat) {
        this.messageFormat = messageFormat;
    }

    public List<Contact> getContactValue() {
        return contacts;
    }

    public void setContactValue(List<Contact> contactValue) {
        this.contacts = contactValue;
    }

    public List<EndPoint> getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(List<EndPoint> endPoint) {
        this.endPoint = endPoint;
    }


    public List<DocLink> getDocs() {
        return docs;
    }

    public void setDocs(List<DocLink> docs) {
        this.docs = docs;
    }
}


