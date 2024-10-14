package web2.man.models.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import web2.man.enums.UserRole;

import java.util.UUID;

@AllArgsConstructor
@Data
public class UserDescription {
    UUID userId;
    UserRole userRole;
}
