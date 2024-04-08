package com.ylstu.qqclient.service;

import com.ylstu.qqcommon.Message;
import com.ylstu.qqcommon.MessageType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * @author YI
 * @version 1.0
 */
public class MessageClientService {

    public static void sendMessage(String content, String senderID, String getterID) {
        //先创建出message
        Message message = new Message();
        message.setMesType(MessageType.Message_Comm_Mes);
        message.setSender(senderID);
        message.setGetter(getterID);
        message.setSendTime(new Date().toString());
        message.setContent(content);

        //打开输出流，将message传到服务端
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    ManageCCSThread.getCCST(senderID).socket.getOutputStream());

            oos.writeObject(message);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static void sendMessageToAll(String content, String senderID) {
        Message message = new Message();
        message.setContent(content);
        message.setMesType(MessageType.getMessage_Comm_Mes_All);
        message.setSendTime(new Date().toString());
        message.setSender(senderID);
        message.setGetter("所有人");

        //打开输出流，将message传到服务端
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    ManageCCSThread.getCCST(senderID).socket.getOutputStream());

            oos.writeObject(message);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void sendFile(String srcFilePath, String destFilePath, String senderID, String getterID) {
        //创建输入流，从文件将数据读入字节数组bytes
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(srcFilePath);

            byte[] bytes = new byte[(int)new File(srcFilePath).length()];
            fis.read(bytes);

            //将字节数组包入message
            Message ms = new Message();
            ms.setFileContent(bytes);
            ms.setSender(senderID);
            ms.setGetter(getterID);
            ms.setSrcFilePath(srcFilePath);
            ms.setDestFilePath(destFilePath);
            ms.setMesType(MessageType.Message_File_TO_Server);

            //打开到服务器的对象流，将信息传过去
            ObjectOutputStream oos = new ObjectOutputStream(
                    ManageCCSThread.getCCST(senderID).socket.getOutputStream());

            oos.writeObject(ms);
            System.out.println("发送成功.....");

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
