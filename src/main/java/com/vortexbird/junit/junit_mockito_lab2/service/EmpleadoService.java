package com.vortexbird.junit.junit_mockito_lab2.service;

import com.vortexbird.junit.junit_mockito_lab2.model.Empleado;

public interface EmpleadoService {

	public void guardarEmpleado(Empleado empleado) throws Exception;
	
	public void eliminarempleado(int codigoEmpleado) throws Exception;
	
	public Empleado consultarEmpleadoPorCodigo(int codigoEmpleado) throws Exception;
	
}
