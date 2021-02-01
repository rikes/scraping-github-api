package br.com.rickes.scraping.tools;

import java.io.IOException;
import java.util.List;

public interface IHtmlTools {
	
	String getBodyPage(String url) throws IOException, InterruptedException;
	
	List<String> getUrlPage(String html);
	
	String getLinesFile(String html);
	
	String getSizeFile(String html);
	
	String getNameFile (String html);
	
	String getExtensionFile(String file);
	
}
