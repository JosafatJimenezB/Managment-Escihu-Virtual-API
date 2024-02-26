package com.escihu.apiescihuvirtual.service.Student;

import com.escihu.apiescihuvirtual.Dto.Student.PaginatedStudentDtoResponse;
import com.escihu.apiescihuvirtual.Dto.Student.StudentDtoRequest;
import com.escihu.apiescihuvirtual.Dto.Student.StudentDtoResponse;
import com.escihu.apiescihuvirtual.persistence.Entity.Enums.StatusStudent;
import com.escihu.apiescihuvirtual.persistence.Entity.Student.Student;
import com.escihu.apiescihuvirtual.persistence.Repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @Override
    public Student createStudent(StudentDtoRequest studentDtoRequest) {
        Student student = Student.builder()
                .nombre(studentDtoRequest.getNombre())
                .apellidoPaterno(studentDtoRequest.getApellidoPaterno())
                .apellidoMaterno(studentDtoRequest.getApellidoMaterno())
                .celular(studentDtoRequest.getCelular())
                .curp(studentDtoRequest.getCurp())
                .correoEscolar(null)
                .estadoCivil(studentDtoRequest.getEstadoCivil())
                .sexo(studentDtoRequest.getSexo())
                .correoPersonal(studentDtoRequest.getCorreoPersonal())
                .nacionalidad(studentDtoRequest.getNacionalidad())
                .ingresoMensual(studentDtoRequest.getIngresoMensual())
                .direccion(studentDtoRequest.getDireccion())
                .matricula(null)
                .tipoSangre(studentDtoRequest.getTipoSangre())
                .nombreCarrera(studentDtoRequest.getNombreCarrera())
                .statusAlumno(StatusStudent.PROCESO_INSCRIPCION)
                .institucionProcedenciaEstado(studentDtoRequest.getInstitucionProcedenciaEstado())
                .telefono(studentDtoRequest.getTelefono())
                .institucionProcedencia(studentDtoRequest.getInstitucionProcedencia())
                .institucionProcedenciaMunicipio(studentDtoRequest.getInstitucionProcedenciaMunicipio())
                .build();

        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Long id, StudentDtoRequest studentDtoRequest) {
        Optional<Student> studentExists = studentRepository.findById(id);

        if(!studentExists.isPresent()){
            return null;
        }

        Student student = studentExists.get();

        student = Student.builder()
                .nombre(studentDtoRequest.getNombre())
                .apellidoPaterno(studentDtoRequest.getApellidoPaterno())
                .apellidoMaterno(studentDtoRequest.getApellidoMaterno())
                .celular(studentDtoRequest.getCelular())
                .curp(studentDtoRequest.getCurp())
                .correoEscolar(null)
                .estadoCivil(studentDtoRequest.getEstadoCivil())
                .sexo(studentDtoRequest.getSexo())
                .correoPersonal(studentDtoRequest.getCorreoPersonal())
                .nacionalidad(studentDtoRequest.getNacionalidad())
                .ingresoMensual(studentDtoRequest.getIngresoMensual())
                .direccion(studentDtoRequest.getDireccion())
                .matricula(null)
                .tipoSangre(studentDtoRequest.getTipoSangre())
                .nombreCarrera(studentDtoRequest.getNombreCarrera())
                .statusAlumno(StatusStudent.PROCESO_INSCRIPCION)
                .institucionProcedenciaEstado(studentDtoRequest.getInstitucionProcedenciaEstado())
                .telefono(studentDtoRequest.getTelefono())
                .institucionProcedencia(studentDtoRequest.getInstitucionProcedencia())
                .institucionProcedenciaMunicipio(studentDtoRequest.getInstitucionProcedenciaMunicipio())
                .build();

        return studentRepository.save(student);
    }

    @Override
    public PaginatedStudentDtoResponse listStudentsPaginated(Pageable pageable) {
        Page<Student> studentsPage = studentRepository.findAll(pageable);

        List<StudentDtoResponse> studentsDto = studentsPage.getContent().stream()
                .map(student -> new StudentDtoResponse(
                        student.getId(),
                        student.getStatusAlumno(),
                        student.getNombre(),
                        student.getApellidoPaterno(),
                        student.getApellidoMaterno(),
                        student.getNombreCarrera())
                ).collect(Collectors.toList());

        return new PaginatedStudentDtoResponse(
                studentsDto,
                studentsPage.getNumber(),
                studentsPage.getTotalPages(),
                studentsPage.getSize()
        );
    }

    @Override
    public List<StudentDtoResponse> listStudents() {

        List<Student> students = studentRepository.findAll();

        return students.stream()
                .map(student -> new StudentDtoResponse(
                        student.getId(),
                        student.getStatusAlumno(),
                        student.getNombre(),
                        student.getApellidoPaterno(),
                        student.getApellidoMaterno(),
                        student.getNombreCarrera())
                ).collect(Collectors.toList());
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public boolean exists(Long id) {
        return studentRepository.existsById(id);
    }

    public String generarMatricula(int año, String numeroCarrera, int numeroEstudiante) {
        String matricula = "";

        matricula += String.format("%02d", año);

        matricula += numeroCarrera;

        matricula += String.format("%03d", numeroEstudiante);

        return matricula;
    }
}
