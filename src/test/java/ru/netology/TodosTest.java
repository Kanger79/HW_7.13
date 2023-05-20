package ru.netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class TodosTest {
    Todos todos = new Todos();

    SimpleTask simpleTask = new SimpleTask(5, "Позвонить родителям, купить морковь");

    String[] subtasks = {"Купить Молоко", "Яйца", "Хлеб"};

    Epic epic = new Epic(55, subtasks);

    Meeting meeting = new Meeting(
            555,
            "Выкатка 3й версии приложения",
            "Купить приложение НетоБанка",
            "Во вторник после обеда"
    );

    @BeforeEach
    public void setup() {

        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);
    }

    @Test
    public void shouldShowAllTasks() {  //Тест-1_Находим все задачи

        Task[] expected = {simpleTask, epic, meeting};
        Task[] actual = todos.findAll();

        Assertions.assertArrayEquals(expected, actual);
        System.out.println("Тест-1_Поиск всех типов задач:");
        System.out.print("Epic: id-" + epic.getId() + ", Подзадача - ");
        System.out.println(Arrays.toString(epic.getSubtasks()));
        System.out.print("SimpleTask: id-" + simpleTask.getId() + ", Подзадача - ");
        System.out.println(" '" + simpleTask.getTitle() + "'");
        System.out.print("Meeting: id-" + meeting.getId() + ", Подзадача -");
        System.out.println(" '" + meeting.getTopic() + "', '" + meeting.getProject() + "', '" + meeting.getStart()+ "'");
    }

    @Test
    public void shouldFindQueryInMeeting() { //Тест-2_Находим по запросу в классе Meeting

        String query = "приложение";         //Текст запроса. создан для отображения запроса в логах (System.out.println...)
        Task[] expected = {meeting};
        Task[] actual = todos.search(query);

        Assertions.assertArrayEquals(expected, actual);

        System.out.println("Тест-2. Поиск в классе Meeting: по запросу '" + query + "', найдено '" + meeting.project + "'");
    }

    @Test
    public void shouldFindQueryInSimpleTask() { //Тест-3_Находим по запросу в классе SimpleTask

        String query = "Позвонить";         //Текст запроса
        Task[] expected = {simpleTask};
        Task[] actual = todos.search(query);

        Assertions.assertArrayEquals(expected, actual);

        System.out.println("Тест-3. Поиск в классе SimpleTask: по запросу '" + query + "' найдено '" + simpleTask.getTitle() + "'");
    }

    @Test
    public void shouldFindQueryInEpic() {   //Тест-4_Находим по запросу в классе Epic

        String query = "Хлеб";
        Task[] expected = {epic};
        Task[] actual = todos.search(query);
        Assertions.assertArrayEquals(expected, actual);

        System.out.println("Тест-4. Поиск в классе Epic: по запросу " + query + "' найдено " + Arrays.toString(subtasks) + "'");
    }

    @Test
    public void shouldNotFindNothing() {       //Тест-5_Не находим по запросу ни в одном из классов

        String query = "лук";
        Task[] expected = {};
        Task[] actual = todos.search(query);
        Assertions.assertArrayEquals(expected, actual);

        System.out.println("Тест-5. Поиск по не оответствующему запросу: по запросу '" + query + "' ничего не найдено");
    }

    @Test
    public void shouldFindSeveralTasks() {  //Тест-6_Находим по запросу в нескольких классах

        String query = "упить";
        Task[] expected = {simpleTask, epic, meeting}; // (!) Находит, но если их поменять местами - тест не проходит (!!!) + влияет регистр
        Task[] actual = todos.search(query);
        Assertions.assertArrayEquals(expected, actual);

        System.out.println("Тест-6. Искомый запрос в нескольких классах. По запросу '" + query + "' найдено: '" + simpleTask.getTitle() + "' в классе SimpleTas, " + Arrays.toString(subtasks) + " в классе Epic и '" + meeting.getProject() + "' в классе Meeting");
    }

}