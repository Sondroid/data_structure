import java.io.*;
import java.util.*;

class Subway {
    public static void main(String[] args) {
        
        Hashtable<String, HashNode> ht = new Hashtable<>();

        try{
            File file = new File(args[0]);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String text = br.readLine();
            br.close();

            File routeMap = new File(text);
            br = new BufferedReader(new FileReader(file));

            // add vertices & edges to hash table
            bulidMap(br, ht);

		}
        catch(IOException e){
            System.out.println("Wrong route map error : " + e.toString());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("QUIT") == 0)
					break;

				evaluate(input, ht);
			}
			catch(Exception e)
			{
                e.printStackTrace();
			}
		}
    }

    static void evaluate(String input, Hashtable<String, HashNode> ht) throws Exception{
        String[] inputSplit = input.split(" ");

        String origin = inputSplit[0];
        String destin = inputSplit[1];

        String[] originKeys = getKeysFromName(origin, ht);

        Path[] paths = new Path[originKeys.length];
        
        for(int i=0; i < originKeys.length; i++){
            paths[i] = dijkstra(originKeys[i], destin, ht);
        }
        
        Path shortestPath = getShortest(paths);

        // print shortest path
        String p = "";
        for(int j=shortestPath.numItems-1; j >= 0; j--){
            if(j > 0 && shortestPath.path[j].equals(shortestPath.path[j-1])){
                p = p + "[" + shortestPath.path[j] + "] ";
                j--;
            }
            else if(j > 0){
                p = p + shortestPath.path[j] + " ";
            }
            else{
                p = p + shortestPath.path[j];
            }
        }
        System.out.println(p);

        // print the spending time
        System.out.println(shortestPath.time);
    }

    static Path dijkstra(String originKey, String destinName, Hashtable<String, HashNode> ht) throws Exception{
        
        Path path = new Path(ht.size());
        RelaxableMinHeap heap = new RelaxableMinHeap(ht);
        heap.get(originKey).dist = 0;
        heap.buildHeap();

        while(true){
            DistNode s = heap.takeMinAndRelax(ht);
            
            if(ht.get(s.key).name.equals(destinName)){
                DistNode curr = s;
                path.time = s.dist;
                while(curr != null){
                    path.add(curr, ht);
                    curr = curr.getPrev();
                }
                break;
            }
        }
        
        return path;
    }

    static Path getShortest(Path[] paths){

        Path shortest = paths[0];
        for(int i=1; i < paths.length; i++){
            if(paths[i].compareTo(shortest) < 0){
                shortest = paths[i];
            }
        }
        return shortest;
    }

    static String[] getKeysFromName(String origin, Hashtable<String, HashNode> ht){
        
        Enumeration<String> allKeys = ht.keys();
        String[] keys = new String[ht.size()];
        
        int i = 0;
        while(allKeys.hasMoreElements()){
            String curr = allKeys.nextElement();
            if(ht.get(curr).name.equals(origin)){
                keys[i] = curr;
                i++;
            }
        } 
        
        String[] trimmedKeys= new String[i];
        for(int j=0; j < trimmedKeys.length; j++){
            trimmedKeys[j] = keys[j];
        }
        return trimmedKeys;
    }


    static void addTransfer(Hashtable<String, HashNode> ht){
        Enumeration<String> i = ht.keys();

        while(i.hasMoreElements()){
            String it = i.nextElement();
            Enumeration<String> j = ht.keys();

            while(j.hasMoreElements()){
                String jt = j.nextElement();
                if(!it.equals(jt) && ht.get(it).name.equals(ht.get(jt).name)){
                    ht.get(it).list.add(new AdjacencyPair(jt, 5));
                }
            }
        }
    }

    static void bulidMap(BufferedReader br, Hashtable<String, HashNode> ht) throws IOException{
        String line;
        String[] lineSplit;
        while((line = br.readLine()) != null){
            // add each vertecies to hashtable
            lineSplit = line.split(" ");
            if(lineSplit.length != 3) break;
            HashNode hashNode = new HashNode(lineSplit[1], lineSplit[2]);
            ht.put(lineSplit[0], hashNode);
        }

        while((line = br.readLine()) != null){
            // add each edges to adjacency list
            lineSplit = line.split(" ");
            AdjacencyPair pair = new AdjacencyPair(lineSplit[1], Integer.parseInt(lineSplit[2]));
            ht.get(lineSplit[0]).list.add(pair);
        }

        // add transfering edge to adjacency list
        addTransfer(ht);
    }


}