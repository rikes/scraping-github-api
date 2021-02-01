package br.com.rickes.scraping.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rickes.scraping.model.Archive;
import br.com.rickes.scraping.repository.ArchiveRepository;
import br.com.rickes.scraping.tools.HtmlToolGitHub;
import br.com.rickes.scraping.tools.IHtmlTools;

/**
 * Class of service responsible for implementing business rules when scraping from GitHub
 * 
 * @author Henrique
 */
@Service
public class ScrapingGitHubService implements IScrapingGitHub {
    
    private final String URL_GIT = "https://github.com";

    private String urlParam;

    @Autowired
    private ArchiveRepository archiveRepository;
    
    private List<Archive> archives;
    private IHtmlTools htmlTool;
    private List<String> links;

    /**Method responsible for scraping on GitHub, 
     * searching for data by scanning pages or in the database
     * 
     * @param pUrl Search html body in URL
     * @return Grouping of data by name, length, lines and size
     */
	public Map<String, List<Archive>> getAllArchives(String pUrl){       
		this.archives = new ArrayList<>();
        this.htmlTool = new HtmlToolGitHub();
        
        this.urlParam = pUrl;
        List<Archive> listArchives = archiveRepository.findByUrl(pUrl);

        if(listArchives != null && !listArchives.isEmpty()){
            this.archives = listArchives;
        }else{
            this.searchAllArchives(pUrl);
            this.archiveRepository.saveAll(archives);
        }

        Map<String, List<Archive>> mapGroupExtension = this.archives.stream().collect(Collectors.groupingBy(Archive::getExtension));

        return mapGroupExtension;
    }

    
    /**Recursive method that scans pages
     * 
     * @param pUrl Page URL
     */
    protected void searchAllArchives(String pUrl){
        
        try {
            
            String bodyHTML = htmlTool.getBodyPage(pUrl);
            
			links = htmlTool.getUrlPage(bodyHTML);

			if(links == null || links.isEmpty()) {
                saveArchive(bodyHTML, pUrl);
			}else {
				for (String link : links) {
					this.searchAllArchives(URL_GIT+link);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    /**Build an 'Archive' object and add it to the list
     * 
     * @param pUrl Page URL
     * @param pBodyHTML HTML body with file data (Size and lines)
     */
    protected void saveArchive(String pBodyHTML, String pUrl)
    {
        Archive archive = new Archive();

        archive.setUrl(this.urlParam);
        archive.setLines(this.htmlTool.getLinesFile(pBodyHTML));
        archive.setSize(this.htmlTool.getSizeFile(pBodyHTML));
        archive.setName(this.htmlTool.getNameFile(pUrl));
        archive.setExtension(this.htmlTool.getExtensionFile(archive.getName()));

        this.archives.add(archive);
    }

}
