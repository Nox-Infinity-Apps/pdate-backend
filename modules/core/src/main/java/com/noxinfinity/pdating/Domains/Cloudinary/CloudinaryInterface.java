package com.noxinfinity.pdating.Domains.Cloudinary;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface CloudinaryInterface {
    Map<?,?> upload(MultipartFile file, String folderName) throws IOException;
    Map<?,?> download(MultipartFile file, String folderName);}

