package com.aulo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class ExeBat {
	/**
	 * java执行bat文件1
	 */
	public static void exeBat1(String file_path) {
		try {
			String exeBat = file_path;
			Process child = Runtime.getRuntime().exec(exeBat);
			InputStream in = child.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String line = br.readLine().toString();
			while (line != null) {
				System.out.println(line); // 输出测试
				line = br.readLine().toString();
			}

			try {
				child.waitFor();
				br.close();
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 必须要有一个输出流，将缓冲区内的内容输出
	/**
	 * java执行bat文件2
	 */
	public static boolean spawnExe(String batch_file, boolean waitOnClose) {
		boolean retval = false;
		try {
			String osName = System.getProperty("os.name");
			String[] cmd = new String[3];

			// Win9X
			if (osName.equals("WIN_95") || osName.equals("WIN_98")
					|| osName.startsWith("WIN_3X") || osName.startsWith("WIN_FW")) {
				cmd[0] = "command.com";
				cmd[1] = "/C";
				cmd[2] = batch_file;
				retval = true;
			}
			// W2K or greater
			else if (osName.startsWith("WIN")) {
				cmd[0] = "cmd.exe";
				cmd[1] = "/C";
				cmd[2] = batch_file;
				retval = true;
			}

			if (retval) {
				Runtime rt = Runtime.getRuntime();
				Process proc = rt.exec(cmd);

				if (waitOnClose)
					proc.waitFor();
			}
		} catch (Exception e) {
			e.printStackTrace();
			retval = false;
		}
		return retval;
	}

	/**
	 * FileWriter 生成bat文件
	 */
	static void WriteFile(String bat) {
		try {
			File FileName = new File("b.bat");
			FileWriter fileOut = new FileWriter(FileName);
			BufferedWriter fou = new BufferedWriter(fileOut);
			fou.write(bat);
			fou.close();
		} catch (IOException e) {
			System.out.println("文件错误");
		}

	}

	/*
	 * public static void main(String args[]){ String
	 * str="ping 192.168.0.1 > b.txt"; WriteFile(str); try { String command =
	 * "cmd.exe /c"+"start /min b.bat"; Process child =
	 * Runtime.getRuntime().exec(command); } catch (IOException e){
	 * System.out.println("文件错误"); }
	 * 
	 * }
	 */
	/**
	 * FileOutputStream 生成bat文件
	 */
	static void WriteFile2(String bat) {
		try {
			File FileName = new File("f://bat//b.bat");
			FileOutputStream fileOut = new FileOutputStream(FileName);
			DataOutputStream fou = new DataOutputStream(fileOut);
			// fou.writeChars(bat); //会出问题
			fou.write(bat.getBytes());
			fou.close();
		} catch (IOException e) {
			System.out.println("文件错误");
		}

	}
	/*
	 * public static void main(String args[]){ String
	 * str="ping 192.168.0.1 >f:/bat/b.txt"; WriteFile(str); try { String
	 * command = "cmd.exe /c"+"start /min f:\\bat//b.bat"; Process child =
	 * Runtime.getRuntime().exec(command); } catch (IOException e){
	 * System.out.println("文件错误"); }
	 */
}
