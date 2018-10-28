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
    public static void saveFile(String url, Document content, int depth) throws IOException{
        String fileName = url.replaceAll("https://","");
        fileName = fileName.replaceAll("/", "");
        fileName = fileName.replace(".", "-");
        String filePath = "Storage";
        for(int i=0;i<=depth;i++)
        {
            filePath = filePath + "\\" + "Depth"+ Integer.toString(i);
        }
        File file = new File(filePath + "\\" + fileName + ".html");
        FileUtils.writeStringToFile(file, content.outerHtml(), "UTF-8");
    }
}
