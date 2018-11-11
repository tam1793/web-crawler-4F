/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author tam
 */
import com.shekhargulati.urlcleaner.UrlCleaner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import crawler.Entity.UrlCrawle;

public class Crawler extends Thread {

    public static int NUMBER_OF_THREAD = 0;

    @Override
    public void run() {
        while (true) {
            Entity.UrlCrawle currentURL = getUrlInQueue();
            if (currentURL != null) {
                String url = currentURL.getUrl();
                int depth = currentURL.getDepth(); // Lay tu Frontier. <url, depth>

                try {
                    crawl(url, depth);
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
    }

    private synchronized static Entity.UrlCrawle getUrlInQueue() {
        if (!Frontier.urlQueue.isEmpty()) {
            return Frontier.urlQueue.poll();
        } else {
            return null;
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
                System.out.println(Frontier.crawledUrl.size() + "| " + depth + " " + url);
//                System.out.println(this.thread + " " + url);

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
                    Entity.UrlCrawle el = new Entity.UrlCrawle(urlCleaned.split("\\?")[0], depth);

                    if (!Frontier.urlQueue.contains(el) && !Frontier.crawledUrl.contains(el.getUrl())) {
                        addUrlIntoQueue(el);
                    }
                }
            } catch (Exception e) {
                // System.out.println(e);
            }
        }
    }

    private Document request(String url) {
        try {
            Document doc = Jsoup.connect(url).ignoreContentType(true).get();
            return doc;
        } catch (Exception exc) {
            // System.err.println("ERR Request(): " + exc.getMessage());
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
        String[] prefixes = {"http://", "https://"};

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

    public static void initializeCrawler(int maxThread, int maxDepth, ArrayList<String> filterTypes, String seedURL) {
        Frontier.MAX_DEPTH = maxDepth;
        Crawler.NUMBER_OF_THREAD = maxThread;

        Frontier.setFileTypes(filterTypes);

        UrlCrawle seed = new UrlCrawle(seedURL, 0);
        Frontier.urlQueue.add(seed);
    }
}
