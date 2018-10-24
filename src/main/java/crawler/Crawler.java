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
import org.jsoup.nodes.Document;


import org.jsoup.Jsoup;

public class Crawler {
    public void run() {
        while (!Frontier.urlQueue.isEmpty()) {
            String url = (String) Frontier.urlQueue.poll();
            int depth = 0; // Lay tu Frontier. <url, depth>

            crawl(url, depth);

            break;
        }
    }

    public void crawl(String url, int depth) {
        if (Frontier.shouldVisit(url)) {
            Document pageDocument = request(url);
            getLink(pageDocument, depth + 1);
            Storage.saveFile(url, pageDocument, depth);
        }
    }

    private void getLink(Document doc, int depth) {
        // To do: get link from Document
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
}
