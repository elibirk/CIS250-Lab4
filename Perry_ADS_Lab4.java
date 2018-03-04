import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class Perry_ADS_Lab4 {
	public static void main(String[]args) throws IOException, FileNotFoundException{
		PrintWriter out = new PrintWriter("output.txt");
		
		ArrayList<Double> original = new ArrayList<Double>();
		ArrayList<Double> rnums = original;
		double random;//random double temp value
		for(int i = 0; i < 100; i++){//fill array
			Random r = new Random(); //gets new random number
			random = r.nextDouble(); //sets random as a random double
			rnums.add(random);
		}//end for

		long startTime;//start, end, elapsed, and average all used for experimental study
		long endTime;
		long elapsed;
		long average;
		ArrayList<Long> times = new ArrayList<Long>();//array to store times for experimental study
		
		System.out.println("The original array: ");
		out.println("The original array: ");
		PrintArray(original,out);
		
		for(int i = 1; i < 4; i++){//merge sort
			startTime = System.nanoTime();
			mergeSort(rnums);
			endTime = System.nanoTime();
			elapsed = endTime - startTime;
			System.out.println("\nMerge sort, run " + i + ", taking " + elapsed + " nanoseconds, results in:");
			out.println();//unnecessary-seeming newlines are added for the txt file because Notepad doesn't 
			//read \n properly, Notepad++ could read them, but I assume Notepad will be used
			out.println("Merge sort, run " + i + ", taking " + elapsed + " nanoseconds, results in:");
			PrintArray(rnums,out);
			rnums = original;//rnums must be reset each time in order to ensure fair comparisons
			times.add(elapsed);//add times to times
		}
		average = (times.get(0) + times.get(1) + times.get(2))/3; //averages the merge times
		times.add(average);
			
		System.out.println("\n");//empty lines for formatting
		out.println();
		out.println();
		
		for(int i = 1; i < 4; i++){//quick sort
			startTime = System.nanoTime();
			quickSort(rnums);
			endTime = System.nanoTime();
			elapsed = endTime - startTime;
			System.out.println("\nQuick sort, run " + i + ", taking " + elapsed + " nanoseconds, results in:");
			out.println();
			out.println("Quick sort, run " + i + ", taking " + elapsed + " nanoseconds, results in:");
			PrintArray(rnums, out);
			rnums = original;
			times.add(elapsed);
		}
		average = (times.get(4) + times.get(5) + times.get(6))/3; //averages the quick times
		times.add(average);
		
		
		System.out.println("\n\nBecause Bucket Sort requires another sorting algorithm for doubles, two versions "
				+ "are included:");
		out.println();
		out.println();
		out.println("Because Bucket Sort requires another sorting algorithm for doubles, two versions are"
				+ " included:");
		
		for(int i = 1; i < 4; i++){//quick bucket
			startTime = System.nanoTime();
			bucketSort(rnums);
			endTime = System.nanoTime();
			elapsed = endTime - startTime;
			System.out.println("\nBucket sort (using quick sort), run " + i + ", taking " + elapsed + 
					" nanoseconds, results in:");
			out.println();
			out.println("Bucket sort (using quick sort), run " + i + ", taking " + elapsed + 
					" nanoseconds, results in:");
			PrintArray(rnums, out);
			rnums = original;
			times.add(elapsed);
		}
		
		average = (times.get(8) + times.get(9) + times.get(10))/3; //averages the quick bucket times
		times.add(average);
		
		System.out.println();//empty line for formatting
		out.println();
		
		for(int i = 1; i < 4; i++){//merge bucket sort
			startTime = System.nanoTime();
			bucketSortMerge(rnums);
			endTime = System.nanoTime();
			elapsed = endTime - startTime;
			System.out.println("\n\nBucket sort (using merge sort), run " + i + ", taking " + elapsed + 
					" nanoseconds, results in:");
			out.println();
			out.println();
			out.println("Bucket sort (using merge sort), run " + i + ", taking " + elapsed + 
					" nanoseconds, results in:");
			PrintArray(rnums, out);
			rnums = original;
			times.add(elapsed);
		}
		average = (times.get(12) + times.get(13) + times.get(14))/3; //averages the merge bucket times
		times.add(average);
		
		//experimental study results:
		int i = 0;
		System.out.println("\n\n\n\nExperimental Study:\nMerge Sort:");
		out.println();
		out.println();
		out.println();
		out.println();
		out.println("Experimental Study:");
		out.println("Merge Sort:");
		for(int j = 1; j < 4; j++){
			System.out.println("\tRun " + j + ": " + times.get(i));
			out.println("\tRun " + j + ": " + times.get(i));//for some reason notepad /does/ understand \t
			i++;
		}
		System.out.println("\tAverage: " + times.get(i));
		out.println("\tAverage: " + times.get(i));
		i++;
		
		System.out.println("Quick Sort:");
		out.println("Quick Sort:");
		for(int j = 1; j < 4; j++){
			System.out.println("\tRun " + j + ": " + times.get(i));
			out.println("\tRun " + j + ": " + times.get(i));
			i++;
		}
		System.out.println("\tAverage: " + times.get(i));
		out.println("\tAverage: " + times.get(i));
		i++;
		
		System.out.println("Quick Bucket Sort:");
		out.println("Quick Bucket Sort:");
		for(int j = 1; j < 4; j++){
			System.out.println("\tRun " + j + ": " + times.get(i));
			out.println("\tRun " + j + ": " + times.get(i));
			i++;
		}
		System.out.println("\tAverage: " + times.get(i));
		out.println("\tAverage: " + times.get(i));
		i++;
		
		System.out.println("Merge Bucket Sort:");
		out.println("Merge Bucket Sort:");
		for(int j = 1; j < 4; j++){
			System.out.println("\tRun " + j + ": " + times.get(i));
			out.println("\tRun " + j + ": " + times.get(i));
			i++;
		}
		System.out.println("\tAverage: " + times.get(i));
		out.println("\tAverage: " + times.get(i));
		
		out.close();
	}//end main
	
	
	public static void mergeSort(ArrayList<Double> randomnums){
		int  n = randomnums.size();
		if(n < 2) {//return singular 
			return;
		}//end if
		int mid = n/2;
		ArrayList<Double> array1 = new ArrayList<Double>(randomnums.subList(0, mid));//divide into halves
		ArrayList<Double> array2 = new ArrayList<Double>(randomnums.subList(mid, n));
		mergeSort(array1);//divide each half
		mergeSort(array2);
		
		merge(array1, array2, randomnums);
	}//end mergeSort
	
	
	
	
	public static void merge(ArrayList<Double> arr1, ArrayList<Double> arr2, ArrayList<Double> fin){
		int i = 0, j = 0;
		while(i + j < fin.size()){
			if(j == arr2.size() || (i < arr1.size() && arr1.get(i) < arr2.get(j))){
				fin.set(i+j, arr1.get(i));//copy i into final
				i++;//increment i
			}//end if
			else{
				fin.set(i+j, arr2.get(j));//copy j into final
				j++;//increment j
			}
		}
	}//end merge
	
	
	
	
	public static void quickSort(ArrayList <Double> arr){
		int n = arr.size();
		if(n < 2){//return singular elements, since there is nothing to sort
			return;
		}
		double pivot = arr.get(0); //sets first value as pivot value
		ArrayList <Double> A = new ArrayList<Double>();
		ArrayList <Double> B = new ArrayList<Double>();
		ArrayList <Double> C = new ArrayList<Double>();
		while (n > 0){
			double e = arr.get(n-1);
			if (e < pivot){//less than pivot
				A.add(e);
			}
			else if(e > pivot){//greater than pivot
				C.add(e);
			}
			else{//element is equal to pivot
				B.add(e);
			}
			n--;
		}//end while

		quickSort(A);//sort a
		quickSort(C);//sort c, b does not need sorting because it is all the same
		n = 0;
		int m = 0;
		while(n < A.size()){//add A into final
			arr.set(m, A.get(n));
			n++;
			m++;
		}
		n = 0;
		while(n < B.size()){//add B into final
			arr.set(m,  B.get(n));
			n++;
			m++;
		}
		n = 0;
		while(n < C.size()){//add C into final
			arr.set(m,  C.get(n));
			n++;
			m++;
		}
	}//end quickSort
	
	
	
	
	public static void bucketSortMerge(ArrayList<Double> arr){
		int n = arr.size();
		ArrayList<ArrayList<Double>> buckets = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < 10; i++){
			ArrayList<Double> temp = new ArrayList<Double>();
			buckets.add(temp);
		}
		for(int i = 0; i < n; i++){
			int temp = (int)(arr.get(i) * 10);
			buckets.get(temp).add(arr.get(i));//sorts each element into a bucket
		}
		for(int i = 0; i < 10; i++){
			mergeSort(buckets.get(i));
		}
		
		arr = buckets.get(0);
		for (int i = 1; i < 10; i++){
			arr.addAll(buckets.get(i));
		}//concatenate the buckets
		
	}//end bucket sort
	
	
	
	public static void bucketSort(ArrayList<Double> arr){
		int n = arr.size();
		ArrayList<ArrayList<Double>> buckets = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < 10; i++){
			ArrayList<Double> temp = new ArrayList<Double>();
			buckets.add(temp);
		}
		for(int i = 0; i < n; i++){
			int temp = (int)(arr.get(i) * 10);
			buckets.get(temp).add(arr.get(i));//sorts each element into a bucket
		}
		for(int i = 0; i < 10; i++){
			quickSort(buckets.get(i));
		}
		
		arr = buckets.get(0);
		for (int i = 1; i < 10; i++){
			arr.addAll(buckets.get(i));
		}//concatenate the buckets
		
	}//end bucket sort
	
	
	public static void PrintArray(ArrayList<Double> arr, PrintWriter out){
		for(int i = 0; i < arr.size(); i++){
			System.out.print(arr.get(i) + ", ");
			out.print(arr.get(i) + ", ");
		}
		System.out.println();//empty line for formatting
		out.println();
	}
	
}//end class
