package lk.ijse.dep9.service.custom;

import lk.ijse.dep9.dto.IssueNoteDTO;
import lk.ijse.dep9.entity.IssueNote;
import lk.ijse.dep9.service.SuperService;
import lk.ijse.dep9.service.exceptions.AlreadyIssuedException;
import lk.ijse.dep9.service.exceptions.LimitExceedException;
import lk.ijse.dep9.service.exceptions.NotAvailableException;
import lk.ijse.dep9.service.exceptions.NotFoundException;

public interface IssueService extends SuperService {
    void placeNewIssueNote(IssueNoteDTO issueNoteDTO) throws NotFoundException,
            NotAvailableException, LimitExceedException, AlreadyIssuedException;
}
