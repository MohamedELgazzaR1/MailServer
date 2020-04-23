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
	int end=data.length;
	int mid=(start+end)/2;
	Stack search=new Stack();
	search.push(end);
	search.push(mid);
	search.push(start);
	while(start<=end) {
		start=(int)search.pop();
		mid=(int)search.pop();
		end=(int)search.pop();
		if(compare(Target,data[mid]) > 0) {
			start=mid;
			mid=(mid+end)/2;
			search.push(end);
			search.push(mid);
			search.push(start);
		}else if(compare(Target,data[mid]) < 0) {
			end=mid;
			mid=(start+mid)/2;
			search.push(end);
			search.push(mid);
			search.push(start);
		}else {
			return mid;
		}
	}
	return -1;
	}
}
