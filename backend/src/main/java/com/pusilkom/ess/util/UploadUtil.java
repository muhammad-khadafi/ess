package com.pusilkom.ess.util;

import com.google.common.collect.Maps;
import com.google.common.net.MediaType;
import com.pusilkom.ess.service.EmployeeDocumentService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.github.slugify.Slugify;
import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pusilkom on 3/24/2017.
 */
@Component
public class UploadUtil {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${dir.upload.employee}") String DIR_UPLOAD_EMPLOYEE;
    @Autowired
    EmployeeDocumentService employeeDocumentService;

    @Autowired RandomUtil randomUtil;

    @Autowired
    DebugUtil d;

    public String generateNewFileName(String originalFileName)  {
        String fileName = FilenameUtils.removeExtension(originalFileName);
        String extension = FilenameUtils.getExtension(originalFileName);

        log.info("### fileName : {}", fileName);
        log.info("### extension : {}", extension);

        Slugify slugify = new Slugify();
        String newFileName = new DateTime().toString("yyyyMMddHHmmss") + "-" + randomUtil.shortUUID() + "-" + slugify.slugify(fileName) + "." + extension;
        return newFileName;
    }

    public Map<String, String> saveEmployeeDocument(MultipartFile... listMultipartFile) throws IOException {
        Map<String, String> response = new HashMap<String, String>();
        int i = 0;
        for (MultipartFile multipartFile : listMultipartFile) {
            if (multipartFile == null) continue;

            String newFileName = this.generateNewFileName(multipartFile.getOriginalFilename());

            File fileDest = new File(DIR_UPLOAD_EMPLOYEE, newFileName);
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), fileDest);
            response.put(multipartFile.getName()+i, newFileName);
            i++;
        }

        return response;
    }

    private MediaType getMediaType(File checkFile) {
        try {
            Tika tika = new Tika();

            File file = checkFile;

            String mimeType = tika.detect(file);
            MediaType mediaType = MediaType.parse(mimeType);
            return mediaType;
        } catch (IOException e) {
            log.error("validate file type ", e);
            throw new RuntimeException(e);
        }
    }

    public boolean beforeSaveEmployeeDoc(MultipartFile... listMultipartFile) throws IOException {
        boolean temp = true;
        Map<String, String> response = Maps.newHashMap();
        for (MultipartFile multipartFile : listMultipartFile) {
            if (multipartFile == null) continue;
            File fileDest = new File(DIR_UPLOAD_EMPLOYEE, multipartFile.getOriginalFilename());
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), fileDest);
            MediaType mediaType = this.getMediaType(fileDest);
            if (!mediaType.is(MediaType.PDF)) {
                temp = false;
            }
            FileUtils.deleteQuietly(fileDest);
        }
        return temp;
    }

    public void deleteFileEmployeeDocument(String filename) {
        File fileDest = new File(DIR_UPLOAD_EMPLOYEE, filename);
        FileUtils.deleteQuietly(fileDest);
    }

    public Map<String, String> saveFileDoc(String pathFile, MultipartFile... listMultipartFile) throws IOException {
        Map<String, String> response = new HashMap<String, String>();
        int i = 0;
        for (MultipartFile multipartFile : listMultipartFile) {
            if (multipartFile == null) continue;

            String newFileName = this.generateNewFileName(multipartFile.getOriginalFilename());

            File fileDest = new File(pathFile, newFileName);
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), fileDest);
            response.put(multipartFile.getName(), newFileName);
            i++;
        }

        return response;
    }
}
