package com.homework.blockcopy;

import java.io.File;
import java.io.FileInputStream;

public class Progress implements Runnable{
	private long status = 0;
	private File source;
	private FileInputStream tmp;
	private BlockCopy copy;

	public Progress(File source, FileInputStream tmp, BlockCopy copy) {
		super();
		this.source = source;
		this.tmp = tmp;
		this.copy = copy;
	}
	public Progress() {
		super();
	}
	public File getSource() {
		return source;
	}
	public void setSource(File source) {
		this.source = source;
	}
	
	public FileInputStream getTmp() {
		return tmp;
	}
	public void setTmp(FileInputStream tmp) {
		this.tmp = tmp;
	}

	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}

	private synchronized void getPercent (FileInputStream tmp, File source)  {
		while (copy.getStatus() != source.length()) {
			System.out.println(source.getName() + " " + ((copy.getStatus() * 100)/source.length()) + "%");
		}
	}

	@Override
	public void run() {
		getPercent(tmp, source);
	}
}
