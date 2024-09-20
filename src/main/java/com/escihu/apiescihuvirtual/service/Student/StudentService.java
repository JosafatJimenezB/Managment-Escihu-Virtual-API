package com.escihu.apiescihuvirtual.service.Student;

import com.escihu.apiescihuvirtual.Dto.Student.StudentDtoRequest;
import com.escihu.apiescihuvirtual.Dto.Student.StudentDtoResponse;
import com.escihu.apiescihuvirtual.Dto.Student.StudentUpdateDtoRequest;
import com.escihu.apiescihuvirtual.persistence.Entity.Enums.StatusStudent;
import com.escihu.apiescihuvirtual.persistence.Entity.Student.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    /**
     * Crea un nuevo estudiante.
     *
     * @param studentDtoRequest objeto con los datos estudiante {@link StudentDtoRequest}
     * @return el estudiante creado {@link Student}
     */
    Student createStudent(StudentDtoRequest studentDtoRequest);

    /**
     * Actualiza un estudiante.
     *
     * @param studentId el id del estudiante a ser actualizado
     * @param studentDtoRequest objeto con los nuevos datos del estudiante {@link StudentUpdateDtoRequest}
     * @return el estudiante actualizado {@link Student}
     */
    Student updateStudent(Long studentId, StudentUpdateDtoRequest studentDtoRequest);

    /**
     * Lista todos los estudiantes.
     *
     * @return una lista de objetos {@link StudentDtoResponse} con todos los estudiantes
     */
    Page<StudentDtoResponse> listStudentsPaginated(Pageable pageable);
    /**
     * Lista todos los estudiantes paginados.
     *
     * @return un objeto Page con los estudiantes
     */
    List<StudentDtoResponse> listStudents();

    /**
     * Obtiene un estudiante por su id.
     *
     * @param studentId el id del estudiante
     * @return el estudiante {@link Student}
     */
    Student getStudentById(Long studentId);

    /**
     * Obtiene un estudiante por su id y username.
     * @param studentId el id del estudiante a buscar
     * @return el estudiante {@link Student}
     */
    Optional<Student> findById(Long studentId);

    /**
     * Verifica si un estudiante existe.
     * @param studentId el id del estudiante a verificar
     * @return true si el estudiante existe, false si no existe
     */
    boolean existsStudent(Long studentId);

    List<Student> findStudentsByStatusAlumno(StatusStudent alumnoStatus);

}
