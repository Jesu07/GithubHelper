package com.demo.github_helper.service;

import com.demo.github_helper.webmodel.BranchWebModel;

public interface GithubApiService {

    Object getAllPublicRepositories() throws Exception;

    Object getAllRepositoryTopic(String owner, String repoName) throws Exception;

    Object getRepo(String owner, String repoName) throws Exception;

    Object listBranches(String owner, String repoName) throws Exception;

    Object getBranch(String owner, String repoName, String branchName) throws Exception;

    Object createBranch(BranchWebModel branchWebModel) throws Exception;

    Object saveNewFile(String owner, String repo, String path, String reqBodyData) throws Exception;

}
