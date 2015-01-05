package com.config;

import com.common.Constant;
import com.common.FileUtil;
import com.common.Util;
import com.config.xmlParser.ConfigXmlParser;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	//初始化项目环境
    	Util.initProjectEnviroment();
    	
    	//选择第一个框架FrameWork1
    	String sourcePath = Constant.PRE_PROJECT_PATH + Constant.FRAMEWORK_FOLDER + "/FrameWork1";
    	String newPath = Constant.ANDROID_PHONEGAP_FOLDER;
    	String inputPath = Constant.PRE_PROJECT_PATH + Constant.XML_FOLDER;
    	String resourcePath = Constant.PRE_PROJECT_PATH + Constant.RESOURCE_FOLDER;
    	
    	//初始化框架环境
//    	FileUtil.delFolder(newPath);
    	FileUtil.delFolder(inputPath);
    	FileUtil.newFolder(inputPath);
    	FileUtil.copyFolder(sourcePath, newPath);
    	FileUtil.copyFolder(resourcePath, newPath);
    	
    	//解析框架配置文件
    	ConfigXmlParser parser = new ConfigXmlParser();
    	parser.initConfig();
    }
}
