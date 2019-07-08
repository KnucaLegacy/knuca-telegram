package com.theopus.telegram.handler;

import java.util.function.BiFunction;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.common.collect.ImmutableSet;
import com.theopus.entity.schedule.Building;
import com.theopus.entity.schedule.Department;
import com.theopus.entity.schedule.Faculty;
import com.theopus.entity.schedule.Group;
import com.theopus.entity.schedule.Room;
import com.theopus.entity.schedule.Teacher;
import com.theopus.entity.schedule.enums.YearOfStudy;
import com.theopus.schedule.backend.repository.DepartmentRepository;
import com.theopus.schedule.backend.repository.GroupRepository;
import com.theopus.schedule.backend.repository.Repository;
import com.theopus.schedule.backend.repository.RoomRepository;
import com.theopus.schedule.backend.repository.TeacherRepository;
import com.theopus.telegram.FormatManager;
import com.theopus.telegram.TelegramSerDe;
import com.theopus.telegram.handler.entity.SearchCommand;
import com.theopus.telegram.handler.entity.Type;

public class SearchCommandConfigurer {

    private final TelegramSerDe serDe;
    private final FormatManager formatManager;
    private final GroupRepository groupRepository;
    private final Repository<Faculty> facultyRepository;
    private final DepartmentRepository departmentRepository;
    private final TeacherRepository teacherRepository;
    private final RoomRepository roomRepository;
    private final Repository<Building> buildingRepository;

    public SearchCommandConfigurer(TelegramSerDe serDe,
                                   FormatManager formatManager,
                                   GroupRepository groupRepository,
                                   Repository<Faculty> facultyRepository,
                                   DepartmentRepository departmentRepository,
                                   TeacherRepository teacherRepository,
                                   RoomRepository roomRepository,
                                   Repository<Building> buildingRepository) {
        this.serDe = serDe;
        this.formatManager = formatManager;
        this.groupRepository = groupRepository;
        this.facultyRepository = facultyRepository;
        this.departmentRepository = departmentRepository;
        this.teacherRepository = teacherRepository;
        this.roomRepository = roomRepository;
        this.buildingRepository = buildingRepository;
    }

    public <T> ImmutableSet<MenuAction<SearchCommand, ?, ?>> searchActions(BiFunction<SearchCommand, Stream<Group>, Stream<ImmutablePair<String, T>>> groupSearchTerminate,
                                                                           BiFunction<SearchCommand, Stream<Teacher>, Stream<ImmutablePair<String, T>>> teacherSearchTerminate,
                                                                           BiFunction<SearchCommand, Stream<Room>, Stream<ImmutablePair<String, T>>> roomSearchTerminate,
                                                                           Class<T> targetType,
                                                                           String header,
                                                                           String targetCommand) {

        String searchFirstHeaderOnly = "" +
                header + "\n" +
                "%s:\n";
        String searchSecond = "" +
                header + "\n" +
                "%s\n" +
                "%s:\n";
        String searchFinal = "" +
                header + "\n" +
                "%s - %s :";

        //main
        MenuAction<SearchCommand, Type, SearchCommand> startAction = MenuAction.ofSame(
                command -> command.getType() == null,
                command -> Stream.of(Type.TEACHER, Type.ROOM, Type.GROUP),
                (command, typeStream) -> typeStream.map(type -> ImmutablePair.of(formatManager.format(type), new SearchCommand(type))),
                command -> String.format(searchFirstHeaderOnly, formatManager.searchStartTitle()),
                command -> null, SearchHandler.COMMAND);

        //group
        MenuAction<SearchCommand, Faculty, SearchCommand> groupAction0 = MenuAction.ofSame(
                command -> command.getType().equals(Type.GROUP) &&
                        command.getFacultyId() == SearchCommand.NOT_INITIALIZED &&
                        command.getStudy() == null,
                command -> facultyRepository.all().stream(),
                (command, facultyStream) -> facultyStream.map(faculty -> {
                    SearchCommand searchCommand = new SearchCommand(command);
                    searchCommand.setFacultyId(faculty.getId());
                    return ImmutablePair.of(faculty.getName(), searchCommand);
                }),
                command -> String.format(searchFirstHeaderOnly, formatManager.selectFaculty()),
                command -> new SearchCommand(),
                command -> true, SearchHandler.COMMAND);

        MenuAction<SearchCommand, YearOfStudy, SearchCommand> groupAction1 = MenuAction.ofSame(
                command -> command.getType().equals(Type.GROUP) &&
                        command.getFacultyId() > SearchCommand.NOT_INITIALIZED &&
                        command.getStudy() == null,
                command -> Stream.of(YearOfStudy.values()),
                (command, studyStream) -> studyStream.map(study -> {
                    SearchCommand searchCommand = new SearchCommand(command);
                    searchCommand.setStudy(study);
                    return ImmutablePair.of(String.valueOf(study.intVal()), searchCommand);
                }),
                command -> String.format(searchSecond, facultyRepository.get((long) command.getFacultyId()).getName(), formatManager.selectYos()),
                command -> new SearchCommand(Type.GROUP),
                command -> true,
                SearchHandler.COMMAND);

        MenuAction<SearchCommand, Group, T> groupAction2 = MenuAction.of(
                command -> command.getType().equals(Type.GROUP) &&
                        command.getFacultyId() > SearchCommand.NOT_INITIALIZED &&
                        command.getStudy() != null,
                command -> {
                    Faculty faculty = new Faculty();
                    faculty.setId(command.getFacultyId());
                    return groupRepository.get(faculty, command.getStudy()).stream();
                },
                groupSearchTerminate,
                command -> String.format(searchFinal,
                        facultyRepository.get((long) command.getFacultyId()).getName(), command.getStudy().intVal() + " курс"),
                command -> new SearchCommand(Type.GROUP).setFacultyId(command.getFacultyId()),
                t -> false, SearchHandler.COMMAND, targetCommand);

        //teacher
        MenuAction<SearchCommand, Faculty, SearchCommand> teacherAction0 = MenuAction.ofSame(
                command -> command.getType().equals(Type.TEACHER) &&
                        command.getFacultyId() == SearchCommand.NOT_INITIALIZED &&
                        command.getDepartmentId() == SearchCommand.NOT_INITIALIZED,
                command -> facultyRepository.all().stream(),
                (command, facultyStream) -> facultyStream.map(faculty -> {
                    SearchCommand searchCommand = new SearchCommand(command);
                    searchCommand.setFacultyId(faculty.getId());
                    return ImmutablePair.of(faculty.getName(), searchCommand);
                }),
                command -> String.format(searchFirstHeaderOnly, formatManager.selectFaculty()),
                command -> new SearchCommand(),
                command -> true, SearchHandler.COMMAND);

        MenuAction<SearchCommand, Department, SearchCommand> teacherAction1 = MenuAction.ofSame(
                command -> command.getType().equals(Type.TEACHER) &&
                        command.getFacultyId() > SearchCommand.NOT_INITIALIZED &&
                        command.getDepartmentId() == SearchCommand.NOT_INITIALIZED,
                command -> {
                    Faculty faculty = new Faculty();
                    faculty.setId(command.getFacultyId());
                    return departmentRepository.get(faculty).stream();
                },
                (command, departmentStream) -> departmentStream.map(dpt -> {
                    SearchCommand searchCommand = new SearchCommand(command);
                    searchCommand.setDepartmentId(dpt.getId());
                    return ImmutablePair.of(String.valueOf(dpt.getName()), searchCommand);
                }),
                command -> String.format(searchSecond, facultyRepository.get((long) command.getFacultyId()).getName(), formatManager.selectDept()),
                command -> new SearchCommand(Type.TEACHER),
                command -> true,
                SearchHandler.COMMAND);

        MenuAction<SearchCommand, Teacher, T> teacherAction2 = MenuAction.of(
                command -> command.getType().equals(Type.TEACHER) &&
                        command.getFacultyId() > SearchCommand.NOT_INITIALIZED &&
                        command.getDepartmentId() > SearchCommand.NOT_INITIALIZED,
                command -> {
                    Department department = new Department();
                    department.setId(command.getDepartmentId());
                    return teacherRepository.get(department).stream();
                },
                teacherSearchTerminate,
                command -> String.format(searchFinal,facultyRepository.get((long) command.getFacultyId()).getName(),
                        "каф. " + departmentRepository.get((long) command.getDepartmentId()).getName()),
                command -> new SearchCommand(Type.TEACHER).setFacultyId(command.getFacultyId()),
                t -> false, SearchHandler.COMMAND, targetCommand);

        //room
        MenuAction<SearchCommand, Building, SearchCommand> roomAction0 = MenuAction.ofSame(
                command -> command.getType().equals(Type.ROOM) &&
                        command.getBuildingId() == SearchCommand.NOT_INITIALIZED,
                command -> buildingRepository.all().stream().filter(building -> !building.getName().isEmpty()),
                (command, buildingStream) -> buildingStream.map(building -> {
                    SearchCommand searchCommand = new SearchCommand(command);
                    searchCommand.setBuildingId(Math.toIntExact(building.getId()));
                    return ImmutablePair.of(building.getName(), searchCommand);
                }),
                command -> String.format(searchFirstHeaderOnly, formatManager.selectBuilding()),
                command -> new SearchCommand(),
                command -> true, SearchHandler.COMMAND);


        MenuAction<SearchCommand, Room, T> roomAction1 = MenuAction.of(
                command -> command.getType().equals(Type.ROOM) &&
                        command.getBuildingId() > SearchCommand.NOT_INITIALIZED,
                command -> {
                    Building building = new Building();
                    building.setId((long) command.getBuildingId());
                    return roomRepository.get(building).stream();
                },
                roomSearchTerminate,
                command -> String.format(searchFirstHeaderOnly, buildingRepository.get((long) command.getBuildingId()).getName()),
                command -> new SearchCommand(Type.ROOM),
                t -> false, SearchHandler.COMMAND, targetCommand);


        return new ImmutableSet.Builder<MenuAction<SearchCommand, ?, ?>>()
                .add(startAction)
                .add(groupAction0)
                .add(groupAction1)
                .add(groupAction2)
                .add(teacherAction0)
                .add(teacherAction1)
                .add(teacherAction2)
                .add(roomAction0)
                .add(roomAction1)
                .build();
    }
}
