package start;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

import model.Client;

public class HomeBankingService {
	//se va a enfocar en la recuperacion de credenciales 
	
	//seprar logica de negocio y presentacion (I/O)
	
	// 1. Constantes (en mayusculas y final)
	
	private static final int MAX_ATTEMPTS = 3;
	private static final long TOKEN_DURATION_MS = 30_000;
	// 2. Variables estáticas no finales
	static int attempts; 
	
	private static final List<Client> clients = new ArrayList<>();
	private static final Set<Integer> recoveryTokens = new HashSet<>();
	
	public static void recoveryCredentials(Scanner input) { //1
		
		System.out.println("Ingrese su numero de dni para recuperar su clave. ");
		String dniRecovery = input.nextLine();
				
		boolean validDni = dniExists(dniRecovery);
		
		if(validDni) {
			Integer recoveryToken = generateRecoveryToken(); //hacer algo con este recoveryToken
			sendEmailRecovery(dniRecovery, recoveryToken);
			validRecoveryToken(recoveryToken, input);
			} else {
			System.out.println("El dni ingresado es incorrecto. ");
		}
	}
	
	public static void sendEmailRecovery(String dni, Integer recoveryToken) {	//3
		Optional<String> emailExists = clients.stream()   //optinal puede ser vacio
				.filter(client -> client.getDni().equals(dni))
				.map(Client::getEmail)
				.findFirst();
		
		emailExists.ifPresent(email -> { //Muestra los 3 primeros caracteres del email o en su defecto ******
			String emailPartial = email.length() >= 3 
				? email.substring(0,3) + "***@***"
				: email + "***@***";
			
			System.out.println("Se le ha enviado un token de recuperacion al email. ");
			System.out.println(emailPartial);
			//envia el token al correo y llama al metodo tt
			
		});

	}
	
	public static Integer generateRecoveryToken() { //crea un token de recup y verifica que no tenga digitos repetidos //2
		
		Integer recoveryToken;
		do {
			 recoveryToken = (int)(Math.random() * 900000) + 100000;
				} while(recoveryTokens.contains(recoveryToken) || HomeBanking.hasRepetedDigits(recoveryToken));
		
			recoveryTokens.add(recoveryToken);
			
			return recoveryToken;
	}
	
	
	public static void validRecoveryToken(Integer recoveryToken, Scanner input) { //4 //falta llamar al metodo validRecoveryToken
		long tokenStartTime = System.currentTimeMillis();
		long tokenDuration = 30 * 1000; // 30 segundos en milisegundos
		Integer inputRecoveryToken;
		
		for(attempts = 0; attempts <= MAX_ATTEMPTS; attempts++) {
			
			long currentTime = System.currentTimeMillis();
			
			  // Verificar si el token ya expiró
	        if (currentTime - tokenStartTime > tokenDuration) {
	            System.out.println("El token ha expirado. Solicite uno nuevo.");
	            return;
	        }
			
			System.out.println("Por favor ingrese el token de recuperacion que se le envio al email. Debe tener 6 digitos.");
			System.out.println("Tiene como maximo tres intentos para ingresarlo.");
			
			inputRecoveryToken = HomeBanking.fillTokenData("Token: ", input); //retorna el dato leido por el usuario. 
			
            boolean isValid = HomeBanking.validateSizeToken(inputRecoveryToken) && 
					HomeBanking.validateToken(recoveryToken, inputRecoveryToken);
				
			if(isValid) {
				String updatedPassword = updateCredentials(input);
				System.out.println("Contrasenia actualizada correctamente. ");
				return;
			} else {
				System.out.println("Token incorrecto. Vuelva a intentarlo. ");
			}
		}
			System.out.println("Supero el numero de intentos permitidos. ");		
	}
	
	public static String updateCredentials(Scanner input) { //actualiza la contraseña del usuario 
		
		System.out.println("Por favor ingrese su nueva contrasenia: ");
		String updatedPassword = askForConfirmedPassword("Contrasenia: ", input);
		
		return updatedPassword;
	}
		
	public static boolean dniExists(String dniRecovery) {
		boolean valid = clients.stream()
				.anyMatch(client -> client.getDni().equals(dniRecovery));
		
				return valid;				
	}
	
	public static String askForConfirmedPassword(String message, Scanner input) {
    String password;
    String confirm;

    do {
        password = HomeBanking.fillPasswordData(message, input); 
        System.out.println("Confirme su contraseña:");
        confirm = input.nextLine();

        if (!password.equals(confirm)) {
            System.out.println("Las contraseñas no coinciden. Intente nuevamente.");
        }

    } while (!password.equals(confirm));

    return password;
}
	
//		public static boolean intentarConReintentos(Supplier<Boolean> validacion, int maxIntentos) {
//		    for (int intento = 1; intento <= maxIntentos; intento++) {
//		        if (validacion.get()) {
//		            return true;
//		        } else {
//		            System.out.println("Intento " + intento + " fallido.");
//		        }
//		    }
//		    return false;
//		}
//
//	Thread thread = new Thread(() -> {
//	    try {
//	        while (!Thread.currentThread().isInterrupted()) {
//	            System.out.println("Trabajando...");
//	            Thread.sleep(1000);
//	        }
//	    } catch (InterruptedException e) {
//	        System.out.println("Interrumpido y finalizando.");
//	    }
//	});
//	thread.start();
//
//	Thread.sleep(5000);
//	thread.interrupt(); // Esto "despierta" y finaliza el hilo

}
