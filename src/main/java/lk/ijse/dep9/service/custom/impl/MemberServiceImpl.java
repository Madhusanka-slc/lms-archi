package lk.ijse.dep9.service.custom.impl;

import lk.ijse.dep9.dao.DAOFactory;
import lk.ijse.dep9.dao.DAOTypes;
import lk.ijse.dep9.dao.custom.MemberDAO;
import lk.ijse.dep9.dao.exception.ConstraintViolationException;
import lk.ijse.dep9.dto.MemberDTO;
import lk.ijse.dep9.entity.Member;
import lk.ijse.dep9.service.custom.MemberService;
import lk.ijse.dep9.service.exceptions.DuplicateException;
import lk.ijse.dep9.service.exceptions.InUseException;
import lk.ijse.dep9.service.exceptions.NotFoundException;
import lk.ijse.dep9.service.util.Converter;
import lk.ijse.dep9.util.ConnectionUtil;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MemberServiceImpl implements MemberService {

    private MemberDAO memberDAO;

    private final Converter converter;

    public MemberServiceImpl() {
        this.memberDAO = DAOFactory.getInstance().getDAO(ConnectionUtil.getConnection(), DAOTypes.MEMBER);
        this.converter = new Converter();
    }

    @Override
    public void signupMember(MemberDTO member) throws DuplicateException {
        if (memberDAO.existsByContact(member.getContact())) {
            throw new DuplicateException("Member Contact is Already Exists");

        }
        member.setId(UUID.randomUUID().toString());

        memberDAO.save(converter.toMember(member));

    }

    @Override
    public void updateMember(MemberDTO member) throws NotFoundException {
        if (!memberDAO.existsById(member.getId())) {
            throw new NotFoundException("Member doesn't exits");
        }
        memberDAO.update(converter.toMember(member));

    }

    @Override
    public void removeMemberAccount(String memberId) throws NotFoundException, InUseException {
        if (!memberDAO.existsById(memberId)) {
            throw new NotFoundException("Member doesn't exists");
        }

        try {
            memberDAO.deleteById(memberId);
        } catch (ConstraintViolationException e) {
            throw new InUseException("Member details are still in use",e);
        }


    }

    @Override
    public MemberDTO getMemberDetails(String memberId) throws NotFoundException {
        return memberDAO.findById(memberId).
                map(converter::toMemberDTO).orElseThrow(()->new NotFoundException("Member doesn't exist"));
    }

    @Override
    public List<MemberDTO> findMembers(String query, int size, int page) {

        List<Member> memberEntityList = memberDAO.findMembersByQuery(query, size, page);
        return memberEntityList.stream().map(converter::toMemberDTO).collect(Collectors.toList());

    }
}
