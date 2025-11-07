package com.oliveiralucas.barber_time.repository;

import com.oliveiralucas.barber_time.enums.AppointmentStatusEnum;
import com.oliveiralucas.barber_time.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.time.LocalDateTime;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findAllByShopIdAndStartAtBetweenOrderByStartAt(Long shopId, LocalDateTime startAt, LocalDateTime endAt);

    List<Appointment> findAllByBarberIdAndStartAtBetweenOrderByStartAt(Long barberId, LocalDateTime startAt, LocalDateTime endAt);

    List<Appointment> findAllByCustomerIdAndStartAtGreaterThanEqualOrderByStartAt(Long customerId, LocalDateTime startAt);

    List<Appointment> findAllByAppointmentStatusOrderByStartAt(AppointmentStatusEnum status);

    boolean existsByBarberIdAndAppointmentStatusNotInAndStartAtLessThanAndEndAtGreaterThan(Long barberId,
                                                                                           Iterable<AppointmentStatusEnum> appointmentStatus,
                                                                                           LocalDateTime endAt, LocalDateTime startAt);

}
