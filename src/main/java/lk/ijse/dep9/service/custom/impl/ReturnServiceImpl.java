package lk.ijse.dep9.service.custom.impl;

import lk.ijse.dep9.dao.DAOFactory;
import lk.ijse.dep9.dao.DAOTypes;
import lk.ijse.dep9.dao.custom.MemberDAO;
import lk.ijse.dep9.dao.custom.QueryDAO;
import lk.ijse.dep9.dao.custom.ReturnDAO;
import lk.ijse.dep9.dto.ReturnDTO;
import lk.ijse.dep9.dto.ReturnItemDTO;
import lk.ijse.dep9.entity.Return;
import lk.ijse.dep9.entity.ReturnPK;
import lk.ijse.dep9.service.custom.ReturnService;
import lk.ijse.dep9.service.exceptions.AlreadyReturnedException;
import lk.ijse.dep9.service.exceptions.NotFoundException;
import lk.ijse.dep9.service.util.Converter;
import lk.ijse.dep9.service.util.Executor;
import lk.ijse.dep9.util.ConnectionUtil;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;

public class ReturnServiceImpl implements ReturnService {
    private ReturnDAO returnDAO;
    private MemberDAO memberDAO;
    private final QueryDAO queryDAO;

    private Converter converter;

    public ReturnServiceImpl() {
        queryDAO= DAOFactory.getInstance().getDAO(ConnectionUtil.getConnection(), DAOTypes.QUERY);
    }

    @Override
    public void updateReturnStatus(ReturnDTO returnDTO) throws NotFoundException {
        var returnItems = new HashSet<>(returnDTO.getReturnItems());
        try {
            ConnectionUtil.getConnection().setAutoCommit(false);

            returnDTO.getReturnItems().forEach(
                    returnItem -> {
                if(!queryDAO.isValidIssueItem(returnDTO.getMemberId(),
                        returnItem.getIssueNoteId(),
                        returnItem.getIsbn())) throw  new NotFoundException("Invalid Return");

                if(returnDAO.existsById(new ReturnPK(returnItem.getIssueNoteId(),returnItem.getIsbn()))){
                    throw new AlreadyReturnedException("item has been returned already");
                }
                        Return returnEntity = converter.toReturn(returnItem);
                returnEntity.setDate(Date.valueOf(LocalDate.now()));
                        returnDAO.save(returnEntity);
            });

            ConnectionUtil.getConnection().commit();
        } catch (Throwable t) {
            Executor.execute(ConnectionUtil.getConnection()::rollback);
        }finally {
            Executor.execute(()->ConnectionUtil.getConnection().setAutoCommit(true));

        }


    }
}
