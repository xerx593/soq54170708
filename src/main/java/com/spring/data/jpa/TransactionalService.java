package com.spring.data.jpa;

import com.spring.data.jpa.model.A;
import com.spring.data.jpa.model.B;
import com.spring.data.jpa.repo.ARepository;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alexander Lutz, alexlutz79@gmail.com
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class TransactionalService {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionalService.class);
    @Autowired
    private transient ARepository aRepository;

    public void doAddWork() {
        A a = aRepository.save(new A());
        LOG.info("adding b's.");
        for (int i = 1; i <= 10; i++) {
            B b = new B();
            b.setA(a);
            a.getBs().add(b);
        }
    }

    public void modifyOne() {
        aRepository.findById(1L).ifPresent(a -> {
            LOG.info("modifying 1 b.");
            if (a.getB(1L) == null) {
                LOG.info("!null!");
            } else {
                a.getB(1L).setSomeString("modified!");
            }
        });
    }

    public void modifyAll() {
        aRepository.findById(1L).ifPresent(a -> {
            LOG.info("modifying all b's.");
            for (B b : a.getBs()) {
                b.setSomeString("modified!");
            }
        });
    }
}
