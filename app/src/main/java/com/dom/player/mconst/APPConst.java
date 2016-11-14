package com.dom.player.mconst;

/**
 * Created by chendom on 16-11-8.
 */

public class APPConst {
    public static class PlayerMSG {
        public static final int PLAYMSG = 11;
        public static final int PLAYPAUSE = 10;
        public static final int PLAYESTOP = 00;
        public static final int PLAYCONTINUEMSG =111;
        public static final int PROGRESSCHANGE= 110;
        public static final int PLAYINGMSG=100;


        public static final String UPDATE_ACTION = "com.dom.action.UPDATE_ACTION";
        public static final String CTL_ACTION = "com.dom.action.CTL_ACTION";
        public static final String MUSIC_CURRENT = "com.dom.action.MUSIC_CURRENT";
        public static final String MUSIC_DURATION = "com.dom.action.MUSIC_DURATION";
    }
    public static class AppApi{
        public static final String APIKEY="f9a6dc0392b9c598afcf80c60048f8ef";
        public static final String path="http://api.tianapi.com/huabian/?key="+APIKEY+"&num=10";
    }
}