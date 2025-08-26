import manager.TaskManager;
import manager.Task;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager manager = new TaskManager();

        while (true) {
            System.out.println("\n--- TASK MANAGER ---");
            System.out.println("1) Показать все задачи");
            System.out.println("2) Добавить задачу");
            System.out.println("3) Удалить задачу");
            System.out.println("4) Отметить выполненной / невыполненной");
            System.out.println("5) Редактировать задачу");
            System.out.println("6) Поиск по описанию");
            System.out.println("7) Сортировка");
            System.out.println("8) Выйти");
            System.out.print("Выбор: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    List<Task> tasks = manager.getTasks();
                    if (tasks.isEmpty()) {
                        System.out.println("(список пуст)");
                    } else {
                        for (Task t : tasks) {
                            System.out.println(t);
                        }
                    }
                    break;

                case "2":
                    System.out.print("Введите текст задачи: ");
                    String text = scanner.nextLine();
                    manager.addTask(text);
                    System.out.println("Добавлено.");
                    break;

                case "3":
                    System.out.print("ID для удаления: ");
                    int delId = Integer.parseInt(scanner.nextLine());
                    if (manager.deleteTask(delId)) {
                        System.out.println("Удалено.");
                    } else {
                        System.out.println("Не найдено.");
                    }
                    break;

                case "4":
                    System.out.print("ID для переключения статуса: ");
                    int toggleId = Integer.parseInt(scanner.nextLine());
                    if (manager.toggleDone(toggleId)) {
                        System.out.println("Статус изменён.");
                    } else {
                        System.out.println("Не найдено.");
                    }
                    break;

                case "5":
                    System.out.print("ID задачи для редактирования: ");
                    int editId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Новое описание: ");
                    String newDesc = scanner.nextLine();
                    if (manager.editTask(editId, newDesc)) {
                        System.out.println("Изменено.");
                    } else {
                        System.out.println("Не найдено.");
                    }
                    break;

                case "6":
                    System.out.print("Введите ключевое слово: ");
                    String key = scanner.nextLine();
                    List<Task> found = manager.search(key);
                    if (found.isEmpty()) {
                        System.out.println("Ничего не найдено.");
                    } else {
                        for (Task t : found) {
                            System.out.println(t);
                        }
                    }
                    break;

                case "7":
                    System.out.println("1) По ID");
                    System.out.println("2) По имени (A-Z)");
                    System.out.println("3) По выполненности");
                    System.out.print("Выбор сортировки: ");
                    String s = scanner.nextLine();
                    if (s.equals("1")) manager.sortById();
                    else if (s.equals("2")) manager.sortByName();
                    else if (s.equals("3")) manager.sortByDone();
                    System.out.println("Сортировка выполнена.");
                    break;

                case "8":
                    System.out.println("Выход...");
                    return;

                default:
                    System.out.println("Неверный ввод.");
            }
        }
    }
}
