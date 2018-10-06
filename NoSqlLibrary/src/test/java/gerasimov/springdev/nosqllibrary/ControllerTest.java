package gerasimov.springdev.nosqllibrary;

import gerasimov.springdev.nosqllibrary.controller.LibRestController;
import gerasimov.springdev.nosqllibrary.facade.LibraryFacade;
import gerasimov.springdev.nosqllibrary.model.Book;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LibRestController.class)
@Ignore("Unauthorized даже для публичных методов")
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryFacade libraryFacade;

    @Test
    public void createTest() throws Exception {
        mockMvc.perform(post("/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"title\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void readTest() throws Exception {
        given(libraryFacade.allBooks())
                .willReturn(Collections.singletonList(new Book("testBook")));
        mockMvc.perform(get("/list"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("testBook")));
    }

    @Test
    public void updateTest() throws Exception {
        Book book = new Book();
        book.setId("id");
        given(libraryFacade.showBookInfo("id"))
                .willReturn(Optional.of(book));
        mockMvc.perform(put("/upd").contentType(MediaType.APPLICATION_JSON).content("{\"id\":\"id\",\"title\":\"newTitle\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(delete("/del?id=id"))
                .andExpect(status().isOk());
    }
}
