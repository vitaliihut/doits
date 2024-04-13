package ua.insultape.hub.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ProcessedAgentDataResponse {
    private Integer id;
    private String roadState;
    private int userId;
    private double x;
    private double y;
    private double z;
    private double latitude;
    private double longitude;
    private Timestamp timestamp;
}
