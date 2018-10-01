package gerasimov.springdev.nosqllibrary;

import gerasimov.springdev.nosqllibrary.controller.LibController;
import gerasimov.springdev.nosqllibrary.facade.LibraryFacade;
import gerasimov.springdev.nosqllibrary.model.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LibController.class)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryFacade libraryFacade;

    @Test
    public void listTest() throws Exception {
        given(libraryFacade.allBooks())
                .willReturn(Collections.singletonList(new Book("testBook")));
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("testBook")));
    }

    @Test
    public void createTest() throws Exception {
        mockMvc.perform(get("/edit"))
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("New comment"))))
                .andExpect(content().string(containsString("Title")));
    }

    @Test
    public void editTest() throws Exception {
        Book book = new Book();
        book.setId("id");
        given(libraryFacade.showBookInfo("id"))
                .willReturn(Optional.of(book));
        mockMvc.perform(get("/edit/id"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("New comment")))
                .andExpect(content().string(containsString("Title")));
    }
}
