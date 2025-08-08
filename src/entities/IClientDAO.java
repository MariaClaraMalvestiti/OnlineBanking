package entities;

import java.util.List;

import exceptions.NotExistClientException;
import model.Client;

public interface IClientDAO {
	
	public void addClient(Client client);
	public List<Client> getAllClients();
	public Client findByEmail(String email);
	public boolean updateClient(String email, Client updatedData) throws NotExistClientException;
	public boolean deleteClient(String email) throws NotExistClientException;
	public Double deposit(double value, String email) throws NotExistClientException;
	public Double withdraw(double value, String email) throws NotExistClientException;
	
}
