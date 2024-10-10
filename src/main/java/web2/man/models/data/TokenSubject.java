package web2.man.models.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import web2.man.enums.UserRole;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@Data
public class TokenSubject implements Serializable {
    UUID userId;
    UserRole userRole;
}
