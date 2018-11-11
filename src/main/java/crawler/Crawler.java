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

public class Crawler extends Thread {

    @Override
    public void run() {
        while (!Frontier.urlQueue.isEmpty()) {
            Entity.UrlCrawle currentURL = Frontier.urlQueue.poll();
            String url = currentURL.getUrl();
            int depth = currentURL.getDepth(); // Lay tu Frontier. <url, depth>

            // System.out.printf("Crawling %s - remain: %d\n", url,
            // Frontier.urlQueue.size());
            try {
                crawl(url, depth);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    public void crawl(String url, int depth) throws IOException {
        if (Frontier.shouldVisit(url, depth)) {
            Document pageDocument = request(url);
            addUrlIntocrawledUrl(url);

            if (pageDocument != null) {
                if (depth < Frontier.MAX_DEPTH) {
                    getLink(pageDocument, depth + 1, getHostname(url));
                }
                Storage.saveFile(url, pageDocument, depth);
            }
        }
    }

    private static void getLink(Document doc, int depth, String hostname) {
        Elements linksOnPage = doc.select("a[href]");
        for (Element page : linksOnPage) {
            try {
                if (page.attr("abs:href") != "") {
                    String currentURL = page.attr("abs:href");
                    if (currentURL.indexOf('/') == 0) {
                        currentURL = currentURL.replace("/", hostname);
                    }

                    String urlCleaned = UrlCleaner.normalizeUrl(currentURL);
                    // System.out.println(urlCleaned.split("\\?")[0]);
                    Entity.UrlCrawle el = new Entity.UrlCrawle(urlCleaned.split("\\?")[0], depth);

                    if (!Frontier.urlQueue.contains(el) && !Frontier.crawledUrl.contains(el.getUrl())) {
                        addUrlIntoQueue(el);
                    }
                }
            } catch (Exception e) {
                // System.out.println(e);
            }
        }
        // System.out.println("Counter Link: " + count);
        // System.out.println("--------------------------------------------------------");
        // System.out.println("Length Queue: " + Frontier.urlQueue.size());
        // while (!Frontier.urlQueue.isEmpty()) {
        // Entity.UrlCrawle item = Frontier.urlQueue.poll();
        // System.out.println(item.getUrl());
        // }
    }

    private Document request(String url) {
        try {
            System.out.println("url: " + url);
            Document doc = Jsoup.connect(url).ignoreContentType(true).get();
            return doc;
        } catch (Exception exc) {
            System.err.println("ERR Request(): " + exc.getMessage());
        }

        return null;
    }

    private static void addUrlIntoQueue(Entity.UrlCrawle el) {
        Frontier.urlQueue.add(el);
    }

    private static void addUrlIntocrawledUrl(String url) {
        Frontier.crawledUrl.add(url);
    }

    private static String getHostname(String url) {
        String[] prefixes = { "http://", "https://" };

        for (String prefix : prefixes) {
            int start = url.indexOf(prefix);

            if (start != -1) {
                String temp = url.substring(start + prefix.length());
                if (temp.indexOf('/') == -1) {
                    return temp;
                }
                return temp.substring(0, temp.indexOf("/") + 1).replace("/", "");
            }
        }

        return null;
    }
}
