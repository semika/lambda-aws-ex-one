package com.semika.aws;

/*
 * Copyright 2015-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with the License. A copy of the License is located at
 *
 *     http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AuthPolicy receives a set of allowed and denied methods and generates a valid
 * AWS policy for the API Gateway authorizer. The constructor receives the calling
 * user principal, the AWS account ID of the API owner, and an apiOptions object.
 * The apiOptions can contain an API Gateway RestApi Id, a region for the RestApi, and a
 * stage that calls should be allowed/denied for. For example
 *
 * new AuthPolicy(principalId, AuthPolicy.PolicyDocument.getDenyAllPolicy(region, awsAccountId, restApiId, stage));
 *
 * @author Jack Kohn
 */
public class AuthPolicy {

    // IAM Policy Constants
    public static final String VERSION = "Version";
    public static final String STATEMENT = "Statement";
    public static final String EFFECT = "Effect";
    public static final String ACTION = "Action";
    public static final String NOT_ACTION = "NotAction";
    public static final String RESOURCE = "Resource";
    public static final String NOT_RESOURCE = "NotResource";
    public static final String CONDITION = "Condition";

    String principalId;
    transient PolicyDocument policyDocumentObject;
    Map<String, Object> policyDocument;

    public AuthPolicy(String principalId, PolicyDocument policyDocumentObject) {
        this.principalId = principalId;
        this.policyDocumentObject = policyDocumentObject;
    }

    public AuthPolicy() {
    }

    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }

    /**
     * IAM Policies use capitalized field names, but Lambda by default will serialize object members using camel case
     *
     * This method implements a custom serializer to return the IAM Policy as a well-formed JSON document, with the correct field names
     *
     * @return IAM Policy as a well-formed JSON document
     */
    public Map<String, Object> getPolicyDocument() {
        Map<String, Object> serializablePolicy = new HashMap<>();
        serializablePolicy.put(VERSION, policyDocumentObject.Version);
        Statement[] statements = policyDocumentObject.getStatement();
        Map<String, Object>[] serializableStatementArray = new Map[statements.length];
        for (int i = 0; i < statements.length; i++) {
            Map<String, Object> serializableStatement = new HashMap<>();
            Statement statement = statements[i];
            serializableStatement.put(EFFECT, statement.Effect);
            serializableStatement.put(ACTION, statement.Action);
            serializableStatement.put(RESOURCE, statement.getResource());
            serializableStatement.put(CONDITION, statement.getCondition());
            serializableStatementArray[i] = serializableStatement;
        }
        serializablePolicy.put(STATEMENT, serializableStatementArray);
        return serializablePolicy;
    }

    public void setPolicyDocument(PolicyDocument policyDocumentObject) {
        this.policyDocumentObject = policyDocumentObject;
    }

}
