package br.com.rickes.scraping.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.rickes.scraping.model.Archive;
import br.com.rickes.scraping.service.ScrapingGitHubService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api")
@Api(value = "API Scraping in GitHub")
public class ScrapingGitHubController {

    @Autowired
    ScrapingGitHubService scrapingService;

    @GetMapping(value="/files")
    @ApiOperation(value = "get description files from GitHub")
    public ResponseEntity<Map<String, List<Archive>>> getFilesGitHub(@RequestParam(value = "url", required = true) String urlGitHub) {
    	
    	Map<String, List<Archive>> mapFiles = scrapingService.getAllArchives(urlGitHub);
        
        if (mapFiles != null && !mapFiles.isEmpty()) {
        	return new ResponseEntity<Map<String,List<Archive>>>(mapFiles, HttpStatus.OK) ;
        }
        
        return new ResponseEntity<Map<String,List<Archive>>>(HttpStatus.NO_CONTENT) ;
        

    }
}
