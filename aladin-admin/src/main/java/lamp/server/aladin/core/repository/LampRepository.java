package lamp.server.aladin.core.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

@NoRepositoryBean
public interface LampRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID>, QueryDslPredicateExecutor<T> {
}
