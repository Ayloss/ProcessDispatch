import java.util.*;

/**
 * 先到先服务算法
 *
 * Created by status200 on 2017/11/15.
 */
public class FCFS {

    public static void main(String[] args) {

        // 对进程的到达时间排序
        Arrays.sort(Process.processes, Comparator.comparingInt(e -> e.arriveTimePoint));

        // 队列模拟程序调度
        LinkedList<Process> queue = new LinkedList<>();

        // 数组转换为队列
        queue.addAll(Arrays.asList(Process.processes));

        // 总时间
        int timePoint = 0;

        while(!queue.isEmpty()) {
            // 出队
            Process process = queue.poll();

            // 进程未到达
            if(timePoint < process.arriveTimePoint) {
                timePoint = process.arriveTimePoint;
            }

            // 执行完该进程的时间
            timePoint += process.serveTime;
            // 统计
            process.statistic(timePoint);
        }

        Process.printResult();

    }
}
