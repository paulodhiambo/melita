package io.melita.orderservice.models.requests;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class InstallationSchedule {
    private LocalDate date;
    private String timeSlot;
}
