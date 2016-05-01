package lamp.admin.web.support.pagination;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Pagination implements Iterable<PaginationItem> {

    public static final String DEFAULT_PAGE_PARAM_NAME = "page";
    public static final String DEFAULT_SIZE_PARAM_NAME = "size";

    private String pageParamName = DEFAULT_PAGE_PARAM_NAME;
    private String sizeParamName = DEFAULT_SIZE_PARAM_NAME;

    private int paginationItemSize = 10;

    private int currentPage;
    private int totalPages;
    private int startPage;
    private int totalSize;

    private int prevOffsetPage;
    private int nextOffsetPage;

    private List<PaginationItem> items = new ArrayList<>();

    public Pagination(String url, Page<?> page) {

        currentPage = page.getNumber() + 1;
        totalPages = page.getTotalPages();
        startPage = (((currentPage - 1) / paginationItemSize) * paginationItemSize) + 1;
        totalSize = startPage + (paginationItemSize - 1) > totalPages ? totalPages : startPage + (paginationItemSize - 1);

        prevOffsetPage = startPage - 1;
        nextOffsetPage = totalSize + 1;

        String paginationUrl = url;
        if (paginationUrl.indexOf('?') > -1) {
            paginationUrl = paginationUrl + "&" + pageParamName + "=";
        } else {
            paginationUrl = paginationUrl + "?" + pageParamName + "=";
        }


        for (int i = startPage; i <= totalSize; i++) {
            PaginationItem item = new PaginationItem(i, i == currentPage, paginationUrl + (i-1) + "&" + sizeParamName +  "=" + page.getSize());
            items.add(item);
        }
    }

    public boolean hasPrevOffsetPage() {
        return prevOffsetPage > 1 ? true : false;
    }

    public boolean hasNextOffsetPage() {
        return nextOffsetPage < totalPages ? true : false;
    }


    public boolean hasFirstPage() {
        return currentPage > 1;
    }

    public boolean hasLastPage() {
        return currentPage < totalPages;
    }

    @Override
    public Iterator<PaginationItem> iterator() {
        return items.iterator();
    }

    public static Pagination of(String url, Page<?> page) {
        return new Pagination(url, page);
    }
}
