package com.mobicule.vodafone.loginService.syncMQ.controller;


import com.mobicule.vodafone.loginService.common.entities.Response;
import com.mobicule.vodafone.loginService.syncMQ.model.posResubRequest.POSResubmissionApiRequestJSON;
import com.mobicule.vodafone.loginService.syncMQ.model.posResubResponse.CreateSalesOrderVBMResponse;
import com.mobicule.vodafone.loginService.syncMQ.model.posResubResponse.MetaInfoResponse;
import com.mobicule.vodafone.loginService.syncMQ.model.posResubResponse.POSResubmissionApiResponseJSON;
import com.mobicule.vodafone.loginService.syncMQ.model.posResubResponse.ResultStatus;
import com.mobicule.vodafone.loginService.syncMQ.service.JsonMQRequestReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
@RestController
@RequestMapping("/mq/json")
@RequiredArgsConstructor
public class MQControllerJson {

    private final JsonMQRequestReplyService service;

    @PostMapping("/{entity}")
    public Object callMQ(@RequestBody POSResubmissionApiRequestJSON request,
                         @PathVariable String entity) throws Exception {

        POSResubmissionApiResponseJSON posResubmissionApiResponseJSON = new POSResubmissionApiResponseJSON();

        Object ResponseObject = service.callMQ(request, entity);

        posResubmissionApiResponseJSON = (POSResubmissionApiResponseJSON) ResponseObject;
        log.info("Web Service Response ------------> " + posResubmissionApiResponseJSON.toString());
        if (posResubmissionApiResponseJSON.getCreateSalesOrderVBMResponse() != null) {
            CreateSalesOrderVBMResponse createSalesOrderVBMResponse = posResubmissionApiResponseJSON
                    .getCreateSalesOrderVBMResponse();

            MetaInfoResponse metaInfoResponse = null;
            ResultStatus resultStatus = null;
            if (posResubmissionApiResponseJSON.getCreateSalesOrderVBMResponse().getMetaInfoResponse() != null) {
                metaInfoResponse = createSalesOrderVBMResponse.getMetaInfoResponse();
                resultStatus = metaInfoResponse.getResultStatus();
            } else {
                log.info("Web Service Response MetaInfoResponse Getting Null------------> ");
            }

            if (metaInfoResponse.getResultStatus().getErrorCode().equalsIgnoreCase("VEAI00000I")) {
                log.info(":::::  Request Resubmit Suscessfully ..... ");
                log.info("success case ");
                Response response = new Response(Response.ResponseStatus.SUCCESS, resultStatus.getDescription().get(1).toString(), new ArrayList<>());
                return response;

            }
            else {
                Response response = new Response(Response.ResponseStatus.FAILURE, resultStatus.getDescription().get(1).toString(), new ArrayList<>());
                return response;
            }
        }
        return ResponseObject;
    }
}
