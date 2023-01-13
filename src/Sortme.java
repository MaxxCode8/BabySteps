

import java.util.*;
//Embedded all sorting algorithms into one java file!

public class Sortme {
// quick_sort
// plus one to call them and create a user interface where user can input the array for sorting
// and sorted array will be returned in a disciplined manner

    public static void main (String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Provide the length of the array: ");
        int n = scan.nextInt();
        int[] inputarr = new int[n];
        for(int i = 0; i < n; i++){
            System.out.println("Insert next element: ");
            int ele = scan.nextInt();
            inputarr[i] = ele;
        }
        System.out.println("Un-Sorted Input Array: " + Arrays.toString(inputarr));
        System.out.println();

//      System.out.println("Sorted Array using Quick Sort: " + Arrays.toString(quickSort(inputarr,0,inputarr.length-1)));

    }

/* WE HAVE TO CREATE THREE HElPER METHODS HERE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
    //  QUICK SORT : We need three sub_methods here , swap, partition, finally quicksort_out
        static void swap(Integer[] arr, int i, int j){
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

        static int partition(Integer[] arr, int low, int high){
            // Defining pivot point as last ele in array
            int pivot = arr[high];

            // INDEX of the smallest ele found....
            // right most ele index found to the right of pivot
            int i = low-1;
            for (int j = low; j <= high-1 ; j++){
                if (arr[j] < pivot){
//                  incrementing index of smallest ele...
                    i++;
                    swap(arr,i,j);
                }
            }
            swap(arr,i+1,high);
            return (i+1);
        }

        public static Integer[] quickSort (Integer[] arr, int low, int high){
            if (low < high){
                // pi is now perfect partioning index because of partition method!
                int pi = partition(arr,low ,high);

                //Seperately sorting elements before partition and after partition!
                quickSort(arr,low, pi-1);
                quickSort(arr,pi + 1, high);
            }
            return arr;
        }

}


