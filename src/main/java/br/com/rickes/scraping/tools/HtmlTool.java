package br.com.rickes.scraping.tools;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlTool {
    
	private final String PATTERN_TAG_URL = "(<\\s*a class=\"js-navigation-open link-gray-dark\"[^>]*>(.*?))";
	private final String PATTERN_URL = "([^\\s=]+)=(?:\"([^\"\\\\]*(?:\\\\.[^\"\\\\]*)*)\"|(\\S+))";
	private final String PATTERN_NAME_FILE = "(?si)<strong class=\"final-path[^>]+?>(.*?)<\\/strong>";
	private final String PATTERN_EXT_FILE = "(?<=\\.)[^.]+$";
	private final String PATTERN_SIZE_LINE = "(?si)<div class=\"text-mono f6 flex-auto pr-3 flex-order-2 flex-md-order-1 mt-2 mt-md-0[^>]+?>(.*?)<\\/div>";
	private final String PATTERN_NUMBER = "[1-9][.0-9]* ";
	private final String LINES = "lines";
	private final String BYTES = "Bytes";
	private final String KB = "KB";
	private final String MB = "MB";
	
	public String getBodyPage(String url) throws IOException, InterruptedException {
	        
		HttpClient client = HttpClient.newHttpClient();

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		return response.body();
	}
	
	public List<String> getUrlPage(String html){
		List<String> matchUrl = new ArrayList<>();
		
		Pattern p1 = Pattern.compile(PATTERN_TAG_URL);
        Matcher m1 = p1.matcher(html);	        
		
        while(m1.find()) {
        	
        	//Links
	        Pattern p = Pattern.compile(PATTERN_URL);
	        Matcher m = p.matcher(m1.group(0));
        	
	        while(m.find()) {
	            
	        	if(m.group(1).equals("href")) {
		        	matchUrl.add(m.group(2));
	            }
	        }	
        	
        }
        
        return matchUrl;
	}

	
	public String linesFile(String html) {
		String countLines = "";
		
		Pattern p = Pattern.compile(PATTERN_SIZE_LINE);
        Matcher m = p.matcher(html);
        
        while(m.find()) {
        	
        	Pattern p1 = Pattern.compile(PATTERN_NUMBER + LINES);
	        Matcher m1 = p1.matcher(m.group(1));
	        
	        while(m1.find()) {
	        	countLines = m1.group(0);
	        }
        }
        
                     
        return countLines.replaceAll("[^0-9]", "") == "" ? "0" : countLines;

	}
	
	public String sizeFile(String html) {
		String size = "";
		String tag = "";
		
		Pattern p = Pattern.compile(PATTERN_SIZE_LINE);
		Pattern pBytes = Pattern.compile(PATTERN_NUMBER + BYTES);
        Pattern pKB = Pattern.compile(PATTERN_NUMBER + KB);
        Pattern pMB = Pattern.compile(PATTERN_NUMBER + MB);
        
        Matcher m = p.matcher(html);
        
        while(m.find()) {
        	tag = m.group(1);
        }
        
        Matcher mBytes = pBytes.matcher(tag);
        Matcher mKB = pKB.matcher(tag);
   	 	Matcher mMB = pMB.matcher(tag);
        
        while(mBytes.find()) {
            	size = mBytes.group(0); 
        }
        while(mKB.find()) {
            	size = mKB.group(0); 
        }
        
        while(mMB.find()) {
            	size = mMB.group(0); 
        }
        
        return size;
	}
	
	
	public String getNameFile (String html) {
		String file = "";
		
		Pattern p = Pattern.compile(PATTERN_NAME_FILE);
        Matcher m = p.matcher(html);
        
        while(m.find()) {
        	file = m.group(1);
        }
        
        return file;
        
	}
	
	
	public String getExtensionFile(String file) {
		String extension = "";
		Matcher m = Pattern.compile(PATTERN_EXT_FILE).matcher(file);
		
        while(m.find()) {
        	extension = m.group(0);
        }
		
        return extension;
		
	}





}