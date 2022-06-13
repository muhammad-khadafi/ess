package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.cmd.LetterCmd;
import com.pusilkom.ess.dto.form.search.LevelSearchForm;
import com.pusilkom.ess.dto.form.search.ListSuratForm;
import com.pusilkom.ess.dto.table.LetterItem;
import com.pusilkom.ess.dto.table.LevelRateItem;
import com.pusilkom.ess.dto.table.PettyCashItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.Letter;
import com.pusilkom.ess.model.PettyCash;
import com.pusilkom.ess.model.TluLetter;
import com.pusilkom.ess.model.TluLetterExample;
import com.pusilkom.ess.model.mapper.LetterMapper;
import com.pusilkom.ess.model.mapper.TluLetterMapper;
import com.pusilkom.ess.util.CommonUtil;
import com.pusilkom.ess.util.DebugUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by fahri on 1/7/17.
 */


@Service
@Transactional(readOnly = true)
public class LetterService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LetterMapper letterMapper;
    @Autowired
    DebugUtil d;

    @Autowired
    TluLetterMapper tluLetterMapper;
    @Transactional(readOnly = false)
    public List<LetterItem> getKeteranganLetter() {

        // 3) Query ada di Mapper XML. Lokasi : src/main/resources/com/pusilkom/euis/model/mapper/custom/InstitusiMapper.xml
        // Perhatikan sekali lagi, mapper xml utk custom query ada di folder 'custom'
        return letterMapper.getKeteranganLetter();
    }

    @Transactional(readOnly = false)
    public List<LetterItem> getCategoryLetter() {

        // 3) Query ada di Mapper XML. Lokasi : src/main/resources/com/pusilkom/euis/model/mapper/custom/InstitusiMapper.xml
        // Perhatikan sekali lagi, mapper xml utk custom query ada di folder 'custom'
        return letterMapper.getCategoryLetter();
    }

    @Transactional(readOnly = false)
    public LetterItem getKeteranganId() {

        // 3) Query ada di Mapper XML. Lokasi : src/main/resources/com/pusilkom/euis/model/mapper/custom/InstitusiMapper.xml
        // Perhatikan sekali lagi, mapper xml utk custom query ada di folder 'custom'
        return letterMapper.getKeteranganId();
    }
    public TablePagination<LetterItem> getTablePagination(ListSuratForm listSuratForm) {
        List<LetterItem> listItem = letterMapper.getListLetter(listSuratForm);
        Long totalItemFiltered = letterMapper.getTotalLetterListBySearchForm(listSuratForm);
        return new TablePagination<LetterItem>(listSuratForm, listItem, totalItemFiltered  );
    }
   //10L
    @Transactional(readOnly = false)
    public void saveCmd(LetterCmd letter) {
        Date date = new Date();
        Integer idLetter = null;
        boolean isNew = (letter.getId() == null);
        if(isNew){
            LetterItem lastRow = this.getLastRowLetter();
            log.info("*** LASTROW : {}", d.toString(lastRow));
           // Integer counter = 1;
            if (lastRow == null) {

            } else {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                int currentYear = calendar.get(Calendar.YEAR);
                calendar.setTime(lastRow.getCreated_date());
                int lastRowYear = calendar.get(Calendar.YEAR);
                if (currentYear - lastRowYear > 0) {
                    TluLetterExample ex = new TluLetterExample();
                    ex.createCriteria().andValueEqualTo(letter.getCategory());
                    List<TluLetter> listTluKtl = tluLetterMapper.selectByExample(ex);
                    for(TluLetter tl : listTluKtl){
                        tl.setCounter(0);
                        tluLetterMapper.updateByPrimaryKey(tl);
                        System.out.println("RESET COUNTER LETTER at : " + new Date());
                        System.out.println("Current Year : " + currentYear);
                        System.out.println("Last Row Year : " + lastRowYear);
                    }
                    //    counter = ex. + 1;
                }
            }
            TluLetterExample x = new TluLetterExample();
            x.createCriteria().andValueEqualTo(letter.getCategory());
            TluLetter tlu = tluLetterMapper.selectByExample(x).get(0);
           // log.info("*** cektlu : {}", d.toString(tlu));
            String awal = "00";
            Integer tengah = tlu.getCounter() + 1;
            letter.setSerialNumber(awal.substring(0, (awal.length() - (Integer.toString(tengah).length())) + 1)+ tengah + "/" + "PIK-UI"  + "/" + letter.getCategory() + "/" + (date.getYear() + 1900));
            letterMapper.insertSelective(letter.toLetter());
            tlu.setCounter(tengah);
          //  System.out.println(tlu);
            tluLetterMapper.updateByPrimaryKey(tlu);
          //  System.out.println(tlu);
//            String awal = "10000";
//            Integer tengah = letterMapper.getKeteranganId().getId();
//            letter.setSerialNumber("000" + tengah + "-" + "PIK_UI"  + "-" + letter.getCategory() + (date.getYear() + 1900));
        }
    }

    public LetterItem getLastRowLetter(){
        LetterItem lastRow = letterMapper.getLastRowLetter();
        return lastRow;
    }

}