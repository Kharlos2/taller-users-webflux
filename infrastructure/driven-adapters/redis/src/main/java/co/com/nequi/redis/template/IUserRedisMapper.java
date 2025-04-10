package co.com.nequi.redis.template;

import co.com.nequi.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserRedisMapper {

    User toUser(UserRedis userJson);
    UserRedis toUserJson(User user);
}
