package aex.client;

import sun.reflect.generics.tree.Tree;

import java.io.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

public class ConfigHandler {

    public static void writeConfig(TreeSet fondsenSet){
        File availableFunds = new File("./fundsAvailable.cfg");
        if (!availableFunds.isFile()){
            try{
                availableFunds.createNewFile();
            }
            catch (Exception e){

            }
        }
        try{
            PrintWriter pw = new PrintWriter("./fundsAvailable.cfg");
            int i = 0;
            for (Iterator<String> iterator = fondsenSet.iterator(); iterator.hasNext();){
                pw.println("[" + i + "] " + iterator.next());
                i++;
            }
            pw.close();
        }catch (Exception e){

        }

    }

    public static TreeSet<String> readConfig(){
        File cfg = new File("./config.cfg");
        if (cfg.isFile()){
            try{
                TreeSet cfgSet = new TreeSet();
                Scanner scan = new Scanner(cfg);
                while (scan.hasNextLine()){
                    cfgSet.add(scan.nextLine());
                }
                return cfgSet;
            }
            catch (Exception e){
                return null;
            }
        }
        else{
            return null;
        }
    }
}
