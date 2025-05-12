package com.yanoos.global.util;

import com.yanoos.crawler.util.util.SystemUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {

    public static void saveFile(String path, byte[] fileData) throws IOException {
        Path dir = Path.of(path).getParent();
        if(!Files.exists(dir)){
            Files.createDirectories(dir);
        }

        Files.write(Path.of(path), fileData);
    }
}
