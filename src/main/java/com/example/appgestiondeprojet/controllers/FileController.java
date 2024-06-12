package com.example.appgestiondeprojet.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.example.appgestiondeprojet.entity.FileDB;
import com.example.appgestiondeprojet.message.ResponseFile;
import com.example.appgestiondeprojet.message.ResponseMessage;
import com.example.appgestiondeprojet.services.FileStorageService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200/", exposedHeaders = "Access-Control-Allow-Origin")
@Slf4j
@RestController
@RequestMapping("/File")
public class FileController {

  @Autowired
  private FileStorageService storageService;

  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFile(@RequestPart("file") MultipartFile file) {
    String message = "";
    try {
      storageService.store(file);
      message = "Uploaded the file successfully: " + file.getOriginalFilename();
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }

  @PostMapping("/uploadf")
  public Long uploadFilef(@RequestPart("file") MultipartFile file) throws IOException {
    return storageService.store1(file);
  }

  @DeleteMapping("/delete-file/{id-file}")
  @ResponseBody
  public void deleteFile(@PathVariable("id-file") Long idFile) {
    storageService.deletefile(idFile);
  }

  @GetMapping("/files")
  public ResponseEntity<List<ResponseFile>> getListFiles() {
    List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
      String fileDownloadUri = ServletUriComponentsBuilder
              .fromCurrentContextPath()
              .path("/File/file/")
              .path(dbFile.getId().toString())
              .toUriString();
      return new ResponseFile(
              dbFile.getName(),
              fileDownloadUri,
              dbFile.getType(),
              dbFile.getData().length);
    }).collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(files);
  }

  @GetMapping("/file/{id}")
  public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
    FileDB fileDB = storageService.getFile(id);
    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileDB.getName() + "\"")
            .header(HttpHeaders.CONTENT_TYPE, fileDB.getType())
            .body(fileDB.getData());
  }


  @GetMapping("/filesdetail/{id}")
  public FileDB getFileDetail(@PathVariable Long id) {
    return storageService.getFile(id);
  }
}
