package serviceImpl;

import java.io.Serializable;


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
