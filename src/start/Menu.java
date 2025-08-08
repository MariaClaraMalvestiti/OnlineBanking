package start;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Menu {

    public final static String USER = "ADMINISTRADOR";
    public final static String PASS = "abcD12eF";
    public final static Integer MAX_ATTEMPTS = 3;
    public final static String EXIT_MESSAGE = "Gracias por utilizar el Online Banking.";
    public final static String WELCOME_MESSAGE = "Credenciales correctas, Bienvenido a Online Banking.";
    public final static String TOKEN_MESSAGE = "Clave Token generada Automaticamente:";
    public final static String RETRY_YES = "S";
    public final static String RETRY_NO = "N";

    public static void Home() {
        Set<Integer> tokens = new HashSet<>();
        Scanner log = new Scanner(System.in);
        
        System.out.println("Bienvenido a Online Banking, por favor ingrese las credenciales solicitadas: ");
        
        boolean isValidCredentials = false;
        int attempt = 1;
        
        while (attempt <= MAX_ATTEMPTS && !isValidCredentials) {
            System.out.println("\n--- Intento " + attempt + " de " + MAX_ATTEMPTS + " ---");
            
            // Generar token para este intento
            Integer token = generateToken(tokens);
            System.out.println(TOKEN_MESSAGE + " " + token);
            
            // Intentar login
            isValidCredentials = login(log, token);
            
            if (!isValidCredentials) {
                if (attempt < MAX_ATTEMPTS) {
                    // Preguntar si quiere continuar (excepto en el último intento)
                    if (!askForRetry(log)) {
                        break; // El usuario decidió salir
                    }
                } else {
                    System.out.println("Se han agotado todos los intentos.");
                }
                attempt++;
            }
        }
        
        if (isValidCredentials) {
            start();
        } else {
            System.out.println("Acceso denegado. Demasiados intentos fallidos.");
            end();
        }
        
        log.close();
    }
    
    public static boolean askForRetry(Scanner log) {
        while (true) {
            System.out.print("Credenciales incorrectas. ¿Desea intentarlo nuevamente? (S/N): ");
            String retry = log.nextLine().trim().toUpperCase();
            
            if (RETRY_NO.equals(retry)) {
                return false; // No quiere continuar
            } else if (RETRY_YES.equals(retry)) {
                return true; // Quiere continuar
            } else {
                System.out.println("Error. Debe ingresar S para Sí o N para No.");
            }
        }
    }

    public static void start() {
        System.out.println(WELCOME_MESSAGE);
        System.out.println("Sesión iniciada correctamente.");
        //agregar el menu principal que seguramente este en una clase aparte
        System.out.println(EXIT_MESSAGE);
    }
    
    public static void end() {
        System.out.println(EXIT_MESSAGE);
        System.out.println("Saliendo del Online Banking.");        
    }
    
    public static boolean login(Scanner log, Integer token) { 
        
        System.out.println("Por favor ingrese su nombre de usuario: ");
        String userInput = fillUserData("Usuario: ", log);

        System.out.println("Por favor ingrese su contraseña: ");
        String passInput = fillPasswordData("Contraseña: ", log);
        
        Integer tokenInput = validateInputToken(log);
                
        return validateCredentials(userInput, passInput) && validateToken(token, tokenInput);
    }
    
    public static Integer generateToken(Set<Integer> tokens) {
        Integer token;
        do {
            token = (int)(Math.random() * 900000) + 100000;
        } while(tokens.contains(token) || hasRepetedDigits(token));
        
        tokens.add(token);
        return token;
    }
    
    public static String fillUserData(String message, Scanner log) {
        String userData = "";
        String alphabetic = "[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+";
        boolean valid = false;
        
        while(!valid) {
            System.out.print(message);
            userData = log.nextLine().trim();
            if(userData.matches(alphabetic) && !userData.isEmpty()) {
                valid = true;
            } else {
                System.out.println("Error: Debe ingresar solo letras, no números ni espacios vacíos.");  
            }
        }
        return userData;
    }

    public static String fillPasswordData(String message, Scanner log) {
        String passwordData = "";
        
        while(true) {
            System.out.print(message);
            passwordData = log.nextLine();
            if(isValidPassword(passwordData)) {
                break;
            } else {
                System.out.println("""
                    La contraseña no es segura. Debe contener al menos:
                    - 8 caracteres
                    - Una letra mayúscula
                    - Una letra minúscula
                    - Un número
                    """); 
            }
        }  
        return passwordData;
    }
    
    public static boolean isValidPassword(String password) {
        if (password.length() < 8) return false;

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;  
            if (Character.isLowerCase(c)) hasLower = true; 
            if (Character.isDigit(c)) hasDigit = true;    
        }
        return hasUpper && hasLower && hasDigit;
    }

    public static Integer fillTokenData(String message, Scanner log) {
        Integer integerData = null;
        boolean valid = false;
        
        while(!valid) {
            System.out.print(message);
            try {
                integerData = Integer.parseInt(log.nextLine().trim());
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar solo números.");
            }
        }
        return integerData;
    }
    
    public static Integer validateInputToken(Scanner log) {
        Integer inputToken = null;
        
        do {
            System.out.println("Por favor ingrese su clave token. Debe tener 6 dígitos:");
            inputToken = fillTokenData("Token: ", log);
                    
        } while(!validateSizeToken(inputToken));
        
        return inputToken;
    }
    
    public static boolean validateSizeToken(Integer input) {
        if(input != null) {
            String inputStr = String.valueOf(input);
            return inputStr.matches("\\d{6}");
        }
        return false; 
    }
    
    public static boolean validateCredentials(String userInput, String passInput) {
        boolean isValidUser = USER.equalsIgnoreCase(userInput);
        boolean isValidPass = PASS.equals(passInput);
        return isValidUser && isValidPass;
    }
    
    public static boolean validateToken(Integer token, Integer tokenInput) {
        return token.equals(tokenInput);
    }
    
    public static boolean hasRepetedDigits(Integer token) { 
        String str = String.valueOf(token);
        Set<Character> digits = new HashSet<>();
        
        for(char c : str.toCharArray()) {
            if (!digits.add(c)) { 
                return true; // Hay dígitos repetidos
            }
        }
        return false; // No hay dígitos repetidos
    }
    
    // Método main para probar el programa
    public static void main(String[] args) {
        Home();
    }
}