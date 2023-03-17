package org.launchcode.codingevents.controllers;

import org.launchcode.codingevents.data.EventCategoryRepository;
import org.launchcode.codingevents.data.EventRepository;
import org.launchcode.codingevents.data.TagRepository;
import org.launchcode.codingevents.models.Event;
import org.launchcode.codingevents.models.EventCategory;
import org.launchcode.codingevents.models.Tag;
import org.launchcode.codingevents.models.dto.EventTagDTO;
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

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    @Autowired
    private TagRepository tagRepository;

    // findAll, save, findById

    @GetMapping
    public String displayAllEvents(@RequestParam(required = false) Integer categoryId,
                                   Model model) {

        if (categoryId == null) {
            model.addAttribute("title", "All Events");
            model.addAttribute("events", eventRepository.findAll());
            return "events/index";
        } else {
            Optional<EventCategory> result = eventCategoryRepository.findById(categoryId);

            if (result.isEmpty()) {
                model.addAttribute("title", "Invalid Category ID: " + categoryId);
            } else {
                EventCategory category = result.get();
                model.addAttribute("title", "Events in category: " + category.getName());
                model.addAttribute("events", category.getEvents());
            }

            return "events/index";
        }

    }

    @GetMapping("create")
    public String displayCreateEventForm(Model model) {
        model.addAttribute("field", "Create Event");
        model.addAttribute(new Event());
        model.addAttribute("categories", eventCategoryRepository.findAll());
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
            model.addAttribute("categories", eventCategoryRepository.findAll());
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


            eventToEdit.getEventDetails().setDescription(description);
            eventRepository.save(eventToEdit);
        }
        return "redirect:";
    }

    @GetMapping("details")
    public String displayEventDetails(@RequestParam int eventDetails,
                                      Model model) {

        Optional<Event> result = eventRepository.findById(eventDetails);
        result.ifPresent(event -> model.addAttribute("event", event));

        return "events/details";
    }

    @GetMapping("add-tag")
    public String displayAddTagForm(@RequestParam Integer eventId,
                                    Model model) {

        Optional<Event> result = eventRepository.findById(eventId);
        Event event = result.get();
        model.addAttribute("title", "Add tag to: " + event.getName());
        model.addAttribute("event", event);
        model.addAttribute("tags", tagRepository.findAll());
        EventTagDTO eventTag = new EventTagDTO();
        eventTag.setEvent(event);
        model.addAttribute("eventTag", eventTag);

        return "events/add-tag.html";
    }

    @PostMapping("add-tag")
    public String processAddTagForm(@ModelAttribute @Valid EventTagDTO eventTag,
                                    Model model,
                                    Errors errors) {

        if (!errors.hasErrors()) {
            Event event = eventTag.getEvent();
            Tag tag = eventTag.getTag();
            // The below might not work as the getTags does not have a compare method???
            if (!event.getTags().contains(tag)) {
                event.addTag(tag);
                eventRepository.save(event);
            }
            return "redirect:details?eventDetails=" + event.getId();
        }
        // need to write else

        return "redirect:add-tag";
    }
}
