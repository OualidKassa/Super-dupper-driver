package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.data.Note;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoteTests extends CloudStorageApplicationTests {

	@Test
	public void shouldCreateNote() {
		String noteTitle = "create a new note";
		String noteDescription = "bla bla bla bla";
		HomePage homePage = shouldSignUpAndLogPage();
		newNote(noteTitle, noteDescription, homePage);
		homePage.navToNotesTab();
		homePage = new HomePage(driver);
		Note note = homePage.getFirstNote();
		Assertions.assertEquals(noteTitle, note.getNoteTitle());
		Assertions.assertEquals(noteDescription, note.getNoteDescription());
		deleteNote(homePage);
		homePage.logout();
	}

	@Test
	public void shouldChangeNote() {

		HomePage pageHome = shouldSignUpAndLogPage();
		String noteTitle = "Note new";
		String noteDescription = "awsome note for me";
		newNote(noteTitle, noteDescription, pageHome);
		pageHome.navToNotesTab();
		pageHome = new HomePage(driver);
		pageHome.editNote();
		String modifiedNoteTitle = "Some note";
		pageHome.modifyNoteTitle(modifiedNoteTitle);
		String modifiedNoteDescription = "Note is modified";
		pageHome.modifyNoteDescription(modifiedNoteDescription);
		pageHome.saveNoteChanges();
		SuccesfullPage succesfullPage = new SuccesfullPage(driver);
		succesfullPage.clickOk();
		pageHome.navToNotesTab();
		Note note = pageHome.getFirstNote();
		Assertions.assertEquals(modifiedNoteTitle, note.getNoteTitle());
		Assertions.assertEquals(modifiedNoteDescription, note.getNoteDescription());
	}

	@Test
	public void shouldDeleteNote() {

		HomePage homePage = shouldSignUpAndLogPage();

		String title = "note to delete";
		String noteDescription = "description to delete";

		newNote(title, noteDescription, homePage);
		homePage.navToNotesTab();
		homePage = new HomePage(driver);

		Assertions.assertFalse(homePage.noNotes(driver));
		deleteNote(homePage);
		Assertions.assertTrue(homePage.noNotes(driver));
	}

	private void newNote(String noteTitle, String noteDescription, HomePage homePage) {
		homePage.navToNotesTab();
		homePage.addNewNote();
		homePage.setNoteTitle(noteTitle);
		homePage.setNoteDescription(noteDescription);
		homePage.saveNoteChanges();
		SuccesfullPage succesfullPage = new SuccesfullPage(driver);
		succesfullPage.clickOk();
		homePage.navToNotesTab();
	}

	private void deleteNote(HomePage homePage) {
		homePage.deleteNote();
		SuccesfullPage succesfullPage = new SuccesfullPage(driver);
		succesfullPage.clickOk();
	}

}
