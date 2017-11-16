import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * 最短进程优先算法.
 * 实际中每个进程的服务时间只能通过以往的统计估算.
 *
 * Created by status200 on 2017/11/15.
 */
public class SPN {

    public static void main(String[] args) {

        // 按照到达时间排序
        Arrays.sort(Process.processes, Comparator.comparingInt(e -> e.arriveTimePoint));

        // 未执行完毕的进程列表
        LinkedList<Process> unfinishedProcessList = new LinkedList<>();
        unfinishedProcessList.addAll(Arrays.asList(Process.processes));

        // 当前时刻
        // 直接跳到第一个到达的进程
        int timePoint = unfinishedProcessList.getFirst().arriveTimePoint;

        // 还有进程未执行完毕时
        while(!unfinishedProcessList.isEmpty()) {

            Process shortestProcess = null;
            // 选取最短的进程
            for(Process process : unfinishedProcessList) {
                // 该进程在当前时间点已经到达
                if(timePoint >= process.arriveTimePoint) {
                    // 此处选择遇到的第一个进程
                    shortestProcess = shortestProcess == null?process : shortestProcess;

                    // 比较
                    if(shortestProcess.serveTime > process.serveTime) {
                        shortestProcess = process;
                    }
                }
            }

            // 当前时间点有最短进程则执行
            if(shortestProcess != null) {
                timePoint += shortestProcess.serveTime;
                shortestProcess.statistic(timePoint);
            }
            // 当前时间点没有未执行结束的进程
            // 跳到下一个进程的执行时刻
            else {
                timePoint = unfinishedProcessList.getFirst().arriveTimePoint;
            }

            // 移除掉该进程
            unfinishedProcessList.remove(shortestProcess);
        }

        Process.printResult();
    }
}
