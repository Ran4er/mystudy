public class Main {
    public static void main(String[] args) {
        int[] arr = {4000, 3000, 1000, 2000};
        System.out.println(average(arr));
    }

    public static double average(int[] salary) {
        double sum = 0d;
        int max = Integer.MAX_VALUE, min = Integer.MIN_VALUE;

        for(int s : salary){
            max = Math.min(max, s);
            min = Math.max(min, s);
            sum += s;
        }

        System.out.println(max);
        System.out.println(min);
        System.out.println(sum);

        return (sum - max - min) / (salary.length - 2);
    }
}