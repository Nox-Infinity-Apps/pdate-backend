package com.noxinfinity.pdating.Primary.Sandbox;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin(origins = "*")
public class SandboxController {
    @GetMapping("/graphql-san")
    public String showTemplate() {
        return "sandbox";
    }
}
