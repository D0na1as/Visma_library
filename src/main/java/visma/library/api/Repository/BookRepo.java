package visma.library.api.Repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepo {

    @Value("${library}")
    private  String path;
}
