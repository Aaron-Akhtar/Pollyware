package me.aaronakhtar.pollyware;

import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static Map<String, String> returnProps(String[] properties){
        Map<String, String> propertiesResult = new HashMap<>();
        for (String f : properties){
            propertiesResult.put(f, System.getProperty(f));
        }
        return propertiesResult;
    }

    public static boolean isConn(OutputStream outputStream){
        try{
            outputStream.write(0);
            outputStream.flush();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
