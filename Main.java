package com.homework.blockcopy;

import java.io.File;




public class Main {

	public static void main(String[] args) {
		File file = null;
		File target = null;
		
		BlockCopy copy = new BlockCopy();
		Thread tmp = new Thread();
			file = new File("src/com/homework/blockcopy/Source/");
			target = new File("src/com/homework/blockcopy/new folder/");
			
			if (!target.exists()) target.mkdirs();
				
				
				copy = new BlockCopy(file,target);
				tmp = new Thread(copy);
				
			
			
				
				tmp.start();
			

	}

}
