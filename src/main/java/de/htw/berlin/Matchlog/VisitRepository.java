package de.htw.berlin.Matchlog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    List<Visit> findByUserIdOrderByDateDesc(String userId);

    List<Visit> findByVisibilityOrderByDateDesc(Visibility visibility);
}
