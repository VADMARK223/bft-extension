package com.bftcom.bftextension.controller;

import com.bftcom.bftextension.model.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author Markitanov Vadim
 * @since 21.09.2022
 */
@RestController
@RequestMapping("/config")
public class ConfigController {
    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.username}")
    String user;

    @Autowired
    DataSource dataSource;

    @GetMapping
    Config getConfig(HttpServletRequest request) throws SQLException {
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();

        Config config = new Config();
        config.setServer(baseUrl);
        config.setUrl(url);
        config.setUser(user);
        config.setSchema(dataSource.getConnection().getSchema());
        return config;
    }
}
