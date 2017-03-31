package com.boozallenstaffingportalapp.myapp.repository.search;

import com.boozallenstaffingportalapp.myapp.domain.Tag;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Tag entity.
 */
public interface TagSearchRepository extends ElasticsearchRepository<Tag, Long> {
}
