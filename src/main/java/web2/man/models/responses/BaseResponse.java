package web2.man.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor //resposta sem dados e sem erro
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {
    String errorMessage;
    Object data;

    //resposta de erro
    public BaseResponse(String errorMessage) {
        this.setErrorMessage(errorMessage);
    }
    //resposta de dados
    public BaseResponse(Object data) {
        this.setData(data);
    }
}
