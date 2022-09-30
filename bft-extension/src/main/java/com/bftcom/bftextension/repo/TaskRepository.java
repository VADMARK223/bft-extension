package com.bftcom.bftextension.repo;

import com.bftcom.bftextension.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Markitanov Vadim
 * @since 21.09.2022
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
}
