package lamp.admin.web.support.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class PaginationItem {

    private int page;
    private boolean current;
    private String url;

}
