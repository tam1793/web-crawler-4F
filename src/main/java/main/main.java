/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import crawler.Crawler;
import crawler.Frontier;
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
        String seed = "https://news.zing.vn/";
        Frontier.initializeFrontier(seed);

        // Start crawling
        Crawler f4Cralwer = new Crawler();
        f4Cralwer.run();
    }
    
}
