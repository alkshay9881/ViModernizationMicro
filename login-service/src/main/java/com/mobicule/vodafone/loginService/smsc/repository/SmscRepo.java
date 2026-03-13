package com.mobicule.vodafone.loginService.smsc.repository;

import com.mobicule.vodafone.loginService.smsc.model.SMPPDetailsMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmscRepo extends JpaRepository<SMPPDetailsMapping,Long> {



}
