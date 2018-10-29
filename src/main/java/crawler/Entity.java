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
public class Entity {

    public static class UrlCrawle implements Comparable<UrlCrawle> {

        String url;
        int deth;

        public UrlCrawle(String url, int deth) {
            this.url = url;
            this.deth = deth;
        }

        @Override
        public int compareTo(UrlCrawle o) {
            return this.url.compareToIgnoreCase(o.url);
        }

    }
}
