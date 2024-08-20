package com.demo.github_helper.webmodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BranchWebModel {

    private String owner;
    private String repo;
    private String parentBranch; // Branch name
    private String ref; // BranchName. it should be like this "refs/heads/featureA"
    private String sha;

}
