package com.sd.controller;

import com.google.gson.Gson;
import com.sd.dto.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author SD
 * @since 2017/7/5
 */
@RunWith(SpringRunner.class)
@WebMvcTest(HelloController.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    private final String paramName = "userName";

    @Test
    public void checkParamNull() throws Exception {
        final String url = "/param/not-null";
        String r1 = mvc.perform(get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(r1, HelloController.failedResult);

        String r2 = mvc.perform(get(url).param(paramName, "").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(r2, HelloController.successResult);

        String r3 = mvc.perform(get(url).param(paramName, "SD").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(r3, HelloController.successResult);
    }

    @Test
    public void checkParamEmpty() throws Exception {
        final String url = "/param/not-empty";
        String r1 = mvc.perform(get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(r1, HelloController.failedResult);

        String r2 = mvc.perform(get(url).param(paramName, "").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(r2, HelloController.failedResult);

        String r3 = mvc.perform(get(url).param(paramName, "SD").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(r3, HelloController.successResult);
    }

    @Test
    public void checkParamFieldNull() throws Exception {
        final String url = "/param/field/not-null";

        User user = new User();
        Gson gson = new Gson();

        user.setUserName(null);
        String r1 = mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(gson.toJson(user)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(r1, HelloController.failedResult);

        user.setUserName("");
        String r2 = mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(gson.toJson(user)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(r2, HelloController.successResult);

        user.setUserName("SD");
        String r3 = mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(gson.toJson(user)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(r3, HelloController.successResult);
    }

    @Test
    public void checkParamFieldEmpty() throws Exception {
        final String url = "/param/field/not-empty";

        User user = new User();
        Gson gson = new Gson();

        user.setUserName(null);
        String r1 = mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(gson.toJson(user)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(r1, HelloController.failedResult);

        user.setUserName("");
        String r2 = mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(gson.toJson(user)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(r2, HelloController.failedResult);

        user.setUserName("SD");
        String r3 = mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(gson.toJson(user)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(r3, HelloController.successResult);
    }

}