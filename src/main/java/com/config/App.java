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
    	
    	String frameworkName = "framework1";
    	//选择第一个框架FrameWork1
    	String sourcePath = Constant.FRAMEWORK_FOLDER + "/" + frameworkName;

    	String newPath = Constant.ANDROID_PHONEGAP_FOLDER;
    	String inputPath = Constant.XML_FOLDER;
    	String resourcePath = Constant.RESOURCE_FOLDER;
    	
    	//初始化框架环境
    	FileUtil.delFolder(newPath);
//    	FileUtil.delFolder(inputPath);
    	FileUtil.newFolder(newPath);
    	FileUtil.newFolder(inputPath);
    	
    	ConfigXmlParser parser = new ConfigXmlParser();
    	parser.initConfig(frameworkName);

    	
    	FileUtil.copyFolder(sourcePath, newPath);
    	FileUtil.copyFolder(resourcePath, newPath);
    	
    	//解析框架配置文件
    	    }
}
