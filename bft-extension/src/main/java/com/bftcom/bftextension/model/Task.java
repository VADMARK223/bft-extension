package com.bftcom.bftextension.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Markitanov Vadim
 * @since 21.09.2022
 */
@Entity
@Data
@Table(name = "TASK")
public class Task {
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "SEQ_TASK_GENERATOR_ID")
    @SequenceGenerator(name = "SEQ_TASK_GENERATOR_ID", sequenceName = "SEQ_TASK_ID", allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "URL")
    private String url;

    @Column(name = "PRIORITY")
    private Integer priority;
}
