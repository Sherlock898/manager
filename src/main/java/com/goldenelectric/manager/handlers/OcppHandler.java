package com.goldenelectric.manager.handlers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.goldenelectric.manager.models.Transaction;
import com.goldenelectric.manager.models.User;
import com.goldenelectric.manager.ocpp.ChargePoint;
import com.goldenelectric.manager.ocpp.WebSocketSessionManager;
import com.goldenelectric.manager.ocpp.Util.DateUtil;
import com.goldenelectric.manager.ocpp.payloads.BootNotificationConf;
import com.goldenelectric.manager.ocpp.payloads.BootNotificationReq;
import com.goldenelectric.manager.ocpp.payloads.ChangeConfigurationReq;
import com.goldenelectric.manager.ocpp.payloads.HeartbeatConf;
import com.goldenelectric.manager.ocpp.payloads.MeterValueConf;
import com.goldenelectric.manager.ocpp.payloads.MeterValueReq;
import com.goldenelectric.manager.ocpp.payloads.RemoteStartTransactionReq;
import com.goldenelectric.manager.ocpp.payloads.RemoteStopTransactionConf;
import com.goldenelectric.manager.ocpp.payloads.StartTransactionConf;
import com.goldenelectric.manager.ocpp.payloads.StartTransactionReq;
import com.goldenelectric.manager.ocpp.payloads.StatusNotificationConf;
import com.goldenelectric.manager.ocpp.payloads.StatusNotificationReq;
import com.goldenelectric.manager.ocpp.payloads.StopTransactionConf;
import com.goldenelectric.manager.ocpp.payloads.StopTransactionReq;
import com.goldenelectric.manager.services.TransactionService;
import com.goldenelectric.manager.services.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Component
@Qualifier("ocppWsHandler")
public class OcppHandler extends TextWebSocketHandler {
    private final WebSocketSessionManager sessionManager = WebSocketSessionManager.getInstance();

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Connection established from " + session.getId());
        // Check for ocpp protocol
        String protocol = session.getHandshakeHeaders().get("Sec-WebSocket-Protocol").get(0);
        if (!protocol.equals("ocpp1.6")) {
            session.close(CloseStatus.POLICY_VIOLATION);
        }
        // Get charger id from uri path and create a new charging point
        System.out.println(session.getUri().getPath());
        String chargerId = session.getUri().getPath().split("/")[3];
        String chargerUser = "null";
        try {
            chargerUser = session.getUri().getPath().split("/")[2];  
        } catch (Exception e) {
            System.out.println("Error, user not especified.");
        }
        System.out.println("Creating charge point with id: " + chargerId);
        ChargePoint cp = new ChargePoint(chargerId, chargerUser);
        sessionManager.addChargePoint(session, cp);

        // Send change configuration
        // TODO: This is useless, it will be here just for templating purposes, in case we wan't to change config another time
        /*
        ChangeConfigurationReq changeConfigurationReq = new ChangeConfigurationReq("AuthorizeRemoteTxRequests", "true");
        session.sendMessage(new TextMessage(new Gson().toJson(new Object[] { 2, DateUtil.getCurrentTime().toString(), "ChangeConfiguration", changeConfigurationReq})));
        */
    }

    // Handle incoming messages
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // TODO: might use more sophisticated message handling/routing
        JsonArray call = JsonParser.parseString(message.getPayload()).getAsJsonArray();
        Integer messageType = call.get(0).getAsInt();
        if (messageType != 2) {
            // TODO: Process CALLRESULTS and CALLERRORS
            return;
        }
        int messageId = call.get(1).getAsInt();
        String action = "";
        try {
            action = call.get(2).getAsString();
        } catch (Exception e) {
            System.out.println("Couldnt parse to stirng: " + call.get(2));
        }
        
        JsonElement payload = call.get(3);

        // Route payload to appropriate handler
        switch (action) {
            case "BootNotification":
                BootNotificationReq bootNotificationReq = new Gson().fromJson(payload, BootNotificationReq.class);
                handleBootNotification(session, messageId, bootNotificationReq);
                break;

            case "Heartbeat":
                System.out.println("Heartbeat received from " + session.getId());
                handleHeartbeat(session, messageId);
                break;

            case "StatusNotification":
                StatusNotificationReq statusNotificationReq = new Gson().fromJson(payload, StatusNotificationReq.class);
                handleStatusNotification(session, messageId, statusNotificationReq);
                break;

            case "MeterValues":
                MeterValueReq meterValueReq = new Gson().fromJson(payload, MeterValueReq.class);
                System.out.println(meterValueReq.getMeterValues());
                handleMeterValues(session, messageId, meterValueReq);
                break;

            case "StartTransaction":
                StartTransactionReq startTransactionReq = new Gson().fromJson(payload, StartTransactionReq.class);
                
                handleStartTransaction(session, messageId, startTransactionReq);
                break;

            case "StopTransaction":
                StopTransactionReq stopTransactionReq = new Gson().fromJson(payload, StopTransactionReq.class);
                handleStopTransaction(session, messageId, stopTransactionReq);
                break;

            case "RemoteStartTransaction":
                RemoteStartTransactionReq remoteStartTransactionReq = new Gson().fromJson(payload,
                        RemoteStartTransactionReq.class);
                handleRemoteStartTransaction(session, messageId, remoteStartTransactionReq);
                break;

            case "RemoteStopTransaction":
                RemoteStopTransactionConf remoteStopTransactionReq = new Gson().fromJson(payload,
                        RemoteStopTransactionConf.class);
                handleRemoteStopTransaction(session, messageId, remoteStopTransactionReq);
                break;

            default:
                System.out.println("Unknown action");
                session.sendMessage(new TextMessage(new Gson().toJson(new Object[] { 3, messageId, new Object[] {} })));
                break;
        }

        // Update all users with the new charge point information
        sessionManager.broadcastCpToUsers();
    }

    private void handleBootNotification(WebSocketSession session, int messageId,
            BootNotificationReq bootNotificationReq) throws Exception {
        System.out.println("Boot notification received from session: " + session.getId());
        // Save the charging point
        ChargePoint cp = sessionManager.getChargePoint(session);
        cp.bootNotification(bootNotificationReq.getChargePointModel(), bootNotificationReq.getChargePointSerialNumber(),
                bootNotificationReq.getChargePointVendor(), bootNotificationReq.getFirmwareVersion());

        // Send response
        System.out.println("Sending boot notification response");
        BootNotificationConf conf = new BootNotificationConf(DateUtil.getCurrentTime(), 60, "Accepted");
        sendResponse(session, messageId, conf);
    }

    private void handleStatusNotification(WebSocketSession session, int messageId,
            StatusNotificationReq statusNotificationReq) throws Exception {
        System.out.println("Status notification recieved");
        ChargePoint cp = sessionManager.getChargePoint(session);
        cp.updateStatus(statusNotificationReq.getConnectorId(), statusNotificationReq.getStatus());

        // Send response
        StatusNotificationConf conf = new StatusNotificationConf();
        sendResponse(session, messageId, conf);
    }

    private void handleHeartbeat(WebSocketSession session, int messageId) throws Exception {
        System.out.println("Heartbeat Recieved");

        // Send response
        HeartbeatConf conf = new HeartbeatConf(DateUtil.getCurrentTime());
        sendResponse(session, messageId, conf);
    }

    private void handleMeterValues(WebSocketSession session, int messageId, MeterValueReq meterValueReq)
            throws Exception {
        System.out.println("Meter values received");
        ChargePoint cp = sessionManager.getChargePoint(session);
        cp.addMeterValues(meterValueReq.getConnectorId(), meterValueReq.getMeterValues());

        // Send response
        MeterValueConf conf = new MeterValueConf();
        sendResponse(session, messageId, conf);
    }

    private void handleStartTransaction(WebSocketSession session, int messageId, StartTransactionReq startTransactionReq) {
        System.out.println("Start transaction received");
        ChargePoint cp = sessionManager.getChargePoint(session);

        // Add transaction to database, get its id
        User user = null;
        Integer temp_id = 0;
        try{
            user = userService.findById(Long.parseLong(startTransactionReq.getIdTag()));
        }
        catch(Exception e){
            System.out.println("User not found");
            StartTransactionConf conf = new StartTransactionConf("Rejected", 0);
            return;
        }
        // String timestamp to Date
        Date timestamp = DateUtil.parseDate(startTransactionReq.getTimestamp());
        Transaction transaction = new Transaction(timestamp, null, Transaction.Status.CHARGING, Long.valueOf(startTransactionReq.getMeterStart()), null, user);
        transaction = transactionService.save(transaction);
        cp.addTransaction(transaction.getId(), startTransactionReq.getConnectorId(), "Accepted", startTransactionReq.getIdTag(), startTransactionReq.getMeterStart(), startTransactionReq.getTimestamp());
        
        // Send response
        StartTransactionConf conf = new StartTransactionConf("Accepted", transaction.getId().intValue());
        sendResponse(session, messageId, conf);
    }

    private void handleStopTransaction(WebSocketSession session, int messageId, StopTransactionReq stopTransactionReq) {
        System.out.println("Stop transaction received");
        
        Transaction transaction = transactionService.findById(Long.valueOf(stopTransactionReq.getTransactionId()));

        // FIXME: This is insecure but it is here if the app crashes while on a transaction, in this case we don't save the transaction.
        if (transaction == null) {
            System.out.println("Transaction not found: " + stopTransactionReq.getTransactionId());
            StopTransactionConf conf = new StopTransactionConf("Accepted");
            sendResponse(session, messageId, conf);
            return;
        }

        ChargePoint cp = sessionManager.getChargePoint(session);

        // Update transaction
        transaction.setEndMeterValue(Long.valueOf(stopTransactionReq.getMeterStop()));
        transaction.setEndDate(DateUtil.parseDate(stopTransactionReq.getTimestamp()));
        transaction.setStatus(Transaction.Status.COMPLETED);

        transactionService.save(transaction);
        
        // Update charge point
        cp.stopTransaction(transaction.getId());
        //System.out.println("Transaction stopped: " + stopTransactionReq.getMeterValues().get(0));

        // Send response
        StopTransactionConf conf = new StopTransactionConf("Accepted");
        sendResponse(session, messageId, conf);
    }

    // Responses of calls from the charger
    private void handleRemoteStartTransaction(WebSocketSession session, int messageId,
            RemoteStartTransactionReq remoteStartTransactionReq) {
        System.out.println("Starting charging on connector: " + remoteStartTransactionReq.getConnectorId());
    }

    private void handleRemoteStopTransaction(WebSocketSession session, int messageId,
            RemoteStopTransactionConf remoteStopTransactionReq) {
        System.out.println("Stopping charging status: " + remoteStopTransactionReq.getStatus());
    }

    // Send confirmation to charger
    private void sendResponse(WebSocketSession session, int messageId, Object response) {
        try {
            session.sendMessage(new TextMessage(new Gson().toJson(new Object[] { 3, messageId, response })));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("Connection closed");
        sessionManager.removeChargePoint(session);
    }

}
