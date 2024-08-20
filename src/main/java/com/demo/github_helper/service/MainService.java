package com.demo.github_helper.service;

import com.demo.github_helper.webmodel.BranchWebModel;
import com.demo.github_helper.webmodel.CommitWebModel;

import java.util.List;
import java.util.Map;

public interface MainService {

    List<?> getAllPublicRepositories() throws Exception;

    Map<?, ?> getAllRepositoryTopic(String owner, String repoName) throws Exception;

    Map<?,?> getRepository(String owner, String repoName) throws Exception;

    List<?> getAllBranches(String owner, String repoName) throws Exception;

    Map<?, ?> getBranchByName(String owner, String repoName, String branchName) throws Exception;

    Map<?, ?> createBranch(BranchWebModel branchWebModel) throws Exception;

    List<Map<?, ?>> saveNewFiles(CommitWebModel commitWebModel) throws Exception;

}
