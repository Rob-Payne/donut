package com.example.donuts;

import com.example.donuts.dao.DonutsRepository;
import com.example.donuts.model.Donuts;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;
import java.sql.Date;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DonutsControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    DonutsRepository donutsRepository;

    @Test
    @Transactional
    @Rollback
    public void listDonutsTest() throws Exception{
        //Create new object and assign parameters
        Donuts newDonuts = new Donuts();
        newDonuts.setName("Donut1");
        newDonuts.setTopping("Sprinkles");
        newDonuts.setExpiration(Date.valueOf("2021-12-31"));
        Long newDonutsId = donutsRepository.save(newDonuts).getId();

        MockHttpServletRequestBuilder listDonutsTest = get("/donuts")
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(listDonutsTest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name",is("Donut1")))
                .andExpect(jsonPath("$[0].topping", is("Sprinkles")))
                .andExpect(jsonPath("$[0].expiration", is("2021-12-31")));
    }

    @Test
    @Transactional
    @Rollback
    public void readDonutsByIdTest() throws Exception{
        //Create new object
        Donuts newDonuts = new Donuts();
        newDonuts.setName("Donut1");
        newDonuts.setTopping("Sprinkles");
        newDonuts.setExpiration(Date.valueOf("2021-12-31"));
        Long newDonutsId = donutsRepository.save(newDonuts).getId();

        MockHttpServletRequestBuilder readDonutsTest = get("/donuts/" + newDonutsId)
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(readDonutsTest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("Donut1")))
                .andExpect(jsonPath("$.topping", is("Sprinkles")))
                .andExpect(jsonPath("$.expiration", is("2021-12-31")));
    }

    @Test
    @Transactional
    @Rollback
    public void createDonutsTest() throws Exception{
        MockHttpServletRequestBuilder createDonutsTest = post("/donuts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Donut2\",\"topping\":\"Not Sprinkles\", \"expiration\":\"2021-12-01\"}");

        this.mvc.perform(createDonutsTest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("Donut2")))
                .andExpect(jsonPath("$.topping", is("Not Sprinkles")))
                .andExpect(jsonPath("$.expiration", is("2021-12-01")));
    }

    @Test
    @Transactional
    @Rollback
    public void deleteDonutsTest() throws Exception{
        //Create new object and assign parameters
        Donuts newDonuts = new Donuts();
        newDonuts.setName("Donut3");
        newDonuts.setTopping("Sprinkles");
        newDonuts.setExpiration(Date.valueOf("2021-12-31"));
        Long newDonutsId = donutsRepository.save(newDonuts).getId();

        //This checks to make sure the test donut actually exists. Otherwise,
        //our delete test would pass, even though it didn't actually work.
        MockHttpServletRequestBuilder donutsCreationTest = get("/donuts/"+newDonutsId)
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(donutsCreationTest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        //Test donut created, now we delete it
        MockHttpServletRequestBuilder deleteDonutsTest = delete("/donuts/"+newDonutsId)
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(deleteDonutsTest)
                .andExpect(status().isOk());

        //Now check to see if the deleted donut still exists
        MockHttpServletRequestBuilder checkDeletedDonutsTest = get("/donuts/"+newDonutsId)
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(checkDeletedDonutsTest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @Transactional
    @Rollback
    public void patchDonutsTest() throws Exception{
        //Create new object and assign parameters
        Donuts newDonuts = new Donuts();
        newDonuts.setName("Donut1");
        newDonuts.setTopping("Sprinkles");
        newDonuts.setExpiration(Date.valueOf("2021-12-31"));
        Long newDonutsId = donutsRepository.save(newDonuts).getId();

        MockHttpServletRequestBuilder patchDonutsTest = patch("/donuts/"+newDonutsId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Donut2\",\"topping\":\"Not Sprinkles\", \"expiration\":\"2021-12-01\"}");

        this.mvc.perform(patchDonutsTest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("Donut2")))
                .andExpect(jsonPath("$.topping", is("Not Sprinkles")))
                .andExpect(jsonPath("$.expiration", is("2021-12-01")));
    }
}
