import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

class ShortestPathTask extends RecursiveTask<Void>
{
    public static int InputMatrix[][];
    public  int start;
    public  int end;

    public int Length;
    public int[]path;

    public  ShortestPathTask(int start,int end,int length,int[]inputPath)
    {
        //System.out.println("!" + length);
        Length = length;
        this.start = start;
        this.end = end;
        this.path = new int[inputPath.length+1];
        for(int i = 0;i<inputPath.length;i++)
            this.path[i] = inputPath[i];
        this.path[this.path.length-1] = start;
    }

    public void SingleCoreFindPath(int start,int end,int length,int[]inputPath){
        if(start == end)
        {
            //System.out.println("!"+ length);
            if(length<Length) {
                Length = length;
                this.path = inputPath;
            }
        }
        for(int i = 0; i<ShortestPathTask.InputMatrix[start].length;i++)
        {
            //System.out.println("$"+ length);
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
            SingleCoreFindPath(i,end,length+ShortestPathTask.InputMatrix[start][i],newPath);
        }

    }

    @Override
    protected Void compute() {
        if(start == end)
        {
            return null;
        }
        ShortestPathTask[] tasks = new ShortestPathTask[ShortestPathTask.InputMatrix[start].length];
       for(int i = 0; i<ShortestPathTask.InputMatrix[start].length;i++)
       {
           tasks[i] = null;
           if(ShortestPathTask.InputMatrix[start][i]<=0)
           {
               continue;
           }
           boolean isCont = false;
           for(int j = 0;j<path.length;j++)
           {
               if(path[j] == i)
               {
                   isCont = true;
               }
           }
           if(isCont)
               continue;
           ShortestPathTask task = new ShortestPathTask(i,end,Length+ShortestPathTask.InputMatrix[start][i],path.clone());
           tasks[i] = task;
           tasks[i].fork();
       }
        this.Length = Integer.MAX_VALUE;
       for(int i = 0;i<tasks.length;i++)
       {
           if(tasks[i]!= null)
           {
               tasks[i].join();
                   if (tasks[i].Length < this.Length) {
                       this.path = tasks[i].path;
                       this.Length = tasks[i].Length;
                   }

           }
       }
           return null;
    }
}

