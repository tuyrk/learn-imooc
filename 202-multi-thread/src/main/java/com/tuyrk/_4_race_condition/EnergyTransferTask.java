package com.tuyrk._4_race_condition;

/**
 * 能量转移任务
 */
public class EnergyTransferTask implements Runnable {

    // 共享的能量世界
    private EnergySystem energySystem;
    // 能量转移的源能量盒子下标
    private int fromBox;
    // 单次能量转移最大单元
    private double maxAmount;
    // 最大休眠时间（毫秒）
    private static final int DELAY = 10;

    /**
     * 能量转移构造方法
     *
     * @param energySystem 能量系统
     * @param from         被转移能量的盒子
     * @param max          转移的最大能量值
     */
    public EnergyTransferTask(EnergySystem energySystem, int from, double max) {
        this.energySystem = energySystem;
        this.fromBox = from;
        this.maxAmount = max;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // 转移到能量的盒子
                int toBox = (int) (energySystem.getBoxAmount() * Math.random());
                // 转移的能量值
                double amount = maxAmount * Math.random();
                energySystem.transfer(fromBox, toBox, amount);
                Thread.sleep((int) (DELAY * Math.random()));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
