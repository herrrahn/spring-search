package com.rahn.springsearch.init;

import com.rahn.springsearch.user.User;
import com.rahn.springsearch.user.UserRepository;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Component
public class DataInitializr implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;

    private final EntityManagerFactory entityManagerFactory;

    public DataInitializr(UserRepository userRepository, EntityManagerFactory entityManagerFactory) {
        this.userRepository = userRepository;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {


        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            this.createUser("Rafael Rahn", "rafael.rahn@rahn.com", "1234 065 7890");
            this.createUser("Ronaldo Rahn", "ronaldo@rahn.com", "987 123 6555");
            this.createUser("Elaine Silva", "elaine@live.com", "998 757 8888");
        }
        try {
            this.rebuildInexes();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void createUser(String name, String email, String phoneNumber) {

        User user = new User(name, email, phoneNumber);
        userRepository.save(user);
    }

    public void rebuildInexes() throws InterruptedException {
        EntityManager em = entityManagerFactory.createEntityManager();
        FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
        fullTextEntityManager.createIndexer().startAndWait();
    }

}

