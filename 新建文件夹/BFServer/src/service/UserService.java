//需要客户端的Stub
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserService extends Remote{
	public boolean login(String username, String password) throws RemoteException;

	public boolean logout() throws RemoteException;
	
	public boolean Register(String username, String password) throws RemoteException;
}
