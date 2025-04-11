import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class dataBaseJDBC {
    private static final String URL = "jdbc:mysql://localhost:3306/website";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    // Метод для получения подключения к базе данных
    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Метод для проверки существования пользователя
    public static boolean checkUser(String name, String pass) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, pass);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Если есть результат, пользователь существует
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Метод для регистрации нового пользователя
    public static boolean registerUser(String name, String pass) {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, pass);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Если добавлен хотя бы один ряд, регистрация успешна
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Метод для вывода таблицы users в консоль
    public static void printUsersTable() {
        String query = "SELECT * FROM users";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("Содержимое таблицы users:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("username");
                String pass = rs.getString("password");
                System.out.printf("ID: %d, Name: %s, Password: %s%n", id, name, pass);
            }
        } catch (Exception e) {
            System.err.println("Ошибка при выводе таблицы users:");
            e.printStackTrace();
        }
    }
}