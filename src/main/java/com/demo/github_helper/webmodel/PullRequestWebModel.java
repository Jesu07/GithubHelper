package com.demo.github_helper.webmodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PullRequestWebModel {

    private String owner;
    private String repo;
    private String title;
    private String body;
    private String head;
    private String base;

    private Integer prNo; // Pull Request No

}
