package com.example.springboot_demo.controller;

import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaoxu123
 */
@RestController
@RequestMapping("/session")
public class SessionController {

    @PostMapping("/set")
    public void set(HttpSession session,
                    @RequestParam("key") String key,
                    @RequestParam("value") String value) {
        session.setAttribute(key, value);
    }

    @GetMapping("/get")
    public void get(HttpSession session,
                    @RequestParam("key") String key) {
        session.getAttribute(key);
    }

    @PostMapping("/getAll")
    public Map<String,Object> getAll(HttpSession session){
        Map<String, Object> result = new HashMap<>();
        //遍历
        for (Enumeration<String> enumeration =session.getAttributeNames(); enumeration.hasMoreElements();) {
            String key = enumeration.nextElement();
            Object value = session.getAttribute(key);
            result.put(key,value);
        }
        return result;
    }
}
