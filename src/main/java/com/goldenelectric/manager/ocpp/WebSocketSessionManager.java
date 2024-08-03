package com.goldenelectric.manager.ocpp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

// This class is in charge of communication between the WebSocket sessions and the ChargePoint objects.
public class WebSocketSessionManager {
    private static final WebSocketSessionManager instance = new WebSocketSessionManager();

    private final ConcurrentMap<WebSocketSession, ChargePoint> cpSessions = new ConcurrentHashMap<>();
    private final List<WebSocketSession> users = new ArrayList<>();

    private WebSocketSessionManager() {
    }

    public static WebSocketSessionManager getInstance() {
        return instance;
    }

    public void addChargePoint(WebSocketSession session, ChargePoint cp) {
        cpSessions.put(session, cp);
    }

    public void removeChargePoint(WebSocketSession session) {
        cpSessions.remove(session);
    }

    public ChargePoint getChargePoint(WebSocketSession session) {
        return cpSessions.get(session);
    }

    public ConcurrentMap<WebSocketSession, ChargePoint> getAllChargePoints() {
        return cpSessions;
    }

    public void addUser(WebSocketSession session) {
        users.add(session);
    }

    public void removeUser(WebSocketSession session) {
        users.remove(session);
    }

    public List<WebSocketSession> getAllUsers() {
        return users;
    }

    // Send all charge points information to all users
    public void broadcastCpToUsers() {
        for (WebSocketSession user : users) {
            try {
                user.sendMessage(new TextMessage(new Gson().toJson(this.cpSessions.values())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcastToUser(WebSocketSession user) {
        try {
            user.sendMessage(new TextMessage(new Gson().toJson(this.cpSessions.values())));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
