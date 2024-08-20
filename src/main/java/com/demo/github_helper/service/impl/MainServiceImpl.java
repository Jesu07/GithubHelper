package com.demo.github_helper.service.impl;

import com.demo.github_helper.service.GithubApiService;
import com.demo.github_helper.webmodel.BranchWebModel;
import com.demo.github_helper.webmodel.CommitWebModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.demo.github_helper.service.MainService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class MainServiceImpl implements MainService {

    public static final Logger logger = LoggerFactory.getLogger(MainServiceImpl.class);

    @Autowired
    GithubApiService githubApiService;

    @Override
    public List<?> getAllPublicRepositories() throws Exception {
        return (List<?>) githubApiService.getAllPublicRepositories();
    }

    @Override
    public Map<?, ?> getAllRepositoryTopic(String owner, String repoName) throws Exception {
        return (Map<?, ?>) githubApiService.getAllRepositoryTopic(owner, repoName);
    }

    @Override
    public List<?> getAllBranches(String owner, String repoName) throws Exception {
        return (List<?>) githubApiService.listBranches(owner, repoName);
    }

    @Override
    public Map<?, ?> getRepository(String owner, String repoName) throws Exception {
        return (Map<?, ?>) githubApiService.getRepo(owner, repoName);
    }

    @Override
    public Map<?, ?> getBranchByName(String owner, String repoName, String branchName) throws Exception {
        return (Map<?, ?>) githubApiService.getBranch(owner, repoName, branchName);
    }

    @Override
    public Map<?, ?> createBranch(BranchWebModel branchWebModel) throws Exception {
        Map<?, ?> parentBranch = (Map<?, ?>) githubApiService.getBranch(branchWebModel.getOwner(), branchWebModel.getRepo(), branchWebModel.getParentBranch());
        if (parentBranch != null) {
            Map<String, Object> map = (Map<String, Object>) parentBranch.get("commit");
            logger.info("Parent branch commit value -> {}", map);
            String sha = (String) map.get("sha");
            branchWebModel.setSha(sha); // updating the input sha value from parent branch commit sha
            return (Map<?, ?>) githubApiService.createBranch(branchWebModel);
        }
        return null;
    }

    @Override
    public List<Map<?, ?>> saveNewFiles(CommitWebModel commitWebModel) throws Exception {
        List<Map<?, ?>> savedFilesObj = new ArrayList<>();
        String branchToSave;
        try {
            Map<?, ?> existingBranch = (Map<?, ?>) githubApiService.getBranch(commitWebModel.getOwner(), commitWebModel.getRepo(), commitWebModel.getBranchToSave());
        } catch (Exception e) {
            if (e.getMessage().contains("404")) {
                Map<?, ?> newBranch = this.createBranch(BranchWebModel.builder()
                        .owner(commitWebModel.getOwner())
                        .repo(commitWebModel.getRepo())
                        .ref("refs/heads/" + commitWebModel.getNewBranchName())
                        .parentBranch("main")
                        .sha("")
                        .build());
                branchToSave = (String) newBranch.get("ref");
                logger.info("Newly saved branch name -> {}", branchToSave);
                if (branchToSave.contains(commitWebModel.getNewBranchName())) commitWebModel.setBranchToSave(branchToSave);
            }
        }

        if (null != commitWebModel.getFiles() && !commitWebModel.getFiles().isEmpty()) {
            // Iterating the input files to get filename and base64 encode content
            Map<String, Object> fileNameAndContentMap = new HashMap<>();
            commitWebModel.getFiles().forEach(file -> {
                String encodedContent = null;
                try {
                    encodedContent = Base64.getEncoder().encodeToString(file.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                fileNameAndContentMap.put(file.getOriginalFilename(), encodedContent);
            });
            // Iterating the file data map to save one by one.
            fileNameAndContentMap.forEach((key, value) -> {
                String path = commitWebModel.getPath() + "/" + key;

                Map<String, Object> bodyParamMap = new LinkedHashMap<>();
                bodyParamMap.put("owner", commitWebModel.getOwner());
                bodyParamMap.put("repo", commitWebModel.getRepo());
                bodyParamMap.put("branch", commitWebModel.getBranchToSave());
                bodyParamMap.put("path", path);
                bodyParamMap.put("message", commitWebModel.getMessage());
                Map<String, String> mp = new HashMap<>();
                mp.put("name", commitWebModel.getCommitterName());
                mp.put("email", commitWebModel.getCommitterMail());
                bodyParamMap.put("committer", mp);
                bodyParamMap.put("content", value);
                String requestBodyData;
                try {
                    requestBodyData = new ObjectMapper().writeValueAsString(bodyParamMap);
                    Map<?, ?> obj = (Map<?, ?>) githubApiService.saveNewFile(commitWebModel.getOwner(), commitWebModel.getRepo(), path, requestBodyData);
                    if (obj != null) savedFilesObj.add(obj);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return savedFilesObj;
    }

}
