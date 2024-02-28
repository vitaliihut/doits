package ua.insultape.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "processed_agent_data")
public class ProcessedAgentData {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String roadState;
    private int user_id;
    private double x;
    private double y;
    private double z;
    private double latitude;
    private double longitude;
    private Timestamp timestamp;
}
