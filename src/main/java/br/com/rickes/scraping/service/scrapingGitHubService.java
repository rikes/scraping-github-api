package br.com.rickes.scraping.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.rickes.scraping.model.Address;
import br.com.rickes.scraping.model.Archive;
import br.com.rickes.scraping.repository.AddressRepository;
import br.com.rickes.scraping.repository.ArchiveRepository;
import br.com.rickes.scraping.tools.HtmlTool;

public class scrapingGitHubService {
    
    private final String URL_GIT = "https://github.com";


    @Autowired
    AddressRepository addressRepository;

    @Autowired
    ArchiveRepository archiveRepository;

    private List<Archive> archives = new ArrayList<>();

    private Address address = new Address();

    public void getAllArchives(String pUrl){
        Optional<Address> addressDB = addressRepository.findByUrl(pUrl);
        
        List<Archive> listArchives = archiveRepository.findByAddress(addressDB.get());

        if(listArchives != null && !listArchives.isEmpty()){
            //return arquivosConvertidos
        }else{
            this.searchAllArchives(pUrl);
        }
    }


    protected void searchAllArchives(String pUrl){
        HtmlTool htmlTool;
        
        try {
            htmlTool = new HtmlTool();
            
            String bodyHTML = htmlTool.getBodyPage(pUrl);
			
			ArrayList<String> links = (ArrayList<String>) htmlTool.getUrlPage(bodyHTML);
			
			if(links == null || links.isEmpty()) {
				saveArchive(pUrl, bodyHTML);
			}else {
				for (String link : links) {
					System.out.println(link);
					this.getAllArchives(URL_GIT+link);
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

    protected void saveArchive(String pUrl, String pBodyHTML)
    {
        Address address = new Address();
        address.setUrl(pUrl);

        Archive



    }
    


    protected String convertJson(List<Archive> pListArchives){

        return null;
    }


    








}