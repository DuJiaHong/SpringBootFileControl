package com.du.FileSystem.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FileControl {

    @GetMapping("/")
    public String fileControl(){
        return "index";
    }
}
