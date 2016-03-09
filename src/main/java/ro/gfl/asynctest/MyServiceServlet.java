package ro.gfl.asynctest;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class MainServiceServlet
 */
@WebServlet(name = "MyService", urlPatterns = "/1", asyncSupported = true)
public class MyServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ObjectMapper mapper;

	private ExecutorService executor;
	

	@Override
	public void init() throws ServletException {
		mapper =  new ObjectMapper();
		executor = Executors.newFixedThreadPool(Runtime
				.getRuntime().availableProcessors()*2);

	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		final AsyncContext context = request.startAsync(request, response);
		context.addListener(new AsyncListener() {
		    /** complete() has already been called on the async context, nothing to do */
		    public void onComplete(AsyncEvent event) throws IOException { 
		    	// do nothing
		    }
		    /** timeout has occured in async task... handle it */
		    public void onTimeout(AsyncEvent event) throws IOException {
		      log("onTimeout called");
		      log(event.toString());
		      context.getResponse().getWriter().write("TIMEOUT");
		      context.complete();
		    }
		    /** THIS NEVER GETS CALLED - error has occured in async task... handle it */
		    public void onError(AsyncEvent event) throws IOException {
		      log("onError called");
		      log(event.toString());
		      context.getResponse().getWriter().write("ERROR");
		      context.complete();
		    }
		    /** async context has started, nothing to do */
		    public void onStartAsync(AsyncEvent event) throws IOException {
		    	// do nothing
		    }
		  });
		executor.execute(new Runnable() {

			@Override
			public void run() {
				try {
					HttpServletResponse response = (HttpServletResponse) context
							.getResponse();
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
				context.complete();
			}
		});
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		if(executor!=null){
			executor.shutdown();
		}
	}

}
