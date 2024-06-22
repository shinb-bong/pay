package summer.pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import summer.pay.domain.Salary;

@Repository
public interface SalaryRepository extends JpaRepository<Salary,Long> {
}
