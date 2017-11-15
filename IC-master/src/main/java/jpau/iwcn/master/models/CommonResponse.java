package jpau.iwcn.master.models;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonResponse <T> {
	
	private boolean success;

    private T result;
    
    private List<String> errors;
    
    public static <T> CommonResponse<T> success(T result) {
        return new CommonResponse<>(true, result, null);
    }
    
    public static <T> CommonResponse<T> error(String error) {
        List<String> errors = new ArrayList<>();
        errors.add(error);
        return new CommonResponse<>(false, null, errors);
    }

}
