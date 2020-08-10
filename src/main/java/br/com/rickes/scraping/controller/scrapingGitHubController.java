package br.com.rickes.scraping.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.rickes.scraping.model.Archive;
import br.com.rickes.scraping.repository.ArchiveRepository;
import br.com.rickes.scraping.service.ScrapingGitHubService;


@RestController
@RequestMapping(value = "/api")
public class scrapingGitHubController {

    @Autowired
    ScrapingGitHubService scrapingService;

    @GetMapping(value="/files")
    public Map<String, List<Archive>> getFilesGitHub(@RequestParam(value = "url", required = true) String urlGitHub) {
        Map<String, List<Archive>> mapFiles = scrapingService.getAllArchives(urlGitHub);

        return mapFiles;

    }
}