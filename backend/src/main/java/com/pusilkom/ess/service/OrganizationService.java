package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.cmd.OrganizationCmd;
import com.pusilkom.ess.dto.form.search.OrganizationSearchForm;
import com.pusilkom.ess.dto.table.OrganizationItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.eventbus.publisher.AsyncEventPublisher;
import com.pusilkom.ess.model.Organization;
import com.pusilkom.ess.model.OrganizationExample;
import com.pusilkom.ess.model.mapper.InstitusiMapper;
import com.pusilkom.ess.model.mapper.OrganizationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fahri on 1/7/17.
 */


@Service
@Transactional(readOnly = true)
public class OrganizationService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired InstitusiMapper institusiMapper;
    @Autowired
    OrganizationMapper organizationMapper;
    @Autowired AsyncEventPublisher asyncEventPublisher;

    public Organization getById(Integer id) {
        return organizationMapper.selectByPrimaryKey(id);
    }

    @Transactional(readOnly = false)
    public void saveCmd(OrganizationCmd organizationCmd) {
        //business logic di sini

        this.save(organizationCmd.toOrganization());
    }

    @Transactional(readOnly = false)
    public void delete(Integer id) {
        OrganizationExample ex = new OrganizationExample();
        ex.createCriteria().andParentIdEqualTo(id.intValue());
        List<Organization> organizations = organizationMapper.selectByExample(ex);

        for (Organization organization : organizations){
            organization.setParentId(null);
            organizationMapper.updateByPrimaryKey(organization);
        }

        organizationMapper.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly = false)
    public void save(Organization organization) {
        if (organization.getId() == null) {
            organizationMapper.insertSelective(organization);
        } else {
            organizationMapper.updateByPrimaryKey(organization);
        }
    }

    @Transactional(readOnly = false)
    public List<Organization> getAll() {
        OrganizationExample ex = new OrganizationExample();
        ex.createCriteria();
        return organizationMapper.selectByExample(ex);
    }


    @Transactional(readOnly = false)
    public List<Organization> getAllPossibleParents(Long id) {

        OrganizationExample ex = new OrganizationExample();
        ex.createCriteria().andIdNotEqualTo(id.intValue());
        List<Organization> organizations = organizationMapper.selectByExample(ex);
        List<Organization> possibleParents = new ArrayList<Organization>();
        for(Organization organizationEx : organizations){
            if(!isParent(id.intValue(), organizationEx)){
                possibleParents.add(organizationEx);
            }
        }

        return possibleParents;
    }


    private Boolean isParent(Integer id, Organization org){
        if(org.getParentId() == id){
            return true;
        } else if(org.getParentId() == null){
            return false;
        } else {
            return isParent(id, organizationMapper.selectByPrimaryKey(org.getParentId()));
        }
    }


    public boolean hasDuplicateOrganizationByOrganizationCmd(OrganizationCmd organizationCmd) {
        boolean isEdit = (organizationCmd.getId() != null);

        OrganizationExample organizationExample = new OrganizationExample();
        OrganizationExample.Criteria criteria = organizationExample.createCriteria();
        criteria.andNameEqualTo(organizationCmd.getName());

        if(isEdit) {
            criteria.andIdNotEqualTo(organizationCmd.getId());
        }

        List<Organization> list = organizationMapper.selectByExample(organizationExample);

        return !list.isEmpty();
    }

    public boolean hasNotParentIdByOrganizationCmd(OrganizationCmd organizationCmd) {
        if(organizationCmd.getParentId() == null) {
            return true;
        }
        OrganizationExample organizationExample = new OrganizationExample();
        OrganizationExample.Criteria criteria = organizationExample.createCriteria();
        criteria.andIdEqualTo(organizationCmd.getParentId());

        List<Organization> list = organizationMapper.selectByExample(organizationExample);

        return list.isEmpty();
    }

    public TablePagination<OrganizationItem> getTablePagination(OrganizationSearchForm organizationSearchForm) {
        List<OrganizationItem> listItem = organizationMapper.getListOrganizationItemBySearchForm(organizationSearchForm);
        Long totalItemFiltered = organizationMapper.getTotalOrganizationItemBySearchForm(organizationSearchForm);

        return new TablePagination<OrganizationItem>(organizationSearchForm, listItem, totalItemFiltered);
    }

    @Transactional(readOnly = false)
    public List<Organization> getAllOrganization() {
        return organizationMapper.selectAllOrganization();
    }
}
