package com.bizlogic.tools.functions;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bizlogic.tools.constants.BasicConstants;
import com.bizlogic.tools.model.FileDownload;

import org.apache.commons.codec.digest.DigestUtils;

@SuppressWarnings("all")
public abstract class FilesTools {

  public static String uniqueFileName(String name, Map<String, String> processHeaders) {
    long now = (new Date()).getTime();
    return DigestUtils.sha1Hex(processHeaders.toString() + name + Long.toString(now));
  }

  public static String setWorkPath() {
    String localRoot = Paths.get("").toAbsolutePath().toString() + File.separator + ".files" + File.separator;
    String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    String localPath = localRoot + dateTime + File.separator;
    File root = new File(localRoot);
    if (!root.exists())
      root.mkdir();
    File path = new File(localPath);
    if (!path.exists())
      path.mkdir();
    return localPath;
  }

  public static FileDownload downloadFile(String fileUrl) {
    FileDownload result = new FileDownload();
    try {
      URL fullUrl = new URL(fileUrl);
      String url = fullUrl.getPath();
      String fileName = url.substring(url.lastIndexOf('/') + 1, url.length());
      result.setFileName(fileName);
      String localRootPath = Paths.get("").toAbsolutePath().toString() + File.separator + BasicConstants.WORK_PATH
          + File.separator;
      File root = new File(localRootPath);
      if (!root.exists())
        root.mkdir();
      String dateTime = BasicTools.nowSec("yyyyMMdd");
      String localPath = localRootPath + "\\" + dateTime + "\\";
      File copy = new File(localPath);
      if (!copy.exists())
        copy.mkdir();
      String localFile = localPath + fileName;
      Path target = Paths.get(localFile);
      result.setTarget(target);
      try (InputStream in = fullUrl.openStream()) {
        Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        result.setResult(true);
      } catch (IOException e) {
        result.setMessage(e.getMessage());
        result.setResult(false);
      }
    } catch (Exception e) {
      result.setMessage(e.getMessage());
      result.setResult(false);
    }
    return result;
  }

}
