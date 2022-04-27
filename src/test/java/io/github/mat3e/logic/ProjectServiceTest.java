//package io.github.mat3e.logic;
//
//import io.github.mat3e.TaskConfigurationProperties;
//import io.github.mat3e.model.*;
//import io.github.mat3e.model.projection.GroupReadModel;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import java.nio.file.NoSuchFileException;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.*;
//import java.util.stream.Collectors;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//class ProjectServiceTest {
//
//    @Test
//    @DisplayName("should throw IllegalStateException when configured to allow just 1 group and the other undone group exist")
//    void createGroup_noMultipleGroupsConfig_And_openGroupsExist_throwsIllegalStateException() {
//        //given
//        var mockGroupRepository = groupRepositoryReturning(true);
//        //and
//        TaskConfigurationProperties mockConfig = configurationReturning(false);
//
//        // system under test
//        var toTest = new ProjectService(null, mockGroupRepository, mockConfig);
//
//        //when
//        var exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(), 0));
//
//        //then
//        assertThat(exception).isInstanceOf(IllegalStateException.class)
//                .hasMessageContaining("Only one undone group from project is allowed");
//
//
//    }
//
//    @Test
//    @DisplayName("should throw IllegalArgumentException when configuration ok and no projects for given id")
//    void createGroup_noMultipleGroupsConfig_And_noUndoneOpenGroupsExist_noProjects_throwsIllegalArgumentException() {
//        //given
//        var mockRepository = mock(ProjectRepository.class);
//        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
//
//        //and
//        TaskConfigurationProperties mockConfig = configurationReturning(true);
//
//        // system under test
//        var toTest = new ProjectService(mockRepository, null, mockConfig);
//
//        //when
//        var exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(), 0));
//
//        //then
//        assertThat(exception).isInstanceOf(IllegalArgumentException.class)
//                .hasMessageContaining("project with given id not found");
//
//
//    }
//
//
//    @Test
//    @DisplayName("should throw IllegalArgumentException when configuration to allow just 1 group and no groups and projects")
//    void createGroup_configurationIsNoOk_And_thereAreProjects_throwsIllegalArgumentException() {
//        //given
//        var mockRepository = mock(ProjectRepository.class);
//        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
//        //and
//
//        TaskGroupRepository taskGroupRepository = groupRepositoryReturning(false);
//        //and
//        TaskConfigurationProperties mockConfig = configurationReturning(true);
//
//        // system under test
//        var toTest = new ProjectService(mockRepository, taskGroupRepository, mockConfig);
//
//        //when
//        var exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(), 0));
//
//        //then
//        assertThat(exception).isInstanceOf(IllegalArgumentException.class)
//                .hasMessageContaining("project with given id not found");
//
//
//    }
//
//    @Test
//    @DisplayName("Should create a new group from project")
//    void createGroup_configurationOk_existingProject_createsAnsSavesGroup() {
//        //given
//        var today = LocalDate.now().atStartOfDay();
//        //and
//        TaskConfigurationProperties mockConfig = configurationReturning(true);
//        //and
//        TaskGroupRepository inMemoryGroupRepo = inMemoryGroupRepository();
//        int countBeforeCall = ((InMemoryGroupRepository) inMemoryGroupRepo).count();
//        //and
//        var mockRepository = mock(ProjectRepository.class);
//        when(mockRepository.findById(anyInt()))
//                .thenReturn(Optional.of(
//                        projectWith("bar", Set.of(-1,-2))));
//
//        //system under test
//        var toTest = new ProjectService(mockRepository, inMemoryGroupRepo, mockConfig);
//
//        //when
//        GroupReadModel result = toTest.createGroup(today, 1);
//
//        //then
//        assertThat(result)
//                .has()
//        assertThat(countBeforeCall-1)
//                .isEqualTo(((InMemoryGroupRepository) inMemoryGroupRepo).count());
//    }
//
//    private Project projectWith(String projectDescription, Set<Integer> daysToDeadline ) {
//        var result = mock(Project.class);
//        when(result.getDescription()).thenReturn(projectDescription);
//        when(result.getProjectSteps()).thenReturn(
//                daysToDeadline.stream()
//                .map(days -> {
//                    var step = mock(ProjectSteps.class);
//                    when(step.getDescription()).thenReturn("foo");
//                    when(step.getDaysToDeadline()).thenReturn(daysToDeadline);
//                    return step;
//                })
//                .collect(Collectors.toSet())
//        );
//          return result;
//    }
//
//    private TaskConfigurationProperties configurationReturning(final boolean result) {
//        var mockTempate = mock(TaskConfigurationProperties.Template.class);
//        when(mockTempate.isAllowMultipleTasks()).thenReturn(result);
//        var mockConfig = mock(TaskConfigurationProperties.class);
//        when(mockConfig.getTemplate()).thenReturn(mockTempate);
//        return mockConfig;
//    }
//
//    private TaskGroupRepository groupRepositoryReturning(final boolean result) {
//        var mockGroupRepository = mock(TaskGroupRepository.class);
//        when(mockGroupRepository.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(result);
//        return mockGroupRepository;
//    }
//
//    private InMemoryGroupRepository inMemoryGroupRepository() {
//        return new InMemoryGroupRepository();
//    }
//
//    private static class InMemoryGroupRepository implements TaskGroupRepository{
//        private int index = 0;
//        private Map<Integer, TaskGroup> map = new HashMap<>();
//
//        public int count() {
//            return map.values().size();
//        };
//
//        @Override
//        public List<TaskGroup> getAll() {
//            return new ArrayList<>(map.values());
//        }
//
//        @Override
//        public Optional<TaskGroup> findById(Integer id) {
//            return Optional.ofNullable(map.get(id));
//        }
//
//        @Override
//        public TaskGroup save(TaskGroup entity) {
//            if (entity.getId() == 0) {
//                try {
//                    TaskGroup.class.getDeclaredField("id").set(entity, ++index);
//                } catch (NoSuchFieldException | IllegalAccessException e) {
//                    throw new RuntimeException(e);
//                }
//            } else {
//                map.put(entity.getId(), entity);
//            }
//            return entity;
//        }
//
//        @Override
//        public boolean existsByDoneIsFalseAndProject_Id(Integer projectId) {
//            return map.values().stream()
//                    .filter(group -> !group.isDone())
//                    .anyMatch(group -> group.getProject() != null && group.getProject().getId() == projectId);
//        }
//    };
//    }
//}
