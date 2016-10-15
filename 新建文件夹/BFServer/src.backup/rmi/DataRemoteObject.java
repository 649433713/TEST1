package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import service.ExecuteService;
import service.IOService;
import service.UserService;
import serviceImpl.ExecuteServiceImpl;
import serviceImpl.IOServiceImpl;
import serviceImpl.UserServiceImpl;

public class DataRemoteObject extends UnicastRemoteObject implements IOService, UserService,ExecuteService{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4029039744279087114L;
	private IOService iOService;
	private UserService userService;
	private ExecuteService executeService;
	protected DataRemoteObject() throws RemoteException {
		iOService = new IOServiceImpl();
		userService = new UserServiceImpl();
		executeService = new ExecuteServiceImpl();
	}

	@Override
	public boolean writeFile(String file, String userId, String fileName) throws RemoteException{
		// TODO Auto-generated method stub
		return iOService.writeFile(file, userId, fileName);
	}

	@Override
	public String readFile(String userId, String fileName) throws RemoteException{
		// TODO Auto-generated method stub
		return iOService.readFile(userId, fileName);
	}

	@Override
	public String readFileList(String userId) throws RemoteException{
		// TODO Auto-generated method stub
		return iOService.readFileList(userId);
	}

	@Override
	public boolean login(String username, String password) throws RemoteException {
		// TODO Auto-generated method stub
		return userService.login(username, password);
	}

	@Override
	public boolean logout() throws RemoteException {
		// TODO Auto-generated method stub
		return userService.logout();
	}

	@Override
	public String execute(String code, String param) throws RemoteException {
		// TODO 自动生成的方法存�?
		return executeService.execute(code, param);
	}

	@Override
	public boolean Register(String username, String password) throws RemoteException {
		// TODO 自动生成的方法存�?
		return userService.Register(username, password);
	}

	@Override
	public boolean fileIsExist(String file, String userId) throws RemoteException {
		// TODO 自动生成的方法存�?
		return iOService.fileIsExist(file, userId);
	}

	@Override
	public boolean updateVersion(String file, String userId, String fileName) throws RemoteException {
		// TODO 自动生成的方法存�?
		return iOService.updateVersion(file, userId, fileName);
	}

	@Override
	public boolean delete(String userId, String fileName) throws RemoteException {
		// TODO 自动生成的方法存�?
		return iOService.delete(userId, fileName);
	}

	@Override
	public String readVersion(String userId, String fileName) throws RemoteException {
		// TODO 自动生成的方法存�?
		return iOService.readVersion(userId, fileName);
	}

	@Override
	public String readVersion2(String userId, String fileName, String version) throws RemoteException {
		// TODO 自动生成的方法存�?
		return iOService.readVersion2(userId, fileName, version);
	}

}
