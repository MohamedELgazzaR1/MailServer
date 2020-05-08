package Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import classes.SinglyLinkedList;
import classes.Stack;
import interfaces.ILinkedList;

public class Filter {
	
	public static long compare(String X, String Y) {
		long out;
		try {
			out = Long.parseLong(X) - Long.parseLong(Y);
		}catch(Exception e) {
			out = X.compareTo(Y);
		}
		return out;
	}

	public static int binarySearch(String [] data,String Target) {
	
		int start=0;
		int end=data.length - 1;
		Stack search=new Stack();
		search.push(end);
		search.push(start);
		while(start<=end) {
			start=(int)search.pop();
			end=(int)search.pop();
			if (start == end) {
				if(compare(Target,data[start]) == 0) {
					return start;
				}
			}
			int mid=start+ (end - 1)/2;
			if(compare(Target,data[mid]) > 0) {
				search.push(end);
				search.push(mid + 1);
			}else if(compare(Target,data[mid]) < 0) {
				search.push(mid - 1);
				search.push(start);
			}else {
				return mid;
			}
		}
		return -1;
	}
	
	public static ILinkedList linearSearch (File currentfolder, String target) {
		File[] folderslist = currentfolder.listFiles();
		ILinkedList foundmails = new SinglyLinkedList();
		int sz = folderslist.length;
		for (int i = 0; i < sz; i++) {
			if (folderslist[i].isDirectory()) {
				File currentfile = new File(folderslist[i], "indexfile.txt");
				try {
					Scanner scan = new Scanner(currentfile);
					//skip time line
					scan.nextLine();
					while(scan.hasNextLine()) {
						String line = scan.nextLine();
						if (line.indexOf(target) != -1) {
							foundmails.add(folderslist[i]);
							scan.close();
							break;
						}
					}
					scan.close();
				} catch (FileNotFoundException e) {
					return null;
				}
			}
		}
		return foundmails;
	}
}
