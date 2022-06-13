package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.EmployeeContractSearchForm;
import com.pusilkom.ess.dto.form.search.LevelSearchForm;
import com.pusilkom.ess.dto.form.search.ListSuratForm;
import com.pusilkom.ess.dto.table.*;
import com.pusilkom.ess.model.EmployeeContract;
import com.pusilkom.ess.model.EmployeeContractExample;
import com.pusilkom.ess.model.Letter;
import com.pusilkom.ess.model.LetterExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LetterMapper extends BaseMapper<Letter, Integer, LetterExample> {
    List<LetterItem> getKeteranganLetter();
    LetterItem getKeteranganId();
    List<LetterItem> getListLetter(@Param("listSuratForm") ListSuratForm listSuratForm);
    Long getTotalLetterListBySearchForm(@Param("listSuratForm") ListSuratForm listSuratForm);
    List<LetterItem> getCategoryLetter();
    @Select("select l.id , l.created_date, tl.counter " +
            "from letter l left join tlu_letter tl on l.category = tl.value " +
            " order by l.id desc limit 1")
    LetterItem getLastRowLetter();
}
