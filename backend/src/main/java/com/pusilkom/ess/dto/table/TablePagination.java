package com.pusilkom.ess.dto.table;

import com.pusilkom.ess.dto.form.search.BaseTableSearchForm;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by fahri on 1/17/17.
 */
public class TablePagination<T> {

    private final List<T> data;
    private final Pagination pagination;

    public TablePagination(BaseTableSearchForm baseTableSearchForm, List<T> data, Long total) {
        this.data = data;
        this.pagination = new Pagination(total, baseTableSearchForm.getPerPage(), baseTableSearchForm.getPage());
    }

    public List<T> getData() {
        return data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    class Pagination {
        private Long per_page;
        private Long current_page;
        private Long from;
        private Long last_page;
        private Long to;
        private Long total;

        Pagination(Long total, Long perPage, Long currentPage) {
            this.per_page = perPage;
            this.total = total;
            this.current_page = currentPage;

            this.from = (this.current_page - 1) * perPage + 1;

            this.to = this.total < perPage ? this.total : this.getFrom() + perPage - 1;

            BigDecimal totalBD = BigDecimal.valueOf(total);
            BigDecimal perPageDB = BigDecimal.valueOf(perPage);

            this.last_page = totalBD.divide(perPageDB, 0, BigDecimal.ROUND_UP).longValue();
        }

        public Long getPer_page() {
            return per_page;
        }

        public Long getCurrent_page() {
            return current_page;
        }

        public Long getFrom() {
            return from;
        }

        public Long getLast_page() {
            return last_page;
        }

        public Long getTo() {
            return to;
        }

        public Long getTotal() {
            return total;
        }
    }
}
