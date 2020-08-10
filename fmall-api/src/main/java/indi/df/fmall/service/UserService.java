package indi.df.fmall.service;

import indi.df.fmall.bean.UmsMember;
import indi.df.fmall.bean.UmsMemberReceiveAddress;

import java.util.List;

public interface UserService {

    List<UmsMember> getAllUser();

    List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId);

    UmsMember login(UmsMember umsMember);

    void addUserToken(String token, String memberId);

    public UmsMember addOauthUser(UmsMember umsMember);

    UmsMember checkOauthUser(UmsMember umsCheck);

    UmsMember getOauthUser(UmsMember umsMemberCheck);

    UmsMemberReceiveAddress getReceiveAddressById(String receiveAddressId);
}
