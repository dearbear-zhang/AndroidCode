package com.mine.dearbear.interfaces;

public interface WifiRetriealInter {
    interface View {

    }

    interface Presenter {

    }

    interface Mode {
        void start();

        void msgGet(String msg);

        void bkGet(String msg);

        void finish();
    }
}
