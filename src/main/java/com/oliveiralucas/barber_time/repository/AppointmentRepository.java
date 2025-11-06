package com.oliveiralucas.barber_time.repository;

import com.oliveiralucas.barber_time.enums.AppointmentStatusEnum;
import com.oliveiralucas.barber_time.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByBarberIdAndAppointmentStatusNotInAndStartAtLessThanAndEndAtGreaterThan(Long barberId,
                                        Iterable<AppointmentStatusEnum> appointmentStatus,
                                        LocalDateTime endAt,
                                        LocalDateTime startAt);

}
