package com.ylstu.qqclient.view;

import com.ylstu.qqclient.service.MessageClientService;
import com.ylstu.qqclient.service.UserClientService;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author YI
 * @version 1.0
 */
public class QQView {
    private Boolean loop = true;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new QQView().mainMenu();
    }

    private void mainMenu() throws IOException, ClassNotFoundException {

        while(loop) {
            System.out.println("-----------即时通信系统----------");
            System.out.println("----------1、登录      ----------");
            System.out.println("----------2、退出      ----------");

            System.out.print("请输入选择：");
            Scanner scanner = new Scanner(System.in);
            String key = scanner.next();

            switch (key){
                case"1":
                    System.out.print("输入用户名：");
                    String userID = scanner.next();
                    System.out.print("输入密码：");
                    String passWd = scanner.next();

                    if(UserClientService.checkUser(userID,passWd)){
                        //登录成功
                        System.out.println("---------登录成功---------");
                        //打印二级菜单
                        while(loop){
                            System.out.println("--------用户（"+userID+"）在线-------");
                            System.out.println("1.显示在线用户列表");
                            System.out.println("2.私发消息");
                            System.out.println("3.群发消息");
                            System.out.println("4.发送文件");
                            System.out.println("9.退出");
                            System.out.print("输入你的选择：");

                            key = scanner.next();

                            switch (key){
                                case"1":
                                    UserClientService.onlineFriendList();
                                    break;
                                case"2":
                                    System.out.println("请输入聊天对象：");
                                    String getterID = scanner.next();
                                    System.out.println("请输入聊天消息：");
                                    String content = scanner.next();
                                    System.out.println("用户"+userID+"正在和"+getterID+"聊天");

                                    MessageClientService.sendMessage(content,userID,getterID);
                                    break;
                                case"3":
                                    System.out.println("群聊中....\n请输入聊天消息：");
                                    String pubContent = scanner.next();

                                    MessageClientService.sendMessageToAll(pubContent,userID);
                                    break;
                                case"4":
                                    System.out.println("正在传输文件......");
                                    System.out.println("请输入发送对象:");
                                    String getter = scanner.next();
                                    System.out.println("请输入文件路径");
                                    String srcFilePath = scanner.next();
                                    System.out.println("请输入文件存储路径：");
                                    String destFilePath = scanner.next();

                                    MessageClientService.sendFile(srcFilePath,destFilePath,userID,getter);

                                    break;
                                case"9":
                                    UserClientService.logOut();
                                    break;
                            }
                        }

                    }else {
                        //登录失败
                        System.out.println("登录失败");
                    }
                    break;
                case "2":
                    loop = false;
                    break;
            }

        }

    }
}

