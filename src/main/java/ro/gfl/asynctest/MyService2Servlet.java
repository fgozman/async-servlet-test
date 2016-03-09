package ro.gfl.asynctest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class MyService2Servlet
 */
@WebServlet(name="MyService2",urlPatterns="/2")
public class MyService2Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ObjectMapper mapper;   
    
	@Override
	public void init() throws ServletException {
		mapper = new ObjectMapper();
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getOutputStream()
			.write(mapper.writeValueAsBytes(new Customer(
							"Nume1", 1234, null)));
		} catch (JsonProcessingException e) {
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		} 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
