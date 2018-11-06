/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

/**
 *
 * @author tam
 */
import com.shekhargulati.urlcleaner.UrlCleaner;
import java.io.IOException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

public class Crawler {
    
    public void run() throws IOException {
        while (!Frontier.urlQueue.isEmpty()) {
            String url = Frontier.urlQueue.poll().getUrl();
            // TODO: Get depth from urlQueue
            int depth = 0; // Lay tu Frontier. <url, depth>

            System.out.printf("Crawling %s - remain: %d\n", url, Frontier.urlQueue.size());
            crawl(url, depth);
            
            break;
        }
    }
    
    public void crawl(String url, int depth) throws IOException {
        if (Frontier.shouldVisit(url, depth)) {
            Document pageDocument = request(url);

            if (pageDocument != null) {
                if (depth < Frontier.MAX_DEPTH) {
                    getLink(pageDocument, depth + 1);
                }

                Storage.saveFile(url, pageDocument, depth);
            }
        }
    }
    
    private static void getLink(Document doc, int depth) {
        int count = 0;
        Elements linksOnPage = doc.select("a[href]");
        for (Element page : linksOnPage) {
            count++;
            String urlCleaned = UrlCleaner.normalizeUrl(page.attr("abs:href"));
            System.out.println(urlCleaned.split("\\?")[0]);
            Entity.UrlCrawle el = new Entity.UrlCrawle(urlCleaned, depth);
            
            if (!Frontier.urlQueue.contains(el) && !Frontier.crawledUrl.contains(el.getUrl())) {
                Frontier.urlQueue.add(el);
            }
        }
        System.out.println("Counter Link: " + count);
        System.out.println("--------------------------------------------------------");
        System.out.println("Length Queue: " + Frontier.urlQueue.size());
        while (!Frontier.urlQueue.isEmpty()) {
            Entity.UrlCrawle item = Frontier.urlQueue.poll();
            System.out.println(item.getUrl());
        }        
    }
    
    private Document request(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            return doc;
        } catch (Exception exc) {
            System.err.println("ERR Request(): " + exc.getMessage());
        }
        
        return null;
    }
    
    public static void main(String[] args) throws IOException {
        
    }
}
