package com.example.crm.services;

import com.example.crm.wrappers.request.AuthenticationRequest;
import com.example.crm.wrappers.request.DataForRegister;
import com.example.crm.wrappers.response.AuthenticationResponse;

public interface RegisterService {
    AuthenticationResponse register(DataForRegister data);
    AuthenticationResponse authentication(AuthenticationRequest request);
}
