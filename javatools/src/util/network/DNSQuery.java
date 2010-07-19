package util.network;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import org.xbill.DNS.Address;

public class DNSQuery {

    /**
     * @param args
     * @throws UnknownHostException 
     */
    public static void main(String[] args) throws UnknownHostException {
        // TODO Auto-generated method stub
        String host;
        if (args.length <= 0) {
            //            println("useage: java DNSQuery www.example.com");
            println("address please:");
            host = scan();
        } else {
            host = args[0];
        }

        InetAddress address = Address.getByName(host);
        println("address[Object]:" + address);
        long app = 0;
        for (int i = 0; i < 4; i++) {
            //            println("byte:" + address.getAddress()[i]);
            //            println("ubyte:" + toInt(address.getAddress()[i]));
            //            println("app_1:" + app);
            app += toInt(address.getAddress()[i]) * pow(4 - i);
            //            println("app_2:" + app);
        }

        println(app);

    }

    private static void println(Object o) {
        System.out.println(o);
    }

    private static String scan() {
        Scanner scanner = new Scanner(System.in);
        String buff = scanner.next();
        scanner.close();
        return buff;
    }

    private static int toInt(byte b) {
        if (b < 0) {
            return b + 256;
        } else {
            return b;
        }
    }

    private static long pow(int n) {
        int r = 1;
        for (int i = 0; i < n - 1; i++) {
            r *= 256;
        }
        return r;
    }

}
