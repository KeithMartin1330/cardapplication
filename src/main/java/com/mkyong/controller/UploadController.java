package com.mkyong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UploadController {

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "C://Users//kmartin//Desktop//Cards//spring-boot-file-upload-example//src//main//resources//files//";

    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,@RequestParam("code") String code,
                                   RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            
            String filetype = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            Path path = Paths.get(UPLOADED_FOLDER +code+"."+filetype);
            Files.write(path, bytes);
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded your message");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/uploadStatus";
    }
   
    @RequestMapping(value = "/upload/{cardId}", method = RequestMethod.GET)
    public String view(RedirectAttributes redirectAttributes,@PathVariable(value = "cardId") String cardId) {
    	redirectAttributes.addFlashAttribute("message",
                cardId);
        return "redirect:/messagepage";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }
    @GetMapping("/messagepage")
    public String messagepage() {
        return "messagepage";
    }

}