package com.semika.aws.model.response;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GatewayProxyResponse extends APIGatewayProxyResponseEvent {
    private String message;
}
