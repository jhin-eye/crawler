package com.yanoos.global.entity.event;

import jakarta.persistence.*;
import lombok.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "error_log")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "error_log_id")
    private Long id;

    @Column(name = "topic", nullable = false, length = 255)
    private String topic;
    @Column(name = "topic2", nullable = false, length = 255)
    private String topic2;

    @Column(name = "log", columnDefinition = "text")
    private String log;

    @Column(name="checked", nullable = false, columnDefinition = "boolean default false")
    private Boolean checked;

    @Column(name = "occurrence_time")
    private ZonedDateTime occurrenceTime;
}
