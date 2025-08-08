package entities;

import java.util.ArrayList;
import java.util.List;

import exceptions.NotExistClientException;
import model.Client;


public class ClientDAO implements IClientDAO {

	private List<Client> clients = new ArrayList<>(); 

	@Override
	public void addClient(Client client) { //aniade un cliente a la lista
		clients.add(client);		
	}

	@Override
	public List<Client> getAllClients() { //trae todos los clientes 
		return new ArrayList<>(clients);
	}

	@Override
	public Client findByEmail(String email) { //encuntra un cliente por email
		return clients.stream()
				.filter(c -> c.getEmail().equals(email))
				.findFirst()
				.orElse(null);
	}	

	@Override
	public boolean updateClient(String email, Client updatedData) throws NotExistClientException {
		
		Client client = findByEmail(email);
		
		if (client == null) {
	        throw new NotExistClientException();
	    }
		
		 // Validar que el nuevo email no esté en uso por otro cliente
	    if (updatedData.getEmail() != null && !updatedData.getEmail().equals(email)) {
	        if (findByEmail(updatedData.getEmail()) != null) {
	            throw new IllegalArgumentException("El nuevo email ya está en uso.");
	        }
	        client.setEmail(updatedData.getEmail());
	    }

	    if (updatedData.getName() != null) {
	        client.setName(updatedData.getName());
	    }

	    if (updatedData.getLastname() != null) {
	        client.setLastname(updatedData.getLastname());
	    }

	    if (updatedData.getAccount() != null) {
	        client.setAccount(updatedData.getAccount());
	    }

	    return true;
	}

	@Override
	public boolean deleteClient(String email) throws NotExistClientException {
		
		Client client = findByEmail(email);
		
		if (client == null) {
	        throw new NotExistClientException();
	    }
		
		return clients.remove(client);
	}

	@Override
	public Double deposit(double value, String email) throws NotExistClientException {
		
		if (value <= 0) {
	        throw new IllegalArgumentException("El monto del depósito debe ser positivo.");
	    }

	    Client client = findByEmail(email);

	    if (client == null) {
	        throw new NotExistClientException();
	    }
	    return client.getAccount().deposit(value);
		}
		

	@Override
	public Double withdraw(double value, String email) throws NotExistClientException {
		
		if (value <= 0) {
	        throw new IllegalArgumentException("El monto de la extraccion debe ser positivo.");
	    }

	    Client client = findByEmail(email);
	    
	    if (client == null) {
	        throw new NotExistClientException();
	    }
	    
		return client.getAccount().withdraw(value);
	}

}
