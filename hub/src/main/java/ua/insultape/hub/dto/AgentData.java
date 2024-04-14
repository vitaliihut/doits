package ua.insultape.hub.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
public class AgentData {
    private int userId;
    private Accelerometer accelerometer;
    private GPS gps;
    private Timestamp timestamp;
}
