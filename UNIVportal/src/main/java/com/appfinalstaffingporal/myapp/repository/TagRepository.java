package com.appfinalstaffingporal.myapp.repository;

import com.appfinalstaffingporal.myapp.domain.Tag;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Tag entity.
 */
@SuppressWarnings("unused")
public interface TagRepository extends JpaRepository<Tag,Long> {

}
