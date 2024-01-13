package com.project.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressDemo {

    public static void main(String[] args) {


        try {
            InetAddress byAddress = InetAddress.getByName("小白梨太难了");
            System.out.println(byAddress.getAddress());
            System.out.println(byAddress.getHostAddress());
            System.out.println(byAddress.getHostName());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

    }
}
