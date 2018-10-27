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
import org.jsoup.nodes.Element;

import org.jsoup.Jsoup;

public class Crawler {
    public void run() {
        while (!Frontier.urlQueue.isEmpty()) {
            String url = (String) Frontier.urlQueue.poll();
            // TODO: Get depth from urlQueue
            int depth = 0; // Lay tu Frontier. <url, depth>

            System.out.printf("Crawling %s - remain: %d\n", url, Frontier.urlQueue.size());
            crawl(url, depth);

        }
    }

    public void crawl(String url, int depth) {
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

    private void getLink(Document doc, int depth) {
        // To do: get link from Document
        // To do: normalize link
        // To do: store normalized link in queue
        Object links[] = doc.getElementsByAttribute("href").toArray();

        int nLinks = links.length;
        for (int i = 0; i < nLinks; i++) {
            String url = ((Element) links[i]).attr("href");
            if (url.indexOf('/') == 0) {
                url = url.replaceFirst("/", Frontier.SEED);
            }
            Frontier.urlQueue.add(url);
        }

        int x = 2;
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
