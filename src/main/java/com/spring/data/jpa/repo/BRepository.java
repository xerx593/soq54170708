package com.spring.data.jpa.repo;

import com.spring.data.jpa.model.B;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Alexander Lutz, alexlutz79@gmail.com
 */
public interface BRepository extends CrudRepository<B, Long> {
    
}
