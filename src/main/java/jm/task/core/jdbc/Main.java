package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static UserService userService = new UserServiceImpl();

    public static void main(String[] args) {

        userService.createUsersTable();
        userService.saveUser("Igor", "Gromov", (byte) 29);
        userService.saveUser("Oleg", "Ivanov", (byte) 45);
        userService.saveUser("Roma", "Durov", (byte) 7);
        userService.saveUser("Dima", "Petrov", (byte) 50);

        userService.getAllUsers().forEach(System.out::println);       // получение всех user и печать
        userService.cleanUsersTable();                                // очистка таблицы
        userService.dropUsersTable();                                 // удаление табоицы
    }
}
