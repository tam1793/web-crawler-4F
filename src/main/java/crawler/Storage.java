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
        String fileName = "";
        String txtPath = "";
        String htmlPath = "";
        if (url.contains("https://")) {
            fileName = url.replace("https://", "");
        }
        if (url.contains("http://")) {
            fileName = url.replace("http://", "");
        }
        fileName = fileName.replace("/", "");

        String filePath = "Storage/" + FOLDER_NAME + "/";
        txtPath = filePath + "Text/Depth" + Integer.toString(depth);
        htmlPath = filePath + "Html/Depth" + Integer.toString(depth);
        String[] temp = fileName.split("/");
        File htmlFile;
        File txtFile;
        txtFile = new File(txtPath + "/" + fileName + ".txt");
        if (temp[temp.length - 1].contains(".")) {
            htmlFile = new File(htmlPath + "/" + fileName);
        } else {
            htmlFile = new File(htmlPath + "/" + fileName + ">html");
        }

        FileUtils.writeStringToFile(htmlFile, content.outerHtml(), "UTF-8");
        FileUtils.writeStringToFile(txtFile, content.toString());
    }
}
