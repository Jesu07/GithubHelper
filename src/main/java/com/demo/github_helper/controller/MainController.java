package com.demo.github_helper.controller;

import com.demo.github_helper.webmodel.CommitWebModel;
import com.demo.github_helper.service.MainService;
import com.demo.github_helper.webmodel.BranchWebModel;
import com.demo.github_helper.webmodel.PullRequestWebModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MainController {

    public static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    MainService mainService;

    @GetMapping("/helloWorld")
    public ResponseEntity<?> helloWorld() {
        return ResponseEntity.ok().body("Hai from Backend");
    }

    /**
     * To get all public repository details
     *
     * @return List
     */
    @GetMapping("/getAllPublicRepositories")
    public ResponseEntity<?> getAllPublicRepositories() {
        List<?> response;
        try {
            response = mainService.getAllPublicRepositories();
            if (response != null && !response.isEmpty()) return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            logger.error("Error at getAllPublicRepositories() -> {}", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok().body(response);
    }

    /**
     * To get all topics from a repository
     *
     * @param owner
     * @param repoName
     * @return Map
     */
    @GetMapping("/getAllRepositoryTopics/{owner}/{repoName}")
    public ResponseEntity<?> getAllRepositoryTopics(@PathVariable("owner") String owner,
                                                    @PathVariable("repoName") String repoName) {
        Map<?, ?> response;
        try {
            response = mainService.getAllRepositoryTopic(owner, repoName);
            if (response != null && !response.isEmpty()) return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            logger.error("Error at getAllRepositoryTopic() -> {}", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok().body(response);
    }

    /**
     * Getting repository details
     *
     * @param owner
     * @param repoName
     * @return Map
     */
    @GetMapping("/getRepo/{owner}/{repoName}")
    public ResponseEntity<?> getRepository(@PathVariable("owner") String owner,
                                           @PathVariable("repoName") String repoName) {
        Map<?, ?> response;
        try {
            response = mainService.getRepository(owner, repoName);
            if (response != null && !response.isEmpty()) return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            logger.error("Error at getRepository() -> {}", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok().body(response);
    }

    /**
     * To get all branches from a repository
     *
     * @param owner
     * @param repoName
     * @return List
     */
    @GetMapping("/getAllBranches/{owner}/{repoName}")
    public ResponseEntity<?> getAllBranches(@PathVariable("owner") String owner,
                                            @PathVariable("repoName") String repoName) {
        List<?> response;
        try {
            response = mainService.getAllBranches(owner, repoName);
            if (response != null && !response.isEmpty()) return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            logger.error("Error at getAllBranches() -> {}", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok().body(response);
    }

    /**
     * To get a branch details
     *
     * @param owner
     * @param repoName
     * @param branchName
     * @return Map
     */
    @GetMapping("/getBranch/{owner}/{repoName}/{branchName}")
    public ResponseEntity<?> getBranchByName(@PathVariable("owner") String owner,
                                             @PathVariable("repoName") String repoName,
                                             @PathVariable("branchName") String branchName) {
        Map<?, ?> response;
        try {
            response = mainService.getBranchByName(owner, repoName, branchName);
            if (response != null) return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            logger.error("Error at getBranchByName() -> {}", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok().body(null);
    }

    /**
     * To create a new branch
     *
     * @param branchWebModel
     * @return Map
     */
    @PostMapping("/createBranch")
    public ResponseEntity<?> createBranch(@RequestBody BranchWebModel branchWebModel) {
        try {
            Map<?, ?> response = mainService.createBranch(branchWebModel);
            if (response != null) return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            logger.error("Error at createBranch() -> {}", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok().body(null);
    }

    /**
     * To create commit with new files
     *
     * @param commitWebModel
     * @return List
     */
    @PutMapping(path = "/saveNewFiles", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> saveNewFiles(@ModelAttribute CommitWebModel commitWebModel) {
        try {
            List<Map<?, ?>> response = mainService.saveNewFiles(commitWebModel);
            if (response != null) return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            logger.error("Error at saveNewFiles() -> {}", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok().body(null);
    }

    /**
     * To create a Pull Request
     *
     * @param pullRequestWebModel
     * @return Map
     */
    @PostMapping("/createPullRequest")
    public ResponseEntity<?> createPullRequest(@RequestBody PullRequestWebModel pullRequestWebModel) {
        try {
            Map<?, ?> response = mainService.createPullRequest(pullRequestWebModel);
            if (response != null) return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            logger.error("Error at createPullRequest() -> {}", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok().body(null);
    }

    /**
     * To get all pull request details
     *
     * @param pullRequestWebModel
     * @return List
     */
    @GetMapping("/getAllPullRequests")
    public ResponseEntity<?> getAllPullRequests(@RequestBody PullRequestWebModel pullRequestWebModel) {
        try {
            List<?> response = mainService.getAllPullRequests(pullRequestWebModel.getOwner(), pullRequestWebModel.getRepo());
            if (response != null) return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            logger.error("Error at getAllPullRequests() -> {}", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok().body(null);
    }

    /**
     * To get a pull request details
     *
     * @param pullRequestWebModel
     * @return Map
     */
    @GetMapping("/getPullRequest")
    public ResponseEntity<?> getPullRequest(@RequestBody PullRequestWebModel pullRequestWebModel) {
        try {
            Map<?, ?> response = mainService.getPullRequest(pullRequestWebModel.getOwner(), pullRequestWebModel.getRepo(), pullRequestWebModel.getPrNo());
            if (response != null) return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            logger.error("Error at getPullRequest() -> {}", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok().body(null);
    }

}
