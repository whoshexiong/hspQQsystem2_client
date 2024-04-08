package com.ylstu.qqclient.service;

import java.util.HashMap;

/**
 * @author YI
 * @version 1.0
 */
public class ManageCCSThread {

    private static HashMap<String, ClientConnectServerThread> hm = new HashMap<>();

    public static void addCCST(String userID, ClientConnectServerThread ccst){
        hm.put(userID,ccst);
    }

    public static ClientConnectServerThread getCCST(String userID){
        return hm.get(userID);
    }
}
