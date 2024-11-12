package org.thetravellingbard.polling;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
}
