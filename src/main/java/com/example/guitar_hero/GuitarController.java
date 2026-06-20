package com.example.guitar_hero;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class GuitarController {

    // A simple test endpoint: http://localhost:8080/pluck?key=q
    @GetMapping("/pluck")
    public String pluckString(@RequestParam(value = "key") char key) {
        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        int index = keyboard.indexOf(key);

        if (index != -1) {
            return "Backend received key: " + key + " (String Index: " + index + "). Ready to synthesize!";
        } else {
            return "Key " + key + " is not mapped to any guitar string.";
        }
    }
}
