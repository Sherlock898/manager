package com.goldenelectric.manager.handlers;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.goldenelectric.manager.ocpp.ChargePoint;
import com.goldenelectric.manager.ocpp.WebSocketSessionManager;
import com.goldenelectric.manager.ocpp.Util.DateUtil;
import com.goldenelectric.manager.ocpp.payloads.RemoteStartTransactionReq;
import com.goldenelectric.manager.ocpp.payloads.RemoteStopTransactionReq;
import com.goldenelectric.manager.services.TransactionService;
import com.goldenelectric.manager.services.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

@Component
@Qualifier("userWsHandler")
public class UserHandler extends TextWebSocketHandler {
    WebSocketSessionManager sessionManager = WebSocketSessionManager.getInstance();

    @Autowired
    private UserService userService;
    @Autowired
    private TransactionService transactionService;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("User connected");
        Long userId;
        try {
            userId = Long.parseLong(session.getUri().toString().split("/")[4]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid user id: " + session.getUri().toString().split("/")[4] );
            session.close();
            return;
        }

        if (userService.findById(userId) == null) {
            System.out.println("User not found");
            session.close();
            return;
        }

        sessionManager.addUser(session);
        sessionManager.broadcastToUser(session);

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Start transaction messsage: {"action": "StartTransaction", "chargePointId": chargePointId, "connectorId": int}
        // Stop transaction message: [StopTransaction, ChargerId, ConnectorId]

        JsonArray msg = JsonParser.parseString(message.getPayload()).getAsJsonArray();
        if (msg.get(0).getAsString().equals("StartTransaction")) {
            System.out.println("Start transaction");
            Long userId;
            System.out.println(session.getUri().toString().split("/")[4]);
            try {
                userId = Long.parseLong(session.getUri().toString().split("/")[4]);
            } catch (Exception e) {
                System.out.println("Invalid user id: " + session.getUri().toString().split("/")[4]);
                return;
            }
            String chargePointId = msg.get(1).toString();

            // Find chargepoint session
            WebSocketSession cpSession = sessionManager.getAllChargePoints().keySet().stream()
                    .filter(s -> sessionManager.getChargePoint(s).getId().equals(chargePointId))
                    .findFirst().orElse(null);

            if (cpSession == null) {
                System.out.println("Charge point not found");
                return;
            }

            ChargePoint cp = sessionManager.getChargePoint(cpSession);
            // Check if connector id is valid and available
            Integer connectorId;
            try {
                connectorId = Integer.parseInt(msg.get(2).toString());
            } catch (NumberFormatException e) {
                System.out.println("Invalid Connector id: " + msg.get(2).getAsString());
                return;
            }

            if (!cp.getConnectors().containsKey(connectorId) || !cp.getConnectors().get(connectorId).equals("Preparing")) {
                System.out.println("Invalid connector id: " + connectorId);
                System.out.println(cp.getConnectors().get(connectorId));
                return;
            }

            // Send remote start transaction to the charge point
            RemoteStartTransactionReq remoteStartTransactionReq = new RemoteStartTransactionReq(connectorId, Long.toString(userId));
            try {
                System.out.println("Starting remote transaction, user: " + Long.toString(userId) + ", connector: " + connectorId + ", chargepoint: " + chargePointId);
                cpSession.sendMessage(new TextMessage(new Gson().toJson(new Object[] {2, 0, "RemoteStartTransaction", remoteStartTransactionReq })));
            } catch (Exception e) {
                e.printStackTrace();
            }   
        }
        else if(msg.get(0).getAsString().equals("StopTransaction")){
            System.out.println("Stop transaction");
            Long userId;
            System.out.println(session.getUri().toString().split("/")[4]);
            try {
                userId = Long.parseLong(session.getUri().toString().split("/")[4]);
            } catch (Exception e) {
                System.out.println("Invalid user id: " + session.getUri().toString().split("/")[4]);
                return;
            }
            String chargePointId = msg.get(1).toString();

            // Find chargepoint session
            WebSocketSession cpSession = sessionManager.getAllChargePoints().keySet().stream()
                    .filter(s -> sessionManager.getChargePoint(s).getId().equals(chargePointId))
                    .findFirst().orElse(null);

            if (cpSession == null) {
                System.out.println("Charge point not found");
                return;
            }

            ChargePoint cp = sessionManager.getChargePoint(cpSession);
            // Check if connector id is valid and available
            Integer connectorId;
            try {
                connectorId = Integer.parseInt(msg.get(2).toString());
            } catch (NumberFormatException e) {
                System.out.println("Invalid Connector id: " + msg.get(2).getAsString());
                return;
            }

            // Check if connector has transaction
            if (!cp.isCharging(connectorId)) {
                System.out.println("Connector is not charging");
                return;
            }
 
            Long transactionId = cp.getTransactionId(connectorId);
            if (transactionId == null) {
                System.out.println("Transaction not found");
                return;
            }

            // Send remote stop transaction to the charge point
            RemoteStopTransactionReq remoteStopTransactionReq = new RemoteStopTransactionReq(transactionId.intValue());
            try {
                cpSession.sendMessage(new TextMessage(new Gson().toJson(new Object[] {2, DateUtil.getCurrentTime().toString(), "RemoteStopTransaction", remoteStopTransactionReq})));
            } catch (Exception e) {
                e.printStackTrace();
            }   
        }
        

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("User disconnected");
        sessionManager.removeUser(session);
    }
}
