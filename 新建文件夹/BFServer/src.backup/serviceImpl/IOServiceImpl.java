package serviceImpl;

import java.io.*;
import java.rmi.RemoteException;

import service.IOService;

public class IOServiceImpl implements IOService, Serializable {
	public boolean delete(String userId, String fileName) {
		File f = new File(userId + "/" + fileName);
		if (f.exists()) {
			File[] files = f.listFiles();
			for (File file2 : files) {
				file2.delete();
			}
			f.delete();
		}
		return true;
	}

	@Override
	public boolean writeFile(String file, String userId, String fileName) {
		File f = new File(userId + "/" + fileName);
		if (f.exists()) {
			File[] files = f.listFiles();
			for (File file2 : files) {
				file2.delete();
			}
			f.delete();
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO 自动生成�? catch �?
			e.printStackTrace();
		}
		f.mkdir();

		File f2 = new File(f.getAbsolutePath() + "/" + 0 + ".txt");

		try {
			f2.createNewFile();
			FileWriter fileWriter = new FileWriter(f2);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(file);
			bufferedWriter.flush();
			bufferedWriter.close();

		} catch (IOException e1) {
			// TODO 自动生成�? catch �?
			e1.printStackTrace();
			return false;
		}

		File f3 = new File(f.getAbsolutePath() + "/Version.v");
		try {
			f3.createNewFile();
			Version version = new Version();
			FileOutputStream fileWriter = new FileOutputStream(f3);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileWriter);
			objectOutputStream.writeObject(version);
			objectOutputStream.close();
			return true;
		} catch (IOException e) {
			// TODO 自动生成�? catch �?
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String readFile(String userId, String fileName) {
		// TODO Auto-generated method stub
		String code = null;
		File f = new File(userId + "/" + fileName);
		File f3 = new File(f.getAbsolutePath() + "/Version.v");
		int i = 0;
		Version version;
		try {
			FileInputStream filein = new FileInputStream(f3);
			ObjectInputStream objectInputStream = new ObjectInputStream(filein);
			version = (Version) objectInputStream.readObject();
			i = version.getnew();
			objectInputStream.close();
		} catch (IOException | ClassNotFoundException e) {
			// TODO 自动生成�? catch �?
			e.printStackTrace();
		}
		try {
			FileReader reader = new FileReader(new File(f.getAbsolutePath() + "/" + i + ".txt"));
			BufferedReader bufferedReader = new BufferedReader(reader);
			StringBuffer codeBuffer = new StringBuffer();
			int s;
			while ((s = bufferedReader.read()) != -1) {
				codeBuffer.append((char) s);
			}
			bufferedReader.close();
			code = codeBuffer.toString();
		} catch (IOException e) {
			// TODO 自动生成�? catch �?
			e.printStackTrace();
		}

		return code;
	}

	@Override
	public String readFileList(String userId) {
		// TODO Auto-generated method stub
		File file = new File(userId);
		File files[] = file.listFiles();
		StringBuffer fileList = new StringBuffer();
		for (File file2 : files) {
			fileList.append(file2.getName() + "&");
		}

		return fileList.toString();
	}

	@Override
	public boolean fileIsExist(String fileName, String userId) throws RemoteException {
		// TODO 自动生成的方法存�?
		File f = new File(userId + "/" + fileName);
		if (f.exists()) {
			return true;
		} else {
			return false;
		}
	}

	

	@Override

	public boolean updateVersion(String file, String userId, String fileName) throws RemoteException {
		// TODO 自动生成的方法存�?
		File f = new File(userId + "/" + fileName);
		File f3 = new File(f.getAbsolutePath() + "/Version.v");
		int i;
		Version version;
		try {
			FileInputStream filein = new FileInputStream(f3);
			ObjectInputStream objectInputStream = new ObjectInputStream(filein);
			version = (Version) objectInputStream.readObject();
			i = version.getnew();
			objectInputStream.close();
		} catch (IOException | ClassNotFoundException e) {
			// TODO 自动生成�? catch �?
			e.printStackTrace();
			return false;
		}
		try {
			FileReader reader = new FileReader(new File(f.getAbsolutePath() + "/" + i + ".txt"));
			BufferedReader bufferedReader = new BufferedReader(reader);
			StringBuffer codeBuffer = new StringBuffer();
			int s;
			while ((s = bufferedReader.read()) != -1) {
				codeBuffer.append((char) s);
			}
			bufferedReader.close();
			String code = codeBuffer.toString();
			if (code.equals(file)) {
				return false;
			}
		} catch (IOException e) {
			// TODO 自动生成�? catch �?
			e.printStackTrace();
			return false;
		}

		File f2 = new File(f.getAbsolutePath() + "/" + (i + 1) + ".txt");
		try {
			f2.createNewFile();
			FileWriter fileWriter = new FileWriter(f2);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(file);
			bufferedWriter.flush();
			bufferedWriter.close();

		} catch (IOException e1) {
			// TODO 自动生成�? catch �?
			e1.printStackTrace();
			return false;
		}
		try {
			version.setnew();
			FileOutputStream fileWriter = new FileOutputStream(f3);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileWriter);
			objectOutputStream.writeObject(version);
			objectOutputStream.close();
			return true;
		} catch (IOException e) {
			// TODO 自动生成�? catch �?
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String readVersion(String userId, String fileName) throws RemoteException {
		File f = new File(userId + "/" + fileName);
		File files[] = null;
		StringBuffer fileList = new StringBuffer();
		FileFilter filter = new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				String s = pathname.getName().toLowerCase();
				if (s.endsWith(".txt")) {
					return true;
				}
				return false;
			}
		};
		files = f.listFiles(filter);
		for (File file2 : files) {
			fileList.append(file2.getName().charAt(0) + "&");
		}

		return fileList.toString();

	}

	@Override

	public String readVersion2(String userId, String fileName, String version) throws RemoteException {
		String code = null;
		File f = new File(userId + "/" + fileName);
		try {
			FileReader reader = new FileReader(new File(f.getAbsolutePath() + "/" + version + ".txt"));
			BufferedReader bufferedReader = new BufferedReader(reader);
			StringBuffer codeBuffer = new StringBuffer();
			int s;
			while ((s = bufferedReader.read()) != -1) {
				codeBuffer.append((char) s);
			}
			bufferedReader.close();
			code = codeBuffer.toString();
		} catch (IOException e) {
			// TODO 自动生成�? catch �?
			e.printStackTrace();
		}

		return code;
	}
}
