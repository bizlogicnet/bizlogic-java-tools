package com.bizlogic.tools.functions;

import java.util.Map;

import com.bizlogic.tools.model.PageSort;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class DBTools {

  /* FROM BACK
  public static Sort buildSort(String field, String sort) {
    Sort _sort = Sort.by(StringUtils.isBlank(sort) ? Sort.Direction.ASC : Sort.Direction.fromString(sort.toUpperCase()),
        StringUtils.isNotBlank(field) ? "id" : field);
    return _sort;
  }

  public static Pageable buildPageableSort(Integer page, Integer limit, String field, String sort) {
    Sort _sort = StringUtils.isNotBlank(field) ? buildSort(field, sort) : null;
    Pageable _page = page != null & limit != null
        ? _sort != null ? PageRequest.of(page - 1, limit, _sort) : PageRequest.of(page - 1, limit)
        : null;
    return _page;
  }
  */

  public static Sort buildSort(String field, String sort) {
    Direction _direction = StringUtils.isBlank(sort) ? Sort.Direction.ASC
        : Sort.Direction.fromString(sort.toUpperCase());
    String _field = StringUtils.isNotBlank(field) ? field : "id";
    Sort _sort = Sort.by(_direction, _field);
    return _sort;
  }

  public static Pageable buildPageableSort(Integer page, Integer limit, String field, String sort) {
    Sort _sort = StringUtils.isNotBlank(field) ? buildSort(field, sort) : buildSort("id", "asc");
    Pageable _page = page != null & limit != null
        ? _sort != null ? PageRequest.of(page - 1, limit, _sort) : PageRequest.of(page - 1, limit)
        : _sort != null ? PageRequest.of(0, 1000000, _sort) : null;
    return _page;
  }

  
  

  

  public static Pageable buildPageSort(PageSort pageSort) {
    return buildPageableSort(pageSort.getPage(), pageSort.getLimit(), pageSort.getField(), pageSort.getSort());
  }

  public static Pageable mapPageSort(Map<String, String> params) {
    PageSort pageSort = new PageSort();
    pageSort.setPage(BasicTools.valI(params.get("page")));
    pageSort.setLimit(BasicTools.valI(params.get("limit")));
    pageSort.setSort(params.get("sort"));
    pageSort.setField(params.get("field"));
    return buildPageSort(pageSort);
  }

  

}
