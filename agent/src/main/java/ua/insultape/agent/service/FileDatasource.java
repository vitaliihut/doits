package ua.insultape.agent.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import ua.insultape.agent.dto.Accelerometer;
import ua.insultape.agent.dto.AggregatedData;
import ua.insultape.agent.dto.GPS;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FileDatasource {
    private static final int DATA_STACK_NUMBER = 5;

    private BufferedReader accelerometerReader;
    private BufferedReader gpsReader;
    private final String accelerometerFilename = "accelerometer.csv";
    private final String gpsFilename = "gps.csv";

    public void openFiles() throws IOException {
        accelerometerReader = new BufferedReader(new FileReader(ResourceUtils.getFile("classpath:" + accelerometerFilename)));
        gpsReader = new BufferedReader(new FileReader(ResourceUtils.getFile("classpath:" + gpsFilename)));

        // Skip header lines
        accelerometerReader.readLine();
        gpsReader.readLine();
    }

    public List<AggregatedData> read() throws IOException {
        List<AggregatedData> aggregatedDataList = new ArrayList<>();

        while (aggregatedDataList.size() < DATA_STACK_NUMBER) {

            Accelerometer accelerometerData = readAccelerometerData();
            GPS gpsData = readGpsData();

            if (accelerometerData == null || gpsData == null) {
                resetFilePointers();
                continue;
            }

            AggregatedData aggregatedData = new AggregatedData();
            aggregatedData.setAccelerometer(accelerometerData);
            aggregatedData.setGps(gpsData);
            aggregatedData.setDate(new Date()); // Set current date/time

            aggregatedDataList.add(aggregatedData);
        }

        return aggregatedDataList;
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
        gps.setLatitude(Float.parseFloat(parts[0]));
        gps.setLongitude(Float.parseFloat(parts[1]));
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
