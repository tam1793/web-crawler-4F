/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import crawler.Crawler;
import crawler.Frontier;
import crawler.Entity.UrlCrawle;
import crawler.Storage;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author tam
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        System.out.println("Running...");
//        // Add seed
//        if (args[2] != null) {
//            Storage.FOLDER_NAME = args[2];
//        } else {
//            Storage.FOLDER_NAME = new Date().toString();
//        }
//        try {
//            Frontier.MAX_DEPTH = Integer.parseInt(args[1]);
//            UrlCrawle seed = new UrlCrawle(args[0], 0);
//            Frontier.urlQueue.add(seed);
//        } catch (Exception ex) {
//            System.out.println("Error: -> " + ex.getMessage());
//        }
//
//        // Start crawling
//        Crawler f4Cralwer = new Crawler();
//        f4Cralwer.run();

        System.out.println("Running...");
        // Add seed
        Storage.FOLDER_NAME = new Date().toString();

        try {
            Frontier.MAX_DEPTH = 7;
            ArrayList<String> types = new ArrayList<String>();
            types.add("mp3");
            types.add("shtml");
            Frontier.setFileTypes(types);
            UrlCrawle seed = new UrlCrawle("https://news.zing.vn/", 0);
            Frontier.urlQueue.add(seed);
            for (int i = 0; i < 4; i++) {
                Crawler f4Cralwer = new Crawler();
                f4Cralwer.start();
            }
        } catch (Exception ex) {
            System.out.println("Error: -> " + ex.getMessage());
        }

        // Start crawling
        for (int i = 0; i < 4; i++) {
            Crawler f4Cralwer = new Crawler();
            f4Cralwer.start();
        }
//        Crawler f4Cralwer = new Crawler();
//        f4Cralwer.run();

    }
}
