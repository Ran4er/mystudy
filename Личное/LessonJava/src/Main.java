import java.util.*;

//environment variable: File_Name
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hashtable<String, Integer> hash = new Hashtable<>();

        for (int i = 1; i<=4; i++){
            hash.put(sc.next(), i);
        }

        java.util.Enumeration<String> keys = hash.keys();
        while(keys.hasMoreElements()){
            String key = keys.nextElement();
            System.out.println("Key: " + key + ", value: " + hash.get(key));
        }

        System.out.println(hash);

        Map<String, Integer> hmap = new Hashtable<String, Integer>();
        hmap.put("A", 1);
        hmap.put("A", 2);
        System.out.println(hmap);

    }

}