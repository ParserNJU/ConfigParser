package com.config.xmlParser;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.common.Constant;
import com.common.FileUtil;

import net.sf.json.xml.XMLSerializer;


public class ConfigXmlParser {
	
	private String configPath = Constant.ANDROID_PHONEGAP_FOLDER + Constant.CONFIG_XML;

	public void initConfig()
	{
		String xmlStr = this.readFile();
		String jsonStr = this.xmltoJson(xmlStr);
		this.writeJsonToFile(jsonStr);
		this.createXMLFile();
	}
	
	@SuppressWarnings("resource")
	private String readFile()
	{
		BufferedReader br;
		String xml = "";
		String data;
		try {
			br = new BufferedReader(new FileReader(configPath));
			data = br.readLine();
			while( data!=null){
				xml += data;
			    data = br.readLine(); //接着读下一行  
			}  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return xml;
	}
	
	private String xmltoJson(String xml) {  
				
        XMLSerializer xmlSerializer = new XMLSerializer();  
        String jsonStr =  xmlSerializer.read(xml).toString();  
        return jsonStr;
    }  
	
	private void writeJsonToFile(String str)
	{
		File file = new File(Constant.ANDROID_PHONEGAP_FOLDER+ "config.json");
		try {
			
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(str);
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private void createXMLFile()
	{
		XmlPullParser xpp = null;
		FileInputStream fis = null;
		InputStream is = null;
		
		try {
			fis = new FileInputStream(new File(configPath));
			is = new BufferedInputStream(fis);
			
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			xpp = factory.newPullParser();
			xpp.setInput(is, "utf-8");
			
			final int depth = xpp.getDepth();
			int type;
			while (((type = xpp.next()) != XmlPullParser.END_TAG || xpp
					.getDepth() > depth) && type != XmlPullParser.END_DOCUMENT) {

				if (type != XmlPullParser.START_TAG || !xpp.getName().equals("href")) {
					continue;
				}
				String fileName = xpp.nextText();
				fileName = fileName.substring(0,fileName.indexOf(".")) + ".xml";
				String fileInitStr = "<MPage id=\"" + fileName + "\">\r\n"+
									 "</MPage>";
				
				FileUtil.newFile(Constant.PRE_PROJECT_PATH + Constant.XML_FOLDER + "/" + fileName, fileInitStr);
				
			}
			String dataSourceStr = "<MDataSrc>\r\n" +
										"<MDataItem>\r\n" +
										"</MDataItem>\r\n" +
								   "</MDataSrc>";
			FileUtil.newFile(Constant.PRE_PROJECT_PATH + Constant.XML_FOLDER + "/data_source.xml", dataSourceStr);
			
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
