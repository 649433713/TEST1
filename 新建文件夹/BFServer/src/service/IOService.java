//需要客户端的Stub
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
public interface IOService extends Remote{
	public boolean delete(String userId, String fileName)throws RemoteException;
	
	public boolean fileIsExist(String fileName,String userId)throws RemoteException;
	
	public boolean writeFile(String file, String userId, String fileName)throws RemoteException;
	
	public boolean updateVersion(String file, String userId, String fileName)throws RemoteException;
	
	public String readFile(String userId, String fileName)throws RemoteException;
	
	public String readFileList(String userId)throws RemoteException;
	
	public String readVersion(String userId, String fileName)throws RemoteException;
	
	public String readVersion2(String userId, String fileName,String version)throws RemoteException;
}
