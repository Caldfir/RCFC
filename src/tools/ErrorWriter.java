package tools;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

public class ErrorWriter {

	private FileWriter stream;
	private PrintWriter writer;

	private static ErrorWriter er;

	public static ErrorWriter getInstance() {
		if(er == null){
			er = new ErrorWriter();
		}
		return er;
	}

	private ErrorWriter(){
		try {
			stream = new FileWriter("errorlog.txt");
			writer = new PrintWriter(stream);
		} 
		catch (IOException e) {                    
			JOptionPane.showMessageDialog(null,
					"issue creating error log file",
					"error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void write(String s){
		System.out.println(s);
		writer.println(s);
	}

	public void close(){
		try {
			writer.close();
			stream.close();
		} 
		catch (IOException e) {                    
			JOptionPane.showMessageDialog(null,
					"issue closing error log",
					"error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
