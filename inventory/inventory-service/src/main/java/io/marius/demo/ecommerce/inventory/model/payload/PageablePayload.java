package io.marius.demo.ecommerce.inventory.model.payload;

import java.util.Objects;
import java.util.Optional;
import org.springframework.data.domain.OffsetScrollPosition;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageablePayload implements Pageable {
  //    page: number,
  //    itemsPerPage: number,
  //    sortBy: string[],
  //    sortDesc: boolean[],
  //    groupBy: string[],
  //    groupDesc: boolean[],
  //    multiSort: boolean,
  //    mustSort: boolean

  private final int page;
  private final int itemsPerPage;
  private final Sort sort;

  public PageablePayload(int page, int itemsPerPage, Sort sort) {
    this.page = page;
    this.itemsPerPage = itemsPerPage;
    this.sort = sort;
  }

  @Override
  public boolean isPaged() {
    return Pageable.super.isPaged();
  }

  @Override
  public boolean isUnpaged() {
    return Pageable.super.isUnpaged();
  }

  @Override
  public int getPageNumber() {
    return page;
  }

  @Override
  public int getPageSize() {
    return itemsPerPage;
  }

  @Override
  public long getOffset() {
    return (long) page * itemsPerPage;
  }

  @Override
  public Sort getSort() {
    return sort;
  }

  @Override
  public Sort getSortOr(Sort sort) {
    return Pageable.super.getSortOr(sort);
  }

  @Override
  public Pageable next() {
    return new PageablePayload(page + 1, itemsPerPage, sort);
  }

  @Override
  public Pageable previousOrFirst() {
    return page == 0 ? this : new PageablePayload(page - 1, itemsPerPage, sort);
  }

  @Override
  public Pageable first() {
    return new PageablePayload(0, itemsPerPage, sort);
  }

  @Override
  public Pageable withPage(int pageNumber) {
    return PageRequest.of(pageNumber, itemsPerPage);
  }

  @Override
  public boolean hasPrevious() {
    return page > 0;
  }

  @Override
  public Optional<Pageable> toOptional() {
    return Pageable.super.toOptional();
  }

  @Override
  public OffsetScrollPosition toScrollPosition() {
    return Pageable.super.toScrollPosition();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PageablePayload that = (PageablePayload) o;
    return page == that.page
        && itemsPerPage == that.itemsPerPage
        && Objects.equals(sort, that.sort);
  }

  @Override
  public int hashCode() {
    return Objects.hash(page, itemsPerPage, sort);
  }
}
