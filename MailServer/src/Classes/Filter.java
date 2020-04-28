package Classes;

import classes.Stack;

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
				start=mid + 1;
			}else if(compare(Target,data[mid]) < 0) {
				end=mid - 1;
			}else {
				return mid;
			}
			search.push(end);
			search.push(start);
		}
		return -1;
	}
}
