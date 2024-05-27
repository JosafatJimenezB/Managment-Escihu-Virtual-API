
create table address (
                         numero_exterior integer,
                         numero_interior integer,
                         id bigserial not null,
                         colonia varchar(255),
                         cp varchar(255),
                         direccion varchar(255),
                         estado varchar(255),
                         localidad varchar(255),
                         municipio varchar(255),
                         primary key (id)
);
create table attendance (
                            type_attendance smallint check (type_attendance between 0 and 1),
                            date timestamp(6),
                            id bigserial not null,
                            user_id bigint not null,
                            primary key (id)
);
create table classrooms (
                            created_at timestamp(6),
                            id bigserial not null,
                            updated_at timestamp(6),
                            name varchar(20),
                            description varchar(50),
                            primary key (id)
);
create table course_students (
                                 course_id bigint not null,
                                 student_id bigint not null
);
create table courses (
                         classroom_id bigint,
                         created_at timestamp(6),
                         cycle_id bigint not null,
                         id bigserial not null,
                         manager_user_id bigint,
                         updated_at timestamp(6),
                         name varchar(50) not null,
                         primary key (id)
);
create table cycles (
                        closed_at timestamp(6),
                        created_at timestamp(6),
                        id bigserial not null,
                        updated_at timestamp(6),
                        name varchar(20),
                        primary key (id)
);
create table grade_details (
                               score float(53) not null,
                               created_at timestamp(6),
                               deleted_at timestamp(6),
                               grade_id bigint,
                               id bigserial not null,
                               student_id bigint,
                               updated_at timestamp(6),
                               delete_comment varchar(255),
                               primary key (id)
);
create table grades (
                        created_at timestamp(6),
                        id bigserial not null,
                        subject_id bigint,
                        updated_at timestamp(6),
                        description varchar(255),
                        primary key (id)
);
create table licenciatura (
                              code smallint unique,
                              id bigserial not null,
                              name varchar(255),
                              primary key (id)
);
create table roles (
                       role_id bigserial not null,
                       authority varchar(255),
                       primary key (role_id)
);
create table students (
                          estado_civil smallint check (estado_civil between 0 and 3),
                          matricula varchar(7),
                          direccion_id bigint unique,
                          id bigserial not null,
                          licenciatura_id bigint,
                          user_id bigint unique,
                          apellido_materno varchar(255),
                          apellido_paterno varchar(255),
                          celular varchar(255),
                          correo_escolar varchar(255),
                          correo_personal varchar(255),
                          curp varchar(255) unique,
                          ingreso_mensual varchar(255),
                          institucion_procedencia varchar(255),
                          institucion_procedencia_estado varchar(255),
                          institucion_procedencia_municipio varchar(255),
                          nacionalidad varchar(255),
                          nombre varchar(255),
                          nss varchar(255),
                          sexo varchar(255) check (sexo in ('MASCULINO','FEMENINO')),
                          status_alumno varchar(255) check (status_alumno in ('PROCESO_INSCRIPCION','INACTIVO','ACTIVO','PROCESO_REINSCRIPCION','BAJA','EGRESADO')),
                          telefono varchar(255),
                          tipo_sangre varchar(255),
                          primary key (id)
);
create table subject_schedules (
                                   end_time time(6) not null,
                                   start_time time(6) not null,
                                   week_day integer,
                                   id bigserial not null,
                                   subject_id bigint,
                                   primary key (id)
);
create table subjects (
                          classroom_id bigint not null,
                          course_id bigint not null,
                          id bigserial not null,
                          teacher_id bigint not null,
                          name varchar(50),
                          primary key (id)
);
create table teachers (
                          direccion_id bigint unique,
                          fecha_baja timestamp(6),
                          fecha_nacimiento timestamp(6),
                          id bigserial not null,
                          user_id bigint unique,
                          curp varchar(18) unique,
                          apellido_materno varchar(255),
                          apellido_paterno varchar(255),
                          area_conocimientos varchar(255),
                          cedula_profesional varchar(255),
                          correo_escolar varchar(255),
                          correo_personal varchar(255),
                          estado_civil varchar(255) check (estado_civil in ('CASADO','DIVORCIADO','VIUDO','SOLTERO')),
                          grado_estudios varchar(255),
                          nacionalidad varchar(255),
                          nombre varchar(255),
                          rfc varchar(255) unique,
                          sexo varchar(255) check (sexo in ('MASCULINO','FEMENINO')),
                          status_docente varchar(255) check (status_docente in ('ACTIVO','INACTIVO')),
                          tipo_sangre varchar(255),
                          primary key (id)
);
create table user_role_function (
                                    role_id bigint not null,
                                    user_id bigint not null,
                                    primary key (role_id, user_id)
);
create table users (
                       user_id bigserial not null,
                       email varchar(255) unique,
                       password varchar(255),
                       username varchar(255) unique,
                       primary key (user_id)
);
alter table if exists attendance
    add constraint FKjcaqd29v2qy723owsdah2t8vx
        foreign key (user_id)
            references users;
alter table if exists course_students
    add constraint FKm3befe0jxxln54ulu74nn9gr0
        foreign key (student_id)
            references students;
alter table if exists course_students
    add constraint FKj5fbpmgy0y0es0gvk0311jor3
        foreign key (course_id)
            references courses;
alter table if exists courses
    add constraint FKpeln8go5av13clo5fglfep9xb
        foreign key (classroom_id)
            references classrooms;
alter table if exists courses
    add constraint FKh6r08erlv93llbr5ryr2rbhff
        foreign key (cycle_id)
            references cycles
;alter table if exists courses
    add constraint FKaaehitk68e9ixnh8ou0vr3thl
        foreign key (manager_user_id)
            references teachers;
alter table if exists grade_details
    add constraint FKt4hdwy0s3h3ub7maftyk5w488
        foreign key (grade_id)
            references grades;
alter table if exists grade_details
    add constraint FKnqk7hj7yhsou19wvqggssk6yd
        foreign key (student_id)
            references students;
alter table if exists grades
    add constraint FKrc0s5tgvm9r4ccxitaqtu88k5
        foreign key (subject_id)
            references subjects;
alter table if exists students
    add constraint FKoat4lsnrnjmeiwvr0kih07t68
        foreign key (direccion_id)
            references address;
alter table if exists students
    add constraint FK30aw76eejael3y1lrrgbsiytb
        foreign key (licenciatura_id)
            references licenciatura;
alter table if exists students
    add constraint FKdt1cjx5ve5bdabmuuf3ibrwaq
        foreign key (user_id)
            references users;
alter table if exists subject_schedules
    add constraint FKq1mcih8aojh1uf9lgqi9k0fru
        foreign key (subject_id)
            references subjects;
alter table if exists subjects
    add constraint FKerwwo9vivn43v020xukwla4iw
        foreign key (classroom_id)
            references classrooms;
alter table if exists subjects
    add constraint FKr4k4crqhj5ojibxp458ndbywv
        foreign key (course_id)
            references courses;
alter table if exists subjects
    add constraint FKsjy6ghvvelraa2w9mhv3bbnys
        foreign key (teacher_id)
            references teachers;
alter table if exists teachers
    add constraint FKkvj91ys9ywm51v0vrtkt3mylq
        foreign key (direccion_id)
            references address;
alter table if exists teachers
    add constraint FKb8dct7w2j1vl1r2bpstw5isc0
        foreign key (user_id)
            references users;
alter table if exists user_role_function
    add constraint FKd6necos8jqgyy8c0askwojih4
        foreign key (role_id)
            references roles;
alter table if exists user_role_function
    add constraint FKopi52oq3ywmk6mnx2y84jaauj
        foreign key (user_id)
            references users

