package br.com.rickes.scraping.service;

import java.util.List;
import java.util.Map;

import br.com.rickes.scraping.model.Archive;

public interface IScrapingGitHub {

	Map<String, List<Archive>> getAllArchives(String pUrl);
	
}
