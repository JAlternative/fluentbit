package fluentbit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FluentBitController {

    @GetMapping("user-audit")
    public String logUserAudit() {
        return "Hello World!";
    }

}
