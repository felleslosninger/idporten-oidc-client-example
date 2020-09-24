package no.digdir.oidcclientexample.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SinglePageAppController {

    // forward everything to /, except connection upgrades
    @RequestMapping(value = "/**/{path:[^\\.]+}", headers = "Connection!=Upgrade")
    public String forward() {
        return "forward:/";
    }
}
