package com.mobapphome.mahads.tools;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.XmlDeclaration;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.mobapphome.mahads.types.Program;

import android.text.Html;
import android.util.Log;

public class HttpTools {

	static public List<Program> requestPrograms(String url)
			throws IOException {

		List<Program> ret = new LinkedList<>();

		Document doc = Jsoup
				.connect(url.trim())
				.timeout(3000)
				// .header("Host", "85.132.44.28")
				.header("Connection", "keep-alive")
				// .header("Content-Length", "111")
				.header("Cache-Control", "max-age=0")
				.header("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
				// .header("Origin", "http://85.132.44.28")
				.header("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36")
				.header("Content-Type", "application/x-www-form-urlencoded")
				.header("Referer", url.trim())
				// This is Needed
				.header("Accept-Encoding", "gzip,deflate,sdch")
				.header("Accept-Language", "en-US,en;q=0.8,ru;q=0.6")
				// .userAgent("Mozilla")
				.get();

		 String jsonStr = doc.body().text();
		 //Log.i("Test", jsonStr);
		 
		 try{
			 JSONObject reader = new JSONObject(jsonStr);
			 JSONArray programs = reader.getJSONArray("programs");
			// Log.i("Test", "Programs size = " + programs.length());
			 for(int i = 0; i <programs.length(); ++i){
				 try{
					 JSONObject jsonProgram = programs.getJSONObject(i);
					 String name = jsonProgram.getString("name");
					 String desc = jsonProgram.getString("desc");
					 String uri = jsonProgram.getString("uri");
					 String img = jsonProgram.getString("img");
					 String releaseDate = jsonProgram.getString("release_date");
					 ret.add(new Program(0, name, desc, uri, img, releaseDate));
					 //Log.i("Test", "Added = " + name);
				 }catch (JSONException e) {
					 Log.i("Test",e.toString());
				}
			 }
			 
		 }catch(JSONException e){
			 Log.i("Test",e.toString());
		 }

		return ret;

	}
	
	static public int requestProgramsVersion(String url)
			throws IOException {

		int ret = 0;

		Document doc = Jsoup
				.connect(url.trim())
				.timeout(3000)
				// .header("Host", "85.132.44.28")
				.header("Connection", "keep-alive")
				// .header("Content-Length", "111")
				.header("Cache-Control", "max-age=0")
				.header("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
				// .header("Origin", "http://85.132.44.28")
				.header("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36")
				.header("Content-Type", "application/x-www-form-urlencoded")
				.header("Referer", url.trim())
				// This is Needed
				.header("Accept-Encoding", "gzip,deflate,sdch")
				.header("Accept-Language", "en-US,en;q=0.8,ru;q=0.6")
				// .userAgent("Mozilla")
				.get();

		
		 String jsonStr = doc.body().text();
		 //Log.i("Test", jsonStr);
		 
		 try{
			 JSONObject reader = new JSONObject(jsonStr);
			 ret = Integer.parseInt(reader.getString("version"));
		 }catch(JSONException e){
			 Log.i("Test",e.toString());
		 }catch (NumberFormatException nfe) {
			 Log.i("Test",nfe.toString());
		}

		return ret;

	}

}
