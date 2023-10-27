import java.awt.event.InputEvent;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int[][] Matrix = {
                {0, 3, 8, 4, 2, 6, 8, 5, 4, 12, 16},
                {3, 0, 4, 11, 18, 12, 18, 11, 18, 9, 15},
                {8, 4, 0, 15, 14, 8, 9, 5, 16, 7, 6},
                {4, 11, 15, 0, 18, 7, 3, 12, 10, 11, 5},
                {2, 18, 14, 18, 0, 17, 16, 14, 3, 11, 18},
                {6, 12, 8, 7, 17, 0, 10, 10, 7, 9, 16},
                {8, 18, 9, 3, 16, 10, 0, 16, 15, 12, 15},
                {5, 11, 5, 12, 14, 10, 16, 0, 9, 3, 5},
                {4, 18, 16, 10, 3, 7, 15, 9, 0, 11, 12},
                {12, 9, 7, 11, 11, 9, 12, 3, 11, 0, 7},
                {16, 15, 6, 5, 18, 16, 15, 5, 12, 7, 0}
        };

        ForkJoinPool pool = new ForkJoinPool();
        ShortestPathTask spt = new ShortestPathTask(0,10,0,new int[0]);
        ShortestPathTask.InputMatrix = Matrix;
        long startTime = System.nanoTime();
        startTime = System.nanoTime();
        pool.invoke(spt);
        long endTime = System.nanoTime();
        long elapsedTimeInMicroseconds = (endTime - startTime) / 1000;
        System.out.println("Время выполнения Fork Join Pool "  + String.format("%,d", elapsedTimeInMicroseconds)+ " мкс");
        System.out.println("Длина пути :"+spt.Length);
        System.out.println("Вершины кратчайшего пути");
        for(int i = 0; i< spt.path.length;i++){
            System.out.print(spt.path[i]+" ");
        }
        System.out.println();
        System.out.println("-------------------------------------");

        spt = new ShortestPathTask(0,10,Integer.MAX_VALUE,new int[0]);
        startTime = System.nanoTime();
        spt.SingleCoreFindPath(0,10,0,new int[]{0});
        endTime = System.nanoTime();
        elapsedTimeInMicroseconds = (endTime - startTime) / 1000;
        System.out.println("Время в однопотоке " + String.format("%,d", elapsedTimeInMicroseconds)+ " мкс");
        System.out.println("Длина пути :"+spt.Length);
        System.out.println("Вершины кратчайшего пути");
        for(int i = 0; i< spt.path.length;i++){
            System.out.print(spt.path[i]+" ");
        }
        System.out.println();
        System.out.println("-------------------------------------");

        MultyThreadPathFind multyThreadPathFind = new MultyThreadPathFind(1);
        MultyThreadPathFind.Matrix = Matrix;
        startTime = System.nanoTime();
        multyThreadPathFind.MultyThreadFind(0,10,0,new int[]{0});
        endTime = System.nanoTime();
        elapsedTimeInMicroseconds = (endTime - startTime) / 1000;
        System.out.println("Время произвольного пула потоков с минимально используемым колиеством потоков "  + String.format("%,d", elapsedTimeInMicroseconds)+ " мкс");
        System.out.println("Длина пути :"+MultyThreadPathFind.Length);
        System.out.println("Вершины кратчайшего пути");
        for(int i = 0; i< multyThreadPathFind.path.length;i++){
           System.out.print(multyThreadPathFind.path[i]+" ");
        }
        System.out.println();
        System.out.println("-------------------------------------");

        multyThreadPathFind = new MultyThreadPathFind(5);
        MultyThreadPathFind.Matrix = Matrix;
        startTime = System.nanoTime();
        multyThreadPathFind.MultyThreadFind(0,10,0,new int[]{0});
        endTime = System.nanoTime();
        elapsedTimeInMicroseconds = (endTime - startTime) / 1000;
        System.out.println("Время произвольного пула потоков с 5 потоками "  + String.format("%,d", elapsedTimeInMicroseconds)+ " мкс");
        System.out.println("Длина пути :"+MultyThreadPathFind.Length);
        System.out.println("Вершины кратчайшего пути");
        for(int i = 0; i< multyThreadPathFind.path.length;i++){
            System.out.print(multyThreadPathFind.path[i]+" ");
        }
    }
}