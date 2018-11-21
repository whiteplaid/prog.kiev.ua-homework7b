package com.homework.blockcopy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyFile implements Runnable {
	private File source;
	private FileInputStream tmp;
	private FileOutputStream finish;
	private BlockCopy copy;

	public CopyFile(File source, FileInputStream tmp, FileOutputStream finish, BlockCopy copy) {
		super();
		this.source = source;
		this.tmp = tmp;
		this.finish = finish;
		this.copy = copy;
	}
	
	public CopyFile() {
		super();
	}

	private synchronized void readByte (File source,FileInputStream tmp) throws IOException {
		while (copy.getStatus() != 0) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				
			}
		}
		copy.setReadbyte(true);
		copy.setBlockReady(true);
		int r = 0;
		long status = 0;
		byte[] b = new byte[1024];
		while (copy.getStatus() != source.length()) {
			while ((r = tmp.read(b)) > 0) {			
				finish.write(b,0,r);
				status += r;
				copy.setStatus(status);
			}
		}
	
		copy.setBlockReady(false);
		copy.setDone(true);
		
		notifyAll();
	}
	
	@Override
	public void run() {
		try {
		readByte(source, tmp);
		} catch (IOException e) {
			
		}
	}
}
