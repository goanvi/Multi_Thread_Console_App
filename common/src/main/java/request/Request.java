package request;

import dto.DTO;

import java.io.Serializable;

public class Request<T extends DTO> implements Serializable {
    private static final long serialVersionUID = -6743567631108323096L;
    private int ownerId;
    private T groupDto;
    private String name;
    private String argument;

    public Request(T dto, String name, String argument, int ownerId) {
        this.name = name;
        this.argument = argument;
        this.groupDto = dto;
        this.ownerId = ownerId;
    }
    public Request(T dto, String name, String argument) {
        this.name = name;
        this.argument = argument;
        this.groupDto = dto;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
    public T getDto() {
        return groupDto;
    }

    public void setDto(T groupDto) {
        this.groupDto = groupDto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }
}
