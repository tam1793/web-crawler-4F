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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {

    public void run() throws IOException {
        while (!Frontier.urlQueue.isEmpty()) {
            String url = Frontier.urlQueue.poll().url;
            // TODO: Get depth from urlQueue
            int depth = 0; // Lay tu Frontier. <url, depth>

            crawl(url, depth);

            break;
        }
    }

    public void crawl(String url, int depth) throws IOException {
        if (Frontier.shouldVisit(url, depth)) {
            Document pageDocument = request(url);

            if (depth < Frontier.MAX_DEPTH) {
                getLink(pageDocument, depth + 1);
            }

            Storage.saveFile(url, pageDocument, depth);
        }
    }

    private static void getLink(Document doc, int depth) {
        // To do: get link from Document
        Elements linksOnPage = doc.select("a[href]");
        for (Element page : linksOnPage) {
            String urlCleaned = UrlCleaner.normalizeUrl(page.attr("abs:href"));
//            System.out.println(urlCleaned);
            Entity.UrlCrawle el = new Entity.UrlCrawle(urlCleaned, depth);
            if (!Frontier.urlQueue.contains(el)) {
                Frontier.urlQueue.add(el);
            }
        }
        while (!Frontier.urlQueue.isEmpty()) {
            Entity.UrlCrawle item = Frontier.urlQueue.poll();
            System.out.println(item.url);
        }
        // To do: normalize link
        // To do: store normalized link in queue
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
        Document document = Jsoup.connect("https://www.wikipedia.org").get();
        getLink(document, 0);
    }
}
