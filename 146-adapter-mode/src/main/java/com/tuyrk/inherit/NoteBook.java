package com.tuyrk.inherit;

import com.tuyrk.polymerize.ThreePlug;

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
        ThreePlug threePlug = new TwoPlugAdapterExtends();
        NoteBook noteBook = new NoteBook(threePlug);
        noteBook.charge();
    }
}
