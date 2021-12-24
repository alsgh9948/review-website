package minho.review.user.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter
public class RoleConverter implements AttributeConverter<Role, String> {
    @Override
    public String convertToDatabaseColumn(Role role) {
        if (role == null){
            return "1";
        }
        else {
            return role.code;
        }
    }

    @Override
    public Role convertToEntityAttribute(String code) {
        return Stream.of(Role.values())
                .filter(r -> r.code.equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
