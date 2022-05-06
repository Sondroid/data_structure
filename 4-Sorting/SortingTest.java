import java.io.*;
import java.util.*;

public class SortingTest
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try
		{
			boolean isRandom = false;	// 입력받은 배열이 난수인가 아닌가?
			int[] value;	// 입력 받을 숫자들의 배열
			String nums = br.readLine();	// 첫 줄을 입력 받음
			if (nums.charAt(0) == 'r')
			{
				// 난수일 경우
				isRandom = true;	// 난수임을 표시

				String[] nums_arg = nums.split(" ");

				int numsize = Integer.parseInt(nums_arg[1]);	// 총 갯수
				int rminimum = Integer.parseInt(nums_arg[2]);	// 최소값
				int rmaximum = Integer.parseInt(nums_arg[3]);	// 최대값

				Random rand = new Random();	// 난수 인스턴스를 생성한다.

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 각각의 배열에 난수를 생성하여 대입
					value[i] = rand.nextInt(rmaximum - rminimum + 1) + rminimum;
			}
			else
			{
				// 난수가 아닐 경우
				int numsize = Integer.parseInt(nums);

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 한줄씩 입력받아 배열원소로 대입
					value[i] = Integer.parseInt(br.readLine());
			}

			// 숫자 입력을 다 받았으므로 정렬 방법을 받아 그에 맞는 정렬을 수행한다.
			while (true)
			{
				int[] newvalue = (int[])value.clone();	// 원래 값의 보호를 위해 복사본을 생성한다.

				String command = br.readLine();

				long t = System.currentTimeMillis();
				switch (command.charAt(0))
				{
					case 'B':	// Bubble Sort
						newvalue = DoBubbleSort(newvalue);
						break;
					case 'I':	// Insertion Sort
						newvalue = DoInsertionSort(newvalue);
						break;
					case 'H':	// Heap Sort
						newvalue = DoHeapSort(newvalue);
						break;
					case 'M':	// Merge Sort
						newvalue = DoMergeSort(newvalue);
						break;
					case 'Q':	// Quick Sort
						newvalue = DoQuickSort(newvalue);
						break;
					case 'R':	// Radix Sort
						newvalue = DoRadixSort(newvalue);
						break;
					case 'X':
						return;	// 프로그램을 종료한다.
					default:
						throw new IOException("잘못된 정렬 방법을 입력했습니다.");
				}
				if (isRandom)
				{
					// 난수일 경우 수행시간을 출력한다.
					System.out.println((System.currentTimeMillis() - t) + " ms");
				}
				else
				{
					// 난수가 아닐 경우 정렬된 결과값을 출력한다.
					for (int i = 0; i < newvalue.length; i++)
					{
						System.out.println(newvalue[i]);
					}
				}

			}
		}
		catch (IOException e)
		{
			System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoBubbleSort(int[] value)
	{
		// TODO : Bubble Sort 를 구현하라.
		// value는 정렬안된 숫자들의 배열이며 value.length 는 배열의 크기가 된다.
		// 결과로 정렬된 배열은 리턴해 주어야 하며, 두가지 방법이 있으므로 잘 생각해서 사용할것.
		// 주어진 value 배열에서 안의 값만을 바꾸고 value를 다시 리턴하거나
		// 같은 크기의 새로운 배열을 만들어 그 배열을 리턴할 수도 있다.

		for(int i=0; i < value.length - 1; i++){
			for(int j=0; j < value.length - 1 - i; j++){
				if(value[j] > value[j+1]){
					int temp = value[j];
					value[j] = value[j+1];
					value[j+1] = temp;
				}
			}
		}

		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoInsertionSort(int[] value)
	{
		// TODO : Insertion Sort 를 구현하라.

		for(int i=1; i < value.length; i++){
			for(int j=i; j > 0; j--){
				if(value[j] < value[j-1]){
					int temp = value[j-1];
					value[j-1] = value[j];
					value[j] = temp;
				}
				else break;
			}
		}

		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 강의 코드 참고 시작
	private static int[] DoHeapSort(int[] value)
	{
		// TODO : Heap Sort 를 구현하라.

		buildHeap(value);
		for(int i=value.length-1; i >= 1; i--){
			value[i] = deleteMax(value, i);
		}

		return (value);
	}

	private static void percolateDown(int[] value, int k){
		int child = 2*k+1;
		int right = 2*k+2;
		if(child <= value.length-1){
			if(right <= value.length-1 && value[child] < value[right]){
				child = right;
			}
			if(value[k] < value[child]){
				int temp = value[child];
				value[child] = value[k];
				value[k] = temp;
				percolateDown(value, child);
			}
		}
	}

	private static void buildHeap(int[] value){
		for(int i = (value.length-2)/2; i >= 0; i--){
			percolateDown(value, i);
		}
	}
	
	private static int deleteMax(int[] value, int lastIdx){
		int max = value[0];
		value[0] = value[lastIdx];

		int[] tempValue = new int[lastIdx];
		for(int i=0; i < lastIdx; i++){
			tempValue[i] = value[i];
		}

		percolateDown(tempValue, 0);

		for(int i=0; i < lastIdx; i++){
			value[i] = tempValue[i];
		}

		return max;
	}
	// 강의 코드 참고 끝

	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 강의 코드 참고 시작
	private static int[] DoMergeSort(int[] value)
	{
		// TODO : Merge Sort 를 구현하라.

		mergeSort(value, 0, value.length-1);
		
		return (value);
	}

	private static void mergeSort(int[] array, int p, int r){
		if(p<r){
			int q = (p+r)/2;
			mergeSort(array, p, q);
			mergeSort(array, q+1, r);
			merge(array, p, q, r);
		}
	}

	private static void merge(int[] array, int p, int q, int r){
		int i=p; int j=q+1; int t=0;
		int[] tmp = new int[array.length];
		while(i <= q && j <= r){
			if(array[i] <= array[j]){
				tmp[t++] = array[i++];
			}
			else{
				tmp[t++] = array[j++];
			}
		}
		while(i <= q){
			tmp[t++] = array[i++];
		}
		while(j <= r){
			tmp[t++] = array[j++];
		}
		i = p; t = 0;
		while(i <= r){
			array[i++] = tmp[t++];
		}
	}
	// 강의 코드 참고 끝

	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 강의 코드 참고 시작
	private static int[] DoQuickSort(int[] value)
	{
		// TODO : Quick Sort 를 구현하라.

		quickSort(value, 0, value.length-1);

		return (value);
	}

	private static void quickSort(int[] array, int p, int r){
		if(p < r){
			int q = partition(array, p, r);
			quickSort(array, p, q-1);
			quickSort(array, q+1, r);
		}
	}

	private static int partition(int[] array, int p, int r){
		int x = array[r];
		int i = p-1;
		for(int j = p; j < r; j++){
			if(array[j] <= x){
				i++;
				int temp = array[i];
				array[i] = array[j];
				array[j] = temp;
			}
		}
		array[r] = array[i+1];
		array[i+1] = x;
		return i+1;
	}
	// 강의 코드 참고 끝

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoRadixSort(int[] value)
	{
		// TODO : Radix Sort 를 구현하라.
		
		Queue<Integer> q0 = new LinkedList<>();
		Queue<Integer> q1 = new LinkedList<>();
		Queue<Integer> q2 = new LinkedList<>();
		Queue<Integer> q3 = new LinkedList<>();
		Queue<Integer> q4 = new LinkedList<>();
		Queue<Integer> q5 = new LinkedList<>();
		Queue<Integer> q6 = new LinkedList<>();
		Queue<Integer> q7 = new LinkedList<>();
		Queue<Integer> q8 = new LinkedList<>();
		Queue<Integer> q9 = new LinkedList<>();
		
		// 음수 입력 시 최솟값 만큼 모두 빼줌
		int min = 0;
		for(int t=0; t < value.length; t++){
			if(value[t] < min) min = value[t];
		}
		if(min < 0){
			for(int s=0; s < value.length; s++){
				value[s] -= min;
			}
		}

		int j = 1;
		while(true){
			for(int i=0; i < value.length; i++){
				switch(digit(value[i], j)){
					case 0:
						q0.offer(value[i]); break;
					case 1:
						q1.offer(value[i]); break;
					case 2:
						q2.offer(value[i]); break;
					case 3:
						q3.offer(value[i]); break;
					case 4:
						q4.offer(value[i]); break;
					case 5:
						q5.offer(value[i]); break;
					case 6:
						q6.offer(value[i]); break;
					case 7:
						q7.offer(value[i]); break;
					case 8:
						q8.offer(value[i]); break;
					case 9:
						q9.offer(value[i]); break;
					default: break;
				}
			}
			if(q0.size() == value.length) break;

			int k = 0;
			while(!q0.isEmpty()){
				value[k++] = q0.poll();
			}
			while(!q1.isEmpty()){
				value[k++] = q1.poll();
			}
			while(!q2.isEmpty()){
				value[k++] = q2.poll();
			}
			while(!q3.isEmpty()){
				value[k++] = q3.poll();
			}
			while(!q4.isEmpty()){
				value[k++] = q4.poll();
			}
			while(!q5.isEmpty()){
				value[k++] = q5.poll();
			}
			while(!q6.isEmpty()){
				value[k++] = q6.poll();
			}
			while(!q7.isEmpty()){
				value[k++] = q7.poll();
			}
			while(!q8.isEmpty()){
				value[k++] = q8.poll();
			}
			while(!q9.isEmpty()){
				value[k++] = q9.poll();
			}
			j++;
		}

		if(min < 0){
			for(int s=0; s < value.length; s++){
				value[s] += min;
			}
		}

		return(value);
	}

	// 정수와 자릿수를 받아 해당 자리의 숫자를 반환
	private static int digit(int num, int i){
		String numStr = Integer.toString(num);

		if(numStr.length()-i < 0) return 0;

		return numStr.charAt(numStr.length()-i) - 48;
	}
}
