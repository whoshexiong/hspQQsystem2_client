package com.ylstu.qqclient.service;

import com.ylstu.qqcommon.Message;
import com.ylstu.qqcommon.MessageType;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @author YI
 * @version 1.0
 */
public class ClientConnectServerThread extends Thread{

    Socket socket;

    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run(){
        super.run();

        while (true){
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                System.out.println("\n等待从服务器接收消息");
                Message ms = (Message) objectInputStream.readObject();

                if(ms.getMesType().equals(MessageType.Message_Ret_Online_Friend)){
                    //返回的是在线用户列表
                    String[] usersOnline = ms.getContent().split(" ");

                    System.out.println("========在线好友=======");
                    for (int i = 0; i < usersOnline.length; i++) {
                        System.out.println("好友：" + usersOnline[i]);
                    }
                }

                //如果返回的是别人发来的消息
                if(ms.getMesType().equals(MessageType.Message_Comm_Mes)
                        || ms.getMesType().equals(MessageType.getMessage_Comm_Mes_All)){
                    System.out.println(ms.getSendTime()+" "+ms.getSender()+"向"+ms.getGetter()+"说"+ms.getContent());
                }

                //如果返回的是别人发来的文件
                if(ms.getMesType().equals(MessageType.Message_File_TO_Server)){

                    System.out.println("用户"+ms.getGetter()+"接受了"+ms.getSender()+"发送的文件");

                    FileOutputStream fos = new FileOutputStream(ms.getDestFilePath());
                    fos.write(ms.getFileContent());

                    fos.close();
                }

                //如果返回的是服务器的公告消息
                if(ms.getMesType().equals(MessageType.Message_To_Client)){
                    System.out.println("服务器公告："+ms.getContent());
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
