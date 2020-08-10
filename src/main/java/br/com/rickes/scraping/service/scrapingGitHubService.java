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
import br.com.rickes.scraping.tools.HtmlTool;

@Service
public class ScrapingGitHubService {
    
    private final String URL_GIT = "https://github.com";

    private String urlParam;

    @Autowired
    private ArchiveRepository archiveRepository;

    private List<Archive> archives = new ArrayList<>();
    HtmlTool htmlTool;

    public Map<String, List<Archive>> getAllArchives(String pUrl){       
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


    protected void searchAllArchives(String pUrl){
        
        try {
            htmlTool = new HtmlTool();
            
            String bodyHTML = htmlTool.getBodyPage(pUrl);
			
			ArrayList<String> links = (ArrayList<String>) htmlTool.getUrlPage(bodyHTML);
			
			if(links == null || links.isEmpty()) {
				saveArchive(bodyHTML);
			}else {
				for (String link : links) {
					System.out.println(link);
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

    protected void saveArchive(String pBodyHTML)
    {
        htmlTool = new HtmlTool();
        Archive archive = new Archive();

        archive.setUrl(this.urlParam);
        archive.setLines(htmlTool.getLinesFile(pBodyHTML));
        archive.setSize(htmlTool.getSizeFile(pBodyHTML));
        archive.setName(htmlTool.getNameFile(pBodyHTML));
        archive.setExtension(htmlTool.getExtensionFile(archive.getName()));

        this.archives.add(archive);
        //this.archiveRepository.save(archive);
    }

}