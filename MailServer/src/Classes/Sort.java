package Classes;

import classes.Stack;

public class Sort {
	
	static String[] Swap(String[] data,int a,int b) {
		
		String temp=data[a];
		data[a]=data[b];
		data[b]=temp;
		return data;
		
	}
	
    public static void quickSort(String[] data) {
        Stack sort = new Stack();
        sort.push(0);
        sort.push(data.length);

        while (!sort.isEmpty()) {
            int end = (int) sort.pop();
            int start = (int) sort.pop();
            if (end - start >= 2) {
                int pivot = start ;   
                pivot = divide(data, pivot, start, end);

                sort.push(pivot + 1);
                sort.push(end);

                sort.push(start);
                sort.push(pivot);
            }

            
        }
    }


	
    private static int divide(String[] data, int position, int start, int end) {
        int lower = start;
        int upper = end - 2;
        String piv = data[position];
        Swap(data, position, end - 1);

        while (lower < upper) {
            if (Filter.compare(data[lower], piv)<0) {
                lower++;
            } else if (Filter.compare(data[upper],piv) >0) {
                upper--;
            } else {
                Swap(data, lower, upper);
            }
        }
        int index = upper;
        if (Filter.compare(data[upper],piv) < 0) {
            index++;
        }
        Swap(data, end - 1, index);
        return index;
    }


	 	
	
}