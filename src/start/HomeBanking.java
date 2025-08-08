package start;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class HomeBanking {
	
	// 1. Constantes (en mayusculas y final)
	public final static String USER = "ADMINISTRADOR";
	public final static String PASS = "abcD12eF";
	public final static Integer MAX_ATTEMPTS = 3;
	public final static String EXIT_MESSAGE = "Gracias por utilizar el Online Banking.";
	public final static String WELCOME_MESSAGE = "Credenciales correctas, Bienvenido a Online Banking.";
	public final static String TOKEN_MESSAGE = "Clave Token generada Automaticamente:";
	public final static String RETRY_YES = "S";
	public final static String RETRY_NO = "N";

	// 2. Variables estáticas no finales
	private static int attempts;
	private static boolean isValidCredentials = false;
	
	public static void main(String[] args) {
			
		Set<Integer> tokens = new HashSet<>();
		Scanner log = new Scanner(System.in);
	
		System.out.println("Bienvenido a Online Banking, por favor ingrese las credenciales solicitadas: ");
		
		
		for(attempts = 0; attempts < MAX_ATTEMPTS; attempts++) {
			
			Integer token = generateToken(tokens);
			System.out.println("Clave Token generada Automaticamente: " + token);
			
			isValidCredentials = login(log, token);
			
			if(isValidCredentials) {			
					start();	
					break;
			} 	
						
			boolean retryIfIsTrue = retryCredentials(log, token);	
			
			if(!retryIfIsTrue) { 
				break;
			}else {
				continue;
			}
		}
		
		if(attempts == 3) {
			System.out.println("Usted ha agotado la cantidad de intentos disponibles. ");
			System.out.println(EXIT_MESSAGE);
		}
}
	
	public static void start() {
		System.out.println(WELCOME_MESSAGE);
		System.out.println(EXIT_MESSAGE);
	}
	
	public static boolean retryCredentials(Scanner log, Integer token) {
		boolean stop;
		boolean stay;
		boolean out = true;
		
		while(out) {
		System.out.print("Credenciales incorrectas. ¿Desea intentarlo nuevamente? (S/N): ");
		String retry = log.nextLine().trim();
		
		stay = RETRY_YES.equalsIgnoreCase(retry);
		stop = RETRY_NO.equalsIgnoreCase(retry);
		
			if(stop) {
				end();
				out = false;
				return out;
			} else if(stay) {
				out = true;
				return out;
			} else {
				System.out.println("Error. Debe ingresar (S/N): ");
				}	
			}
		return out;
	}
	
	public static void end() {
		System.out.println(EXIT_MESSAGE);
		System.out.println("Saliendo del Online Banking.");		
	}
		
	public static boolean login(Scanner log, Integer token) { 
		
		System.out.println("Por favor ingrese su nombre de usuario. ");
		String userInput = fillUserData("Usuario: ", log);

		System.out.println("Por favor ingrese su contraseña. ");
		String passInput = fillPasswordData("Constrasenia: ", log);
		
		Integer tokenInput = validateInputToken(log);
				
		return validateCredentials(userInput, passInput) && validateToken(token, tokenInput);
	}
	
	public static Integer generateToken(Set<Integer> tokens) { //genera el token que corresponde a cada sesion de usuario
		Integer token;
		do {
			 token = (int)(Math.random() * 900000) + 100000;
				} while(tokens.contains(token) || hasRepetedDigits(token));
		
			tokens.add(token);
			
			return token;
	}
	//crear metodo para password
	public static String fillUserData(String message, Scanner log) {
	    String userData = "";
	    String alphabetic =  "[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+";
	    boolean valid = false;
	    
	    while(!valid) {
	    	System.out.println(message);
			  userData = log.nextLine();
			  if(userData.matches(alphabetic)) {
				  valid = true;
			  } else {
				System.out.println("Error: Debe ingresar solo letras y no numeros.");  
			  }
	    }
		 return userData;
	}

	public static String fillPasswordData(String message, Scanner log) {
	    String passwordData = "";
	    boolean valid = false;
	    
	    while(!valid) {
	    	System.out.println(message);
			  passwordData = log.nextLine();
			  if(isValidPassword(passwordData)) {
				  break;
			  } else {
				  System.out.println("""
			                La contraseña no es segura. Debe contener al menos:
			                - 8 caracteres
			                - Una letra mayuscula
			                - Una letra minuscula
			                - Un numero
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

	    for (char c : password.toCharArray()) { //"abcD1"
	        if (Character.isUpperCase(c)) hasUpper = true;  
	        if (Character.isLowerCase(c)) hasLower = true; 
	        if (Character.isDigit(c)) hasDigit = true;	
	        }
	    return hasUpper && hasLower && hasDigit;
	}

	public static Integer fillTokenData(String message, Scanner log) { //verifica que el token sea solo numerico. 
		Integer integerData = null;
		boolean valid = false;
		
		while(!valid) {
			System.out.println(message);
			 try {
	                integerData = Integer.parseInt(log.nextLine());
	                valid = true;
	            } catch (NumberFormatException e) {
	                System.out.println("Error: Debe ingresar solo numeros y no letras.");
	            }
			}
		return integerData;
	}
	
	public static Integer validateInputToken(Scanner log) { // valida que el token ingresado por el usuario sea un valor numerico
		Integer inputToken = null;
		
		do {
		System.out.println("Por favor ingrese su clave token. Debe tener 6 digitos.");
		inputToken = fillTokenData("Token: ", log);
				
		}while(!validateSizeToken(inputToken));
			return inputToken;
		}
	
	public static boolean validateSizeToken(Integer input) { //valida que el token tenga exctamente 6 digitos
		boolean isValidDigits = false ;
		if(input != null) {
			String inputStr = String.valueOf(input);
			isValidDigits = inputStr.matches("\\d{6}");
			 	return isValidDigits;
		}
			return isValidDigits; 
	}
	
	public static boolean validateCredentials (String userInput, String passInput) { //valida que el usuario y contraseña sean correctos
		
		 boolean isValidUser = USER.equalsIgnoreCase(userInput) ? Boolean.TRUE : Boolean.FALSE;
		 boolean isValidPass = PASS.equals(passInput) ? Boolean.TRUE : Boolean.FALSE;
		 	return isValidUser && isValidPass;
	}
	
	public static boolean validateToken (Integer token, Integer tokenInput) { //valida que el token del usuario y el generado sean iguales
		boolean validToken = token.equals(tokenInput);
			return validToken;
	}
	
	public static boolean hasRepetedDigits(Integer token) { 
		String inputStr = String.valueOf(token);
		Set<Character>seenDigits = new HashSet<>();
		boolean repeatedDigits = false;
		
		for(int i = 0; i < inputStr.length(); i++) {
			char c = inputStr.charAt(i);
			if (seenDigits.contains(c)) { 
				repeatedDigits = true;
				return repeatedDigits;
			}
			 seenDigits.add(c);
		}
		return repeatedDigits;
	}
	
}
