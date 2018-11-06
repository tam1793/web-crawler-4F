/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.jsoup.nodes.Document;

/**
 *
 * @author tam
 */
public class Storage {

    public static String FOLDER_NAME = "";

    public static void saveFile(String url, Document content, int depth) throws IOException {
        String fileName = "";
        if (url.contains("https://")) {
            fileName = url.replace("https://", "");
        }
        if (url.contains("http://")) {
            fileName = url.replace("http://", "");
        }
        fileName = fileName.replace("/", "");

        String filePath = "Storage/" + FOLDER_NAME;
        filePath = filePath + "/" + "Depth" + Integer.toString(depth);
        File file = new File(filePath + "/" + fileName + ".html");
        FileUtils.writeStringToFile(file, content.outerHtml(), "UTF-8");
    }
}
