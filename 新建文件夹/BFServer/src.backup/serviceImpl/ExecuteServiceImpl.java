//请不要修改本文件�?
package serviceImpl;

import java.rmi.RemoteException;
import service.ExecuteService;

public class ExecuteServiceImpl implements ExecuteService {
	char[] memory = new char[1000];
	int index = 0;
	int startpoint = 0;
	int endpoint = 0;
	int startpoint2 = 0;
	int endpoint2 = 0;
	int startpoint3 = 0;
	int endpoint3 = 0;
	String output = "";


	/**
	 * 编译�?
	 */
	@Override
	public String execute(String code, String param) throws RemoteException {
		param=param.trim()+"\r\n";
		char[] chars = code.toCharArray();
		go(chars, param);
		return output;
	}
	
	private void go(char[] chars,String param) {
		int x = 0;
	
		outer:
		for (int i = 0; i < chars.length; i++) {
			switch (chars[i]) {
			case '+':
				memory[index]++;
				break;
			case '-':
				memory[index]--;
				break;
			case '>':
				index++;
				break;
			case '<':
				index--;
				break;
			case '[':
				if (startpoint == 0) {
					startpoint = i;
				} else if(startpoint2 ==0){
					startpoint2 = i;
				} else {
					startpoint3 = i;
				}
			
				break;
			case ']':
				if (startpoint3 != 0) {
					endpoint3 = i;
				} else if(startpoint2 !=0){
					endpoint2 = i;
				} else {
					endpoint = i;
				}
				if (memory[index] != 0) {
					i =startpoint3 == 0 ? (startpoint2 == 0 ? startpoint : startpoint2):startpoint3;
				}else {
					if (startpoint3!=0) {
						startpoint3 = 0;
						endpoint3 = 0;
					} else if (startpoint2 != 0) {
						startpoint2 = 0;
						endpoint2 = 0;
					} else {
						startpoint = 0;
						endpoint = 0;
					}
				}

				break;
			case ',':
				if (x < param.length()) {
					memory[index] = param.charAt(x);
					x++;
				}	if (x == param.length()) {
					break outer;
				}

				break;
			case '.':
				output +=memory[index];
				break;
			default:
				break;
			}
		}
	}

}
