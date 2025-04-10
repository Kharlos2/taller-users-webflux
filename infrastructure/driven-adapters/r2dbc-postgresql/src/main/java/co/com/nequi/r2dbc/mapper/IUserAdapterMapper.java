package co.com.nequi.r2dbc.mapper;

import co.com.nequi.model.user.User;
import co.com.nequi.r2dbc.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserAdapterMapper {


    User toUser(UserEntity userEntity);

    UserEntity toUserEntity(User user);

}
