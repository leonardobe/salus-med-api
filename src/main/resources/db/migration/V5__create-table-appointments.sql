create table appointments
(

    id               bigint   not null auto_increment,
    physician_id     bigint   not null,
    patient_id       bigint   not null,
    appointment_date datetime not null,

    primary key (id),
    constraint fk_appointment_physician_id foreign key (physician_id) references physicians (id),
    constraint fk_appointment_patient_id foreign key (patient_id) references patients (id)
);
