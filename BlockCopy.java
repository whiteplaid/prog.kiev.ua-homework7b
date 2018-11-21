package com.homework.blockcopy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BlockCopy implements Runnable {
	private File source;
	private File targetDir;
	private boolean readbyte = false;
	private boolean blockReady = false;
	private boolean done = false;
	private long status = 0;

	public BlockCopy(File source, File targetDir) {
		super();
		this.source = source;
		this.targetDir = targetDir;
	}

	public BlockCopy() {
		super();
	}


	private File getSource() {
		return source;
	}

	private File getTargetDir() {
		return targetDir;
	}

	public boolean isReadbyte() {
		return readbyte;
	}

	public synchronized void setReadbyte(boolean readbyte) {
		this.readbyte = readbyte;
		notifyAll();
	}

	public synchronized boolean isBlockReady() {
		return blockReady;
	}

	public synchronized void setBlockReady(boolean blockReady) {
		this.blockReady = blockReady;
		notifyAll();
	}

	public long getStatus() {
		return status;
		
	}

	public synchronized void setStatus(long status) {
		this.status = status;
		notifyAll();
	}

	public synchronized boolean isDone() {
		return done;
	}

	public synchronized void setDone(boolean done) {
		this.done = done;
		notifyAll();
	}

	private synchronized void blockCopy (File source, File targetDir) throws IOException {
		while (isReadbyte()) {
			try {
				wait();
			} catch (InterruptedException e) {
				
			}
		}
		File tmpOne = new File (targetDir,"" + source.getName());
		
		FileInputStream tmp = new FileInputStream(source);
		FileOutputStream finish = new FileOutputStream(tmpOne);
			new Thread(new CopyFile(source,tmp,finish,this)).start();
			new Thread(new CloseFile(tmp, finish,this)).start();
			new Thread(new Progress(source,tmp,this)).start();
		setReadbyte(true);
		notifyAll();
	}
	
	@Override
	public void run() {
		File[] paths;
		paths = getSource().listFiles();
		
		for (File path : paths) {
			try {
				blockCopy(path, getTargetDir());
				} catch (IOException e) {
										
				}		
		}
	}
}
