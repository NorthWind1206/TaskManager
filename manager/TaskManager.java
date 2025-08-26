package manager;

import java.io.*;
import java.util.*;

public class TaskManager {
    private List<Task> tasks = new ArrayList<>();
    private int nextId = 1;
    private final String FILE_NAME = "tasks.txt";

    public TaskManager() {
        loadFromFile();
    }

    public void addTask(String description) {
        Task task = new Task(nextId++, description, false);
        tasks.add(task);
        saveToFile();
    }

    public boolean deleteTask(int id) {
        Iterator<Task> it = tasks.iterator();
        while (it.hasNext()) {
            Task t = it.next();
            if (t.getId() == id) {
                it.remove();
                saveToFile();
                return true;
            }
        }
        return false;
    }

    public boolean toggleDone(int id) {
        for (Task t : tasks) {
            if (t.getId() == id) {
                t.setDone(!t.isDone());
                saveToFile();
                return true;
            }
        }
        return false;
    }

    public boolean editTask(int id, String newDesc) {
        for (Task t : tasks) {
            if (t.getId() == id) {
                t.setDescription(newDesc);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    public List<Task> search(String keyword) {
        List<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(t);
            }
        }
        return result;
    }

    public void sortById() {
        Collections.sort(tasks, Comparator.comparingInt(Task::getId));
    }

    public void sortByName() {
        Collections.sort(tasks, Comparator.comparing(Task::getDescription));
    }

    public void sortByDone() {
        Collections.sort(tasks, Comparator.comparing(Task::isDone));
    }

    public List<Task> getTasks() {
        return tasks;
    }

    // ---------------- сохранение / загрузка ----------------

    private void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Task t : tasks) {
                pw.println(t.getId() + ";" + t.getDescription() + ";" + t.isDone());
            }
        } catch (IOException e) {
            System.out.println("Ошибка сохранения файла: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";", 3);
                int id = Integer.parseInt(parts[0]);
                String desc = parts[1];
                boolean done = Boolean.parseBoolean(parts[2]);
                tasks.add(new Task(id, desc, done));
                nextId = Math.max(nextId, id + 1);
            }
        } catch (IOException e) {
            System.out.println("Ошибка загрузки файла: " + e.getMessage());
        }
    }
}
