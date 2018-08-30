package com.siky.packtpub.parser;


import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

@Component("downloadComponent")
public class Downloader implements Command<String, String> {

    @Override
    public String execute(String url) throws Exception {

        URL oracle;
        try {
            oracle = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not connect to url" + url, e);
        }

        String inputLine;
        StringBuilder sb = new StringBuilder();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(oracle.openStream()))) {

            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
        } catch (IOException e) {
            throw new IOException("Could not read from data", e);
        }

        return sb.toString();
    }

}
