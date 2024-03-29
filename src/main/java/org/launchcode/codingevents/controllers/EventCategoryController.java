package org.launchcode.codingevents.controllers;

import org.launchcode.codingevents.data.EventCategoryRepository;
import org.launchcode.codingevents.models.EventCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("eventCategories")
public class EventCategoryController {

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    @GetMapping
    public String displayAllEventCatagories(Model model) {

        model.addAttribute("title", "All Categories");
        model.addAttribute("eventCategories", eventCategoryRepository.findAll());

        return "eventCategories/index";
    }

    @GetMapping("create")
    public String renderCreateEventCategoryForm(@ModelAttribute EventCategory eventCategory,
                                                Model model) {

        model.addAttribute("title", "Create Category");
        return "eventCategories/create";
    }



    @PostMapping("create")
    public String processCreateEventCategoryForm(@ModelAttribute EventCategory eventCategory,
                                                 Errors errors,
                                                 Model model) {

        model.addAttribute("title", "Create Category");
        if (errors.hasErrors()) {
            return "eventCategories/create";
        }

        eventCategoryRepository.save(eventCategory);
        return "redirect:";
    }

}
