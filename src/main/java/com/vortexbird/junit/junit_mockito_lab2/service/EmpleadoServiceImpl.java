package com.vortexbird.junit.junit_mockito_lab2.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.vortexbird.junit.junit_mockito_lab2.model.Empleado;
import com.vortexbird.junit.junit_mockito_lab2.repository.EmpleadoRepository;

public class EmpleadoServiceImpl implements EmpleadoService {

	private EmpleadoRepository empleadoRepository = new EmpleadoRepository(); 
	
	@Override
	public void guardarEmpleado(Empleado empleado) throws Exception {
		
		if(empleado == null) {
			throw new Exception("El empleado no puede ser null");
		}
		
		if (empleado.getCodigo()<=0) {
			throw new Exception("El código del empleado no puede ser cero o negativo");
		}
		
		//Regla de negocio: Los códigos de los empleados se estructuran así
		//4 primeros dígitos, es el anho de vinculación, los digitos restantes, son consecutivo numérico
		String codigoAlfanumerico = empleado.getCodigo() + "";
		if (codigoAlfanumerico.length()<=4){
			throw new Exception("Código de empleado, no cumple la estructura");
		}
		
		if (empleado.getNombre() == null || empleado.getNombre().trim().length()==0) {
			throw new Exception("El nombre del empleado no puede ser nulo o vacío");
		}
		
		if (empleado.getEstado()==null || !empleado.getEstado().equals("A")) {
			throw new Exception("El estado de un emeplado que inicia, debe ser = A");
		}
		
		empleadoRepository.saveEmpleado(empleado);

	}

	@Override
	public void eliminarempleado(int codigoEmpleado) throws Exception {
		if (codigoEmpleado<=0) {
			throw new Exception("El código del empleado a borrar, no es válido");
		}
		
		//Se consulta el empleado a borrar
		Empleado empleadoABorrar = consultarEmpleadoPorCodigo(codigoEmpleado);
		
		if (empleadoABorrar==null) {
			throw new Exception("No se pudo encontrar el empleado a borrar");
		}
		
		//Regla de negocio: Los códigos de los empleados se estructuran así
		//4 primeros dígitos, es el anho de vinculación, los digitos restantes, son consecutivo numérico
		//Si el anho de vinculación, es de hace más de 5 anhos atrás, no se puede eliminar el empleado por este método
		String codigoAlfanumerico = empleadoABorrar.getCodigo() + "";
		if (codigoAlfanumerico.length()<=4){
			throw new Exception("Código de empleado a borrar, no cumple la estructura");
		}
		
		int anhoVinculación = Integer.parseInt(codigoAlfanumerico.substring(0, 4));
		int anhoActual = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
		
		if ((anhoActual-anhoVinculación)>5) {
			throw new Exception("No se puede borrar un empleado, que lleve más de 5 anhos de vinculación");
		}
		
		empleadoRepository.deleteEmpleado(empleadoABorrar);
		
		
	}

	@Override
	public Empleado consultarEmpleadoPorCodigo(int codigoEmpleado) throws Exception {
		if (codigoEmpleado<=0) {
			throw new Exception("El código del empleado a consultar, no es válido");
		}
		
		return empleadoRepository.getEmpleadoByCodigo(codigoEmpleado);
	}

}
