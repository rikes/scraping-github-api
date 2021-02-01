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

import org.springframework.stereotype.Service;

/**
 * Class responsible for implementing the necessary resources to examine the GitHub page
 * 
 * @author Henrique
 */
@Service
public class HtmlToolGitHub implements IHtmlTools{
	
	private final String PATTERN_TAG_URL = "(<\\s*a class=\"js-navigation-open link-gray-dark\"[^>]*>(.*?))";
	
	private final String PATTERN_URL = "([^\\s=]+)=(?:\"([^\"\\\\]*(?:\\\\.[^\"\\\\]*)*)\"|(\\S+))";
	
	private final String PATTERN_NAME_FILE = "[^\\/]+$";
	private final String PATTERN_EXT_FILE = "(?<=\\.)[^.]+$";
	private final String PATTERN_SIZE_LINE = "(?si)<div class=\"text-mono f6 flex-auto pr-3 flex-order-2 flex-md-order-1 mt-2 mt-md-0[^>]+?>(.*?)<\\/div>";
	private final String PATTERN_NUMBER = "[1-9][.0-9]* ";
	private final String LINES = "lines";
	private final String BYTES = "Bytes";
	private final String KB = "KB";
	private final String MB = "MB";
	HttpClient client;
	List<String> matchUrl;

	public HtmlToolGitHub() {
		this.client = HttpClient.newHttpClient();
	}

	/**
	 * Search the body of a link
	 * 
	 * @param url Search link
	 * @return HTML Body
	 * 
	 */
	public String getBodyPage(String url) throws IOException, InterruptedException {
		
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			
		return response.body();
		
	}
	/**
	 * Search all links on the page using pre-defined patterns with regex
	 * 
	 * @param html HTML Body
	 * @return List of links found
	 * 
	 */
	public List<String> getUrlPage(String html){
		matchUrl = new ArrayList<>();
		
		Pattern p1 = Pattern.compile(PATTERN_TAG_URL);
        Matcher m1 = p1.matcher(html);	        
		
        while(m1.find()) {
        	
        	//Links
	        Pattern p = Pattern.compile(PATTERN_URL);
	        Matcher m = p.matcher(m1.group(0));
        	
	        while(m.find()) {
	            //Find the href tag, to get the link
	        	if(m.group(1).equals("href")) {
		        	matchUrl.add(m.group(2));
	            }
	        }	
        	
        }
        
        return matchUrl;
	}

	/**
	 * Search numbers near 'Lines'
	 * 
	 * @param html Page Body
	 * @Return Number of lines
	 */
	public String getLinesFile(String html) {
		StringBuilder countLines = new StringBuilder();
		
		Pattern p = Pattern.compile(PATTERN_SIZE_LINE);
        Matcher m = p.matcher(html);
        
        while(m.find()) {
        	
        	Pattern p1 = Pattern.compile(PATTERN_NUMBER + LINES);
	        Matcher m1 = p1.matcher(m.group(1));
	        
	        while(m1.find()) {
	        	countLines.append(m1.group(0));
	        }
        }
        
           
        return countLines.toString().replaceAll("[^0-9]", "").equals("") ? "0" : countLines.toString();

	}
	
	/**
	 * Search the file size, using keywords of size (bytes, KB, MB)
	 * 
	 * @param html Page Body
	 * @Return File size
	 */
	public String getSizeFile(String html) {
		StringBuilder size = new StringBuilder();
		StringBuilder tag = new StringBuilder();
		
		Pattern p = Pattern.compile(PATTERN_SIZE_LINE);
		Pattern pBytes = Pattern.compile(PATTERN_NUMBER + BYTES);
        Pattern pKB = Pattern.compile(PATTERN_NUMBER + KB);
        Pattern pMB = Pattern.compile(PATTERN_NUMBER + MB);
        
        Matcher m = p.matcher(html);
        
        while(m.find()) {
        	tag.append(m.group(1));
        }
        
        Matcher mBytes = pBytes.matcher(tag);
        Matcher mKB = pKB.matcher(tag);
   	 	Matcher mMB = pMB.matcher(tag);
        
        while(mBytes.find()) {
            	size.append(mBytes.group(0)); 
        }
        while(mKB.find()) {
        	size.append(mKB.group(0)); 
        }
        
        while(mMB.find()) {
        	size.append(mMB.group(0)); 
        }
        
        return size.toString();
	}
	
	
	/**
	 * Search for the file name using the URL
	 * 
	 * @param url Page URL
	 * @Return File name
	 */
	public String getNameFile (String url) {
		StringBuilder file = new StringBuilder();
		
		Pattern p = Pattern.compile(PATTERN_NAME_FILE);
        Matcher m = p.matcher(url);
        
        while(m.find()) {
        	file.append(m.group(0));
        }
        
        return file.toString();
        
	}

	/**
	 * Search the file's exterior using its name
	 * 
	 * @param file File name
	 * @Return File extension
	 */
	public String getExtensionFile(String file) {
		StringBuilder extension = new StringBuilder();
		
		Matcher m = Pattern.compile(PATTERN_EXT_FILE).matcher(file);
		
        while(m.find()) {
        	extension.append(m.group(0));
        }
		
        return extension.toString();
		
	}

}