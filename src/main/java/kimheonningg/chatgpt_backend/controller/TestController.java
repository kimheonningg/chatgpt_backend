package kimheonningg.chatgpt_backend.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import kimheonningg.chatgpt_backend.data.Version;

@RestController
@RequestMapping("/test")
@CrossOrigin()
@Validated
public class TestController {
    @GetMapping("/version")
    public Version currentVersion() {
        Version currentVersion = new Version("v1");
        return currentVersion;
    }
    
}
