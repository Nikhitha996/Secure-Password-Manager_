import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Scanner;

public class PasswordManager {

    static final String SECRET_KEY = "1234567890123456"; // 16 characters
    static HashMap<String, String> passwordStore = new HashMap<>();

    // Encryption Method
    public static String encrypt(String data) {
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] encryptedValue = cipher.doFinal(data.getBytes());

            return Base64.getEncoder().encodeToString(encryptedValue);

        } catch (Exception e) {
            return "Error";
        }
    }

    // Decryption Method
    public static String decrypt(String encryptedData) {
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] decodedValue = Base64.getDecoder().decode(encryptedData);

            byte[] decryptedValue = cipher.doFinal(decodedValue);

            return new String(decryptedValue);

        } catch (Exception e) {
            return "Error";
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String masterPassword = "admin123";

        System.out.println("===== SECURE PASSWORD MANAGER =====");
        System.out.print("Enter Master Password: ");
        String inputPassword = sc.nextLine();

        if (!inputPassword.equals(masterPassword)) {
            System.out.println("Access Denied");
            return;
        }

        int choice;

        do {

            System.out.println("\n===== MENU =====");
            System.out.println("1. Add Password");
            System.out.println("2. View Password");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:

                    System.out.print("Enter Website/App Name: ");
                    String site = sc.nextLine();

                    System.out.print("Enter Password: ");
                    String password = sc.nextLine();

                    String encryptedPassword = encrypt(password);

                    passwordStore.put(site, encryptedPassword);

                    System.out.println("Password Saved Successfully");
                    break;

                case 2:

                    System.out.print("Enter Website/App Name: ");
                    String searchSite = sc.nextLine();

                    if (passwordStore.containsKey(searchSite)) {

                        String storedEncryptedPassword =
                                passwordStore.get(searchSite);

                        String decryptedPassword =
                                decrypt(storedEncryptedPassword);

                        System.out.println("Saved Password: "
                                + decryptedPassword);

                    } else {
                        System.out.println("No Password Found");
                    }

                    break;

                case 3:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid Choice");
            }

        } while (choice != 3);

        sc.close();
    }
}