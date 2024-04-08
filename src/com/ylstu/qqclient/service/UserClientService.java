package com.ylstu.qqclient.service;

import com.ylstu.qqcommon.Message;
import com.ylstu.qqcommon.MessageType;
import com.ylstu.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author YI
 * @version 1.0
 */
public class UserClientService {

    private static User u;

    private static Socket socket;

    public static Boolean checkUser(String userId,String passWd) throws IOException, ClassNotFoundException {

        Boolean b = false;
        u = new User(userId,passWd);

        //从服务器获取用户信息
        socket = new Socket(InetAddress.getLocalHost(),9999);

        //从本地把用户信息发到服务器
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(u);

        //比较信息后把判断发回来
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

        Message ms = (Message) objectInputStream.readObject();

        if(ms.getMesType().equals(MessageType.Message_Login_Succeed)){

            //创建用户线程
            ClientConnectServerThread clientConnectServerThread = new ClientConnectServerThread(socket);

            clientConnectServerThread.start();

            //将用户线程放入集合中
            ManageCCSThread.addCCST(userId,clientConnectServerThread);

            b = true;
        }else {
            socket.close();
        }
        return b;

    }

    public static void onlineFriendList(){
        Message ms = new Message();
        ms.setMesType(MessageType.Message_Get_Online_Friend);
        ms.setSender(u.getUsername());

        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageCCSThread.getCCST(u.getUsername()).socket.getOutputStream());

            oos.writeObject(ms);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void logOut(){
        Message message = new Message();
        message.setMesType(MessageType.Message_Client_Exit);
        message.setSender(u.getUsername());

        //获得到服务端的输出流
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageCCSThread.getCCST(u.getUsername()).socket.getOutputStream());

            oos.writeObject(message);
            System.out.println("用户："+u.getUsername()+"退出系统");
            System.exit(0);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
