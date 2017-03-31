package com.appfinalstaffingporal.myapp.web.rest;

import com.appfinalstaffingporal.myapp.BahStaffingPortalApp;

import com.appfinalstaffingporal.myapp.domain.Entry;
import com.appfinalstaffingporal.myapp.repository.EntryRepository;
import com.appfinalstaffingporal.myapp.repository.search.EntrySearchRepository;
import com.appfinalstaffingporal.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.appfinalstaffingporal.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EntryResource REST controller.
 *
 * @see EntryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BahStaffingPortalApp.class)
public class EntryResourceIntTest {

    private static final ZonedDateTime DEFAULT_ENTRYDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ENTRYDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CANDIDATENAME = "AAAAAAAAAA";
    private static final String UPDATED_CANDIDATENAME = "BBBBBBBBBB";

    private static final String DEFAULT_MAJOR = "AAAAAAAAAA";
    private static final String UPDATED_MAJOR = "BBBBBBBBBB";

    private static final String DEFAULT_POOL = "AAAAAAAAAA";
    private static final String UPDATED_POOL = "BBBBBBBBBB";

    private static final String DEFAULT_SCHOOL = "AAAAAAAAAA";
    private static final String UPDATED_SCHOOL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_GRADUATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_GRADUATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_AVAILABILITY = "AAAAAAAAAA";
    private static final String UPDATED_AVAILABILITY = "BBBBBBBBBB";

    private static final String DEFAULT_RECRUITER = "AAAAAAAAAA";
    private static final String UPDATED_RECRUITER = "BBBBBBBBBB";

    private static final String DEFAULT_INFORMATION = "AAAAAAAAAA";
    private static final String UPDATED_INFORMATION = "BBBBBBBBBB";

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private EntrySearchRepository entrySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEntryMockMvc;

    private Entry entry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EntryResource entryResource = new EntryResource(entryRepository, entrySearchRepository);
        this.restEntryMockMvc = MockMvcBuilders.standaloneSetup(entryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entry createEntity(EntityManager em) {
        Entry entry = new Entry()
            .entrydate(DEFAULT_ENTRYDATE)
            .candidatename(DEFAULT_CANDIDATENAME)
            .major(DEFAULT_MAJOR)
            .pool(DEFAULT_POOL)
            .school(DEFAULT_SCHOOL)
            .graduation(DEFAULT_GRADUATION)
            .availability(DEFAULT_AVAILABILITY)
            .recruiter(DEFAULT_RECRUITER)
            .information(DEFAULT_INFORMATION);
        return entry;
    }

    @Before
    public void initTest() {
        entrySearchRepository.deleteAll();
        entry = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntry() throws Exception {
        int databaseSizeBeforeCreate = entryRepository.findAll().size();

        // Create the Entry
        restEntryMockMvc.perform(post("/api/entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entry)))
            .andExpect(status().isCreated());

        // Validate the Entry in the database
        List<Entry> entryList = entryRepository.findAll();
        assertThat(entryList).hasSize(databaseSizeBeforeCreate + 1);
        Entry testEntry = entryList.get(entryList.size() - 1);
        assertThat(testEntry.getEntrydate()).isEqualTo(DEFAULT_ENTRYDATE);
        assertThat(testEntry.getCandidatename()).isEqualTo(DEFAULT_CANDIDATENAME);
        assertThat(testEntry.getMajor()).isEqualTo(DEFAULT_MAJOR);
        assertThat(testEntry.getPool()).isEqualTo(DEFAULT_POOL);
        assertThat(testEntry.getSchool()).isEqualTo(DEFAULT_SCHOOL);
        assertThat(testEntry.getGraduation()).isEqualTo(DEFAULT_GRADUATION);
        assertThat(testEntry.getAvailability()).isEqualTo(DEFAULT_AVAILABILITY);
        assertThat(testEntry.getRecruiter()).isEqualTo(DEFAULT_RECRUITER);
        assertThat(testEntry.getInformation()).isEqualTo(DEFAULT_INFORMATION);

        // Validate the Entry in Elasticsearch
        Entry entryEs = entrySearchRepository.findOne(testEntry.getId());
        assertThat(entryEs).isEqualToComparingFieldByField(testEntry);
    }

    @Test
    @Transactional
    public void createEntryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entryRepository.findAll().size();

        // Create the Entry with an existing ID
        entry.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntryMockMvc.perform(post("/api/entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entry)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Entry> entryList = entryRepository.findAll();
        assertThat(entryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEntrydateIsRequired() throws Exception {
        int databaseSizeBeforeTest = entryRepository.findAll().size();
        // set the field null
        entry.setEntrydate(null);

        // Create the Entry, which fails.

        restEntryMockMvc.perform(post("/api/entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entry)))
            .andExpect(status().isBadRequest());

        List<Entry> entryList = entryRepository.findAll();
        assertThat(entryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCandidatenameIsRequired() throws Exception {
        int databaseSizeBeforeTest = entryRepository.findAll().size();
        // set the field null
        entry.setCandidatename(null);

        // Create the Entry, which fails.

        restEntryMockMvc.perform(post("/api/entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entry)))
            .andExpect(status().isBadRequest());

        List<Entry> entryList = entryRepository.findAll();
        assertThat(entryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEntries() throws Exception {
        // Initialize the database
        entryRepository.saveAndFlush(entry);

        // Get all the entryList
        restEntryMockMvc.perform(get("/api/entries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entry.getId().intValue())))
            .andExpect(jsonPath("$.[*].entrydate").value(hasItem(sameInstant(DEFAULT_ENTRYDATE))))
            .andExpect(jsonPath("$.[*].candidatename").value(hasItem(DEFAULT_CANDIDATENAME.toString())))
            .andExpect(jsonPath("$.[*].major").value(hasItem(DEFAULT_MAJOR.toString())))
            .andExpect(jsonPath("$.[*].pool").value(hasItem(DEFAULT_POOL.toString())))
            .andExpect(jsonPath("$.[*].school").value(hasItem(DEFAULT_SCHOOL.toString())))
            .andExpect(jsonPath("$.[*].graduation").value(hasItem(sameInstant(DEFAULT_GRADUATION))))
            .andExpect(jsonPath("$.[*].availability").value(hasItem(DEFAULT_AVAILABILITY.toString())))
            .andExpect(jsonPath("$.[*].recruiter").value(hasItem(DEFAULT_RECRUITER.toString())))
            .andExpect(jsonPath("$.[*].information").value(hasItem(DEFAULT_INFORMATION.toString())));
    }

    @Test
    @Transactional
    public void getEntry() throws Exception {
        // Initialize the database
        entryRepository.saveAndFlush(entry);

        // Get the entry
        restEntryMockMvc.perform(get("/api/entries/{id}", entry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entry.getId().intValue()))
            .andExpect(jsonPath("$.entrydate").value(sameInstant(DEFAULT_ENTRYDATE)))
            .andExpect(jsonPath("$.candidatename").value(DEFAULT_CANDIDATENAME.toString()))
            .andExpect(jsonPath("$.major").value(DEFAULT_MAJOR.toString()))
            .andExpect(jsonPath("$.pool").value(DEFAULT_POOL.toString()))
            .andExpect(jsonPath("$.school").value(DEFAULT_SCHOOL.toString()))
            .andExpect(jsonPath("$.graduation").value(sameInstant(DEFAULT_GRADUATION)))
            .andExpect(jsonPath("$.availability").value(DEFAULT_AVAILABILITY.toString()))
            .andExpect(jsonPath("$.recruiter").value(DEFAULT_RECRUITER.toString()))
            .andExpect(jsonPath("$.information").value(DEFAULT_INFORMATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEntry() throws Exception {
        // Get the entry
        restEntryMockMvc.perform(get("/api/entries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntry() throws Exception {
        // Initialize the database
        entryRepository.saveAndFlush(entry);
        entrySearchRepository.save(entry);
        int databaseSizeBeforeUpdate = entryRepository.findAll().size();

        // Update the entry
        Entry updatedEntry = entryRepository.findOne(entry.getId());
        updatedEntry
            .entrydate(UPDATED_ENTRYDATE)
            .candidatename(UPDATED_CANDIDATENAME)
            .major(UPDATED_MAJOR)
            .pool(UPDATED_POOL)
            .school(UPDATED_SCHOOL)
            .graduation(UPDATED_GRADUATION)
            .availability(UPDATED_AVAILABILITY)
            .recruiter(UPDATED_RECRUITER)
            .information(UPDATED_INFORMATION);

        restEntryMockMvc.perform(put("/api/entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEntry)))
            .andExpect(status().isOk());

        // Validate the Entry in the database
        List<Entry> entryList = entryRepository.findAll();
        assertThat(entryList).hasSize(databaseSizeBeforeUpdate);
        Entry testEntry = entryList.get(entryList.size() - 1);
        assertThat(testEntry.getEntrydate()).isEqualTo(UPDATED_ENTRYDATE);
        assertThat(testEntry.getCandidatename()).isEqualTo(UPDATED_CANDIDATENAME);
        assertThat(testEntry.getMajor()).isEqualTo(UPDATED_MAJOR);
        assertThat(testEntry.getPool()).isEqualTo(UPDATED_POOL);
        assertThat(testEntry.getSchool()).isEqualTo(UPDATED_SCHOOL);
        assertThat(testEntry.getGraduation()).isEqualTo(UPDATED_GRADUATION);
        assertThat(testEntry.getAvailability()).isEqualTo(UPDATED_AVAILABILITY);
        assertThat(testEntry.getRecruiter()).isEqualTo(UPDATED_RECRUITER);
        assertThat(testEntry.getInformation()).isEqualTo(UPDATED_INFORMATION);

        // Validate the Entry in Elasticsearch
        Entry entryEs = entrySearchRepository.findOne(testEntry.getId());
        assertThat(entryEs).isEqualToComparingFieldByField(testEntry);
    }

    @Test
    @Transactional
    public void updateNonExistingEntry() throws Exception {
        int databaseSizeBeforeUpdate = entryRepository.findAll().size();

        // Create the Entry

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEntryMockMvc.perform(put("/api/entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entry)))
            .andExpect(status().isCreated());

        // Validate the Entry in the database
        List<Entry> entryList = entryRepository.findAll();
        assertThat(entryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEntry() throws Exception {
        // Initialize the database
        entryRepository.saveAndFlush(entry);
        entrySearchRepository.save(entry);
        int databaseSizeBeforeDelete = entryRepository.findAll().size();

        // Get the entry
        restEntryMockMvc.perform(delete("/api/entries/{id}", entry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean entryExistsInEs = entrySearchRepository.exists(entry.getId());
        assertThat(entryExistsInEs).isFalse();

        // Validate the database is empty
        List<Entry> entryList = entryRepository.findAll();
        assertThat(entryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEntry() throws Exception {
        // Initialize the database
        entryRepository.saveAndFlush(entry);
        entrySearchRepository.save(entry);

        // Search the entry
        restEntryMockMvc.perform(get("/api/_search/entries?query=id:" + entry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entry.getId().intValue())))
            .andExpect(jsonPath("$.[*].entrydate").value(hasItem(sameInstant(DEFAULT_ENTRYDATE))))
            .andExpect(jsonPath("$.[*].candidatename").value(hasItem(DEFAULT_CANDIDATENAME.toString())))
            .andExpect(jsonPath("$.[*].major").value(hasItem(DEFAULT_MAJOR.toString())))
            .andExpect(jsonPath("$.[*].pool").value(hasItem(DEFAULT_POOL.toString())))
            .andExpect(jsonPath("$.[*].school").value(hasItem(DEFAULT_SCHOOL.toString())))
            .andExpect(jsonPath("$.[*].graduation").value(hasItem(sameInstant(DEFAULT_GRADUATION))))
            .andExpect(jsonPath("$.[*].availability").value(hasItem(DEFAULT_AVAILABILITY.toString())))
            .andExpect(jsonPath("$.[*].recruiter").value(hasItem(DEFAULT_RECRUITER.toString())))
            .andExpect(jsonPath("$.[*].information").value(hasItem(DEFAULT_INFORMATION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Entry.class);
    }
}
