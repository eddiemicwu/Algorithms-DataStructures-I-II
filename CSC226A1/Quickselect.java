/**
 *
 * September 18, 2019
 * CSC 226 - Fall 2019
 */
import java.util.*;
import java.io.*;

public class Quickselect {
	
    public static int Quickselect(int[] A, int k) {
        //Write your code here 
		int value = quickSelect(A, k, 0, A.length-1);
        return value;
    }
	
	public static void swap(int arr[], int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
	
	/**
     * 
     *
     * @param array 	input array
     * @param k 		value of k to look for the k-th order statistic
     * @param left 		index of where the array starts
	 * @param right     index of where the array ends
	 * @return 
     */
	
	public static int quickSelect(int[] array, int k, int left, int right){
		
		if(left == right){
			return  array[left];
		}
		// 
		int m =  partition(array, k, left, right);
		int length =  m - left + 1;
		if(length == k){
			return array[m];
		}
		
		if(length > k){
			return quickSelect(array, k, left, m-1);
		}else{
			return quickSelect(array, k - length, m+1, right);
		}
	}
	
	/**
     * 
     *
     * @param array 	input array
     * @param k 		value of k to look for the k-th order statistic
     * @param left 		index of where the array starts
	 * @param right     index of where the array ends
	 * @return
     */
	
	public static int partition(int[] array, int k, int left, int right){
		int pivot = getPivotValue(array, left, right);
		while(left < right){
			while(array[left] < pivot){
				left++;
			}
			while(array[right] >  pivot){
				right--;
			}
			if(array[left] == array[right]){
				left++;
			}else  if(left <  right){
				int temp = array[left];
				array[left] = array[right];
				array[right]  = temp;
			}
		}
		return right;
	}
	
	/**
     * 
     *
     * @param array 	input array
     * @param k 		value of k to look for the k-th order statistic
     * @param left 		index of where the array starts
	 * @param right     index of where the array ends
	 * @return
     */
    
	public static int getPivotValue(int[] array, int left, int right){
		if(right-left+1 < 9){
			Arrays.sort(array);
			return array[array.length/2];
		}
		//Array to hold 9 element or less than 9 element.
		int temp[] = null;
		//Array to hold medians
		int medians[] = new int[(int)Math.ceil((double)(right-left+1)/9)];
		int medianIndex = 0;
		while(left <= right){
			temp = new int[Math.min(9, right-left+1)];
			for(int i=0;i<temp.length && left<=right;i++){
				temp[i] = array[left];
				left++;
			}
			//sort the small array 
			Arrays.sort(temp);
			//find the median and store it in medians array.
			medians[medianIndex]=temp[temp.length/2];
			medianIndex++;
		}
		//call recursive to find the median of medians.
		return getPivotValue(medians, 0, medians.length-1);
	}
	
    public static void main(String[] args) {
        Scanner s;
        int[] array;
        int k;
        if(args.length > 0) {
			try{
				s = new Scanner(new File(args[0]));
				int n = s.nextInt();
				array = new int[n];
				for(int i = 0; i < n; i++){
					array[i] = s.nextInt();
				}
			} catch(java.io.FileNotFoundException e) {
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n", args[0]);
        }else {
			s = new Scanner(System.in);
			System.out.printf("Enter a list of non-negative integers. Enter a negative value to end the list.\n");
			int temp = s.nextInt();
			ArrayList<Integer> a = new ArrayList<Integer>();
			while(temp >= 0) {
				a.add(temp);
				temp=s.nextInt();
			}
			array = new int[a.size()];
			for(int i = 0; i < a.size(); i++) {
				array[i]=a.get(i);
			}
			System.out.println("Enter k");
		}
        k = s.nextInt();
        System.out.println("The " + k + "th smallest number is the list is "
			   + Quickselect(array,k));	
    }
}
