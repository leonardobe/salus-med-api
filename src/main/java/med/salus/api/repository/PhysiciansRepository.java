package med.salus.api.repository;

import med.salus.api.domain.entity.Physician;
import med.salus.api.domain.valueobject.Specialty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface PhysiciansRepository extends JpaRepository<Physician, Long> {
    Page<Physician> findAllByActiveTrue(Pageable pp);

    @Query("""
            SELECT p
            FROM Physician p
            LEFT JOIN Appointment a
                ON a.physician = p
                AND FUNCTION('DATE', a.appointmentDate) = FUNCTION('DATE', :date)
            WHERE p.active = true
            AND p.specialty = :specialty
            AND p.id NOT IN (
                SELECT a2.physician.id
                FROM Appointment a2
                WHERE a2.appointmentDate = :date
            )
            GROUP BY p
            ORDER BY COUNT(a.id) ASC
        """)
    Page<Physician> findAvailableBySpecialtyOrderByBusy(
            @Param("specialty") Specialty specialty, @Param("date") LocalDateTime date, Pageable pageable);

    @Query("""
        select p.active
        from Physician p
        where p.id = :id
        """)
    Boolean findByIdAndActiveTrue(Long id);
}
