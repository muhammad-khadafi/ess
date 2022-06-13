package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.ListSuratForm;
import com.pusilkom.ess.dto.table.LetterItem;
import com.pusilkom.ess.model.Letter;
import com.pusilkom.ess.model.LetterExample;
import com.pusilkom.ess.model.TluLetter;
import com.pusilkom.ess.model.TluLetterExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TluLetterMapper extends BaseMapper<TluLetter, Integer, TluLetterExample> {

}
