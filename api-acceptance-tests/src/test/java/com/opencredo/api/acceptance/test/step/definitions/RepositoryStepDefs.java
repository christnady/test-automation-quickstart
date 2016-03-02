package com.opencredo.api.acceptance.test.step.definitions;


import com.opencredo.api.acceptance.test.common.Repository;
import com.opencredo.api.acceptance.test.config.spring.TestConfig;
import com.opencredo.api.acceptance.test.interaction.objects.GithubApi;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes= TestConfig.class)
public class RepositoryStepDefs extends AbstractStepDefinition {
    @Autowired
    private GithubApi githubApi;

    private List<Repository> repositoryList = new ArrayList<>();

    @Before
    public void before(Scenario scenario) {
        super.before(scenario);
    }

    @When("^I retrieve a list of repositories for user \"([^\"]*)\"$")
    public void I_retrieve_a_list_of_repositories_for_user(String user) throws Throwable {
        repositoryList = githubApi.getRepositoryListForUser(user);

        embedTextInReport("Received list containing '" + repositoryList.size() + "' repositories");
    }

    @Then("^the following repositories should be present in the repository list$")
    public void theFollowingRepositoriesShouldBePresentInTheRepositoryList(final List<String> expectedRepositories) throws Throwable {

        expectedRepositories.forEach(expectedRepository -> {
            assertThat(repositoryList).extracting("name").contains(expectedRepository);
        });
    }
}
