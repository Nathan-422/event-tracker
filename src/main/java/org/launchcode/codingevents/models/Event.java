package org.launchcode.codingevents.models;

import org.springframework.cglib.core.Local;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

public class Event {


    private int id;
    private static int nextId = 1;

    @NotBlank(message = "Name is required")
    @Size(min = 1, max=80, message = "Name must be between 1 and 80 characters")
    private String name;

    @Size(max = 500, message = "Description too long")
    private String description;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email. Please try again.")
    private String contactEmail;

    @NotBlank(message = "Location is required")
    @NotNull(message = "Location is required")
    private String location;

    @NotNull(message = "Please include whether or not registration is required for this event")
    private boolean registrationRequired;

    @Min(1)
    private int maxAttendance;

    @Future(message = "Date cannot be in the past")
//    @NotNull(message = "A date is required")  // this does not work with LocalDate objects?
    private LocalDate date;

    public Event(String name,
                 String description,
                 String contactEmail,
                 String location,
                 Boolean registrationRequired,
                 int maxAttendance,
                 LocalDate date
                ) {
        this.name = name;
        this.description = description;
        this.contactEmail = contactEmail;
        this.location = location;
        this.registrationRequired = registrationRequired;
        this.maxAttendance = maxAttendance;
        this.date = date;

        // create unique IDs
        this.id = nextId;
        nextId++;
    }

    public Event() {}

    public LocalDate getDate() {
        return date;
    }

    public void setDate(String date) {
        LocalDate actualDate = LocalDate.parse(date);
        this.date = actualDate;
    }

    public int getMaxAttendance() {
        return maxAttendance;
    }

    public void setMaxAttendance(int maxAttendance) {
        this.maxAttendance = maxAttendance;
    }

    public boolean isRegistrationRequired() {
        return registrationRequired;
    }

    public void setRegistrationRequired(boolean registrationRequired) {
        this.registrationRequired = registrationRequired;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
