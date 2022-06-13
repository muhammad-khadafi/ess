package com.pusilkom.ess.dto.form.search;

import com.github.dandelion.datatables.core.ajax.ColumnDef;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BaseTableSearchFormDataTable {

    public static String STRING_SEMUA = "";
    public static Long LONG_SEMUA = -1L;
    Logger log = LoggerFactory.getLogger(this.getClass());

    private final String search = "";
    private final Integer start = -1;
    private final Integer length = -1;
    private final List<ColumnDef> columns = Lists.newArrayList();
    private final List<ColumnDef> order = Lists.newArrayList();
    private final Integer draw = -1;

    private List<ColumnDef> appendedAdditionalSortingColumnDefs = new ArrayList<ColumnDef>();
    private List<ColumnDef> appendedRawAdditionalSortingColumnDefs = new ArrayList<ColumnDef>();
    private List<ColumnDef> prependedAdditionalSortingColumnDefs = new ArrayList<ColumnDef>();
    private List<ColumnDef> prependedRawAdditionalSortingColumnDefs = new ArrayList<ColumnDef>();


    public void prependAdditionalSortingColumnDef(ColumnDef columnDef) {
        prependedAdditionalSortingColumnDefs.add(columnDef);
    }

    public void prependRawAdditionalSortingColumnDef(ColumnDef columnDef) {
        prependedRawAdditionalSortingColumnDefs.add(columnDef);
    }

    public void appendAdditionalSortingColumnDef(ColumnDef columnDef) {
        appendedAdditionalSortingColumnDefs.add(columnDef);
    }

    public void appendRawAdditionalSortingColumnDef(ColumnDef columnDef) {
        appendedRawAdditionalSortingColumnDefs.add(columnDef);
    }

    public void clearAdditionalSortingColumnDefs() {
        prependedAdditionalSortingColumnDefs.clear();
        prependedRawAdditionalSortingColumnDefs.clear();
        appendedAdditionalSortingColumnDefs.clear();
        appendedRawAdditionalSortingColumnDefs.clear();
    }

    public void clearSortingColumnDefs() {
        this.order.clear();
    }

    private String convertToOrderSyntax(String columnName, ColumnDef.SortDirection sortDirection) {
        String toUpperUnderscoreColumnName = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, columnName);

//        String normalizedColumnName = "";
        String normalizedColumnName = toUpperUnderscoreColumnName;
//        String normalizedColumnName = columnName;

//        for(int index = 0; index < toUpperUnderscoreColumnName.length(); index++){
//            char currentChar = toUpperUnderscoreColumnName.charAt(index);

//            if(CharMatcher.DIGIT.matches(currentChar)){
//                normalizedColumnName += '_';
//            }
//
//            normalizedColumnName += currentChar;
//        }

        return String.format(" %s  %s ",
                normalizedColumnName,
                sortDirection);

    }

    public String getOrderQuery() {

        StringBuilder queryBuilder = new StringBuilder();

        if (this.hasOneSortedColumn()
                || !prependedAdditionalSortingColumnDefs.isEmpty()
                || !appendedAdditionalSortingColumnDefs.isEmpty()) {

            List<String> orderParams = new ArrayList<String>();
            queryBuilder.append(" ORDER BY ");

            for (ColumnDef columnDef : prependedAdditionalSortingColumnDefs) {
                orderParams.add(convertToOrderSyntax(columnDef.getName(), columnDef.getSortDirection()));
            }

            for (ColumnDef columnDef : prependedRawAdditionalSortingColumnDefs) {
                orderParams.add(String.format(" %s  %s ", columnDef.getName(), columnDef.getSortDirection()));
            }

            for (ColumnDef columnDef : this.order) {
                orderParams.add(convertToOrderSyntax(columnDef.getName(), columnDef.getSortDirection()));
            }

            for (ColumnDef columnDef : appendedAdditionalSortingColumnDefs) {
                orderParams.add(convertToOrderSyntax(columnDef.getName(), columnDef.getSortDirection()));
            }

            for (ColumnDef columnDef : appendedRawAdditionalSortingColumnDefs) {
                orderParams.add(String.format(" %s  %s ", columnDef.getName(), columnDef.getSortDirection()));
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

    public Boolean hasOneSortedColumn() {
        return !order.isEmpty();
    }

}
