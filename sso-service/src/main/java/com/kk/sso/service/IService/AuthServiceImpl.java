package com.kk.sso.service.IService;

import com.kk.sso.model.RecordToken;
import com.kk.sso.request.LoginReq;
import com.kk.sso.request.RegisterReq;
import org.springframework.security.access.prepost.PreAuthorize;

public interface AuthServiceImpl {
    RecordToken login(LoginReq req);
    @PreAuthorize("@func.apply(#req)")
    RecordToken register(RegisterReq req);
}
