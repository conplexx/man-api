package web2.man.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import web2.man.enums.BaseResponseType;

@Data
@NoArgsConstructor //resposta sem dados e sem erro
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {
    String errorMessage;
    Object data;
    BaseResponseType type;

    //resposta de erro
    public BaseResponse(String errorMessage) {
        this.setErrorMessage(errorMessage);
        type = BaseResponseType.ERROR;
    }
    //resposta de dados
    public BaseResponse(Object data) {
        this.setData(data);
        type = BaseResponseType.DATA;
    }
}
