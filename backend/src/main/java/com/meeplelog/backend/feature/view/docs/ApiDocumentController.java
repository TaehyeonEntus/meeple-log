package com.meeplelog.backend.feature.view.docs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApiDocumentController {
    @GetMapping("/")
    public String apiDocument() {
        return "redirect:/docs/index.html";
    }
}
