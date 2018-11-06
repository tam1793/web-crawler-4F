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

        private String url;
        private int deth;

        public UrlCrawle(String url, int deth) {
            this.url = url;
            this.deth = deth;
        }

        public String getUrl() {
            return this.url;
        }

        public int getDepth() {
            return this.deth;
        }

        @Override
        public boolean equals(Object o) {
            UrlCrawle conpare = (UrlCrawle) o;
            return this.url.compareTo(conpare.url) == 0;
        }

        @Override
        public int compareTo(UrlCrawle o) {
            return this.url.compareTo(o.url);
        }
    }
}
