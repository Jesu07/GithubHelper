package com.demo.github_helper.webmodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommitWebModel {

    private String owner;
    private String repo;
    private String branchToSave;
    private String newBranchName; // New Branch name, if branch doesn't exist.
    private String path;
    private String message;
    private String committerName;
    private String committerMail;

    private List<MultipartFile> files;

}
