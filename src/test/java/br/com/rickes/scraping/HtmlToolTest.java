package br.com.rickes.scraping;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.rickes.scraping.tools.HtmlTool;

@SpringBootTest
public class HtmlToolTest {
    
    @Autowired
    private HtmlTool htmlTool;

	@Test
	public void validateExtension() {
        String file = "readme.md";
        String extension = htmlTool.getExtensionFile(file);

        assertEquals("md", extension);
	}

    @Test
	public void validateGetNameFile() {
        String lineHTML = "<span class=\"js-repo-root text-bold\"><span class=\"js-path-segment d-inline-block wb-break-all\"><a data-pjax=\"true\" href=\"/rikes/DataMiningCAGED\"><span>DataMiningCAGED</span></a></span></span><span class=\"separator\">/</span><span class=\"js-path-segment d-inline-block wb-break-all\"><a data-pjax=\"true\" href=\"/rikes/DataMiningCAGED/tree/master/Dados\"><span>Dados</span></a></span><span class=\"separator\">/</span><strong class=\"final-path\">Estruturas.md</strong>";
        String nameFile = htmlTool.getNameFile(lineHTML);

        assertEquals("Estruturas.md", nameFile);
	}


}