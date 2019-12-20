package com.tuyrk.polymerize;

/**
 * 笔记本电脑
 *
 * @author tuyrk
 */
public class NoteBook {
    /**
     * 期望使用三相插座进行充电
     */
    private ThreePlug threePlug;

    public NoteBook(ThreePlug threePlug) {
        this.threePlug = threePlug;
    }

    /**
     * 使用插座进行充电
     */
    public void charge() {
        threePlug.powerWithThree();
    }

    public static void main(String[] args) {
        GbTwoPlug twoPlug = new GbTwoPlug();
        // 使用二相转三相的适配器
        ThreePlug threePlug = new TwoPlugAdapter(twoPlug);
        // 使用三相插座进行充电
        NoteBook noteBook = new NoteBook(threePlug);
        noteBook.charge();
    }
}
