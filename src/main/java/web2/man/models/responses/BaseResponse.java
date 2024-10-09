package web2.man.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor //resposta sem dados e sem erro
public class BaseResponse {
    String errorMessage;
    Object data;

    //resposta de erro
    public BaseResponse(String errorMessage) {
        this.setErrorMessage(errorMessage);
    }
    //resposta de dados
    public BaseResponse(Object data) {
        this.data = data;
    }
}
