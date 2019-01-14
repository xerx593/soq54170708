package com.spring.data.jpa;

import com.spring.data.jpa.model.A;
import com.spring.data.jpa.model.B;
import com.spring.data.jpa.repo.ARepository;
import com.spring.data.jpa.repo.BRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author xerx593
 */
@SpringBootApplication
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner demo(ARepository repository, BRepository bRepo, TransactionalService txClass) {
        return (args) -> {
            //adds 1 A with 10 B's (associated)
            txClass.doAddWork();
            // test findAll
            LOG.info("A found with findAll():");
            LOG.info("-------------------------------");
            for (A a : repository.findAll()) {
                LOG.info(a.toString());
            }
            LOG.info("");

            // test find1
            repository.findById(1L)
                    .ifPresent(lA -> {
                        LOG.info("A found with findById(1L):");
                        LOG.info("--------------------------------");
                        LOG.info(lA.toString());
                        LOG.info("");
                    });
            logBs(bRepo.findAll());
            txClass.modifyOne();
            LOG.info("");
            logBs(bRepo.findAll());
            txClass.modifyAll();
            LOG.info("");
            logBs(bRepo.findAll());
        };
    }

    private void logBs(Iterable<B> bs) {
        LOG.info("B found with findAll():");
        LOG.info("-------------------------------");
        for (B b : bs) {
            LOG.info(b.toString());
            LOG.info(b.getSomeString());
            LOG.info("a_id: {}", b.getA().getId());
        }
        LOG.info("");
    }
}
