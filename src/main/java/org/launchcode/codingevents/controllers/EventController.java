package org.launchcode.codingevents.controllers;

import org.launchcode.codingevents.data.EventRepository;
import org.launchcode.codingevents.models.Event;
import org.launchcode.codingevents.models.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    // findAll, save, findById

    @GetMapping
    public String displayAllEvents(Model model) {
        model.addAttribute("events", eventRepository.findAll());
        return "events/index";
    }

    @GetMapping("create")
    public String displayCreateEventForm(Model model) {
        model.addAttribute("field", "Create Event");
        model.addAttribute(new Event());
        model.addAttribute("types", EventType.values());
        return "events/create";
    }

    @PostMapping("create")
    public String processCreateEvent(@ModelAttribute @Valid Event newEvent,
                                     Errors errors,
                                     Model model) {
        // Model Binding:
        // with this Model Attribute, spring will look for fields of the Event object
        // and attempt to match those against fields in the form.

        if (errors.hasErrors()) {
            model.addAttribute("events", eventRepository.findAll());
            model.addAttribute("types", EventType.values());
            return "events/create";
        }

        eventRepository.save(newEvent);
        return "redirect:";
    }

    @GetMapping("delete")
    public String displayDeleteEventForm(Model model) {
        model.addAttribute("title", "Delete Events");
        model.addAttribute("events", eventRepository.findAll());

        return "events/delete";
    }

    @PostMapping("delete")
    public String processDeleteEventForm(@RequestParam(required = false) int[] eventIds) {

        if (eventIds != null) {
            for (int id : eventIds) {
                eventRepository.deleteById(id);
            }
        }

        return "redirect:";
        // takes us back to the page that submitted this
        // after the colon would be the reletive path, but we just want the same thing, so it's empty
    }

    @GetMapping("edit/{eventId}")
    public String displayEditForm(Model model, @PathVariable int eventId) {
        Optional<Event> possibleEvent = eventRepository.findById(eventId);

        if (possibleEvent.isPresent()) {
            Event eventToEdit = possibleEvent.get();
            model.addAttribute("event", eventToEdit);

            String title = "Edit " + eventToEdit.getName() + " (id=" + eventToEdit.getId() + ")";
            model.addAttribute("title", title);
        }

        return "events/edit";
    }

    @PostMapping("edit")
    public String processEditForm(int eventId, String name, String description) {
        // TODO: For the moment this method is broken. It needs to have persistent storage implemented.
        //  integrate Optional<T> objects.
        Optional<Event> possibleEvent = eventRepository.findById(eventId);
        if (possibleEvent.isPresent()) {
            Event eventToEdit = possibleEvent.get();
            eventToEdit.setName(name);
            eventToEdit.setDescription(description);
            eventRepository.save(eventToEdit);
        }
        return "redirect:";
    }


}
