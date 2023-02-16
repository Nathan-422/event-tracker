package org.launchcode.codingevents.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("events")
public class EventController {

    @GetMapping
    public String displayAllEvents(Model model) {

//        TODO: code this

        List<String> events = new ArrayList<>();



        return "events/index";
    }

    @GetMapping("create")
    public String renderCreateEventForm() {
        return "events/create";
    }

    @PostMapping
    public String addToEventList() {

    }

}
