package com.appfinalstaffingporal.myapp.repository.search;

import com.appfinalstaffingporal.myapp.domain.Entry;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Entry entity.
 */
public interface EntrySearchRepository extends ElasticsearchRepository<Entry, Long> {
}
