package serviceImpl;

import java.io.Serializable;

/*
 * 存有当前工程版本信息
 * 
 */
public	class Version implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
	
		private int newest = 0;

		int getnew() {
			return newest;
		}

		void setnew() {
			newest++;
		}

	}
