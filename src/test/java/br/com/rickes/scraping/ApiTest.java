package br.com.rickes.scraping;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.rickes.scraping.model.Archive;
import br.com.rickes.scraping.service.ScrapingGitHubService;

@SpringBootTest
class ApiTest {
    
	@Autowired
    ScrapingGitHubService scrapingService;

	@Test
	void check200Code() {
		String urlGitHub = "https://github.com/rikes/template-spring-boot";
		//String urlGitHub = "22222";
        Map<String, List<Archive>> mapFiles = scrapingService.getAllArchives(urlGitHub);
        
        assertTrue(mapFiles != null && !mapFiles.isEmpty());
	}

}