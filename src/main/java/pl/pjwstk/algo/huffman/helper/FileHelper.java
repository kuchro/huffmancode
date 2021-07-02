package pl.pjwstk.algo.huffman.helper;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileHelper {

    public static String textFromFileUnderPath(String filePath) {
        try {
            var file = getLastModified(filePath);
            System.out.printf("Read content from: %s%s\n",filePath,file.getName());
            FileInputStream fis = new FileInputStream(String.format("%s/%s", filePath,file.getName()));
            return IOUtils.toString(fis, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new RuntimeException("Something went wrong.");
        }
    }

    public static void writeToFile(String type,StringBuilder stringBuilder) throws IOException {
        var date = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date());
        var fileInfo = String.format("files/%s/%s-%s.%s",type,
                (type=="output" ? "bits" : "decoded"),date,
                (type=="output" ? "txt.huff" : "txt"));
        System.out.printf("Saved under: %s\n",fileInfo);
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileInfo));
        writer.write(stringBuilder.toString());
        writer.close();
    }


    public static File getLastModified(String directoryFilePath)
    {
        File directory = new File(directoryFilePath);
        File[] files = directory.listFiles(File::isFile);
        long lastModifiedTime = Long.MIN_VALUE;
        File chosenFile = null;

        if (files != null)
        {
            for (File file : files)
            {
                if (file.lastModified() > lastModifiedTime)
                {
                    chosenFile = file;
                    lastModifiedTime = file.lastModified();
                }
            }
        }

        return chosenFile;
    }

}
