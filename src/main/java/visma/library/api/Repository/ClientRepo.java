package visma.library.api.Repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class ClientRepo {

    @Value("${clients}")
    private  String path;
}
