import java.util.Arrays;
import java.util.Comparator;

/**
 * 最短剩余时间算法
 *
 * Created by status200 on 2017/11/15.
 */
public class SRT {

    // 查找剩余时间最短的进程
    public static Process findSRTProcess(int timePoint) {

        Process SRTProcess = null;

        for (int i = 0; i < Process.processes.length; i++) {
            Process p = Process.processes[i];
            // 进程已经到达且还没结束
            if (p.leftServeTime > 0 && p.arriveTimePoint <= timePoint) {
                SRTProcess = SRTProcess == null ? p : SRTProcess;

                if (SRTProcess.leftServeTime > p.leftServeTime) {
                    SRTProcess = p;
                }
            }
        }

        return SRTProcess;
    }

    public static void main(String[] args) {

        // 按照到达时间排序
        Arrays.sort(Process.processes, Comparator.comparingInt(e -> e.arriveTimePoint));

        // 当前时间点
        int timePoint = Process.processes[0].arriveTimePoint;

        // 当前执行的进程
        Process currentProcess = Process.processes[0];

        // 进程未全部到达
        for (int i = 1;i< Process.processes.length - 1;i++) {

            int nextArriveTimePoint = timePoint + Process.processes[i+1].arriveTimePoint;

            // 下一个进程还未到达时
            do {
                timePoint++;

                // 当前还有可以执行的进程
                if(currentProcess!=  null) {

                    currentProcess.leftServeTime--;

                    // 进程执行完毕
                    if (currentProcess.leftServeTime <= 0) {
                        currentProcess.statistic(timePoint);

                        // 寻找当前可以执行的进程中剩余时间最短的来执行
                        currentProcess = findSRTProcess(timePoint);
                    }
                }

            } while (timePoint < nextArriveTimePoint);

            // 下一个进程已经到达，切换到剩余时间最短的进程
            currentProcess = findSRTProcess(timePoint);
        }

        // 进程已经全部到达
        // 选取剩余时间最短的挨个执行完
        while (currentProcess != null) {
            timePoint += currentProcess.leftServeTime;
            currentProcess.leftServeTime = 0;
            currentProcess.statistic(timePoint);

            currentProcess = findSRTProcess(timePoint);
        }


        Process.printResult();
    }
}
