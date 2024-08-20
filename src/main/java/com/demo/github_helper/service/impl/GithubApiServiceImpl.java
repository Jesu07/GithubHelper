package com.demo.github_helper.service.impl;

import com.demo.github_helper.service.GithubApiService;
import com.demo.github_helper.util.HttpUtil;
import com.demo.github_helper.webmodel.BranchWebModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GithubApiServiceImpl implements GithubApiService {

    public static final Logger logger = LoggerFactory.getLogger(GithubApiServiceImpl.class);

    @Autowired
    HttpUtil httpUtil;

    @Override
    public Object getAllPublicRepositories() throws Exception {
        final String uri = httpUtil.constructUri("repositories");
        return httpUtil.executeGetRequest(uri);
    }

    @Override
    public Object getAllRepositoryTopic(String owner, String repoName) throws Exception {
        final String uri = httpUtil.constructUri(owner, repoName, "topics");
        return httpUtil.executeGetRequest(uri);

    }

    @Override
    public Object listBranches(String owner, String repoName) throws Exception {
        final String uri = httpUtil.constructUri(owner, repoName, "branches");
        return httpUtil.executeGetRequest(uri);
    }

    @Override
    public Object getRepo(String owner, String repoName) throws Exception {
        final String uri = httpUtil.constructUri(owner, repoName, "");
        return httpUtil.executeGetRequest(uri);
    }

    @Override
    public Object getBranch(String owner, String repoName, String branchName) throws Exception {
        final String uri = httpUtil.constructUri(owner, repoName, "branches/" + branchName);
        return httpUtil.executeGetRequest(uri);
    }

    @Override
    public Object createBranch(BranchWebModel branchWebModel) throws Exception {
        final String uri = httpUtil.constructUri(branchWebModel.getOwner(), branchWebModel.getRepo(), "git/refs");
        Map<String, Object> bodyParamMap = new HashMap<>();
        bodyParamMap.put("owner", branchWebModel.getOwner());
        bodyParamMap.put("repo", branchWebModel.getRepo());
        bodyParamMap.put("ref", branchWebModel.getRef());
        bodyParamMap.put("sha", branchWebModel.getSha());
        String reqBodyData = new ObjectMapper().writeValueAsString(bodyParamMap);
        return httpUtil.executePostRequest(uri, reqBodyData);
    }

    @Override
    public Object saveNewFile(String owner, String repo, String path, String reqBodyData) throws Exception {
        final String uri = httpUtil.constructUri(owner, repo, "contents/" + path);
        return httpUtil.executePutRequest(uri, reqBodyData);
    }
}
