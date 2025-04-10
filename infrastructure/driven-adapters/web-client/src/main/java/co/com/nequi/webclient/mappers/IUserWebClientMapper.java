package co.com.nequi.webclient.mappers;

import co.com.nequi.model.user.User;
import co.com.nequi.webclient.dto.Data;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserWebClientMapper {


    @Mapping(source = "data.id", target = "idApi")
    @Mapping(source = "data.email", target = "email")
    @Mapping(source = "data.first_name", target = "firstName")
    @Mapping(source = "data.last_name", target = "lastName")
    @Mapping(source = "data.avatar", target = "avatar")
    User toUser(Data data);

}
