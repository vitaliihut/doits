package ua.insultape.store.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "processed_agent_data")
public class ProcessedAgentDataDb {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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
