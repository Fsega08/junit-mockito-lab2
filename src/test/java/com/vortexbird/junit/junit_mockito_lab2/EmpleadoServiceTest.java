package com.vortexbird.junit.junit_mockito_lab2;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vortexbird.junit.junit_mockito_lab2.model.Empleado;
import com.vortexbird.junit.junit_mockito_lab2.repository.EmpleadoRepository;
import com.vortexbird.junit.junit_mockito_lab2.service.EmpleadoServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceTest {

	@Mock
	private EmpleadoRepository empleadoRepository;
	
	@InjectMocks
	private EmpleadoServiceImpl empleadoService;
	
	
	
	@Test
	void guardarEmpleado_debe_guardar_empleado() throws Exception {
		
		/*
		 * *************************************
		 * Arrange
		 * sea empleado
		 * ************************************* 
		 */
		Empleado empleado = new Empleado();
		empleado.setCodigo(2020123);
		empleado.setNombre("Johan Bejarano");
		empleado.setEstado("A");
		
		//NO haga nada cuando llamen al mock.saveEmpleado
		doNothing().when(empleadoRepository).saveEmpleado(any(Empleado.class));
		
		/*
		 * *************************************
		 * Act
		 * Invoque la unidad a probar
		 * ************************************* 
		 */
		empleadoService.guardarEmpleado(empleado);
		
		
		/*
		 * *************************************
		 * Assert
		 * sea empleado
		 * ************************************* 
		 */
		
		//Verifique que se haya llamado el mock.saveEmpleado en el hilo de ejecución
		verify(empleadoRepository).saveEmpleado(any(Empleado.class));
		
	}
	
	@Test
	void guardarEmpleado_no_debe_guardar_empleado_por_codigo_invalido() throws Exception {
		
		/*
		 * *************************************
		 * Arrange
		 * sea empleado
		 * ************************************* 
		 */
		Empleado empleado = new Empleado();
		empleado.setCodigo(2020);
		empleado.setNombre("Johan Bejarano");
		empleado.setEstado("A");
		
		//NO haga nada cuando llamen al mock.saveEmpleado
		//lenient debido a que como se lanza una excepción antes de que
		//realmente se llame empleadoRepository.saveEmpleado
		lenient().doNothing().when(empleadoRepository).saveEmpleado(any(Empleado.class));
		
		/*
		 * *************************************
		 * Act and assert throws
		 * Invoque la unidad a probar
		 * ************************************* 
		 */
		//ASuegurse que se lanza una Excepción cuando se invoca gudardarEmpleado, con ese empleado configurado
		assertThrows(Exception.class, ()->{
			empleadoService.guardarEmpleado(empleado);
		});
		
		
	}
	
}
