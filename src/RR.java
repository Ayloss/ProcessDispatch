import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * 轮转算法
 *
 * Created by status200 on 2017/11/15.
 */
public class RR {

    // 时间片为1
    public static int slice = 1;

    public static void main(String[] args) {

        // 对进程的到达时间排序
        Arrays.sort(Process.processes, Comparator.comparingInt(e -> e.arriveTimePoint));

        // 队列模拟程序调度
        LinkedList<Process> queue = new LinkedList<>();

        // 未到达的进程序号
        int arriveProcessIndex = 0;

        // 直接跳到第一个到达的进程
        int timePoint = Process.processes[0].arriveTimePoint;
        queue.add(Process.processes[0]);

        arriveProcessIndex++;

        while (true) {

            Process process = null;
            // 队列非空
            if (!queue.isEmpty()) {
                // 队列第一个进程出队,即先到先服务
                process = queue.poll();


            }

            // 时间片内计数
            // 轮转算法发生中断会有两个地方
            // 1.分配给一个进程的时间片结束,进程仍未执行完毕。此时该进程重新加入队列尾部。
            // 2.分配给一个进程的时间片内(结束或者没有结束)，进程已经执行完毕。此时重新开始新一轮的时间片计时，并挑选队首进程执行。
            int sliceCount = 0;

            while (sliceCount < slice) {
                sliceCount++;
                // 时刻增加一个时间片
                timePoint++;

                // 进程执行单位时间
                if (process != null) {

                    process.leftServeTime--;

                    if (process.leftServeTime <= 0) {
                        // 进程在时间片内执行完毕
                        process.statistic(timePoint);
                        process = queue.poll();

                        // 重新开始一个时间片的计时
                        sliceCount = 0;
                    }

                }

                // 判断该时刻有无进程到达
                if (arriveProcessIndex < Process.processes.length
                        && timePoint >= Process.processes[arriveProcessIndex].arriveTimePoint) {
                    queue.add(Process.processes[arriveProcessIndex]);
                    arriveProcessIndex++;
                }
            }


            // 经过一个时间片进程未结束,再入队
            if (process != null && process.leftServeTime > 0) {
                queue.add(process);
            }

            // 所有进程都已经到达且队列为空
            if (arriveProcessIndex >= Process.processes.length && queue.isEmpty()) {
                break;
            }

        }

        Process.printResult();
    }
}
