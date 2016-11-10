package com.dom.player.chacheutils;


public class FileManager {
	/**
	 * the path of chache file
	 * @return
     */
	public static String getSaveFilePath() {
		if (CommonUtil.hasSDCard()) {
			return CommonUtil.getRootFilePath() + "com.player.dev/tmps/";
		} else {
			return CommonUtil.getRootFilePath() + "com.player.dev/tmps";
		}
	}
}
