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

    @GetMapping("create")
    public String renderCreateEventCategoryForm(Model model) {

        model.addAttribute("Title", "Create Category");
        model.addAttribute("eventCategory", new EventCategory());

        return "eventCategories/create";
    }

    @PostMapping("create")
    public String processCreateEventCategoryForm(@ModelAttribute EventCategory eventCategory,
                                                 Errors errors,
                                                 Model model) {

        // TODO: 17.4.3.3 This is where I'm stopping. I need to review this more before I continue.

        if (errors.hasErrors()) {
            model.addAttribute("eventCategories", eventCategoryRepository.findAll());
            return "events/create";
        }

        eventCategoryRepository.save(eventCategory);
        return "redirect:";
    }

}
