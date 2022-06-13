package com.pusilkom.ess.dto.form.search;

import com.google.common.base.CaseFormat;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.List;

/**
 * Created by fahri on 1/16/17.
 */
public class BaseTableSearchForm {
    private String sort = "";

    private Long from = -1L;
    private Long page = -1L;
    private Long per_page = -1L;

    private List<ColumnDef> sortColumnDefs = Lists.newArrayList();

    private List<ColumnDef> appendedRawSortColumnDefs = Lists.newArrayList();
    private List<ColumnDef> appendedSortColumnDefs = Lists.newArrayList();

    private List<ColumnDef> prependedRawSortColumnDefs = Lists.newArrayList();
    private List<ColumnDef> prependedSortColumnDefs = Lists.newArrayList();

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Long getFrom() {
        this.from = (this.page - 1) * this.per_page + 1;

        return from;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getPerPage() {
        return per_page;
    }

    public void setPer_page(Long per_page) {
        this.per_page = per_page;
    }

    public List<ColumnDef> getSortColumnDefs() {
        sortColumnDefs = Lists.newArrayList();

        for(String columns : Splitter.on(',')
                .trimResults()
                .omitEmptyStrings().split(this.sort)) {
            String[] columnSplit = columns.split("\\|");
            sortColumnDefs.add(new ColumnDef(columnSplit[0], SortDirection.valueOf(columnSplit[1].toUpperCase())));
        }

        return sortColumnDefs;
    }

    public void appendSort(ColumnDef columnDef) {
        appendedSortColumnDefs.add(columnDef);
    }

    public List<ColumnDef> getAppendedSortColumnDefs() {
        return appendedSortColumnDefs;
    }

    public void prependSort(ColumnDef columnDef) {
        prependedSortColumnDefs.add(columnDef);
    }

    public List<ColumnDef> getPrependedSortColumnDefs() {
        return prependedSortColumnDefs;
    }

    private String convertToOrderSyntax(ColumnDef columnDef) {
        String toUpperUnderscoreColumnName = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, columnDef.getName());

        String normalizedColumnName = toUpperUnderscoreColumnName;

        return String.format(" %s  %s ",
                normalizedColumnName,
                columnDef.getSortDirection());
    }

    public String getOrderQuery() {

        StringBuilder queryBuilder = new StringBuilder();

        if (StringUtils.hasText(this.sort)) {

            List<String> orderParams = Lists.newArrayList();
            queryBuilder.append(" ORDER BY ");

            for (ColumnDef columnDef : prependedSortColumnDefs) {
                orderParams.add(this.convertToOrderSyntax(columnDef));
            }

            for (ColumnDef columnDef : this.getSortColumnDefs()) {
                orderParams.add(this.convertToOrderSyntax(columnDef));
            }

            for (ColumnDef columnDef : appendedSortColumnDefs) {
                orderParams.add(this.convertToOrderSyntax(columnDef));
            }

            Iterator<String> itr2 = orderParams.iterator();
            while (itr2.hasNext()) {
                queryBuilder.append(itr2.next());
                if (itr2.hasNext()) {
                    queryBuilder.append(" , ");
                }
            }
        }

        return queryBuilder.toString();
    }
}
