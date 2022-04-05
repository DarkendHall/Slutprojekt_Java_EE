package org.darkend.slutprojekt_java_ee.jms.confiq;

import org.junit.jupiter.api.Test;
import org.springframework.core.task.TaskExecutor;

import static org.assertj.core.api.Assertions.assertThat;

class TaskConfigTest {

    private final TaskConfig taskConfig = new TaskConfig();

    @Test
    void taskConfigConfigurations() {
        var taskExecutor = taskConfig.taskExecutor();

        assertThat(taskExecutor).isInstanceOf(TaskExecutor.class);
    }
}
