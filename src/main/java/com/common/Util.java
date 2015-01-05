package com.common;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class Util {

	public static String getProjectPath() {
		String path = System.getProperty("user.dir");
		int index = path.lastIndexOf("/");

		if (index == -1) {
			index = path.lastIndexOf("\\");
		}

		path = path.substring(0, index);
		return path;
	}

	/**
	 * 初始化运行环境
	 */
	public static void initProjectEnviroment() {
		// 配置环境路径
		Constant.PRE_PROJECT_PATH = Util.getProjectPath();

		// 配置log路径
		try {
			Logger logger = Logger.getLogger(Constant.LOG_NAME);
			LoggerUtil.setLogingProperties(logger);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Constant.ANDROID_PHONEGAP_FOLDER = Constant.PRE_PROJECT_PATH + Constant.ANDROID_PHONEGAP_FOLDER;
		Constant.CONFIG_XML = Constant.PRE_PROJECT_PATH + Constant.CONFIG_XML;
		Constant.XML_FOLDER = Constant.PRE_PROJECT_PATH + Constant.XML_FOLDER;
		Constant.FRAMEWORK_FOLDER = Constant.PRE_PROJECT_PATH + Constant.FRAMEWORK_FOLDER;
		Constant.RESOURCE_FOLDER = Constant.PRE_PROJECT_PATH + Constant.RESOURCE_FOLDER;
	}
}
