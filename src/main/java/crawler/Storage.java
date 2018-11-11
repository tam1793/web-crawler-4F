/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.jsoup.nodes.Document;

/**
 *
 * @author tam
 */
public class Storage {

    public static String FOLDER_NAME = "";

    public static void saveFile(String url, Document content, int depth) throws IOException {
        String fileName = content.title();

        String filePath = "Storage/" + FOLDER_NAME;
        filePath = filePath + "/" + "Depth" + Integer.toString(depth);

        File htmlFile = new File(filePath + "/" + fileName + ".html");
        File textFile = new File(filePath + "/" + fileName + ".txt");

        try {
            FileUtils.writeStringToFile(htmlFile, content.outerHtml(), "UTF-8");
            FileUtils.writeStringToFile(textFile, content.body().text(), "UTF-8");
        } catch (Exception e) {

        }
    }
}
