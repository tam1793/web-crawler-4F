/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import crawler.Crawler;
import crawler.Frontier;
import crawler.Entity.UrlCrawle;

/**
 *
 * @author tam
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Running...");

        // Add seed

        UrlCrawle seed = new UrlCrawle("https://www.wikipedia.org/", 0);
        Frontier.urlQueue.add(seed);

        // Start crawling
        Crawler f4Cralwer = new Crawler();
        f4Cralwer.run();

    }
}
