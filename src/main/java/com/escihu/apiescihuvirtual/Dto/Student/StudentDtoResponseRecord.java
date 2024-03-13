package com.escihu.apiescihuvirtual.Dto.Student;

import com.escihu.apiescihuvirtual.persistence.Entity.Address.Address;
import com.escihu.apiescihuvirtual.persistence.Entity.Enums.StatusStudent;

public record StudentDtoResponseRecord(StatusStudent statusAlumno, String matricula, String nombre, String apellidoPaterno, String apellidoMaterno, String nombreCarrera, String curp, String nacionalidad, String sexo, String tipoSangre, String estadoCivil, String telefono, String celular, String ingresoMensual, String institucionProcedencia, String institucionProcedenciaEstado, String institucionProcedenciaMunicipio, String correoPersonal, String correoEscolar, Address direccion) {}