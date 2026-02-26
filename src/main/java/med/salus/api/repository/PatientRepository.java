package med.salus.api.repository;

import med.salus.api.domain.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Page<Patient> findAllByActiveTrue(Pageable pp);

    @Query("""
        select p.active
        from Patient p
        where p.id = :id
        """)
    Boolean isActivePatient(Long id);
}
