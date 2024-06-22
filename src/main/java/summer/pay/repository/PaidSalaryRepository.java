package summer.pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import summer.pay.domain.PaidSalary;

@Repository
public interface PaidSalaryRepository  extends JpaRepository<PaidSalary,Long> {
}
