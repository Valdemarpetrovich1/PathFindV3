import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

public class MultyThreadPathFind
{
    public  static int[][]Matrix;

    public static int Length = Integer.MAX_VALUE;
    public   static int[] path;

    private  static ReentrantLock lockerWriteResault;

    public static ExecutorService executorService;

    public MultyThreadPathFind(int setThreadCount){
        MultyThreadPathFind.Length = Integer.MAX_VALUE;
        MultyThreadPathFind.path = new int[0];
        executorService = Executors.newFixedThreadPool(setThreadCount);
        lockerWriteResault = new ReentrantLock();
    }

    public void  MultyThreadFind(int start,int end,int length,int[]inputPath){
        if(start == end)
        {
            lockerWriteResault.lock();
            if(length<MultyThreadPathFind.Length)
            {
                MultyThreadPathFind.Length = length;
                MultyThreadPathFind.path = inputPath;
            }
            lockerWriteResault.unlock();
        }
        for(int i = 0; i<MultyThreadPathFind.Matrix[start].length;i++)
        {
            if(ShortestPathTask.InputMatrix[start][i]<=0)
            {
                continue;
            }
            boolean isCont = false;
            for(int j = 0;j<inputPath.length;j++)
            {
                if(inputPath[j] == i)
                {
                    isCont = true;
                }
            }
            if(isCont)
                continue;
            int [] newPath = new int[inputPath.length+1];
            for(int j = 0;j<inputPath.length;j++){
                newPath[j] = inputPath[j];
            }
            newPath[newPath.length-1] = i;
            executorService.execute(new FindTask(i, end, length + MultyThreadPathFind.Matrix[start][i], newPath));
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {}
    }

    public class FindTask extends Thread
    {
        private int Start;
        private int End;
        private int lengthpath;

        private  int[]InputPath;
        public  FindTask(int start,int end,int length,int[]inputPath){
            Start = start;
            End = end;
            lengthpath = length;
            InputPath = inputPath;
        }
        public void run()
        {
            MultyThreadFind(Start,End,lengthpath,InputPath);
        }
        public void  MultyThreadFind(int start,int end,int length,int[]inputPath){
            if(start == end)
            {
                lockerWriteResault.lock();
                if(length<MultyThreadPathFind.Length)
                {
                    MultyThreadPathFind.Length = length;
                    MultyThreadPathFind.path = inputPath;
                }
                lockerWriteResault.unlock();
            }
            for(int i = 0; i<MultyThreadPathFind.Matrix[start].length;i++) {
                if (ShortestPathTask.InputMatrix[start][i] <= 0) {
                    continue;
                }
                boolean isCont = false;
                for (int j = 0; j < inputPath.length; j++) {
                    if (inputPath[j] == i) {
                        isCont = true;
                    }
                }
                if (isCont)
                    continue;
                int[] newPath = new int[inputPath.length + 1];
                for (int j = 0; j < inputPath.length; j++) {
                    newPath[j] = inputPath[j];
                }
                newPath[newPath.length - 1] = i;
                MultyThreadFind(i, end, length + MultyThreadPathFind.Matrix[start][i], newPath);
            }
        }
    }
}
