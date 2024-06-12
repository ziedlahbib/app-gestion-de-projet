package com.example.appgestiondeprojet.services;

import com.example.appgestiondeprojet.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.example.appgestiondeprojet.entity.FileDB;
import com.example.appgestiondeprojet.repository.FileDBRepository;
import com.example.appgestiondeprojet.repository.UserRepository;

import java.io.IOException;
import java.util.stream.Stream;


@Service
public class FileStorageService {
	Long idf;
  @Autowired
  private FileDBRepository fileDBRepo;
  @Autowired
  UserRepository userRepo;
  public FileDB store(MultipartFile file) throws IOException {
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
    return fileDBRepo.save(FileDB);
  }public Long store1(MultipartFile file) throws IOException {
	    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	    FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
	    fileDBRepo.save(FileDB);
	    return FileDB.getId();
	  }
  public void deletefile(Long idfile) {
	  FileDB f =fileDBRepo.findById(idfile).orElse(null);
	  fileDBRepo.delete(f);
  }
  public FileDB getFile(Long id) {
    return fileDBRepo.findById(id).orElse(null);
  }
  
  
  public Stream<FileDB> getAllFiles() {
    return fileDBRepo.findAll().stream();
  }


}