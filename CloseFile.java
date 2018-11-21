package com.homework.blockcopy;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CloseFile implements Runnable {
	private FileInputStream tmp;
	private FileOutputStream finish;
	private BlockCopy copy;
	public CloseFile(FileInputStream tmp, FileOutputStream finish, BlockCopy copy) {
		super();
		this.tmp = tmp;
		this.finish = finish;
		this.copy = copy;
	}
	public CloseFile() {
		super();
	}

	private synchronized void closeFile (FileInputStream tmp, FileOutputStream finish) throws IOException {
	while (!copy.isDone()) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			
		}
	}		
		tmp.close();
		finish.flush();
		finish.close();
		copy.setStatus(0);
		copy.setReadbyte(false);
		System.out.println("FINISH");
		copy.setDone(false);
		notifyAll();		
		
	}
	@Override
	public void run() {
		try {
		closeFile(tmp, finish);
		} catch (IOException e) {
			
		}
	}
}
