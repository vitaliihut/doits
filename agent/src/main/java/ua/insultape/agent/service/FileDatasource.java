package ua.insultape.agent.service;

import org.springframework.stereotype.Service;
import ua.insultape.agent.dto.Accelerometer;
import ua.insultape.agent.dto.AgentData;
import ua.insultape.agent.dto.GPS;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FileDatasource {
    private static final int DATA_STACK_NUMBER = 5;
    private static final int USER_ID = 67;

    private BufferedReader accelerometerReader;
    private BufferedReader gpsReader;
    private final String accelerometerFilename = "accelerometer.csv";
    private final String gpsFilename = "gps.csv";

    public void openFiles() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream accelerometerStream = classLoader.getResourceAsStream(accelerometerFilename);
        InputStream gpsStream = classLoader.getResourceAsStream(gpsFilename);

        accelerometerReader = new BufferedReader(new InputStreamReader(accelerometerStream));
        gpsReader = new BufferedReader(new InputStreamReader(gpsStream));

        // Skip header lines
        accelerometerReader.readLine();
        gpsReader.readLine();
    }

    public List<AgentData> read() throws IOException {
        List<AgentData> agentDataList = new ArrayList<>();

        while (agentDataList.size() < DATA_STACK_NUMBER) {

            Accelerometer accelerometerData = readAccelerometerData();
            GPS gpsData = readGpsData();

            if (accelerometerData == null || gpsData == null) {
                resetFilePointers();
                continue;
            }

            AgentData agentData = new AgentData();
            agentData.setAccelerometer(accelerometerData);
            agentData.setGps(gpsData);
            agentData.setTimestamp(new Timestamp(System.currentTimeMillis())); // Set current date/time
            agentData.setUserId(USER_ID);
            agentDataList.add(agentData);
        }

        return agentDataList;
    }

    private Accelerometer readAccelerometerData() throws IOException {
        String line = accelerometerReader.readLine();
        if (line == null) {
            return null; // No more data in accelerometer file
        }

        String[] parts = line.split(",");
        Accelerometer accelerometer = new Accelerometer();
        accelerometer.setX(Integer.parseInt(parts[0]));
        accelerometer.setY(Integer.parseInt(parts[1]));
        accelerometer.setZ(Integer.parseInt(parts[2]));
        return accelerometer;
    }

    private GPS readGpsData() throws IOException {
        String line = gpsReader.readLine();
        if (line == null) {
            return null; // No more data in GPS file
        }

        String[] parts = line.split(",");
        // Assuming GPS class has appropriate setters
        GPS gps = new GPS();
        gps.setLatitude(Double.parseDouble(parts[0]));
        gps.setLongitude(Double.parseDouble(parts[1]));
        return gps;
    }

    public void closeFiles() throws IOException {
        if (accelerometerReader != null) {
            accelerometerReader.close();
        }
        if (gpsReader != null) {
            gpsReader.close();
        }
    }

    private void resetFilePointers() throws IOException {
        closeFiles();
        openFiles();
    }
}
