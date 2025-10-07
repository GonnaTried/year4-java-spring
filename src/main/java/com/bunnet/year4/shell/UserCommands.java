package com.bunnet.year4.shell;

import com.bunnet.year4.model.User; // Assuming you have a User model
import com.bunnet.year4.service.UserService; // Assuming you have a UserService
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.ArrayTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;

import java.util.List;
import java.util.Optional;

@ShellComponent // 1. Marks this class as a source of shell commands
public class UserCommands {

    private final UserService userService;

    // 2. Inject your existing service just like in a controller
    public UserCommands(UserService userService) {
        this.userService = userService;
    }

    // 3. Define your first command to list all users
    @ShellMethod(key = "user-list", value = "List all registered users in a table.")
    public String listUsers() {
        List<User> users = userService.getAllUsers(); // Assuming this method exists

        if (users.isEmpty()) {
            return "No users found in the database.";
        }

        // Use Spring Shell's TableBuilder for nice formatting
        String[][] data = new String[users.size() + 1][3];
        data[0][0] = "ID";
        data[0][1] = "Username";
        data[0][2] = "Email";

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            data[i + 1][0] = String.valueOf(user.getId()); // Assuming these getters exist
            data[i + 1][1] = user.getUsername();
            data[i + 1][2] = user.getRoles();
        }

        TableModel tableModel = new ArrayTableModel(data);
        TableBuilder tableBuilder = new TableBuilder(tableModel);
        tableBuilder.addFullBorder(BorderStyle.oldschool);

        return tableBuilder.build().render(120); // Render with a max width of 120 chars
    }

    // 4. Define a command to find a single user by their ID
    @ShellMethod(key = "user-find", value = "Find a user by their ID.")
    public String findUserById(
            @ShellOption(help = "The ID of the user to find.") Long id
    ) {
        User user = userService.getUserById(id);

        if (user == null) {
            return "User with ID " + id + " not found.";
        } else {
            StringBuilder userInfo = new StringBuilder();
            userInfo.append("--- User Found ---\n");
            userInfo.append("ID:       ").append(user.getId()).append("\n");
            userInfo.append("Username: ").append(user.getUsername()).append("\n");
            userInfo.append("Email:    ").append(user.getRoles()).append("\n");

            userInfo.append("------------------");

            return userInfo.toString();

        }

    }
}
