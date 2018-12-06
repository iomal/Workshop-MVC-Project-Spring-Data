package app.ccb.repositories;

import java.util.List;
import app.ccb.domain.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Optional<Employee> findByFirstNameAndLastName(String firstName, String lastName);

    @Query (value="SELECT e From app.ccb.domain.entities.Employee as e " +
            "order by size(e.clients) desc, e.id asc")
    List<Employee> employeesByClients();

}
