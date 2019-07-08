package com.theopus.telegram.handler.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.theopus.entity.schedule.enums.YearOfStudy;

public class SearchCommand {

    public static final int NOT_INITIALIZED = -1;

    @JsonProperty("t")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Type type;
    @JsonProperty("f")
    private int facultyId = NOT_INITIALIZED;
    @JsonProperty("y")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private YearOfStudy study;
    @JsonProperty("d")
    private int departmentId = NOT_INITIALIZED;
    @JsonProperty("b")
    private int buildingId = NOT_INITIALIZED;

    public SearchCommand() {
    }

    public SearchCommand(Type type) {
        this.type = type;
    }

    public SearchCommand(SearchCommand command) {
        this.type = command.type;
        this.facultyId = command.facultyId;
        this.study = command.study;
        this.departmentId = command.departmentId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public SearchCommand setFacultyId(int facultyId) {
        this.facultyId = facultyId;
        return this;
    }

    public YearOfStudy getStudy() {
        return study;
    }

    public void setStudy(YearOfStudy study) {
        this.study = study;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "SearchCommand{" +
                "type=" + type +
                ", facultyId=" + facultyId +
                ", study=" + study +
                ", departmentId=" + departmentId +
                '}';
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }
}
