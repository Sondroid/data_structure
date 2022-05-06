import java.io.*;
import java.util.ArrayList;

public class Matching
{	
	static Hashtable<String, intPair> ht;
	public static void main(String args[])
	{	
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("QUIT") == 0)
					break;

				command(input);
			}
			catch (IOException e)
			{
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}

	private static void command(String input) throws IOException
	{		
		switch(input.charAt(0)){
			case '<':
				commandEnter(input.substring(2));
				break;
			case '@':
				commandPrint(input.substring(2));
				break;
			case '?':
				commandSearch(input.substring(2));
				break;
			default:
				break;
		}
	}

	private static void commandEnter(String input) throws IOException{
		ht = new Hashtable<String, intPair>();
		
		File file = new File(input);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		int idxString = 0;
		while((line = br.readLine()) != null){
			idxString += 1;
			for(int idxStart=0; idxStart <= line.length() - 6; idxStart++){
				String subline = line.substring(idxStart, idxStart+6);
				ht.insert(subline, new intPair(idxString, idxStart + 1));
			}
		}
		br.close();
	}

	private static void commandPrint(String input){
		int slot = Integer.parseInt(input);
		AVLTree<String, intPair> tree = ht.table.get(slot);
		ArrayList<String> list = tree.preorder();
		String result = "";
		
		if(list.size() == 0){
			result += "EMPTY";
		}
		else{
			for(int i=0; i < list.size(); i++){
			result += list.get(i);
			result += " ";
			}
			result = result.substring(0, result.length()-1);
		}
		System.out.println(result);
	}

	private static void commandSearch(String input){
		String result = "";
		String head = input.substring(0, 6);
		AVLNode<String, intPair> nodeHead = ht.search(head);
		
		if(nodeHead == null){
			result = "(0, 0)";
		}
		else{
			for(int i=0; i < nodeHead.list.size(); i++){
				if(match(nodeHead.list.get(i), input)){
					result += "(" + nodeHead.list.get(i).int1 + ", " + nodeHead.list.get(i).int2 + ") ";
				}
			}
			result = result.trim();
		}

		System.out.println(result);
	}

	private static boolean match(intPair pair, String pattern){

		int n = pattern.length() / 6;
		int m = pattern.length() % 6;

		for(int i=2; i <= n; i++){
			if(!subMatch(new intPair(pair.int1, pair.int2 + 6*(i-1)), pattern.substring(6*(i-1), 6*i))){
				return false;
			}
		}
		if(m != 0){
			if(!subMatch(new intPair(pair.int1, pair.int2 + 6*(n-1) + m), pattern.substring(6*(n-1) + m))){
				return false;
			}
		}

		return true;
	}

	private static boolean subMatch(intPair pair, String subPattern){
		AVLNode<String, intPair> node = ht.search(subPattern);
		return node.contain(pair);
	}
}
