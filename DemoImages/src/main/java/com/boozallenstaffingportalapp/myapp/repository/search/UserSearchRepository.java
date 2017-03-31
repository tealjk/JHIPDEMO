package com.boozallenstaffingportalapp.myapp.repository.search;

import com.boozallenstaffingportalapp.myapp.domain.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the User entity.
 */
public interface UserSearchRepository extends ElasticsearchRepository<User, Long> {
}
