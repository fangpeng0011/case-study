package com.project.net.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UdpClient {
    public static void main(String[] args) {

        //创建接收端的Socket对象(DatagramSocket)
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket(12345);
            while (true) {
                //创建一个数据包，用于接收数据
                byte[] bys = new byte[1024];
                DatagramPacket dp = new DatagramPacket(bys, bys.length);
                //调用DatagramSocket对象的方法接收数据
                ds.receive(dp);
                //解析数据包，并把数据在控制台显示
                System.out.println("数据是：" + new String(dp.getData(), 0, dp.getLength()));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //关闭接收端
            ds.close();
        }


    }
}
