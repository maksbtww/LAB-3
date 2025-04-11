public class Main {
    public static void main(String[] args) {
        try {
            dataBaseJDBC.printUsersTable();
        } catch (Exception e) {
            System.err.println("Ошибка при запуске программы:");
            e.printStackTrace();
        }
    }
}