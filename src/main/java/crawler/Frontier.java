/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tam
 */
public class Frontier {

    public static PriorityQueue<Entity.UrlCrawle> urlQueue = new PriorityQueue<Entity.UrlCrawle>();
    public static HashSet<String> crawledUrl = new HashSet<String>();
    public static ArrayList<String> fileTypes = new ArrayList<String>();
    public static int MAX_URL_DEPTH = 16;
    public static int MAX_DEPTH = 7;
    public static String SEED;

    public static void initializeFrontier(String seed) {
       
    }

    public static boolean shouldVisit(String url, int depth) {
        // return depth <= MAX_DEPTH && checkCrawledUrl(url) && filterUrl(url) && checkTraps(url);
        return true;
    }

    public static boolean checkCrawledUrl(String url) {
        return crawledUrl.contains(url);
    }

    private static boolean checkTraps(String url) {
        return isURLOverDepth(url);
    }

    public static boolean isURLOverDepth(String url) {
        String[] temp = url.split("/");
        if (temp.length > MAX_URL_DEPTH) {
            return true;
        }
        return false;
    }

    public static void setFileTypes(ArrayList<String> types){
        fileTypes = types;
    }
    
    private static boolean filterUrl(String url) {
        try {
            URI uri = new URI(url);
            String extension = new File(uri.getPath()).getName();
            System.out.println(extension);
            extension = extension.substring(extension.lastIndexOf(".") + 1);
            if(fileTypes.contains(extension))
            {
                return true;
            }
            return false;
        } catch (URISyntaxException ex) { //In case of not a right URL
            Logger.getLogger(Frontier.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
