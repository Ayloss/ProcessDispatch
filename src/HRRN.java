import java.util.Arrays;
import java.util.Comparator;

/**
 * 最高响应比优先算法
 * <p>
 * Created by status200 on 2017/11/16.
 */
public class HRRN {

    // 查找剩余时间最短的进程
    public static Process findHRRNProcess(int timePoint) {

        Process HRRNProcess = null;

        for (int i = 0; i < Process.processes.length; i++) {
            Process p = Process.processes[i];
            // 进程已经到达且还没结束
            if (p.leftServeTime > 0 && p.arriveTimePoint <= timePoint) {
                HRRNProcess = HRRNProcess == null ? p : HRRNProcess;

                // 计算响应比
                int R1 = (HRRNProcess.waitTime + HRRNProcess.serveTime) / HRRNProcess.serveTime;
                int R2 = (p.waitTime + p.serveTime) / p.serveTime;
                if (R1 < R2) {
                    HRRNProcess = p;
                }
            }
        }

        return HRRNProcess;
    }

    public static void main(String[] args) {
        // 按照到达时间排序
        Arrays.sort(Process.processes, Comparator.comparingInt(e -> e.arriveTimePoint));

        // 当前时间点
        int timePoint = Process.processes[0].arriveTimePoint;

        // 当前执行的进程
        Process currentProcess = Process.processes[0];

        do {
            timePoint++;

            // 执行当前进程
            currentProcess.leftServeTime--;

            // 增加所有进程的等待时间
            for(Process process:Process.processes) {
                // 进程已经到达且未结束
                if(process.arriveTimePoint <= timePoint && process.leftServeTime > 0) {
                    process.waitTime++;
                }
            }

            // 当前进程执行完毕
            if (currentProcess.leftServeTime <= 0) {
                currentProcess.statistic(timePoint);

                // 寻找当前可以执行的进程中响应比最高的来执行
                currentProcess = findHRRNProcess(timePoint);

                Process.printResult();
            }

        } while (currentProcess != null);


        Process.printResult();
    }
}
