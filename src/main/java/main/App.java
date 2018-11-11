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

        System.out.println("Running...");

        // Add seed
        Storage.FOLDER_NAME = new Date().toString();

        try {
            // Setup
            ArrayList<String> types = new ArrayList<String>();
            types.add("mp3");
            types.add("shtml");

            Crawler.initializeCrawler(7, 7, types, "https://news.zing.vn/");
            
            // Create threads
            for (int i = 0; i < Crawler.MAX_THREAD; i++) {
                Crawler f4Cralwer = new Crawler();
                f4Cralwer.start();
            }
        } catch (Exception ex) {
            // System.out.println("Error: -> " + ex.getMessage());
        }
    }
}
