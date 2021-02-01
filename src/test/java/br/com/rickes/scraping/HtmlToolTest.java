package br.com.rickes.scraping;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.rickes.scraping.tools.HtmlToolGitHub;

@SpringBootTest
class HtmlToolTest {
    
    @Autowired
    private HtmlToolGitHub htmlTool;

	@Test
	void validateExtension() {
        String file = "proc-registering-connector-monitor-inventory-database.teste.adoc";
        String extension = htmlTool.getExtensionFile(file);

        assertEquals("adoc", extension);
	}

    @Test
    void validateGetNameFile() {
        String lineHTML = "https://github.com/sharifni/Database-Application/blob/master/Sharif_Niyaz_ADBMS_Project.accdb";
        String nameFile = htmlTool.getNameFile(lineHTML);

        assertEquals("Sharif_Niyaz_ADBMS_Project.accdb", nameFile);
	}


}