package com.siky.packtpub.parser;

import com.siky.packtpub.com.siky.packtpub.model.EmailDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("parserComponent")
public class Parser implements Command<String, EmailDTO> {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Parser.class);


    @Override
    public EmailDTO execute(String contetn) {

        Document doc = Jsoup.parse(contetn);
        Elements titleElements = doc.select(".dotd-title > h2");
        Elements descriptionElements = doc.select(".dotd-main-book-summary div");

        String title = titleElements.text();
        String description = descriptionElements.next().next().text();


        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setTitle(title);
        emailDTO.setDescription(description);

        LOGGER.info("Title : " + title);
        LOGGER.info("DESCRIPTION : " + description);

        return emailDTO;
    }



}
