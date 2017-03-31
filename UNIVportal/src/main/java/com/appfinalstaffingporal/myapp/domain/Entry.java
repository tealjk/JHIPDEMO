package com.appfinalstaffingporal.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Entry.
 */
@Entity
@Table(name = "entry")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "entry")
public class Entry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "entrydate", nullable = false)
    private ZonedDateTime entrydate;

    @NotNull
    @Column(name = "candidatename", nullable = false)
    private String candidatename;

    @Column(name = "major")
    private String major;

    @Column(name = "pool")
    private String pool;

    @Column(name = "school")
    private String school;

    @Column(name = "graduation")
    private ZonedDateTime graduation;

    @Column(name = "availability")
    private String availability;

    @Lob
    @Column(name = "recruiter")
    private String recruiter;

    @Lob
    @Column(name = "information")
    private String information;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "entry_tag",
               joinColumns = @JoinColumn(name="entries_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="tags_id", referencedColumnName="id"))
    private Set<Tag> tags = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getEntrydate() {
        return entrydate;
    }

    public Entry entrydate(ZonedDateTime entrydate) {
        this.entrydate = entrydate;
        return this;
    }

    public void setEntrydate(ZonedDateTime entrydate) {
        this.entrydate = entrydate;
    }

    public String getCandidatename() {
        return candidatename;
    }

    public Entry candidatename(String candidatename) {
        this.candidatename = candidatename;
        return this;
    }

    public void setCandidatename(String candidatename) {
        this.candidatename = candidatename;
    }

    public String getMajor() {
        return major;
    }

    public Entry major(String major) {
        this.major = major;
        return this;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getPool() {
        return pool;
    }

    public Entry pool(String pool) {
        this.pool = pool;
        return this;
    }

    public void setPool(String pool) {
        this.pool = pool;
    }

    public String getSchool() {
        return school;
    }

    public Entry school(String school) {
        this.school = school;
        return this;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public ZonedDateTime getGraduation() {
        return graduation;
    }

    public Entry graduation(ZonedDateTime graduation) {
        this.graduation = graduation;
        return this;
    }

    public void setGraduation(ZonedDateTime graduation) {
        this.graduation = graduation;
    }

    public String getAvailability() {
        return availability;
    }

    public Entry availability(String availability) {
        this.availability = availability;
        return this;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getRecruiter() {
        return recruiter;
    }

    public Entry recruiter(String recruiter) {
        this.recruiter = recruiter;
        return this;
    }

    public void setRecruiter(String recruiter) {
        this.recruiter = recruiter;
    }

    public String getInformation() {
        return information;
    }

    public Entry information(String information) {
        this.information = information;
        return this;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public Entry tags(Set<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public Entry addTag(Tag tag) {
        this.tags.add(tag);
        tag.getEntries().add(this);
        return this;
    }

    public Entry removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.getEntries().remove(this);
        return this;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entry entry = (Entry) o;
        if (entry.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, entry.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Entry{" +
            "id=" + id +
            ", entrydate='" + entrydate + "'" +
            ", candidatename='" + candidatename + "'" +
            ", major='" + major + "'" +
            ", pool='" + pool + "'" +
            ", school='" + school + "'" +
            ", graduation='" + graduation + "'" +
            ", availability='" + availability + "'" +
            ", recruiter='" + recruiter + "'" +
            ", information='" + information + "'" +
            '}';
    }
}
