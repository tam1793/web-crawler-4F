/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import org.jsoup.nodes.Document;

/**
 *
 * @author tam
 */
public class Storage {
    public static void saveFile(String url, Document content, int depth) {
        System.out.printf("%s - %d\n%s\n", url, depth, content.title());
    }
}
