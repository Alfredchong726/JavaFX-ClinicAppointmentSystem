package readFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class readFile {

    public ArrayList<String[]> readData(String path) throws IOException {
        File file = new File("/home/cms/java-assignment/java-assignment/src/main/textFiles/" + path);
        ArrayList<String[]> data = new ArrayList<String[]>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] rows = line.split(";");
                data.add(rows);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public Map<String, Map<String, List<String>>> onlyForTimeTableFile() throws IOException {
        File file = new File("/home/cms/java-assignment/java-assignment/src/main/textFiles/timetable.txt");
        Map<String, Map<String, List<String>>> data = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitedString = line.split(";");

                String outerMapKey = splitedString[0] + "," + splitedString[1] + "," + splitedString[2];
                List<String> splitedTime = new ArrayList<String>();
                List<String> splitedDay = new ArrayList<String>();

                Map<String, List<String>> innerMap = new HashMap<>();

                for (int i = 3; i < splitedString.length; i++) {
                    String[] daytime = splitedString[i].split(",");
                    String days = daytime[0];
                    String times = String.join(",", Arrays.copyOfRange(daytime, 1, daytime.length));
                    splitedDay.add(days);
                    splitedTime.add(times);
                }

                for (int i = 0; i < splitedDay.size(); i++) {
                    innerMap.put(splitedDay.get(i), new ArrayList<>(Arrays.asList(splitedTime.get(i).split(", "))));
                }
                data.put(outerMapKey, innerMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void writeData(String filePath, String docId, String content) throws IOException {
        File file = new File("/home/cms/java-assignment/java-assignment/src/main/textFiles/" + filePath);
        ArrayList<String> data = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            if (line instanceof String && line.contains(docId)) {
                data.add(line + ";" + content);
            } else {
                data.add(line);
            }
        }
        System.out.println(data);

        FileWriter writer = new FileWriter("/home/cms/java-assignment/java-assignment/src/main/textFiles/" +
                filePath);
        BufferedWriter bw = new BufferedWriter(writer);

        for (String str : data) {
            bw.write(str);
            bw.newLine();
        }

        br.close();
        bw.close();
        writer.close();
    }

    public void UpdateData(String filePath, String docId, String day, String content) throws IOException {
        File file = new File("/home/cms/java-assignment/java-assignment/src/main/textFiles/" + filePath);
        ArrayList<String> data = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            if (line instanceof String && line.contains(docId)) {
                String[] parts = line.split(";");
                for (String part : parts) {
                    if (part.contains(day)) {
                        parts[Arrays.binarySearch(parts, part)] = content;
                    }
                }
                data.add(String.join(";", parts));
            } else {
                data.add(line);
            }
        }

        FileWriter writer = new FileWriter("/home/cms/java-assignment/java-assignment/src/main/textFiles/" + filePath);
        BufferedWriter bw = new BufferedWriter(writer);

        for (String str : data) {
            bw.write(str);
            bw.newLine();
        }

        br.close();
        bw.close();
        writer.close();
    }

    public void deleteData(String filePath, String docId) throws IOException {
        File file = new File("/home/cms/java-assignment/java-assignment/src/main/textFiles/" + filePath);
        ArrayList<String> data = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            if (line instanceof String && line.contains(docId)) {
                continue;
            } else {
                data.add(line);
            }
        }

        FileWriter writer = new FileWriter("/home/cms/java-assignment/java-assignment/src/main/textFiles/" + filePath);
        BufferedWriter bw = new BufferedWriter(writer);

        for (String str : data) {
            bw.write(str);
            bw.newLine();
        }

        br.close();
        bw.close();
        writer.close();
    }

    public void updateStatusForAppointment(String PatientID, String DoctorName, String status) throws IOException {
        File file = new File("/home/cms/java-assignment/java-assignment/src/main/textFiles/appointment.txt");
        ArrayList<String> data = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.contains(PatientID) && line.contains(DoctorName)) {
                String[] newData = line.split(";");
                newData[6] = status;
                data.add(String.join(";", newData));
            } else {
                data.add(line);
            }
        }

        FileWriter writer = new FileWriter(
                "/home/cms/java-assignment/java-assignment/src/main/textFiles/appointment.txt");
        BufferedWriter bw = new BufferedWriter(writer);

        for (String str : data) {
            bw.write(str);
            bw.newLine();
        }

        br.close();
        bw.close();
        writer.close();

    }

    public Integer countNumberOfData(String fileName) throws IOException {
        File file = new File("/home/cms/java-assignment/java-assignment/src/main/textFiles/" + fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        Integer count = 0;
        while (br.readLine() != null) {
            count += 1;
        }
        br.close();
        return count;
    }

    public double countTotalIncome() throws IOException {
        File file = new File("/home/cms/java-assignment/java-assignment/src/main/textFiles/patientRecords.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        double count = 0;
        String line;
        while ((line = br.readLine()) != null) {
            String income = line.split(";")[11];
            try {
                double incomeDouble = Double.parseDouble(income);
                count += incomeDouble;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        br.close();
        return count;
    }

    public void addAppointment(String DoctorName, String day, String time, String timetableContent,
            String appointmentContent) throws IOException {
        ArrayList<String> data = new ArrayList<String>();
        BufferedReader br = new BufferedReader(
                new FileReader("/home/cms/java-assignment/java-assignment/src/main/textFiles/timetable.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(";");
            if (Arrays.asList(parts).contains(DoctorName)) {
                for (String part : parts) {
                    if (part.contains(day)) {
                        parts[Arrays.binarySearch(parts, part)] = timetableContent;
                    }
                }
                data.add(String.join(";", parts));
            } else {
                data.add(line);
            }
        }
        br.close();
        BufferedWriter bw = new BufferedWriter(
                new FileWriter("/home/cms/java-assignment/java-assignment/src/main/textFiles/appointment.txt", true));
        bw.newLine();
        bw.write(appointmentContent);
        bw.close();
    }

    public void addAccount(String fileName, String content) throws IOException {
        BufferedWriter bw = new BufferedWriter(
                new FileWriter("/home/cms/java-assignment/java-assignment/src/main/textFiles/" + fileName, true));
        bw.newLine();
        bw.write(content);
        bw.close();
    }

    public void deleteStaffAcc(String content) throws IOException {
        ArrayList<String> data = new ArrayList<String>();
        BufferedReader br = new BufferedReader(
                new FileReader("/home/cms/java-assignment/java-assignment/src/main/textFiles/clinicStaff.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.contains(content)) {
                data.add(line);
            }
        }
        br.close();

        BufferedWriter bw = new BufferedWriter(
                new FileWriter("/home/cms/java-assignment/java-assignment/src/main/textFiles/clinicStaff.txt"));

        for (String str : data) {
            bw.write(str);
            bw.newLine();
        }

        bw.close();
    }
}
