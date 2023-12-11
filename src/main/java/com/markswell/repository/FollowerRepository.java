package com.markswell.repository;

import com.markswell.domain.Follower;
import com.markswell.domain.User;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Map;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Follower> {

    public boolean isFollower(User user, User follower) {

        Map<String, Object> params = Parameters
                .with("follower", follower)
                .and("user", user)
                .map();

        return find("follower = :follower and user = :user", params)
                .firstResultOptional()
                .isPresent();
    }

    public List<Follower> findByUser(User user) {
        Map<String, Object> parameters = Parameters.with("user", user).map();
        PanacheQuery<Follower> panacheQuery = find("user = :user", parameters);
        return panacheQuery.list();
    }

    public void deleteByUserAndFollower(User user, User follower) {
        Map<String, Object> params = Parameters
                .with("follower", follower)
                .and("user", user)
                .map();
        delete("follower = :follower and user = :user", params);
    }
}
