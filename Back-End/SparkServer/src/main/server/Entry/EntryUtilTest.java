package main.server.Entry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Test class for API endpoints using mocked HTTP requests and responses generated via jUnit and
 * Mockito
 */
class EntryUtilTest {

  @Mock HttpServletRequest request;

  @Mock HttpServletResponse response;

  @BeforeEach
  public void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Requires the entry_id from the HTTP request params Testing requires this is successfully passed
   * to the constructor and sets the value correctly.
   */
  @Test
  void testSelectSpecific() {

    when(request.getParameter("entry_id")).thenReturn("1");
    when(response.getStatus()).thenReturn(200);

    Entry entry = new Entry(Integer.parseInt(request.getParameter("entry_id")));

    assertEquals(1, entry.getEntry_id());
    assertEquals(200, response.getStatus());
  }

  /** Requires the patient_id from the HTTP request params to find all patient entries. */
  @Test
  void testSelectAll() {

    when(request.getParameter("patient_id")).thenReturn("1");
    when(response.getStatus()).thenReturn(200);

    Entry entry = new Entry(null);
    entry.setPatient(Integer.parseInt(request.getParameter("patient_id")));

    assertEquals(1, entry.getPatient());
    assertEquals(200, response.getStatus());
  }

  /**
   * Requires the entry contents as a String and patient_id value from the HTTP request params to
   * create a new entry
   */
  @Test
  void testRegisterEntry() {

    when(request.getParameter("entry")).thenReturn("test entry");
    when(request.getParameter("patient_id")).thenReturn("1");
    when(response.getStatus()).thenReturn(200);

    Entry entry = new Entry(null);
    entry.setEntry(request.getParameter("entry"));
    entry.setPatient(Integer.parseInt(request.getParameter("patient_id")));

    assertEquals("test entry", entry.getEntry());
    assertEquals(1, entry.getPatient());

    assertEquals(200, response.getStatus());
  }

  @Test
  void testAddComment() {
    when(request.getParameter("entry_id")).thenReturn("1");
    when(request.getParameter("comment")).thenReturn("Test comment.");
    when(response.getStatus()).thenReturn(200);

    Entry entry = new Entry(Integer.parseInt(request.getParameter("entry_id")));
    entry.setComment(request.getParameter("comment"));

    assertEquals(1, entry.getEntry_id());
    assertEquals("Test comment.", entry.getComment());

    assertEquals(200, response.getStatus());
  }
}
