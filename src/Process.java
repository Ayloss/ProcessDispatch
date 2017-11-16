/**
 * 进程类
 *
 * Created by status200 on 2017/11/15.
 */
public class Process {

    // 所有进程
    public static Process[] processes = {
            new Process(0,3),
            new Process(2,6),
            new Process(4,4),
            new Process(6,5),
            new Process(8,2)
    };

    // 到达时刻
    public int arriveTimePoint = -1;

    // 服务时间
    public int serveTime = -1;

    // 剩余服务时间
    public int leftServeTime = -1;

    // 结束时刻
    public int finishedTimePoint = -1;

    // 周转时间
    public int turnoverTime = -1;

    // 响应比
    public double responseRate = 0;

    // 等待时间
    public int waitTime = 0;

    public Process(int arriveTimePoint, int serveTime) {
        this.arriveTimePoint = arriveTimePoint;
        this.serveTime = serveTime;
        this.leftServeTime = serveTime;
    }

    // 设置结束时刻,根据结束时刻计算周转时间和响应时间比
    public void statistic(int finishedTimePoint) {
        // 结束时刻
        this.finishedTimePoint = finishedTimePoint;
        // 周转时间
        this.turnoverTime = this.finishedTimePoint - this.arriveTimePoint;
        // 响应时间比
        this.responseRate = (0.0 + this.turnoverTime) / this.serveTime;
    }

    // 输出结果
    public static void printResult() {
        System.out.println("  到达时间   完成时间  服务时间   周转时间     响应比");
        for(Process process : processes) {
            System.out.println(String.format("%10d%10d%10d%10d%10.2f",
                    process.arriveTimePoint,
                    process.finishedTimePoint,
                    process.serveTime,
                    process.turnoverTime,
                    process.responseRate));
        }
    }
}
