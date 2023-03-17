package org.launchcode.codingevents.models.dto;

import org.launchcode.codingevents.models.Event;
import org.launchcode.codingevents.models.Tag;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class EventTagDTO {

    @NotNull(message = "event is required")
    private Event event;

    @NotNull(message = "tag is required")
    private Tag tag;

    public EventTagDTO(Event event, Tag tag) {
        this.event = event;
        this.tag = tag;
    }

    public EventTagDTO() {
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
