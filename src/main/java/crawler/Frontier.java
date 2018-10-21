/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 *
 * @author tam
 */
public class Frontier {

    public static PriorityQueue urlQueue = new PriorityQueue();
    public static HashSet<String> crawledUrl = new HashSet<String>();
    public static ArrayList<String> fileTypes =  new ArrayList<String>();
    public static int MAX_URL_DEPTH = 16;
    public static boolean shouldVisit(String url) {

        if (checkCrawledUrl(url)) {
            checkTraps(url);
            //To do: 
        }
        return false;
    }

    public static boolean checkCrawledUrl(String url) {
        return crawledUrl.contains(url);
    }
    
    private static boolean checkTraps(String url){
        return isURLOverDepth(url);
    }
    
        public static boolean isURLOverDepth(String url) {
        String[] temp = url.split("/");
        if (temp.length > MAX_URL_DEPTH) {
            return true;
        }
        return false;
    }
    
    private static boolean filterUrl( String url){
        return false;
    }
}
