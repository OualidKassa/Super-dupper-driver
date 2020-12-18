package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.data.Credential;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CredentialTests extends CloudStorageApplicationTests {

	@Test
	public void shouldCredentialGetCreate() {
		HomePage homePage = shouldSignUpAndLogPage();
		shouldCredentialTest(MY_GITHUB_URL, USERNAME, PASSWORD, homePage);
		homePage.deleteCredential();
		SuccesfullPage succesfullPage = new SuccesfullPage(driver);
		succesfullPage.clickOk();
		homePage.logout();
	}

	private void shouldCredentialTest(String url, String username, String password, HomePage homePage) {
		shouldCredentialGetCreate(url, username, password, homePage);
		homePage.navToCredentialsTab();
		Credential credential = homePage.getFirstCredential();
		Assertions.assertEquals(url, credential.getUrl());
		Assertions.assertEquals(username, credential.getUserName());
		Assertions.assertNotEquals(password, credential.getPassword());
	}

	private void shouldCredentialGetCreate(String url, String username, String password, HomePage homePage) {
		homePage.navToCredentialsTab();
		homePage.addNewCredential();
		SendCredentialToFields(url, username, password, homePage);
		homePage.saveCredentialChanges();
		SuccesfullPage succesfullPage = new SuccesfullPage(driver);
		succesfullPage.clickOk();
		homePage.navToCredentialsTab();
	}

	private void SendCredentialToFields(String url, String username, String password, HomePage homePage) {
		homePage.setCredentialUrl(url);
		homePage.setCredentialUsername(username);
		homePage.setCredentialPassword(password);
	}


	@Test
	public void shouldTestCredentialWithModification() {
		HomePage homePage = shouldSignUpAndLogPage();
		shouldCredentialTest(MY_GITHUB_URL, USERNAME, PASSWORD, homePage);
		Credential originalCredential = homePage.getFirstCredential();
		String firstEncryptedPassword = originalCredential.getPassword();
		homePage.editCredential();
		String newUrl = SOME_URL;
		String newCredentialUsername = INFO;
		String newPassword = STUDY;
		SendCredentialToFields(newUrl, newCredentialUsername, newPassword, homePage);
		homePage.saveCredentialChanges();
		SuccesfullPage succesfullPage = new SuccesfullPage(driver);
		succesfullPage.clickOk();
		homePage.navToCredentialsTab();
		Credential modifiedCredential = homePage.getFirstCredential();
		Assertions.assertEquals(newUrl, modifiedCredential.getUrl());
		Assertions.assertEquals(newCredentialUsername, modifiedCredential.getUserName());
		String modifiedCredentialPassword = modifiedCredential.getPassword();
		Assertions.assertNotEquals(newPassword, modifiedCredentialPassword);
		Assertions.assertNotEquals(firstEncryptedPassword, modifiedCredentialPassword);
		homePage.deleteCredential();
		succesfullPage.clickOk();
		homePage.logout();
	}

	@Test
	public void shouldTestNavigation() {
		HomePage homePage = shouldSignUpAndLogPage();
		shouldCredentialGetCreate(MY_GITHUB_URL, USERNAME, PASSWORD, homePage);
		shouldCredentialGetCreate(SOME_URL, INFO, STUDY, homePage);
		shouldCredentialGetCreate("http://www.dcomics.com/", "batman", "bruce", homePage);
		Assertions.assertFalse(homePage.noCredentials(driver));
		homePage.deleteCredential();
		SuccesfullPage succesfullPage = new SuccesfullPage(driver);
		succesfullPage.clickOk();
		homePage.navToCredentialsTab();
		homePage.deleteCredential();
		succesfullPage.clickOk();
		homePage.navToCredentialsTab();
		homePage.deleteCredential();
		succesfullPage.clickOk();
		homePage.navToCredentialsTab();
		Assertions.assertTrue(homePage.noCredentials(driver));
		homePage.logout();
	}

	public static final String MY_GITHUB_URL = "https://github.com/OualidKassa";
	public static final String USERNAME = "not my real username";
	public static final String PASSWORD = "And certainly not my password";
	public static final String SOME_URL = "http://www.google.com/";
	public static final String INFO = "project from udacity";
	public static final String STUDY = "Java web spring framework";
}
