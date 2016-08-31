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

import org.wso2.carbon.governance.asset.definition.annotations.TextField;
import org.wso2.carbon.governance.asset.definition.annotations.Type;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Type ("vnd.wso2.soapservice")
public class SoapService extends Service {

    @TextField("Version of Soap Service")
    @Pattern (regexp = "^((\\d+\\.)(\\d+\\.)(\\d+))?$")
    private String version;

    @NotNull
    @TextField (label = "Namespace for Soap Service")
    private String namespace;

    // association
    private List<Endpoint> endpoint;

    // association
    private Policy policy;

    private TransportProtocols transportProtocol;

    private AuthenticationMechanism authenticationMechanism;

    // composition
    private List<Contact> contacts;

    private List<DocLink> docLinks;

    public enum TransportProtocols {
        HTTP, HTTPS, SMTP;

    }

    public enum AuthenticationMechanism {
        OPENID("Open Id"), INFOCARD("Info card"), CLIENTCERTIFICATES("Client Certifcates"), BASICAUTH(
                "HTTPS Basic Authentication"), IPFILTERING("IP Address Filtering");
        private String mechanism;

        private AuthenticationMechanism(String mechanism){
            this.mechanism = mechanism;
        }
    }
}


