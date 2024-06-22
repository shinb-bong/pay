package summer.pay.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import summer.pay.domain.Salary;

@Repository
public interface SalaryRepository extends JpaRepository<Salary,Long> {
	Page<Salary> findAllByPayDateBetween(LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);
}
