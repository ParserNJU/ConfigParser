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
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.common.Constant;
import com.common.FileUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

public class ConfigXmlParser {

	private String configPath = Constant.CONFIG_XML;
	private String frameworkName;

	public void initConfig(String name) {
		this.frameworkName = name;
		List<String> result = this.parseJson(Constant.FRAMEWORK_FOLDER + "/"
				+ name + "/frame/json/config.json");
		for (int i = 0; i < result.size(); i++) {
			this.createXMLFileWithName(result.get(i));
		}

		// framework1解析
		// configPath += "/" + name + ".xml";
		// String xmlStr = this.readFile();
		// String jsonStr = this.xmltoJson(xmlStr);
		// this.writeJsonToFile(jsonStr);
		// this.createXMLFile();
	}

	/**
	 * 解析Json
	 * 
	 * @param filePath
	 */
	public List<String> parseJson(String filePath) {
		List<String> result = new ArrayList<String>();
		String jsonStr = "";

		File file = new File(filePath);
		FileReader fr;
		try {
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line = "";
			while ((line = br.readLine()) != null) {
				jsonStr += line;
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		JSONObject jsonObj = JSONObject.fromObject(jsonStr);
		JSONArray jsonArr = jsonObj.getJSONArray("pages");
		for (int i = 0, size = jsonArr.size(); i < size; i++) {
			JSONObject item = jsonArr.getJSONObject(i);

			if (item.containsKey("content") && item.containsKey("name")) {
				JSONObject contentObj = item.getJSONObject("content");
				if (contentObj.containsKey("type")) {
					if ("other".equals(contentObj.getString("type"))) {
						String pageId = item.getString("name");
						result.add(pageId);
					}
				}
			}

		}
		return result;

	}

	/**
	 * 读取框架的xml配置文件
	 * 
	 * @return
	 */
	@SuppressWarnings("resource")
	private String readFile() {
		BufferedReader br;
		String xml = "";
		String data;
		try {
			br = new BufferedReader(new FileReader(configPath));
			data = br.readLine();
			while (data != null) {
				xml += data;
				data = br.readLine(); // 接着读下一行
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xml;
	}

	private String xmltoJson(String xml) {

		XMLSerializer xmlSerializer = new XMLSerializer();
		String jsonStr = xmlSerializer.read(xml).toString();
		return jsonStr;
	}

	/**
	 * 把解析到的json文件写到config.json中，用于框架去读写
	 * 
	 * @param str
	 */
	private void writeJsonToFile(String str) {
		File file = new File(Constant.FRAMEWORK_FOLDER + "/" + frameworkName
				+ "/frame/json/config.json");
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

	/**
	 * 查询href中的界面文件，每个界面文件创建一个xml文件<包括：page datasource>
	 */
	private void createXMLFile() {
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

				if (type != XmlPullParser.START_TAG
						|| !xpp.getName().equals("href")) {
					continue;
				}
				String fileName = xpp.nextText();
				fileName = fileName.substring(0, fileName.indexOf("."))
						+ ".xml";
				String fileInitStr = "<MPage id=\"" + fileName + "\">\r\n"
						+ "</MPage>";

				FileUtil.newFile(Constant.XML_FOLDER + "/" + fileName,
						fileInitStr);

			}
			String dataSourceStr = "<MDataSrc>\r\n" + "<MDataItem>\r\n"
					+ "</MDataItem>\r\n" + "</MDataSrc>";
			FileUtil.newFile(Constant.XML_FOLDER + "/data_source.xml",
					dataSourceStr);

		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createXMLFileWithName(String fileName) {
		String fileInitStr = "<MPage id=\"" + fileName + "\">\r\n" + "</MPage>";

		FileUtil.newFile(Constant.XML_FOLDER + "/" + fileName+".xml", fileInitStr);
		String dataSourceStr = "<MDataSrc>\r\n" + "<MDataItem>\r\n"
				+ "</MDataItem>\r\n" + "</MDataSrc>";
		FileUtil.newFile(Constant.XML_FOLDER + "/data_source.xml",
				dataSourceStr);
	}
}
