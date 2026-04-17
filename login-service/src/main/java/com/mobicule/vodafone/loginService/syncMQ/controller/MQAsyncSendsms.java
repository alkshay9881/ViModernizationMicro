package com.mobicule.vodafone.loginService.syncMQ.controller;


import com.mobicule.vodafone.loginService.common.entities.Response;
import com.mobicule.vodafone.loginService.syncMQ.model.cocRequest.*;
import com.mobicule.vodafone.loginService.syncMQ.service.AsyncMQSendSmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mq/asyncXml")
@RequiredArgsConstructor
public class MQAsyncSendsms {

    private final AsyncMQSendSmsService asyncMQService;


    @PostMapping("/{entity}")
    public Object callMQ(@RequestBody AsyncReadWriteReqGBO request,
                         @PathVariable String entity) throws Exception {


        AsyncReadWriteReqGBO asyncReadWriteReqGBO = new AsyncReadWriteReqGBO();

        asyncReadWriteReqGBO.setCmn("http://docs.oasis-open.org/wsrf/bf-2");
        asyncReadWriteReqGBO.setXsi("http://group.vodafone.com/contract/vfo/fault/v1");
        asyncReadWriteReqGBO.setCct("http://group.vodafone.com/contract/vho/header/v1");
        asyncReadWriteReqGBO.setWsa("http://group.vodafone.com/schema/service1/event/v1");
        asyncReadWriteReqGBO.setWs_bf("http://group.vodafone.com/schema/vbm/technical/event/v1");
        asyncReadWriteReqGBO.setVfo("http://group.vodafone.com/schema/vbo/technical/event/v1");
        asyncReadWriteReqGBO.setExtvbo("http://www.w3.org/2007/XMLSchema-versioning");
        asyncReadWriteReqGBO.setCcts("http://www.w3.org/2001/XMLSchema");
        asyncReadWriteReqGBO.setVbm("http://www.w3.org/2001/XMLSchema-instance");
        asyncReadWriteReqGBO.setVbo("http://group.vodafone.com/schema/service1/event/v1");
        asyncReadWriteReqGBO.setVc("http://group.vodafone.com/schema/vbm/technical/event/v1");
        asyncReadWriteReqGBO.setTns("http://group.vodafone.com/contract/vho/header/v1");
        asyncReadWriteReqGBO.setTns1("http://group.vodafone.com/schema/common/v1");

        try
        {
            Header header = new Header();
            RouteInfo routeInfo = new RouteInfo();
            Route route = new Route();
            List<Route> RouteList = new ArrayList<>();
            Keys keys = new Keys();
            route.setId("Event.Notifi");
            keys.setKey("segment");
            route.setKeys(keys);
            RouteList.add(route);
            routeInfo.setRoute(RouteList);

            Correlation correlation = new Correlation();
            correlation.setConversationID("Transaction_Id");

            Destination destination = new Destination();
            destination.setSystem("VCONNECT.COCFlagDateUpdate");

            Source source = new Source();
            source.setDivision("circleCode");
            source.setSystem("VCONNECT");

            header.setRouteInfo(routeInfo);
            header.setCorrelation(correlation);
            header.setDestination(destination);
            header.setSource(source);

            NotifyEventVBMRequest notifyEventVBMReq = new NotifyEventVBMRequest();
            EventVBO eventVBO = new EventVBO();
            IDs ids = new IDs();
            List<ID> idList = new ArrayList();
            List<Category> catList = new ArrayList();
            Categories categories = new Categories();

            ID id1 = new ID();
            id1.setSchemeName("ENT_ORG_ID");
            id1.setValue((String) "");
            idList.add(id1);

            ID id2 = new ID();
            id2.setSchemeName("MSISDN");
            id2.setValue((String) "");
            idList.add(id2);

            ID id3 = new ID();
            id3.setSchemeName("PERSON_ENT_ID");
            id3.setValue((String) "");
            idList.add(id3);

            ID id4 = new ID();
            id4.setSchemeName("AGENT_ID");
            id4.setValue((String) "");
            idList.add(id4);
            ids.setIdList(idList);

            Category cat1 = new Category();
            cat1.setListHierarchyID("COC_FLAG");
            cat1.setValue("cocFlag");
            catList.add(cat1);

            Category cat2 = new Category();
            cat2.setListHierarchyID("COC_FLAG_DATE");
            cat2.setValue("coc_Flag_Date");
            catList.add(cat2);
            categories.setCategory(catList);

            eventVBO.setIds(ids);
            eventVBO.setCategories(categories);
            eventVBO.setType("personEntTypeID");

            asyncReadWriteReqGBO.setHeader(header);
            notifyEventVBMReq.setEventVBO(eventVBO);
            asyncReadWriteReqGBO.setNotifyEventVBMRequest(notifyEventVBMReq);

            log.info("ASync Request => " + asyncReadWriteReqGBO);

					/*int records = dbTransactionService.updateEntity("UPDATE_ENTITY_HIERARCHY_COC", entMap);

					log.info("Data updated Successfully");
					log.info("Number of records updated= " + records);*/

        }

        catch (Exception e)
        {
            log.info("----Exception while updating----", e);

        }


        Response responseMqSend= asyncMQService.sendAsync(asyncReadWriteReqGBO,entity);

        if (responseMqSend.getStatus().toString().equalsIgnoreCase("SUCCESS"))
        {
            log.info("success case ");
            Response response = new Response(Response.ResponseStatus.SUCCESS, "SUCCESS", new ArrayList<>());
            return response;
        }
        else {
            Response response = new Response(Response.ResponseStatus.FAILURE, "FAILURE", new ArrayList<>());
            return response;
        }

    }

}
