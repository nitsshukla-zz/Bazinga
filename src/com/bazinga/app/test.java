package com.bazinga.app;

import java.io.File;
import java.util.Scanner;

public class test {
	 public static void main(String[] args) {

			try{
				Scanner scan = new Scanner(new File("work_list.txt"));
				while(scan.hasNext()){
					String line = scan.nextLine();
					String[] ar = line.split(" ");
					String code = ar[ar.length-1];
					System.out.println(code + " $$$ "+ar.toString());
				}
				scan.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		
	}
}
