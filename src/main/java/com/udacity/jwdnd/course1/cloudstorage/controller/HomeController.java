package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.data.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.data.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.data.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.data.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/home")
public class HomeController {
    
    @Autowired
    private final FileService fileService;

    @Autowired
    private final UserService userService;

    @Autowired
    private final NoteService noteService;

    @Autowired
    private final CredentialService credentialService;

    @Autowired
    private final EncryptionService encryptionService;

    public HomeController(
            FileService fileService, UserService userService, NoteService noteService,
            CredentialService credentialService, EncryptionService encryptionService) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String getHomePage(
            Authentication authentication, @ModelAttribute("newFile") FileForm file,
            @ModelAttribute("newNote") NoteForm note, @ModelAttribute("newCredential") CredentialForm credential,
            Model model) {
        Integer userId = retrieveUserId(authentication);
        model.addAttribute("files", this.fileService.getFileListService(userId));
        model.addAttribute("notes", noteService.getNoteListService(userId));
        model.addAttribute("credentials", credentialService.getCredentialListingsService(userId));
        model.addAttribute("encryptionService", encryptionService);

        return "home";
    }

    @GetMapping(value = "/delete-file/{fileName}")
    public String deleteFile(
            Authentication authentication, @PathVariable String fileName, @ModelAttribute("newFile") FileForm file,
            @ModelAttribute("newNote") NoteForm note, @ModelAttribute("newCredential") CredentialForm credential, Model model) {
                fileService.deleteFileService(fileName);
                Integer userId = retrieveUserId(authentication);
                model.addAttribute("files", fileService.getFileListService(userId));
                model.addAttribute("result", "success");

        return "result";
    }
    
    @PostMapping
    public String addNewFile(
            Authentication authentication, @ModelAttribute("newFile") FileForm file,
            @ModelAttribute("newNote") NoteForm note, @ModelAttribute("newCredential") CredentialForm credential, Model model) throws IOException {
                    String userName = authentication.getName();
                    User user = userService.getUserService(userName);
                    Integer userId = user.getUserId();
                    String[] fileListings = fileService.getFileListService(userId);
                    MultipartFile multipartFile = file.getFile();
                    String fileName = multipartFile.getOriginalFilename();
                    boolean fileIsDuplicate = false;
                    for (String fileListing: fileListings) {
                        if (fileListing.equals(fileName)) {
                            fileIsDuplicate = true;
                            break;
                        }
                    }
                    if (!fileIsDuplicate) {
                        fileService.addFileService(userName, multipartFile);
                        model.addAttribute("result", "success");
                    } else {
                        model.addAttribute("result", "error");
                        model.addAttribute("message", "You have tried to add a duplicate file.");
                    }
                    model.addAttribute("files", fileService.getFileListService(userId));
            
                    return "result";
    }

    @GetMapping(
            value = "/get-file/{fileName}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody
    byte[] getFile(@PathVariable String fileName) {
        return fileService.getFileService(fileName).getFileData();
    }

   

    private Integer retrieveUserId(Authentication authentication) {
        String userName = authentication.getName();
        User user = userService.getUserService(userName);
        return user.getUserId();
    }
}
