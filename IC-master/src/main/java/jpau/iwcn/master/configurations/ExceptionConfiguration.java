package jpau.iwcn.master.configurations;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mongodb.MongoException;
import com.mongodb.MongoSocketOpenException;

import jpau.iwcn.master.models.CommonResponse;

@ControllerAdvice
public class ExceptionConfiguration {
	
	@ExceptionHandler(MongoException.class)
	public ResponseEntity<CommonResponse<Void>> mongoException(HttpServletRequest request,
			MongoException e) {
		return new ResponseEntity<>(CommonResponse.error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(MongoSocketOpenException.class)
	public ResponseEntity<CommonResponse<Void>> mongoSocketOpenException(HttpServletRequest request,
			MongoSocketOpenException e) {
		return new ResponseEntity<>(CommonResponse.error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<CommonResponse<Void>> exception(HttpServletRequest request,
			Exception e) {
		CommonResponse<Void> response = CommonResponse.error(e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
