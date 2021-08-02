package com.codeup.springblog;

import com.codeup.springblog.SpringblogApplication;
import com.codeup.springblog.models.Ad;
import com.codeup.springblog.models.User;
import com.codeup.springblog.repositories.AdRepository;
import com.codeup.springblog.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import javax.servlet.http.HttpSession;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringblogApplication.class)
@AutoConfigureMockMvc
public class AdIntegrationTests {

    private User testUser;
    private HttpSession httpSession;

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserRepository userDao;

    @Autowired
    AdRepository adsDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setup() throws Exception {

        testUser = userDao.findByUsername("testUser");

        // Creates the test user if not exists
        if(testUser == null){
            User newUser = new User();
            newUser.setUsername("testUser");
            newUser.setPassword(passwordEncoder.encode("pass"));
            newUser.setEmail("testUser@codeup.com");
            testUser = userDao.save(newUser);
        }

        // Throws a Post request to /login and expect a redirection to the Ads index page after being logged in
        httpSession = this.mvc.perform(post("/login").with(csrf())
                .param("username", "testUser")
                .param("password", "pass"))
                .andExpect(status().is(HttpStatus.FOUND.value()))
                .andExpect(redirectedUrl("/ads"))
                .andReturn()
                .getRequest()
                .getSession();

    }

    // **** Sanity Tests ****

    @Test
    public void contextLoads() {
        // Sanity Test, just to make sure the MVC bean is working
        assertNotNull(mvc);
    }

    @Test
    public void testIfUserSessionIsActive() throws Exception {
        // It makes sure the returned session is not null
        assertNotNull(httpSession);
    }

   //**** Testing the Ads CRUD Functionality ****
    @Test
    public void testCreateAd() throws Exception {
        // Makes a Post request to /ads/create and expect a redirection to the Ad
        this.mvc.perform(
                post("/ads/create").with(csrf())
                        .session((MockHttpSession) httpSession)
                        // Add all the required parameters to your request like this
                        .param("title", "test")
                        .param("description", "for sale"))
                .andExpect(status().is3xxRedirection());
    }

//    Read. In these tests we are going to check if the ads index ("/ads") and show ad page ("/ads/{id}") render correctly.
//Once an Ad has been created, we will find it and test if we can render it in the view.
//It's always a good idea to verify the return status with: status().isOk().
//You can use content().string(containsString()) to search for pieces of text you are expecting in the page.
    @Test
    public void testShowAd() throws Exception {

        Ad existingAd = adsDao.findAll().get(0);

        // Makes a Get request to /ads/{id} and expect a redirection to the Ad show page
        this.mvc.perform(get("/ads/" + existingAd.getId()))
                .andExpect(status().isOk())
                // Test the dynamic content of the page
                .andExpect(content().string(containsString(existingAd.getDescription())));
    }

    @Test
    public void testAdsIndex() throws Exception {
        Ad existingAd = adsDao.findAll().get(0);

        // Makes a Get request to /ads and verifies that we get some of the static text of the ads/index.html template and at least the title from the first Ad is present in the template.
        this.mvc.perform(get("/ads"))
                .andExpect(status().isOk())
                // Test the static content of the page
                .andExpect(content().string(containsString("Latest ads")))
                // Test the dynamic content of the page
                .andExpect(content().string(containsString(existingAd.getTitle())));
    }

//    Update. This test will focus on an existing Ad and change it's title and description. You will notice that we are using the CSRF token again and the session since it's required to be logged in to perform this action.
@Test
public void testEditAd() throws Exception {
    // Gets the first Ad for tests purposes
    Ad existingAd = adsDao.findAll().get(0);

    // Makes a Post request to /ads/{id}/edit and expect a redirection to the Ad show page
    this.mvc.perform(
            post("/ads/" + existingAd.getId() + "/edit").with(csrf())
                    .session((MockHttpSession) httpSession)
                    .param("title", "edited title")
                    .param("description", "edited description"))
            .andExpect(status().is3xxRedirection());

    // Makes a GET request to /ads/{id} and expect a redirection to the Ad show page
    this.mvc.perform(get("/ads/" + existingAd.getId()))
            .andExpect(status().isOk())
            // Test the dynamic content of the page
            .andExpect(content().string(containsString("edited title")))
            .andExpect(content().string(containsString("edited description")));
}

// Delete. For this test we will create an Ad and destroy it to verify if it's gone and redirects back to the ("/ads") URL.
@Test
public void testDeleteAd() throws Exception {
    // Creates a test Ad to be deleted
    this.mvc.perform(
            post("/ads/create").with(csrf())
                    .session((MockHttpSession) httpSession)
                    .param("title", "ad to be deleted")
                    .param("description", "won't last long"))
            .andExpect(status().is3xxRedirection());

    // Get the recent Ad that matches the title
    Ad existingAd = adsDao.findByTitle("ad to be deleted");

    // Makes a Post request to /ads/{id}/delete and expect a redirection to the Ads index
    this.mvc.perform(
            post("/ads/" + existingAd.getId() + "/delete").with(csrf())
                    .session((MockHttpSession) httpSession)
                    .param("id", String.valueOf(existingAd.getId())))
            .andExpect(status().is3xxRedirection());
}
}
