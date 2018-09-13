package com.siky.packtpub.scheduled;

import com.siky.packtpub.com.siky.packtpub.model.EmailDTO;
import com.siky.packtpub.parser.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(Job.class);

    @Value("${request.url}")
    public String URL_TO_PACKT_PUB;

    @Autowired
    @Qualifier(value = "downloadComponent")
    private Command<String, String> downloader;

    @Autowired
    @Qualifier(value = "parserComponent")
    private  Command<String, EmailDTO> parser;

    @Autowired
    @Qualifier(value = "emailComponent")
    private Command<EmailDTO, Void> emailSender;

    @Scheduled(cron = "01 00 06 * * *")
    public void run() throws Exception {

        execute();

    }


    private void execute() throws Exception {
        LOGGER.info("Downloading content from url {} " ,URL_TO_PACKT_PUB);


        String content = downloader.execute(URL_TO_PACKT_PUB);
        EmailDTO emaiLDTO = parser.execute(content);
        emailSender.execute(emaiLDTO);
    }

}
